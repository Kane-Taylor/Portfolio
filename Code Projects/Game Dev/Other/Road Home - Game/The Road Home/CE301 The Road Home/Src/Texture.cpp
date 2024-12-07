#include "Texture.h"

//used to load a texture from a surface
SDL_Texture* Texture::LoadTexture(const char* texture)
{
	SDL_Surface* tempSurf = IMG_Load(texture);
	SDL_Texture* tex = SDL_CreateTextureFromSurface(Game::ren, tempSurf);
	SDL_FreeSurface(tempSurf);
	
	return tex;
}

//renders a a texture onto an entity
void Texture::Draw(SDL_Texture * tex, SDL_Rect start, SDL_Rect end, SDL_RendererFlip flip)
{
	SDL_RenderCopyEx(Game::ren, tex, &start, &end, NULL, NULL, flip);
}
