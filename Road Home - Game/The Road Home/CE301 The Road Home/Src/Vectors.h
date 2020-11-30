#pragma once
#include <iostream>

class Vectors
{
public:
	//set vectors of an entity
	float x;
	float y;
	Vectors();
	Vectors(float x, float y);

	//methods of changing vectors
	Vectors& Add(const Vectors& vec);
	Vectors& Subtract(const Vectors& vec);
	Vectors& Multiply(const Vectors& vec);
	Vectors& Divide(const Vectors& vec);

	//opertaors to change vector
	friend Vectors& operator+(Vectors& v1, const Vectors& v2);
	friend Vectors& operator-(Vectors& v1, const Vectors& v2);
	friend Vectors& operator*(Vectors& v1, const Vectors& v2);
	friend Vectors& operator/(Vectors& v1, const Vectors& v2);

	//opertaors to change vector
	Vectors& operator+=(const Vectors& vec);
	Vectors& operator-=(const Vectors& vec);
	Vectors& operator*=(const Vectors& vec);
	Vectors& operator/=(const Vectors& vec);

	Vectors& operator*(const int& i);
	Vectors& Zero();

	friend std::ostream& operator<<(std::ostream& stream, const Vectors& vec);
};