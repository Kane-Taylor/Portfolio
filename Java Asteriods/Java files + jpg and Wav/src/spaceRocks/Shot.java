package spaceRocks;

import java.awt.*;

import graphics.Shape;
/*
 * Used to draw the shots and is set so that they have a set travel distance before they expire
 */
public class Shot extends Shape 
{
    private int maxDistance;
	private Point distanceTraveled;
    private boolean expired;

    public Shot(int x, int y, int xs, int ys,int maxDistance) 
    {
    	super(x,y,10,10);
    	setDelta(xs,ys);
    	this.maxDistance=maxDistance;
    	distanceTraveled=new Point(0,0);
        expired = false;
    }

    @Override
    public void travel() 
    {
        super.travel();
    	distanceTraveled.translate(Math.abs(getDelta().x),Math.abs(getDelta().y));
    	expired=(distanceTraveled.x + distanceTraveled.y > maxDistance) ;
     }

    @Override
    public void draw(Graphics g)
    {
        g.setColor(Color.RED);
        g.fillOval(getPosition().x, getPosition().y, getDimension().width, getDimension().height);
    }

	public boolean isExpired() 
	{
		return expired;
	}

}
