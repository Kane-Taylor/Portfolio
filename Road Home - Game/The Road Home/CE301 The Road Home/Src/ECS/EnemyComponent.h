#pragma once

#include "EntityComponentSystem.h"
#include "Component.h"
#include "../Vectors.h"

class EnemyComponent : public Component
{
public:
	//constructor
	EnemyComponent() 
	{}
	//deconstructor
	~EnemyComponent()
	{}

	//overide to set the movement and velocity of object
	void init() override
	{

	}

private:
	//componants of a enemy
	MovementComponent* transform;



};
