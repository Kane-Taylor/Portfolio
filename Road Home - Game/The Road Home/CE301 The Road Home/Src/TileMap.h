#pragma once
#include <string>

class TileMap 
{
public:
	 //constructor
	TileMap(std::string i, int m, int t);
	//deconstructor
	~TileMap();

	//loads and adds tiles to a map
	void LoadMap(std::string path, int sizeX, int sizeY);
	void AddTile(int startX, int startY, int xpos, int ypos);

private:
	//variable settings for the tile map
	std::string texID;
	int mapScale;
	int tileSize;
	int scaledSize;

};
