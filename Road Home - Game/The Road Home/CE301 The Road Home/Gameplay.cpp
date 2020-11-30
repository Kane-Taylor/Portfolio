/*
#include <iostream>

#include <cmath>

#include <time.h>

#include <cstdlib>

using namespace std;

// starting main function

int main() {

	// set up my variables for the health

	int PlayerHealth = 5;

	int AIHealth = 5;

	// starts the battle loop will run until a entities health reaches 0

	do {

		int choice;

		// Title used to hide if game over.
		if (PlayerHealth > 0 && AIHealth > 0)
		{
			cout << "--------------------------------------" << endl;

			cout << "-------------- Battle! ---------------" << endl;

			cout << "--------------------------------------" << endl;

			// Ask the player to choose Attack, Parry, Recover

			cout << "Press 1 for Attack, 2 for Parry, 3 for Recover:" << endl;

		}
		else {
			cout << "enter 1 to quit" << endl;
		}


		cin >> choice;

		// AI uses random to chose a number

		int ai = rand() % 3 + 1;

		cout << "The enemy chose: " << ai << endl;

		// gets into all possible outcomes of the players battle choices and effects their health depending on the outcome.

		if (choice == 1 && ai == 1) {

			cout << "Attack meets Attack you both take damage!" << endl;

			PlayerHealth--;
			AIHealth--;

		}

		else if (choice == 1 && ai == 2) {

			cout << "The enemy parries your attack!." << endl;

			PlayerHealth--;

		}

		else if (choice == 1 && ai == 3) {

			cout << "The enemy attempts to recover, you don't give him a chance!" << endl;

			AIHealth--;

		}

		else if (choice == 2 && ai == 1) {

			cout << "You parry the enemies attack!" << endl;

			AIHealth--;

		}

		else if (choice == 2 && ai == 2) {

			cout << "You both act deffensivly, nothing happens!" << endl;


		}

		else if (choice == 2 && ai == 3) {

			cout << "You defend, but the enemy use this time to recover!" << endl;

			AIHealth++;

		}

		else if (choice == 3 && ai == 1) {

			cout << "you attempts to recover, The enemy doesn't give you a chance!" << endl;

			PlayerHealth--;

		}

		else if (choice == 3 && ai == 2) {

			cout << "You recover some health while the enemy is defending!" << endl;

			PlayerHealth++;

		}

		else if (choice == 3 && ai == 3) {

			cout << "You both take the time to recover" << endl;

			PlayerHealth++;
			AIHealth++;

		}

		// used to make the  player know they can only use 1, 2 and 3

		else {

			cout << "You must select 1, 2, or 3" << endl;

		}

		// displays health of those in battle

		cout << "Player Health: " << PlayerHealth << endl;

		cout << "Enemy Health:" << AIHealth << endl;

		if (AIHealth == 0 && PlayerHealth == 0)
			cout << "You both died" << endl;
		else if (PlayerHealth == 0)
			cout << "You died" << endl;
		else if (AIHealth == 0)
			cout << "You won" << endl;


	}


	while (PlayerHealth >= 0 && AIHealth >= 0);
	{
		return 0;
	}

}
*/