package utilities;

import javax.swing.JFrame;

import spaceRocks.HighScores;

public class HighScoresTest {

    public static void main(String[] args) throws Exception {
    	JFrame frame= new JFrame("Asteroids");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null); //center on screen
        HighScores highScores=new HighScores();
        highScores.load();
        String player="KGT";
        highScores.saveHighScore(200,player,"1:20");
        highScores.saveHighScore(300,player,"1:30");
        highScores.saveHighScore(200,player,"1:21");
        highScores.saveHighScore(1300,player,"2:30");
        highScores.saveHighScore(2200,player,"3:30");
        highScores.save();
        highScores.load();
        System.exit(0);
        frame.setVisible(true);
    }
}
