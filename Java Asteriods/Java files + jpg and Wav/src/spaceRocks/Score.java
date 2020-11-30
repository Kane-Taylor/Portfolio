package spaceRocks;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
/*
 * Used to display score using formatting routines
 */
public class Score
{
	private int score; // the score !
    private String player; // initials of the player
    private String played; // date played in the below simple dat format
    private String gameTime; // elapsed time of the game as minutes and seconds
    
    private static DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm"); 
    
    Score()
    {
        init();
    }
    
    Score(int score,String player,Date played,String gameTime)
    {
        this.score=score;
        this.player=player;
        this.played=df.format(played);
        this.gameTime=gameTime;
    }
    
    void init()
    {
    	// default values to bulk out the list
    	score=0;
        player="___";
        played=df.format(new Date());
        gameTime="0:00";
    }
    
    public Score(String in)
    {
        // just split apart the input line
        String[] parts = in.split("\\|");
        try 
        {
            played = parts[0];
            score=Integer.parseInt(parts[1]);
            player=parts[2];
            gameTime=parts[3];
        } catch (Exception e) {
            init();
        }
    }
    
    @Override
    public String toString()
    {
        return played+"|"+getScore()+"|"+player+"|"+gameTime;  
    }
    
    public String toText()
    {
    	return String.format("%s %9d  %s  %s",player,getScore(),gameTime,played);
    }

	public int getScore() {
		return score;
	}

}