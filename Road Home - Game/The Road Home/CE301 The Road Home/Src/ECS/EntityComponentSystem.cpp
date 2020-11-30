#include "EntityComponentSystem.h"

//system used to group entities together in groups such as player, map etc.
void Entity::addGroup(Group mGroup)
{
	groupBitset[mGroup] = true;
	man.AddToGroup(this, mGroup);
}