#pragma once

#include <map>
#include <string>
#include "Texture.h"
#include "Vectors.h"
#include "ECS\EntityComponentSystem.h"

class Assets
{
public:
	//constructor and deconstructor
	Assets(Manager* man);
	~Assets();

	//gameobjects
	void CreateEnemy(Vectors pos, std::string id);

	//texture management
	void AddTexture(std::string id, const char* path);
	SDL_Texture* GetTexture(std::string id);

private:

	Manager* manager;
	std::map<std::string, SDL_Texture*> textures;
};