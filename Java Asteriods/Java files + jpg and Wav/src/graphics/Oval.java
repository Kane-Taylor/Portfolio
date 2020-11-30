package graphics;

import java.awt.Color;
import java.awt.Graphics;

public class Oval extends Shape {

    /**+
     * Oval class that extends shape setting the parameters for the oval and its color
     * @param posX
     * @param posY
     * @param sideWidth
     * @param sideheight
     */
	public Oval(int posX, int posY, int sideWidth, int sideheight) 
	{
		super(posX, posY,sideWidth,sideheight);
		setColor(Color.lightGray);
	}

	@Override
	public void draw(Graphics g) 
	{
        // overridden method for drawing the shape and filling it a particular colour
		g.setColor(getColor()); 
		g.fillOval(getPosition().x, getPosition().y, getDimension().width, getDimension().height); 
	}
}