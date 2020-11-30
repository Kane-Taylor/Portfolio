#pragma once

#include "EntityComponentSystem.h"
#include "../Vectors.h"
#include "../TheRoadHome.h"
#include "../Texture.h"

class TileMapComponent : public Component
{
public:

	//pointers and setters
	SDL_Texture * tex;
	SDL_Rect startRect, endRect;
	Vectors pos;

	//constructor
	TileMapComponent() = default;

	//deconstructor
	~TileMapComponent()
	{
		SDL_DestroyTexture(tex);
	}

	//used to set the texture for each tile given the tiles position
	TileMapComponent(int srcX, int srcY, int xpos, int ypos, int tsize, int tscale, std::string id)
	{
		tex = Game::assets->GetTexture(id);

		startRect.x = srcX;
		startRect.y = srcY;
		startRect.w = startRect.h = tsize;
		pos.x = static_cast<float>(xpos);
		pos.y = static_cast<float>(ypos);
		endRect.w = endRect.h = tsize * tscale;
	}

	void update() override
	{
		endRect.x = static_cast<int>(pos.x - Game::cam.x);
		endRect.y = static_cast<int>(pos.y - Game::cam.y);
	}

	//draws texture onto the tile
	void draw() override
	{
		Texture::Draw(tex, startRect, endRect, SDL_FLIP_NONE);
	}
};