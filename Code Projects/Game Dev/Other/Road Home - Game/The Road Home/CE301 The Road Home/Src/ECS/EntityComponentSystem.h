#pragma once
#include <iostream>
#include <vector>
#include <memory>
#include <algorithm>
#include <bitset>
#include <array>

class Component;
class Entity;
class Manager;

//each component needs an id
using ComponentID = std::size_t;
//each group needs an id
using Group = std::size_t;

//used to return and store a component ID
inline ComponentID getNewComponentID()
{
	static ComponentID lastID = 0u;
	return lastID++;
}

//when component is passed typeid is assigned a set id
template <typename T> inline ComponentID getComponentID() noexcept
{
	static_assert (std::is_base_of<Component, T>::value, "");
	static ComponentID ID = getNewComponentID();
	return ID;
}


//set max size for components and groups
constexpr std::size_t maxComponentsSize = 32;
constexpr std::size_t maxGroupSize = 32;
//bitsets
using ComponentBitSet = std::bitset<maxComponentsSize>;
using GroupBitset = std::bitset<maxGroupSize>;

//array of components
using ComponentArray = std::array<Component*, maxComponentsSize>;

//base class for component
class Component
{
public: 
	//reference to its owner
	Entity* entity;

	virtual void init() {}
	virtual void update() {}
	virtual void draw() {}
	virtual ~Component() {}
};

//base class for Entities
class Entity
{
private:
	Manager& man;
	//used to track if entities are active
	bool active = true;
	//list of all components entities are holding
	std::vector<std::unique_ptr<Component>> components;

	ComponentArray componentArray;
	ComponentBitSet componentBitset;
	GroupBitset groupBitset;

public:
	//constructor using manager reference
	Entity(Manager& manager) : man(manager) {}

	// loops through all its components and calls update and draw for each
	void update()
	{
		for (auto& comp : components) comp->update();
	}
	void draw() 
	{
		for (auto& comp : components) comp->draw();
	}

	//sets entities as active
	bool isActive() const { return active; }
	//removes unactive entities
	void destroy() { active = false; }

	//passes in a group to return bool to see if it contains a certain bit
	bool hasGroup(Group group)
	{
		return groupBitset[group];
	}

	//used to add or remove from a group
	void addGroup(Group group);
	void delGroup(Group group)
	{
		groupBitset[group] = false;
	}


	//able to check if it has components
	template <typename T> bool hasComponent() const
	{
		return componentBitset[getComponentID<T>()];
	}


	//adds componants to entities
	template <typename T, typename... TArgs>
	T& addComponent(TArgs&&... args)
	{

		T* comp(new T(std::forward<TArgs>(args)...));
		comp->entity = this;
		std::unique_ptr<Component>uPtr { comp };
		components.emplace_back(std::move(uPtr));

		//puts componants in same position in the array and bitset
		componentArray[getComponentID<T>()] = comp;
		componentBitset[getComponentID<T>()] = true;

		//initialise and return componant
		comp->init();
		return *comp;
	}

	//gets component function using pointers
	template<typename T> T& getComponent() const
	{
		auto ptr(componentArray[getComponentID<T>()]);
		return *static_cast<T*>(ptr);
	}
};

//used to keep list of all entities
class Manager
{
private:
	//vector that will hold all entities
	std::vector<std::unique_ptr<Entity>> entities;
	//array of vectors (which are groups)
	std::array<std::vector<Entity*>, maxGroupSize> groupedEntities;
public:
	//update all entities
	void update()
	{
		for (auto& ent : entities) ent->update();
	}

	//draw all entities
	void draw()
	{
		for (auto& ent : entities) ent->draw();
	} 

	//refresh all entities (moves through entities and removes unused ones)
	void refresh()
	{
		//used to remove entities from groups
		for (auto i(0u); i < maxGroupSize; i++)
		{
			//move through each of the vectors (groups)
			auto& vec(groupedEntities[i]);
			vec.erase(
				std::remove_if(std::begin(vec), std::end(vec),
					[i](Entity* entity)
			{
				return !entity->isActive() || !entity->hasGroup(i);
			}),
				std::end(vec));
		}

		//code to remove non-active entities
		entities.erase(std::remove_if(std::begin(entities), std::end(entities),
			[](const std::unique_ptr<Entity> &mEntity)
		{
			return !mEntity->isActive();
		}),
			std::end(entities));
	}

	//add entity to a group
	void AddToGroup(Entity* entity, Group group)
	{
		groupedEntities[group].emplace_back(entity);
	}

	//used to get entities as a list (using vectors)
	std::vector<Entity*>& getGroup(Group group)
	{
		return groupedEntities[group];
	}

	//used to add an new entity
	Entity& addEntity()
	{
		Entity *ent = new Entity(*this);
		std::unique_ptr<Entity> uPtr { ent };
		entities.emplace_back(std::move(uPtr));
		return *ent;
	}
};