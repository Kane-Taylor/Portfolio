#pragma once

#include "SDL.h"
#include "SDL_image.h"
#include <iostream>
#include <vector>

class Assets;

//game class
class Game
{
public:
	//constructor and deconstructor
	Game();
	~Game();

	//initialises game
	void init(const char* title, int width, int height, bool fullscreen);

	//functions to run the game (updating, rendering etc.)
	void handleEvents();
	void update();
	bool running() { return isRunning; }
	void render();
	void clean();

	//renderer for game
	static SDL_Renderer *ren;

	//used for check events
	static SDL_Event event;

	//used to check if game is still running
	static bool isRunning;

	//set camara as rectangle
	static SDL_Rect cam;

	//calls assetmanager for assets
	static Assets* assets;

	//enum labels used to group together different entities
	enum groupLabels : std::size_t
	{
		groupMap,
		groupPlayers,
		groupColliders,
		groupEnemy
	};

private:
	int cnt = 0;
	SDL_Window *window;
};