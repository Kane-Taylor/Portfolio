#include "TheRoadHome.h"
//null pointer
Game *game = nullptr;

int main(int argc, char *argv[])
{
	//setting options for framerate
	const int FPS = 60;
	const int frameDelay = 1000 / FPS;
	Uint32 frameBegin;
	int timeForFrame;

	//starting the game and the game the window is played in
	game = new Game();
	game->init("CE301 The Road Home", 800, 640, false);

	//uses game class to handle user inputs, update and render whilst the game is running
	while (game->running())
	{
		//start frame
		frameBegin = SDL_GetTicks();

		game->handleEvents();
		game->update();
		game->render();

		//finds how long it takes to update frame
		timeForFrame = SDL_GetTicks() - frameBegin;
		//slows down frame rate
		if (frameDelay > timeForFrame)
		{
			SDL_Delay(frameDelay - timeForFrame);
		}
	}

	game->clean();
	return 0;
}