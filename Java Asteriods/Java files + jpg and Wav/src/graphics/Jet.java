package graphics;

import java.awt.Color;
import java.util.Arrays;

/**+
 * Jet extends PointsShape using set points to draw the image for the Jet/ship.
 */
public class Jet extends PointsShape { 
	
	private static int xBase[] = {1,3,4,5,7,6,5,5,4,3,3,2};
	private static int yBase[] = {8,8,7,8,8,6,4,2,0,2,4,6};

	public Jet(int posX, int posY, int diameter) 
	{
		super(posX, posY,diameter);
		setColor(Color.RED);
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