package spaceRocks;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;
/*
 * Manages loading and saving in order insert of score into the top 10 list
 */
public class HighScores
{
	private static String scoreFile = "highscores.txt"; 
    private List<Score> scores =new ArrayList<Score>(10);
    
    public void load()
    {
        scores.clear();
        try
        {
            try(BufferedReader br = new BufferedReader(new FileReader(scoreFile))) {
                for(String line; (line = br.readLine()) != null; ) {
                    scores.add(new Score(line));
                }
            }
        }
        catch (IOException e)
        {
            while(scores.size()<10)
                scores.add(new Score());
        }
    }
    
    public void save()
    {
        try
        {
            BufferedWriter writer = new BufferedWriter(new FileWriter(scoreFile));
            for(Score s:scores)
            {
                writer.write(s.toString()+"\n");
            }
            writer.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    
    List<String> toList()
    {
        ArrayList<String> l=new ArrayList<String>(10);
        for(Score s:scores)
        {
        	if (s.getScore()>0)
        		l.add(s.toText());
        }
    	if (l.size()==0)
    		l.add(new Score().toText());
        return l;
    }

    boolean checkHighScore(int playerScore)
    {
        //compare score with those in the list... 
        int i = 0;
        while(i < scores.size() && scores.get(i).getScore() >= playerScore)
        {
            i++;
        }
        return (i < scores.size());
    }
    
    // used for testing
    String inputInitials()
    {
        String input = JOptionPane.showInputDialog("Enter your initials (eg 'KGT')");
        if (input!=null)
        {
        	input=input.toUpperCase();
        	if (input.matches("^[A-Z]*") && input.length()<4 )
	        {
	            return input;
	        }
        }
        return "___";
    }
        
    public void saveHighScore(int playerScore,String player,String gameTime)
    {
        //compare score with those in the list
        int i = 0;
        while(i < scores.size() && scores.get(i).getScore() >= playerScore)
        {
            i++;
        }
        if(i < scores.size())
        {
            // it is a higher score than at least one in the list
            // so shuffle down score from this point
            for(int j = scores.size()-1; j > i; j--)
            {
                scores.set(j,scores.get(j - 1));
            }
            // and insert the new score
            scores.set(i,new Score(playerScore,player!=null?player:inputInitials(),new Date(),gameTime));
        }
    }

 }
