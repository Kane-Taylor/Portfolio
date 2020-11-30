#pragma once

#include "Component.h"
#include "SDL.h"
#include "../Texture.h"
#include "Animations.h"
#include <map>
#include "../Assets.h"

class CharacterComponent : public Component
{
private:
	//pointers
	MovementComponent * move;
	SDL_Texture *tex;
	SDL_Rect startRect, endRect;

	//used to show if animated or not
	bool animated = false;
	int frames = 0;
	//delay between frames
	int speed = 100;

public:
	//sets y position to 0
	int animatedIndex = 0;
	//used to hold animations
	std::map<const char*, Animations> animations;
	//used to set if the sprite should fliped or not
	SDL_RendererFlip spriteFlip = SDL_FLIP_NONE;

	//used so that a sprite can use or not use animations
	CharacterComponent() = default;
	//constructor to that calls setTex which sets the texture
	CharacterComponent(std::string id)
	{
		setTex(id);
	}

	//constructor if animation exists for sprite
	CharacterComponent(std::string id, bool isAnimated)
	{
		//used to play construct the animations
		animated = isAnimated;

		Animations still = Animations(0, 2, 200);
		Animations move = Animations(1, 6, 100);

		animations.emplace("still", still);
		animations.emplace("move", move);

		Play("still");

		setTex(id);
	}

	//deconstructor
	~CharacterComponent()
	{
	}

	//sets texture of a sprite
	void setTex(std::string id)
	{
		tex = Game::assets->GetTexture(id);
	}

	//overide that initialises the entity 
	void init() override
	{
		move = &entity->getComponent<MovementComponent>();
		startRect.x = startRect.y = 0;
		startRect.w = move->width;
		startRect.h = move->height;
	}


	void update() override
	{
		//sets how the animation should be played if an animation exists
		if (animated)
		{
			startRect.x = startRect.w * static_cast<int>((SDL_GetTicks() / speed) % frames);
		}
		startRect.y = animatedIndex * move->height;

		//used to update game camera around the sprite
		endRect.x = static_cast<int>(move->pos.x - Game::cam.x);
		endRect.y = static_cast<int>(move->pos.y - Game::cam.y);
		//used to update where the entity is
		endRect.w = move->width * move->scale;
		endRect.h = move->height * move->scale;
	}

	//draws the texture
	void draw() override
	{
		Texture::Draw(tex, startRect, endRect, spriteFlip);
	}

	//used to set animations to play (such as walk for the player)
	void Play(const char* animName)
	{
		frames = animations[animName].numFrames;
		animatedIndex = animations[animName].id;
		speed = animations[animName].speed;
	}

};