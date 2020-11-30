package graphics;

import java.awt.Graphics;

/**+
 * class that extends shapes that will let me draw shapes using points
 */
public abstract class PointsShape extends Shape { 
	
    protected static int resolution=8;
    private double initScale;

	public PointsShape(int posX, int posY, int diameter) 
	{
		super(posX, posY,diameter,diameter);
		// overriding the default scaling means we need to keep track of the difference 
		//initScale=Math.min((double)diameter/resolution,1.0);
		initScale=(double)diameter/resolution;
	}

	@Override
	public void setScale(double scale) 
	{
		super.setScale(scale*initScale);
	}
	
	abstract int[] copyXBase();
	abstract int[] copyYBase();
	
	@Override
	public void draw(Graphics g) 
	{ 
		// copy the original points and scale them up 
		int xPoints[] = copyXBase();
	    int yPoints[] = copyYBase();
		// just in case lopsided
		int nPoints= Math.min(xPoints.length,yPoints.length);
	    double scale=initScale*getScale();
	    for (int i=0;i< nPoints;i++)
	    {
	    	xPoints[i]=((int) (scale*xPoints[i]))+getPosition().x;
	    	yPoints[i]=((int) (scale*yPoints[i]))+getPosition().y;
	    }
		g.setColor(getColor()); 
		g.fillPolygon(xPoints, yPoints, nPoints);
	}
	
}