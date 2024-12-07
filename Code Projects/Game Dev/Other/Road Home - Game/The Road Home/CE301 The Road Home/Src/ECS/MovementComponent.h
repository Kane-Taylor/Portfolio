#pragma once
#include "Component.h"
#include "../Vectors.h"

class MovementComponent : public Component
{
public:
	//used to set poistition and velecity of an entity
	Vectors pos;
	Vectors vel;

	//set defaults
	int height = 32;
	int width = 32;
	int scale = 1;
	int speed = 3;
	bool blocked = false;

	//used to update entities
	MovementComponent()
	{
		pos.Zero();
	}
	MovementComponent(int sc)
	{
		pos.Zero();
		scale = sc;
	}
	MovementComponent(float x, float y)
	{
		pos.Zero();
	}
	//used to transform a entity with corresponding values
	MovementComponent(float x, float y, int h, int w, int sc)
	{
		pos.x = x;
		pos.y = y;
		height = h;
		width = w;
		scale = sc;
	}

	//override to set velocity to 0
	void init() override
	{
		vel.Zero();
	}

	//overide to update an object to its new position 
	void update() override
	{
		pos.x += static_cast<int>(vel.x * speed);
		pos.y += static_cast<int>(vel.y * speed);
	}

};