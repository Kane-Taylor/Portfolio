package graphics;

import java.awt.Color;

public class Square extends Rectangle {
	/**+
	 * square class that extends shape setting the parameters for the square and its color
	 * @param posX
	 * @param posY
	 * @param sideLength
	 */
	public Square(int posX, int posY, int sideLength) 
	{
		super(posX, posY,sideLength,sideLength);
		setColor(Color.green);
	}

}