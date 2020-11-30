package graphics;

import java.awt.Color;

public class Circle extends Oval {

	/**+
	 * Circle class that extends Oval setting the parameters for the Circle and its color
	 * @param posX
	 * @param posY
	 * @param diameter
	 */
	public Circle(int posX, int posY, int diameter) 
	{
		super(posX, posY,diameter,diameter);
		setColor(Color.cyan);
	}

}