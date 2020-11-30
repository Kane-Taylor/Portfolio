package graphics;

import java.awt.Color;
import java.awt.Graphics;

public class Triangle extends Shape {

	/**+
	 * Triangle class that extends shape setting the parameters for the triangle and its color
	 * @param posX
	 * @param posY
	 * @param sideWidth
	 * @param sideHeight
	 */
	public Triangle(int posX, int posY, int sideWidth, int sideHeight) 
	{
		super(posX, posY,sideWidth,sideHeight);
		setColor(Color.orange);
	}

	@Override
	public void draw(Graphics g) 
	{
        // overridden method for drawing the shape and filling it a particular colour
		g.setColor(getColor()); 
		int xPoints[] = {getPosition().x, getPosition().x+getDimension().width/2, getPosition().x+getDimension().width};
	    int yPoints[] = {getPosition().y, getPosition().y+(getDimension().width), getPosition().y};
		g.fillPolygon( xPoints, yPoints, 3); 
	}
}