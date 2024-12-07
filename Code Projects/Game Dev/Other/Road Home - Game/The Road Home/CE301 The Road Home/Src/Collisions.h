#pragma once
#include <SDL.h>

class HitBoxComponent;

class Collisions
{
public:
	//function to detect collision (access aligned bounding box used for tile map collisions)
	static bool axisAlignedBoundingBox(const SDL_Rect& rectangleA, const SDL_Rect& rectangleB);
	//function to detect collision using hitbox component (used to identify enitities)
	static bool axisAlignedBoundingBox(const HitBoxComponent& collisionA, const HitBoxComponent& collisionB);
};
