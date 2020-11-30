package spaceRocks;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
/*
 * this key listener either stores or does actions immediately depending on the key events
 */
public class KeyManager implements KeyListener {

    static public boolean accelerator = false, decelerate = false, left = false, right = false, 
    		              shoot = false, teleport = false, start = false;

    private boolean initalsMode=false;
    private String initials;
    
    private Game game;
    
    KeyManager(Game game)
    {
        this.game=game;
    }

    //Button pressed starts action
    public void keyPressed(KeyEvent e) 
    {
    	if ( initalsMode)
    		return;
    	char key=e.getKeyChar();
    	int code=e.getKeyCode();
        if (code == KeyEvent.VK_UP) 	     	accelerator=true;      
        if (code == KeyEvent.VK_DOWN) 	    	decelerate=true;       
        if (code == KeyEvent.VK_RIGHT) 	      	right=true;			   
        if (code == KeyEvent.VK_LEFT) 	      	left=true;			   
        if (e.getKeyChar() == ' ') 		      	shoot=true;				
        if (e.getKeyChar() == 't') 		      	teleport=true;         
        if (e.getKeyChar() == 's') 		      	start=true;		       
        if (code == KeyEvent.VK_ESCAPE)         System.exit(0);        
        
        if (key == '+') 	game.getShots().alterMaxShotCount(+1);  
        if (key == '-') 	game.getShots().alterMaxShotCount(-1);  
        if (key == 'x')     game.getVoyager().toggleShield();   
        if (key == 'p')     game.setGamePaused(!game.isGamePaused());
        if (key == 'm')     game.getSoundManager().toggleSound();
    }

    //Button released ends action
    public void keyReleased(KeyEvent e) 
    {
    	if ( initalsMode)
    		return;
    	char key=e.getKeyChar();
    	int code=e.getKeyCode();
        if (code == KeyEvent.VK_UP) 	     accelerator=false;     
        if (code == KeyEvent.VK_DOWN) 	     decelerate=false;      	
        if (code == KeyEvent.VK_RIGHT) 	     right=false;;        	
        if (code == KeyEvent.VK_LEFT) 	     left=false;        		
        if (key == ' ') 				     shoot=false;        	
        if (key =='t') 					     teleport=false;        	
    }

    //below code is for adding initials for the scores

    public void keyTyped(KeyEvent e)
    {
    	if ( initalsMode && initials.length()<3 )
    	{
        	char key=e.getKeyChar();
    		if ((key>='a' && key<='z')||(key==' '))
    			initials=(initials+key).toUpperCase();		
    		if (key==KeyEvent.VK_ESCAPE)
    		{
    			endInitials(false);
    		}
            if(e.getKeyCode() == KeyEvent.VK_ENTER||initials.length()==3)
            {
    			endInitials(true);
            }
    	}
    }
    
    public void startInitials()
    {
    	initalsMode=true;
    	initials="";
    }
    
    public boolean waitForPlayerInitials()
    {
    	return initalsMode;
    }
 
    public String getInitials()
    {
    	String ret=initials;
    	while(ret.length()<3)
    		ret+="_";
    	return ret;
    }

    //used to save scores
    public void endInitials(boolean doSave)
    {
		initalsMode=false;
		if (doSave)
		{
			game.saveScore();
		}
    }

}
