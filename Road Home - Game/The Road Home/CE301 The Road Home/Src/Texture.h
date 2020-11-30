#pragma once

#include "TheRoadHome.h"

class Texture {

public:
	//load and draw texture from a file
	static SDL_Texture* LoadTexture(const char* fileName);
	static void Draw(SDL_Texture* tex, SDL_Rect start, SDL_Rect end, SDL_RendererFlip flip);
};
