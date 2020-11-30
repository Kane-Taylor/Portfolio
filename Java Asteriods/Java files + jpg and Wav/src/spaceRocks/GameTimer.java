package spaceRocks;
/*
 * Simple time tracker to measure game time
 */
public class GameTimer 
{
    private long startMillis;

    public GameTimer() 
    {
    	reset();
    }

    public void reset() 
    {
        startMillis = System.currentTimeMillis();
    }
    
    public String toString() 
    {
    	long seconds = (System.currentTimeMillis() - startMillis) / 1000;
        return String.format(" %d:%02d",(seconds / 60), (seconds % 60));
    }
}
