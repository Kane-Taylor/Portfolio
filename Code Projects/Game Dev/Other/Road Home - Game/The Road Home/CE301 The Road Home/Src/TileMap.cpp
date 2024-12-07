#include "TileMap.h"
#include "TheRoadHome.h"
#include <fstream>
#include "ECS\EntityComponentSystem.h"
#include "ECS\Component.h"

extern Manager man;

//constructor
TileMap::TileMap(std::string i, int m, int t) : texID(i), mapScale(m), tileSize(t)
{
	scaledSize = m * t;
}

//deconstructor
TileMap::~TileMap()
{
}


////used to load map
void TileMap::LoadMap(std::string path, int sizeX, int sizeY)
{
	//used to get a number from array in file
	char num;
	//used to open and store the file that contains the map grid
	std::fstream mapFile;
	mapFile.open(path);

	//int for x and y position that will be stored for each character
	int startX, startY;

	//for loops used to iterate through file and make a tile for each input from the file
	for (int y = 0; y < sizeY; y++)
	{
		for (int x = 0; x < sizeX; x++)
		{
			//used to get a tile using y and x value
			mapFile.get(num);
			startY = atoi(&num) * tileSize;
			mapFile.get(num);
			startX = atoi(&num) * tileSize;

			//add tile to the x and y position
			AddTile(startX, startY, x * scaledSize, y * scaledSize);
			mapFile.ignore();
		}
	}
	mapFile.ignore();

	//for loop to get tiles that will have colliders from the map file
	for (int y = 0; y < sizeY; y++)
	{
		for (int x = 0; x < sizeX; x++)
		{
			mapFile.get(num);
			if (num == '1')
			{
				auto& tcol(man.addEntity());
				tcol.addComponent<HitBoxComponent>("terrain", x * scaledSize, y * scaledSize, scaledSize);
				tcol.addGroup(Game::groupColliders);
			}
			mapFile.ignore();
		}
	}

	//closes map file
	mapFile.close();
}

//caaled to add a tile
void TileMap::AddTile(int startX, int startY, int xpos, int ypos)
{
	auto& tile(man.addEntity());
	//adds tile component
	tile.addComponent<TileMapComponent>(startX, startY, xpos, ypos, tileSize, mapScale, texID);
	//adds tile to group
	tile.addGroup(Game::groupMap);
}