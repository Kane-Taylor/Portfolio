package utilities;

// code copied from Simon Lucas
// code copied by Udo Kruschwitz


import javax.swing.*;

import graphics.Shape;

import java.awt.*;
// import all the Colors
import static java.awt.Color.*;

@SuppressWarnings("serial")
public class ShapeView extends JComponent
{
    static Color[] colors = {black, green, blue, red, yellow, magenta, pink, cyan};
    Shape[][] a;
    int w, h;
    int size;

    public ShapeView(Shape[][] a,int size)
    {
        this.a = a;
        this.size=size;
        w = a.length;
        h = a[0].length;
    }


	public void paintComponent(Graphics g)
	{
		g.setColor(Color.white);
        g.fillRect(0, 0, getWidth(), getHeight());
        for (int i = 0; i < w; i++)
        {
            for (int j = 0; j < h; j++)
            {
                a[i][j].draw(g);
                //g.setColor(Color.black);
            	//g.drawLine(j*w , i*h , j*w ,  i*h);
            }
           	//g.drawLine(i*w,h*i,i*w,h*i);
        }
    }

    public Dimension getPreferredSize()
    {
        return new Dimension(w * size, h * size);
    }
    
}
