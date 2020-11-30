package spaceRocks;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

/*
 *  collection of active shots to draw on the screen.
 *  includes logic to limit the number on screen at any time.
 */
public class Shots
{
	private ArrayList<Shot> volley = new ArrayList<Shot>();
    private int maxOnScreenShots = 6;
 
    public void travel() 
    {
        for (int i = 0; i < volley.size(); i++) 
        {
            Shot temp = volley.get(i);
            temp.travel();
            if (temp.isExpired()) 
            {
            	volley.remove(i);
            }
        }
    }
    
    public void draw(Graphics g)
    {
    	for (Shot s: volley)
        {
            s.draw(g);;
        }
    }
    
    public void boundsCheck(Rectangle bounds) 
    {
        for (Shot s : volley) 
        {
            s.boundsCheck(bounds);
        }
    }
    
    public void alterMaxShotCount(int change) 
    {
		this.maxOnScreenShots += change;
	}

	public void  addShot(Shot fire) 
    {
        if (canShoot()) 
        {
           volley.add(fire);
        }
    }
    
    public boolean canShoot() 
    {
        return (volley.size() < maxOnScreenShots); 
    }
    
    public boolean noMoreShots()
    {
        return getSshotCount() == 0;
    }
    
    void removeShot(int index)
    {
    	volley.remove(index);
    }
    
    public int getSshotCount()
    {
        return volley.size() ;
    }
    
    public Shot getShot(int index)
    {
    	return volley.get(index);
    }
    
    public int getRemainingShots()
    {
    	return maxOnScreenShots - getSshotCount() ;
    }
}
