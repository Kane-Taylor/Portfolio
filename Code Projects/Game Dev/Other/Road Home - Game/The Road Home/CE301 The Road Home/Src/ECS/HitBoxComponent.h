#pragma once
#include <string>
#include <SDL.h>
#include "Component.h"
#include "../Texture.h"

class HitBoxComponent : public Component
{
//
public:
	//collider dimensions
	SDL_Rect col;
	//tags of colliding objects
	std::string tag;

	SDL_Texture* tex;
	SDL_Rect startR, endR;

	//access to movement
	MovementComponent* move;

	//constructor
	HitBoxComponent(std::string t)
	{
		tag = t;
	}

	//constructor for collider
	HitBoxComponent(std::string t, int xpos, int ypos, int size)
	{
		tag = t;
		//position of the collider
		col.x = xpos;
		col.y = ypos;
		col.h = col.w = size;
	}

	
	//adds transform 
	void init() override
	{
		//adds MovementComponent if it does not have one already
		if (!entity->hasComponent<MovementComponent>())
		{
			entity->addComponent<MovementComponent>();
		}

		move = &entity->getComponent<MovementComponent>();

		//code used to display tiles with collider using a png
		tex = Texture::LoadTexture("assets/ColliderTextureon.png");
		startR = { 0, 0, 32, 32 };
		endR = { col.x, col.y, col.w, col.h };

	}
	

	//updates the collision area for a entity (does not update terrain)
	void update() override
	{
		if (tag != "terrain")
		{
			col.x = static_cast<int>(move->pos.x);
			col.y = static_cast<int>(move->pos.y);
			col.w = move->width * move->scale;
			col.h = move->height * move->scale;
		}

		endR.x = col.x - Game::cam.x;
		endR.y = col.y - Game::cam.y;
	}

	//draw override
	void draw() override
	{
		Texture::Draw(tex, startR, endR, SDL_FLIP_NONE);
	}



};