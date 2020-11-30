package spaceRocks;

import java.awt.*;
import java.util.Random;

import graphics.Rock;

/*
 * the rock based shape which is set to come in 3 different sizes
 */
public class Asteroid extends Rock 
{

	private static int movementSpeed = 5;
	private static int baseSize = 3;
    private int sizeClassification;
    private Random rand;
    
	static Asteroid getRandomAsteroid(int scaleRange,Rectangle bounds)
	{
		return new Asteroid((int) (Math.random() * scaleRange) + 1,
				            (int) (Math.random() * bounds.getWidth()),
				            (int) (Math.random() * bounds.getHeight()));
	}
    
    public Asteroid(int sizeClassification) 
    {
        this(sizeClassification,0,0);
    }

    public Asteroid(int sizeClassification, int x, int y) 
    {
    	super(x,y,baseSize*resolution*sizeClassification);
        this.sizeClassification = sizeClassification;
        rand = new Random();
        setDelta(getRandommNonZeroAxisDelta(),getRandommNonZeroAxisDelta());
    }
    
    private int getRandommNonZeroAxisDelta()
    {
    	int m=0;
    	while(m==0) {
    		m= rand.nextInt(movementSpeed) - movementSpeed / 2;
    	}
    	return m;
    }

	public int getSizeClassification() 
	{
		return sizeClassification;
	}
	
}
