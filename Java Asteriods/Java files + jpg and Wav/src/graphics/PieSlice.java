package graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class PieSlice extends Shape {

    /**+
     * PieSlice class that extends shape setting the parameters for the PieSlice and its color
     * @param posX
     * @param posY
     * @param diameter
     */
	public PieSlice(int posX, int posY, int diameter) 
	{
		super(posX, posY,diameter,diameter);
		setColor(Color.red);
	}

	@Override
	public void draw(Graphics g) 
	{
		// overridden method for drawing the shape and filling it a particular colour
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(getColor()); 
		g2.fillArc(getPosition().x-getDimension().width, getPosition().y, getDimension().width*2, getDimension().height*2, 45, 45);
	}
	
}