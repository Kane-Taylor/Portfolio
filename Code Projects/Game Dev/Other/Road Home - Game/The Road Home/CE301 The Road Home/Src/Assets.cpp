#include "Assets.h"
#include "ECS\Component.h"

//constructor
Assets::Assets(Manager* man) : manager(man)
{}

//destructor
Assets::~Assets()
{}

//manager for Enemies used in the game (adds componanats to Enemies)
void Assets::CreateEnemy(Vectors pos, std::string id)
{
	auto& Enemy(manager->addEntity());
	Enemy.addComponent<MovementComponent>(pos.x, pos.y, 32, 32, 2);
	Enemy.addComponent<CharacterComponent>(id, false);
	Enemy.addComponent<EnemyComponent>();
	Enemy.addComponent<HitBoxComponent>("Enemy");
	Enemy.addGroup(Game::groupEnemy);
}

//adds a tetxure to an asset
void Assets::AddTexture(std::string id, const char* path)
{
	textures.emplace(id, Texture::LoadTexture(path));
}

// gets texture of an asset
SDL_Texture* Assets::GetTexture(std::string id)
{
	return textures[id];
}
