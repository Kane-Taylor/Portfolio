#include "TheRoadHome.h"
#include "Texture.h"
#include "TileMap.h"
#include "ECS/Component.h"
#include "Vectors.h"
#include "Collisions.h"
#include "Assets.h"
#include <sstream>

TileMap* map;
Manager man;
SDL_Renderer* Game::ren = nullptr;
SDL_Event Game::event;

//sets camera
SDL_Rect Game::cam = { 0,0,800,640 };

Assets* Game::assets = new Assets(&man);

//sets game to not running
bool Game::isRunning = false;

auto& player(man.addEntity());

//constructor
Game::Game()
{}
//deconstructor
Game::~Game()
{}

//initialsie the game
void Game::init(const char* title, int width, int height, bool fullscreen)
{
	//used to manage window size
	int flags = 0;
	if (fullscreen)
	{
		flags = SDL_WINDOW_FULLSCREEN;
	}

	//used to set the game running and to create the window that will be used for the game
	if (SDL_Init(SDL_INIT_EVERYTHING) == 0)
	{
		window = SDL_CreateWindow(title, SDL_WINDOWPOS_CENTERED, SDL_WINDOWPOS_CENTERED, width, height, flags);
		ren = SDL_CreateRenderer(window, -1, 0);
		if (ren)
		{
			SDL_SetRenderDrawColor(ren, 255, 255, 255, 255);
		}

		isRunning = true;
	}

	//set textures for the entities in the game
	assets->AddTexture("terrain", "assets/terrain textures.png");
	assets->AddTexture("player", "assets/player animations.png");
	assets->AddTexture("Enemies", "assets/enemy.png");

	//create map entitie
	map = new TileMap("terrain", 2, 32);
	//ecs implementation
	//load map
	map->LoadMap("assets/map.map", 25, 20);

	//add components to the player
	player.addComponent<MovementComponent>(1400.0f, 640.0f, 32 , 32, 2);
	player.addComponent<CharacterComponent>("player", true);
	player.addComponent<KeyController>();
	player.addComponent<HitBoxComponent>("player");
	player.addGroup(groupPlayers);

	//set game background colour
	SDL_Color black = { 0, 0, 0, 0 };
	
	//creates Enemies
	assets->CreateEnemy(Vectors(1200, 640), "Enemies");
	assets->CreateEnemy(Vectors(1000, 670), "Enemies");
	assets->CreateEnemy(Vectors(800, 400), "Enemies");
	assets->CreateEnemy(Vectors(300, 300), "Enemies");
	assets->CreateEnemy(Vectors(300, 400), "Enemies");
	assets->CreateEnemy(Vectors(300, 1000), "Enemies");

}

//gets grouped enitities for use
auto& tiles(man.getGroup(Game::groupMap));
auto& players(man.getGroup(Game::groupPlayers));
auto& colliders(man.getGroup(Game::groupColliders));
auto& Enemies(man.getGroup(Game::groupEnemy));

//event handler
void Game::handleEvents()
{
	
	SDL_PollEvent(&event);

	switch (event.type)
	{
	case SDL_QUIT :
		isRunning = false;
		break;
	default:
		break;
	}
}


//update function
void Game::update()
{	
	//updates player collider and position
	SDL_Rect playerCol = player.getComponent<HitBoxComponent>().col;
	Vectors playerPos = player.getComponent<MovementComponent>().pos;

	man.refresh();
	man.update();

	//checks if collisions are taking place
	for (auto& c : colliders)
	{
		SDL_Rect cCol = c->getComponent<HitBoxComponent>().col;
		if (Collisions::axisAlignedBoundingBox(cCol, playerCol))
		{
			player.getComponent<MovementComponent>().pos = playerPos;
		}
	}

	//checks for collisions with moving objects (Enemies)
	for (auto& p : Enemies)
	{
		if (Collisions::axisAlignedBoundingBox(player.getComponent<HitBoxComponent>().col, p->getComponent<HitBoxComponent>().col))
		{
			std::cout << "Hit player!" << std::endl;
			p->destroy();
		}
	}

	//sets the players camera 
	cam.x = static_cast<int>(player.getComponent<MovementComponent>().pos.x - 400);
	cam.y = static_cast<int>(player.getComponent<MovementComponent>().pos.y - 320);
	if (cam.x < 0)
		cam.x = 0;
	if (cam.y < 0)
		cam.y = 0;
	if (cam.x > cam.w)
		cam.x = cam.w;
	if (cam.y > cam.h)
		cam.y = cam.h;
}

//render function (used to redraw anything that may move in the game such as colliders and the player)
void Game::render()
{
	SDL_RenderClear(ren);
	for (auto& t : tiles)
	{
		t->draw();
	}

	for (auto& c : colliders)
	{
		c->draw();
	}

	for (auto& p : players)
	{
		p->draw();
	}

	for (auto& p : Enemies)
	{
		p->draw();
	}


	SDL_RenderPresent(ren);
}

//clears game
void Game::clean()
{
	SDL_DestroyWindow(window);
	SDL_DestroyRenderer(ren);
	SDL_Quit();
}