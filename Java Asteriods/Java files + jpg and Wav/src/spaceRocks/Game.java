package spaceRocks;

import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

import javax.swing.*;
/*
 * The game canvas and event processing class
 * uses a simple timer to generate game loop events
 *
 *
 */
public class Game extends JPanel implements ActionListener, FocusListener 
{
    
	private HighScores highScores;
    private KeyManager keyManager;
    private MouseManager mouseManager;
    private GameTimer gameTimer;
    private Voyager voyager;
    private Asteroids kasteroids;
    private Shots firedShots;
    private SoundManager soundManager;
    
    private Image backgroundImage;
    private Point spawnPoint;
    private String gameTime;

    private int livesRemaing;
    private boolean gameRunning;
    private boolean gameScored;
    private boolean gameStartUp;
    private int respawnCountDown=0;

    private boolean gamePaused=false;
    private boolean spaceDrag=true;
    
    private int gameSpeed=30; 
    private int respawnDelay=50;
    private int playerScore;

    //initialize components
    void initComponenets()
    {
        highScores=new HighScores();
        keyManager=new KeyManager(this);
        mouseManager=new MouseManager(this);
        gameTimer=new GameTimer();
        firedShots=new Shots();
        soundManager=new SoundManager();
    }

    //starts up the game when called by main
    public Game(JFrame frame) throws Exception
    {
        initComponenets();
        // set Background 
        setBackground(Color.BLACK);
        loadBackgroundImage();
        // setup ui
        super.setSize(frame.getWidth(), frame.getHeight());
        spawnPoint = new Point(getWidth() / 2, getHeight() / 2);
        frame.add(this);
        frame.addFocusListener(this);
        frame.addKeyListener(keyManager);
        frame.addMouseListener(mouseManager);
        
        highScores.load();
        startGame();
        gameStartUp=true;
    	gameRunning = false;
    	soundManager.play("load");
        
        // initialise the repaint timer
        Timer actionEventGenerator = new Timer(gameSpeed, this);
        actionEventGenerator.start();
    }

    //what happens at game start
    void startGame()
    {
        soundManager.play("start");
    	gameStartUp=false;
    	gameScored = true;
    	gameRunning = true;
        setGamePaused(false);
        livesRemaing = 3;
        playerScore = 0;
        gameTimer.reset();
        createVoyager();
    }

    //what happens at game end
    void endGame()
    {
        soundManager.play("end");
    	gameScored=false;
    	gameRunning = false;
    	gameTime=gameTimer.toString();
    }

    //reSpawns asteroids
    public void actionPerformed(ActionEvent e) 
    {
        if (!keyManager.waitForPlayerInitials())
        {
        	checkAsteroidsReady();
            if (kasteroids.swarmDestoyed()) 
            {
                kasteroids.reSpawn();
            }
        }
        repaint();
    }

    public void checkAsteroidsReady()
    {
        if (kasteroids==null)
        	kasteroids=new Asteroids(this).createSwarm();
    }

    //checks if game is paused
	boolean isGamePaused()
	{
		return gamePaused;
	}

	//sets game to paused
	boolean setGamePaused(boolean gamePaused) 
	{
		this.gamePaused = gamePaused;
		return gamePaused;
	}

	//starts game if when out of menu or pause screen
	void setGameStart() 
	{
		gamePaused=false ;
        if (!gameRunning||gameStartUp) 
        {
            createVoyager();
            kasteroids = new Asteroids(this).createSwarm();
            startGame();
        }
	}

	//draws the game
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        //First paint the nice background
        g.drawImage(backgroundImage, 0, 0, null);

