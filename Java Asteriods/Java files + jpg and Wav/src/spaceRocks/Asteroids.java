package spaceRocks;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
/*
 * the collection of randomly generated and vectored rock shapes
 * applies travel and collision etc processing to the collection items
 * and splits or removes "hit" items.
 */
public class Asteroids
{

    private ArrayList<Asteroid> swarm;
    private int asteroidCount;
    static private int minDistance = 200;
    private Game game;
    
    Asteroids(Game game)
    {
        this.game=game;
        asteroidCount = 4;
    }
    
    void reSpawn()
    {
   		asteroidCount += 2;
        createSwarm();
    }
    
    Asteroids createSwarm() 
    {
        swarm = new ArrayList<Asteroid>(asteroidCount*2);
        for (int i = 0; i < asteroidCount; i++) 
        {
        	Asteroid a=Asteroid.getRandomAsteroid(3,game.getBounds());
            while ( a.overlaps(game.getVoyager().getBounds()) || 
            		Math.abs(a.getPosition().x - game.getVoyager().getPosition().x) < minDistance) 
            {
                a = Asteroid.getRandomAsteroid(3,game.getBounds());
            }
            addAsteroid(a);
        }
        return this;
    }
    
    public void boundsCheck(Rectangle bounds) 
    {
        for (Asteroid a : swarm) 
        {
        	a.boundsCheck(bounds);
        }
    }
    
    public void travel() 
    {
        for (Asteroid a : swarm) 
        {
            a.travel();
        } 		
    }
    
    public void draw(Graphics g) 
    {
        for (Asteroid a : swarm) 
        {
            a.draw(g);
        }
    }
    
    public boolean swarmDestoyed()
    {
        return (swarm == null || swarm.size() == 0);
    }
    
    public void addAsteroid(Asteroid a) 
    {
        swarm.add(a);
    }
    
    void splitAsteroid(int index)
    {
    	try
    	{
    		Asteroid a=swarm.remove(index);
    		addAsteroid(new Asteroid(a.getSizeClassification() - 1, a.getPosition().x, a.getPosition().y));
    		addAsteroid(new Asteroid(a.getSizeClassification() - 1 , a.getPosition().x, a.getPosition().y));
    	} catch (Exception ex) {
    	}  
    }
    
    public void removeAsteroid(int index)
    {
    	swarm.remove(index);
    }
    
    public int swarmSize()
    {
    	return swarm.size();
    }
    
    public Asteroid getAsteroid(int index)
    {
    	return swarm.get(index);
    }
}
