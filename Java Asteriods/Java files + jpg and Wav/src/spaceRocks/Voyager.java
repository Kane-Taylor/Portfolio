package spaceRocks;

import java.awt.*;
import java.awt.geom.AffineTransform;

import graphics.Jet;
/*
 * The main player sprite is a rotatable shape,
 * which can change velocity
 */
public class Voyager extends Jet {

	private int maxAccelleration=10;
    private int maxVelocity = 20;
    private Point acceleration;    
    private Point centrePoint;
	private int rotationAngle;
	private int shotVelocity = 5;
    private int shield=0;
    private int shieldTime=90;
    private int shieldRechageMultiplier=3;
    private Game game;
    
	public Voyager(Point location,Game game) 
    {
    	super(location.x,location.y,64);
    	this.game=game;
        rotationAngle = 270;
        acceleration = new Point(-1, 0);
        centrePoint = new Point((int)((getBounds().width) / 2), (int)((getBounds().height) / 2)) ;
    }


    //shield mechanic i decided to add which blocks damage to ship for short time
	public boolean isShielded()
    {
		return shield>0;
	}
	
    public int getShieldStatuse()
    {
        return shield;
    }	

	public void toggleShield() 
	{
	    if (shield==0)
	    {
	        // off so set the count down to shieldTime;
	        shield=shieldTime;
	        game.getSoundManager().play("shieldson");
	    }
	    else
	    {
	        if (shield>0)
	        {
	            // wait to recharge shields by a lesser amount
	            shield=(shield-shieldTime)*shieldRechageMultiplier;
                game.getSoundManager().play("shieldsoff");
	        }
	        else
	        {
	            return;
	        }
	    }
	}

	public void countDownShield() 
    {
        if (shield>0)
        {
            // its on so count down towards off
            shield--;
            if (shield==0)
            {
                // if its now off then set recharge time 
                shield=-shieldTime*shieldRechageMultiplier;
                game.getSoundManager().play("shieldsoff");
            }
        }
        else
        {
            if (shield<0)
            {
                // its off  so count up
                shield++;
            }
        }
    }

    //calculations for accelerating ship
    public void accelerate() 
    {
    	if (Math.abs(acceleration.x)<maxAccelleration&&Math.abs(acceleration.y)<maxAccelleration)
    	{
    		move(acceleration.x,-acceleration.y);
    	}
    	Point accel=new Point(getDelta());
        if (Math.abs(accel.x) < maxVelocity && Math.abs(accel.y) < maxVelocity) 
        	changeDelta(acceleration.x,-acceleration.y);
    }

    //deceleration for ship
    public void decelerate() 
    {
    	//\ just shrink the component vector towards zero
        setDelta(towardsZero(getDelta().x),towardsZero(getDelta().y));
    }

    private int towardsZero(int i) 
    {
        return i == 0 ? 0 : i > 0 ? i - 1 : i + 1;
    }

   public void rotate(int change) 
    {
    	// this sets the rotation angle for future travel or shots 
        rotationAngle = (change*3 + rotationAngle) % 360;
        rotationAngle = (rotationAngle < 0) ? 360 - -rotationAngle : rotationAngle;
        acceleration.move( (((int) (5 * Math.sin(Math.toRadians(rotationAngle))))) , (((int) (5 * Math.cos(Math.toRadians(rotationAngle))))));
    }

    public boolean fireShot(Shots firedShots) 
    {
        if (!firedShots.canShoot())
            return false;
        game.getSoundManager().play("shoot");
    	// create the shot with the correct angle of Fire - where we are pointing not where we are going, and speed relative to this
        firedShots.addShot(
                   new Shot((int) (getPosition().x + centrePoint.x + 2 * Math.cos(rotationAngle)),
                            (int) (getPosition().y + centrePoint.y+ 2 * Math.sin(rotationAngle)),
                            shotVelocity * acceleration.x, 
                            -shotVelocity * acceleration.y,
                   			Math.min(game.getBounds().width,game.getBounds().height))
                   );
        return true;
    }

    @Override
    public void draw(Graphics g) 
    {
    	
    	// set the rotation angles and position for using G2D
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform orig = g2d.getTransform();
        AffineTransform at = (AffineTransform) orig.clone();
        at.translate(getPosition().x, getPosition().y);
        at.translate(centrePoint.x, centrePoint.y);
        at.rotate(Math.toRadians(rotationAngle));
        at.translate(-getPosition().x, -getPosition().y);
        at.translate(-centrePoint.x, -centrePoint.y);
        g2d.setTransform(at);
        // now we can draw
        super.draw(g2d);
        if (isShielded())
        {
        	// draw the "shield"
        	drawbounds(g);
        }
        // reset the grahicss
        g2d.setTransform(orig);
    }

    //another mechanic that teleports you to somewhere else in the frame
    public void teleport()
    {
        game.getSoundManager().play("teleport");
        setRandomPos(new Dimension(game.getWidth(),game.getHeight()));
    }
       
}
