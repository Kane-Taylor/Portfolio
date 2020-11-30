package Exercise1;

//You are supposed to add your comments

import java.util.*;

/**+
 * the game
 */
public class TicTacToe {
    /**+
     * main game entry point and loop
     */
    public static void main(String[] args) {
        AIplayer AI= new AIplayer();
        Board b = new Board();
        // these next two members are not currently used could be used for randomization of choices and initilazation
        Point p = new Point(0, 0);
        Random rand = new Random();
        // display initial state of borad (empty)
        b.displayBoard();
        // promt who to see who goes first
        System.out.println("Who makes first move? (1)Computer (2)User: ");
        int choice = b.scan.nextInt();
        if(choice == 1){
            // computer goes first so calcualte best starting move
            AI.callMinimax(0, 1, b);
            for (PointsAndScores pas : AI.rootsChildrenScores) {
                System.out.println("Point: " + pas.point + " Score: " + pas.score);
            }
            // plot chosen point of the computer
            b.placeAMove(AI.returnBestMove(), 1);
            // display updated board
            b.displayBoard();
        }
        // main game loop which continues until the game is over
        while (!b.isGameOver()) {
            // promt for the players move
            System.out.println("Your move: line (1, 2, or 3) colunm (1, 2, or 3)");
            Point userMove = new Point(b.scan.nextInt()-1, b.scan.nextInt()-1);
            // validate move or re-prompt
            while (b.getState(userMove)!=0) {
                System.out.println("Invalid move. Make your move again: ");
                userMove.x=b.scan.nextInt()-1;
                userMove.y=b.scan.nextInt()-1;
            }
            // plot move
            b.placeAMove(userMove, 2);
            // display updated board
            b.displayBoard();
            // if this move wins the game break from loop
            if (b.isGameOver()) {
                break;
            }
            // calculate computers response by calling minimax
            AI.callMinimax(0, 1, b);
            for (PointsAndScores pas : AI.rootsChildrenScores) {
                System.out.println("Point: " + pas.point + " Score: " + pas.score);
            }
            // plot the picked best move
            b.placeAMove(AI.returnBestMove(), 1);
            // display updated board
            b.displayBoard();
        }
        // print out result of the game
        if (b.hasXWon()) {
            System.out.println("Unfortunately, you lost!");
        } else if (b.hasOWon()) {
            System.out.println("You win!");
        } else {
            System.out.println("It's a draw!");
        }
    }
}