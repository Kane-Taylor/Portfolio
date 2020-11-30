package graphics;

import java.awt.Color;
import java.awt.Graphics;

public class Rectangle extends Shape {

    /**+
     * Rectangle class that extends shape setting the parameters for the rectangle and its color
     * @param posX
     * @param posY
     * @param sideWidth
     * @param sideheight
     */
	public Rectangle(int posX, Integer posY, int sideWidth, int sideheight) 
	{
		super(posX, posY,sideWidth,sideheight);
		setColor(Color.blue);
	}

	@Override
	public void draw(Graphics g) 
	{
		// overridden method for drawing the shape and filling it a particular colour
		g.setColor(getColor()); 
		g.fillRect(getPosition().x, getPosition().y, getDimension().width, getDimension().height); 
	}
}