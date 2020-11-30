#include "Vectors.h"

//class used to set and look at changing vector of an entity in the game

Vectors::Vectors()
{
	x = 0.0f;
	y = 0.0f;
}

Vectors::Vectors(float x, float y)
{
	this->x = x;
	this->y = y;
}

Vectors& Vectors::Add(const Vectors& vec)
{
	this->x += vec.x;
	this->y += vec.y;

	return *this;
}

Vectors& Vectors::Subtract(const Vectors& vec)
{
	this->x -= vec.x;
	this->y -= vec.y;

	return *this;
}

Vectors& Vectors::Multiply(const Vectors& vec)
{
	this->x *= vec.x;
	this->y *= vec.y;

	return *this;
}

Vectors& Vectors::Divide(const Vectors& vec)
{
	this->x /= vec.x;
	this->y /= vec.y;

	return *this;
}

Vectors& operator+(Vectors& v1, const Vectors& v2)
{
	return v1.Add(v2);
}

Vectors& operator-(Vectors& v1, const Vectors& v2)
{
	return v1.Subtract(v2);
}

Vectors& operator*(Vectors& v1, const Vectors& v2)
{
	return v1.Multiply(v2);
}

Vectors& operator/(Vectors& v1, const Vectors& v2)
{
	return v1.Divide(v2);
}

Vectors& Vectors::operator+=(const Vectors& vec)
{
	return this->Add(vec);
}

Vectors& Vectors::operator-=(const Vectors& vec)
{
	return this->Subtract(vec);
}

Vectors& Vectors::operator*=(const Vectors& vec)
{
	return this->Multiply(vec);
}

Vectors& Vectors::operator/=(const Vectors& vec)
{
	return this->Divide(vec);
}

Vectors& Vectors::operator*(const int& i)
{
	this->x *= i;
	this->y *= i;

	return *this;
}

Vectors& Vectors::Zero()
{
	this->x = 0;
	this->y = 0;

	return *this;
}

std::ostream& operator<<(std::ostream& stream, const Vectors& vec)
{
	stream << "(" << vec.x << "," << vec.y << ")";
	return stream;
}