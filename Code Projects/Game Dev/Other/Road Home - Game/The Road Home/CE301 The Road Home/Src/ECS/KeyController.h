#pragma once

#include "../TheRoadHome.h"
#include "EntityComponentSystem.h"
#include "Component.h"

class KeyController : public Component
{
public:
	//pointers
	MovementComponent *move;
	CharacterComponent *character;

	//overrides the movement and sprite (does this so player can move and animation can be played)
	void init() override
	{
		move = &entity->getComponent<MovementComponent>();
		character = &entity->getComponent<CharacterComponent>();
	}

	void update() override
	{
		//Key manager used to move player and play animation based of user input
		if (Game::event.type == SDL_KEYDOWN)
		{
			switch (Game::event.key.keysym.sym)
			{
			case SDLK_w:
				move->vel.y = -1;
				character->Play("move");
				break;
			case SDLK_a:
				move->vel.x = -1;
				character->Play("move");
				character->spriteFlip = SDL_FLIP_HORIZONTAL;
				break;
			case SDLK_d:
				move->vel.x = 1;
				character->Play("move");
				break;
			case SDLK_s:
				move->vel.y = 1;
				character->Play("move");
				break;
			default:
				break;
			}
		}
	
		//used to stop player moving once they are no longer pressing keys and plays default idle animation
		if (Game::event.type == SDL_KEYUP)
		{
			switch (Game::event.key.keysym.sym)
			{
			case SDLK_w:
				move->vel.y = 0;
				character->Play("still");
				break;
			case SDLK_a:
				move->vel.x = 0;
				character->Play("still");
				character->spriteFlip = SDL_FLIP_NONE;
				break;
			case SDLK_d:
				move->vel.x = 0;
				character->Play("still");
				break;
			case SDLK_s:
				move->vel.y = 0;
				character->Play("still");
				break;
			case SDLK_ESCAPE:
				Game::isRunning = false;
			default:
				break;
			}
		}
	}
};