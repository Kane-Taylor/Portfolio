package spaceRocks;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


/*
 *  This adds some mouse alternatives to the keyboard
 *  simply by emulating the key actions
 */
public class MouseManager implements MouseListener 
{

    private Game game;
    
    MouseManager(Game game)
    {
        this.game=game;
    }
    
	@Override
	public void mouseClicked(MouseEvent arg0) 
	{
		if (arg0.getButton()==MouseEvent.BUTTON3)	game.getVoyager().toggleShield();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent arg0) 
	{
		if (arg0.getButton()==MouseEvent.BUTTON1)	KeyManager.shoot=true;
		if (arg0.getButton()==MouseEvent.BUTTON2)	KeyManager.teleport=true;
	}

	@Override
	public void mouseReleased(MouseEvent arg0) 
	{
		if (arg0.getButton()==MouseEvent.BUTTON1)	KeyManager.shoot=false;
		if (arg0.getButton()==MouseEvent.BUTTON2)	KeyManager.teleport=false;
	}

}
