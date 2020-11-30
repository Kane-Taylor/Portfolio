package graphics;

import java.awt.Color;
import java.util.Arrays;

/**+
 * Rock extends PointsShape using set points to draw the image for the asteroids
 */
public class Rock extends PointsShape { 
	
	private static int xBase[] = {0,0,1,0,1,2,5,6,7,8,6,4,3};
	private static int yBase[] = {2,3,4,5,6,8,7,5,5,4,1,1,0};

	public Rock(int posX, int posY, int diameter) 
	{
		super(posX, posY,diameter);
		setColor(Color.magenta);
	}

	int[] copyXBase() 
	{
        return Arrays.copyOf(xBase, xBase.length);
	}

	int[] copyYBase() 
	{
        return Arrays.copyOf(yBase, yBase.length);
	}

}