        if (keyManager.waitForPlayerInitials())
        {
            drawInitialsScreen(g);
        }
        else if (isGamePaused())
        {
            drawPaused(g);
        }
        else
        {
            drawGame(g);
        }
    }

    //how the game is draw
    public void drawGame(Graphics g)
    {
        //ensure they exist
        checkAsteroidsReady();
        // see what keys have been pressed and held for processing here
        processQueuedKeys();
        // move all screnn Objects as required
        if (respawnCountDown==0)
        {
            if (spaceDrag) 
            {
                voyager.decelerate();
            }
            voyager.travel();
        }
        kasteroids.travel();
        firedShots.travel();
        // check position on screen...
        voyager.boundsCheck(getBounds());
        kasteroids.boundsCheck(getBounds());
        firedShots.boundsCheck(getBounds());
        // check for interactions
        if (gameRunning) {
            if (respawnCountDown>0) 
            {
                if (respawnCountDown==respawnDelay)
                {
                    createVoyager();
                }
                respawnCountDown--;
            }
            else 
            {
                checkAsteroidsShot();
                checkAsteroidCollisions();
                firedShots.draw(g);
                voyager.draw(g);
            }
        }
        kasteroids.draw(g);
        drawStatus(g);
        if (!gameRunning||gameStartUp) 
        {
            drawGameEndScreen(g);
        }
    }

    //process the keys pressed and execute their actions
    private void processQueuedKeys() 
    {
        if (KeyManager.accelerator)  
        {
            voyager.accelerate();
        }
        if (KeyManager.decelerate) 
        {
            voyager.decelerate();
        }
        if (KeyManager.right) 
        {
            voyager.rotate(1);
        }
        if (KeyManager.left) 
        {
            voyager.rotate(-1);
        }
        if (KeyManager.shoot) 
        {
            if (gameRunning) 
            {
            	voyager.fireShot(firedShots);
            }
            KeyManager.shoot = false;
        }
        if (KeyManager.teleport) 
        {
            if (gameRunning) 
            {
            	voyager.teleport();
            }
            KeyManager.teleport = false;
        }
        if (KeyManager.start) 
        {
        	KeyManager.start=false;
        	setGameStart();
        }

    }

    //load jpg image for the background image
    private void loadBackgroundImage() 
    {
    	backgroundImage = Toolkit.getDefaultToolkit().getImage(getClass().getResource("resources/stars.jpg"));
        this.prepareImage(backgroundImage, null);
     // need to wait for loading
        while (backgroundImage.getWidth(null) == -1) { }
    }

    //checks for collisions with player
    private  void checkAsteroidCollisions() 
    {
        if ( gameRunning) 
        {
        	boolean oops=false;
            for (int i = 0; i < kasteroids.swarmSize(); i++) 
            {
                if (voyager.overlaps(kasteroids.getAsteroid(i).getBounds())) 
                {
                	oops=!voyager.isShielded(); 
                	i=checkAsteroidCanSplit(i);
                }
            }
            if (oops)
            {
                soundManager.play("death");
                respawnCountDown = respawnDelay;
                voyager.setPosition(spawnPoint.x,spawnPoint.y);
                voyager.dontMove();
                livesRemaing--;
                if (livesRemaing == 0 ) {
                	endGame();
                }

            }
            voyager.countDownShield();
        }
    }

    //checks for collisions between Asteroids and shots that should destroy them
    private void checkAsteroidsShot() 
    {
        for (int i = 0; i < kasteroids.swarmSize() ; i++)
        {
            for (int j = 0; j < firedShots.getSshotCount(); j++)
            {
                if (firedShots.getShot(j).overlaps(kasteroids.getAsteroid(i).getBounds()))
                {
                    i=checkAsteroidCanSplit(i);
                    firedShots.removeShot(j);
                    if (firedShots.noMoreShots() || kasteroids.swarmDestoyed() ) 
                    	return;
                }
            }
        }
    }

    //checks to see if the asteroid should split into smaller asteroids when destroyed
    private int checkAsteroidCanSplit(int i)
    {
        int ret=i;
    	// player gets some points here !
        if (kasteroids.getAsteroid(i).getSizeClassification() == 1) 
        {
        	// if it is smallest size remove it and reset  processing index 
            soundManager.play("gone");
        	playerScore++;
        	kasteroids.removeAsteroid(i);
            ret = 0;
        } 
        else 
        {
        	// else split it and return processing index 
            soundManager.play("split");
            playerScore += kasteroids.getAsteroid(i).getSizeClassification();
            kasteroids.splitAsteroid(i);
        }
        return ret;
    }

    //layout for the pause menu
    private void drawPaused(Graphics g)
    {
        setFont(g,36,Color.ORANGE,Font.BOLD);
        drawCentredString(g,"GAME PAUSED",200);
		displayTextLines(g,"Key Controls",280,Arrays.asList(
				" ",
				"'s'        Start                  'p'        Pause/UnPause     ",
				"<ESC>      Quit                   <SPACE>    Fire              ",
				"<up>       Accelerate             <Down>     Decelerate        ",
				"<Right>    Rotate right           <Left>     Rotate Left       ",
				"'t'        Teleport               'x'        Shields on/off    ",
				"'+'        Increase Max Shots     '-'        Decrease Max Shots",
				"'m'        mute/unmute sounds                                  ",
				" ",
				"Mouse Controls",
				" ",
				"Left = Fire          Middle = Teleport          Right = Shields"));
    }

    //Draws status about the game towards the top of the frame
    private void drawStatus(Graphics g) 
    {
        setFont(g,13,Color.WHITE,Font.PLAIN);
        int shieldCounter=voyager.getShieldStatuse();
        String s=String.format("Lives = %s     shots = %s     Time = %s     Score = %d %s%s%s", 
        	     livesRemaing,
        	     firedShots.getRemainingShots(),
        	     gameTimer.toString(), 
        	     playerScore, 
        	     (respawnCountDown>0?"     Respawn in "+respawnCountDown:""),
                 (shieldCounter>0?"     Shields off in "+shieldCounter:""),
                 (shieldCounter<0?"     Shields recharged in "+(-shieldCounter):"")     	      
                );
        g.drawString(s, 20, 20);
    }

    //saves score
    void saveScore()
    {
		highScores.saveHighScore(playerScore,keyManager.getInitials(),gameTime);
		highScores.save(); 	
    }

    //Draws game over screen
    private void drawGameEndScreen(Graphics g)
    {
    	if (!gameScored && highScores.checkHighScore(playerScore))
    	{
    		keyManager.startInitials();
    		drawInitialsScreen(g);
    		gameScored=keyManager.waitForPlayerInitials();
    	}
    	else
    	{
    		drawScoresScreen(g);
    	}
    }

    //draws score screen
    private void drawScoresScreen(Graphics g)
    {
        setFont(g,60,Color.YELLOW,Font.BOLD);
        drawCentredString(g,gameStartUp?"START GAME":"GAME OVER",100);
        setFont(g, 20,Color.YELLOW,Font.BOLD);
        drawCentredString(g,"s to start , p to pause/get help",200);
    
        setFont(g, 15,Color.YELLOW,Font.BOLD);
        int start=320;
        drawCentredString(g,"HIGH SCORES",start);
        for(String s: highScores.toList())
        {
        	start+=20;
        	drawCentredString(g,s,start);
        }
        displayTextLines(g,"HIGH SCORES",320,highScores.toList());
    }

    //
    private void displayTextLines(Graphics g,String title,int start,java.util.List<String> strings)
    {
        setFont(g, 15,Color.YELLOW,Font.BOLD);
        drawCentredString(g,title,start);
        for(String s: strings)
        {
        	start+=20;
        	drawCentredString(g,s,start);
        }
    }

    //draws score screen
    private void drawInitialsScreen(Graphics g)
    {
        setFont(g,50,Color.YELLOW,Font.BOLD);
        drawCentredString(g,"New high score !  "+playerScore,300);
        setFont(g, 20,Color.YELLOW,Font.BOLD);
        drawCentredString(g,"Enter your initials (eg 'KGT') "+keyManager.getInitials(),400);
    }


    //Game is paused when focus on the game tab is lost
    public void focusGained(FocusEvent e) 
    {
    }
    public void focusLost(FocusEvent e) 
    {
        setGamePaused(true);
    }

    //sets font
    private void setFont(Graphics g, int size,Color color,int style)
    {
        g.setColor(color);
        g.setFont( new Font(Font.MONOSPACED, style, size));
    }
    
    private void drawCentredString(Graphics g, String s, int y)
    {
        g.drawString(s, ((int) (getWidth() - g.getFontMetrics().getStringBounds(s, g).getWidth()) / 2),  y);
    }

    //spawns new ship
    private void createVoyager()
    {
    	soundManager.play("create");
    	voyager = new Voyager(spawnPoint,this);
    }

	Voyager getVoyager()
	{
		return voyager;
	}

	Shots getShots() 
	{
		return firedShots;
	}
	
	SoundManager getSoundManager() 
    {
        return soundManager;
    }

            


}



