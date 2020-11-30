#pragma once

struct Animations
{
	//setting options for animation
	int id;
	int numFrames;
	int speed;

	//constructors
	Animations() {}
	Animations(int i, int f, int s)
	{
		id = i;
		numFrames = f;
		speed = s;
	}

};