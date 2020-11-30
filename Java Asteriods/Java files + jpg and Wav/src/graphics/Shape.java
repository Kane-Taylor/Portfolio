package graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

/**+
 * abstract class ‘Shape’ that will encapsulate other shapes
 */
public abstract class Shape 
{
	private Point delta; // the change to be applied to position when it travels
	private Dimension unscaled; // original size
	private Dimension scaled; // scaled up size
	private Point position; // current coordinates
	private Color color; // current color
	private double scale; // ratio of scaled to original sizes

	public abstract void draw(Graphics g);

	//sets position and size of shape
    public Shape(int posX, int posY, int lenX, int lenY) 
    {
		position=new Point(posX, posY);
		color=Color.WHITE; // default colour overridden usually
		dontMove(); // initially stationary
        // initially set unscaled
        scale=1.0;
		this.unscaled = new Dimension(lenX,lenY);
		this.scaled = new Dimension(unscaled);
	}

    public void dontMove()
    {
        delta = new Point(0, 0); 
    }

    //used to scale shapes
	public void setScale(double scale) 
	{
		// minimum scale (up only)
		this.scale = scale<1.0?1.0:scale;
		//scale dimensions from original to avoid cumulative rounding errors
		scaled.width=(int) (unscaled.width*this.scale);		
		scaled.height=(int) (unscaled.height*this.scale);
	}

	//checks for overlap of objects
	public boolean overlaps(Rectangle other) 
    {
    	return (getBounds().intersects(other));
    }

    //set position of object using x and y axis
    public void setPosition(int x,int y) 
    {
    	position.x = x;
    	position.y = y;
    }

    //set random position on the screen
    public void setRandomPos(Dimension scrn) 
    {
        setPosition((int) (Math.random() * scrn.getWidth()),(int) (Math.random() * scrn.getHeight()));
    }    

    //used to work out movement
    public void travel() 
    {
    	move(delta.x,delta.y);
    }

    // Checks to see if it is outside the boundary
    public void boundsCheck(Rectangle boundary) 
    {
    	Rectangle bounds=getBounds();
        if (!boundary.intersects(bounds)) 
        {
        	// have to move back inside boundary on other side
        	Point p=bounds.getLocation();
            if (bounds.x < 0) {
                p.x=boundary.width;
            } else if (bounds.x > boundary.width) {
            	p.x=0;
            }
            if (bounds.y < 0) {
            	p.y=boundary.height;
            } else if (bounds.y > boundary.height) {
               	p.y=0;
            }
            setPosition(p.x,p.y);
        }
    }

	public void drawbounds(Graphics g)
	{
		// darws the bounded area
		g.setColor(Color.WHITE);
		Rectangle r= getBounds();
		g.drawRect(r.x,r.y,r.width,r.height);
	}
	
	// encapsulation getter and setters
	
	public Point getDelta() 
	{
		return delta;
	}
	
	public void changeDelta(int accelX,int accelY)
	{
		delta.translate( accelX,accelY);
	}

	public void setDelta(int dx,int dy) 
	{
		this.delta.x = dx;
        this.delta.y = dy;
	}

	public Rectangle getBounds() 
	{
		return new Rectangle(position,scaled);
	}

	public void move(int dx,int dy)
	{
		position.translate(dx, dy);
	}

	public Dimension getDimension() 
	{
		return scaled;
	}

	public Point getPosition() 
	{
		return position;
	}

	public Color getColor() 
	{
		return color;
	}

	public void setColor(Color color) 
	{
		this.color=color;
	}

	public double getScale() 
	{
		return scale;
	}

}
