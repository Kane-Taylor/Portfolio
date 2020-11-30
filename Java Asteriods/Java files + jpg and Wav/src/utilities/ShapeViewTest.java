package utilities;

import java.util.Random;

import graphics.Circle;
import graphics.Jet;
import graphics.Oval;
import graphics.PieSlice;
import graphics.Rectangle;
import graphics.Rock;
import graphics.Shape;
import graphics.Square;
import graphics.Triangle;

//
// Utility class to help text drawing shapes...
//
public class ShapeViewTest
{
	static int numTypes=8;
	
    public static void main(String[] args)
    {
    	int size=80;
        // test the view component
        Random r = new Random();
        int w = numTypes;
        int h = numTypes;
        Shape[][] a = new Shape[w][h];
        
        for (int i = 0; i < w; i++)
        {
            for (int j =0 ; j < h; j++)
            {
            	// get random shape
            	a[i][j] = getShape(i*size,j*size,r,size);
            	int scale=r.nextInt(2)+1;
            	//try scaling some
            	a[i][j].setScale(scale);
            }
        }
        ShapeView tv = new ShapeView(a,size);
        new JEasyFrame(tv, "Shape View");
    }
    
    static Shape getShape(int x, int y,Random r,int maxSize)
    {
    	int size=maxSize/2;
    	switch (r.nextInt(numTypes))
    	{
    	case 0: return new Circle(x+5,y+5,size-10);
    	case 1: return new PieSlice(x,y,size-10);
    	case 2: return new Rectangle(x+5,y+15,size-10,size-30);
    	case 3: return new Triangle(x+5,y+10,size-10,size-20);
    	case 4: return new Square(x+5,y+5,size-10);
    	case 5: return new Oval(x+10,y,size-20,size-5);
    	case 6: return new Rock(x+10,y,(size-25));
    	case 7: return new Jet(x+10,y,(size-25));
    	}	
    	return null;
    }

}