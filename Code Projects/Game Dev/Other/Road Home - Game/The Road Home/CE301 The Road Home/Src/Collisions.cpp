#include "Collisions.h"
#include "ECS\HitBoxComponent.h"

//used to detect if a collision has taken place returning true or false (done using position and size)
bool Collisions::axisAlignedBoundingBox(const SDL_Rect& rectangleA, const SDL_Rect& rectangleB)
{
	if (
		rectangleA.x + rectangleA.w >= rectangleB.x &&
		rectangleB.x + rectangleB.w >= rectangleA.x &&
		rectangleA.y + rectangleA.h >= rectangleB.y &&
		rectangleB.y + rectangleB.h >= rectangleA.y
		)
	{
		return true;
	}

	return false;
}

//collider to detect if two entities are colliding that have the collider component
bool Collisions::axisAlignedBoundingBox(const HitBoxComponent& collisionA, const HitBoxComponent& collisionB)
{
	if (axisAlignedBoundingBox(collisionA.col, collisionB.col))
	{
		//used to test if collisions were working
		//std::cout << colA.tag << " hit: " << colB.tag << std::endl;
		return true;
	}
	else
	{
		return false;
	}
}