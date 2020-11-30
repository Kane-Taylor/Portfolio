package spaceRocks;

import javax.swing.JFrame;

public class Main {

    //main to start game, creates jframe and runs game class
    public static void main(String[] args) throws Exception 
    {
    	JFrame frame= new JFrame("CE203 Assignment 2 Reg 1703742 - 'Kasteroids' by Kane");
        frame.setSize(900, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null); 
        
        // Game is where it all happens...
        Game game = new Game(frame);
        
        frame.setVisible(true);
    }
    
}


// Image used for background taken from - https://pngtree.com/freebackground/charming-fantasy-night-sky-background_351794.html