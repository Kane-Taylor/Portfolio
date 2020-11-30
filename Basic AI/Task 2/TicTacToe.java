package Task2;


/**
 * TicTacToe game
 *
 */
public class TicTacToe
{
    /**
     * Entry point to the Game
     * @param args unused
     */
    public static void main(String[] args)
    {
        //create the board with size 5
        final Board b = new Board(5);
        // and Ai with AlphaBeta pruning on
        final AIplayer AI= new AIplayer(true);
        // ask who starts first
        System.out.println("Who makes first move? (1)Computer (2)User: ");
        if(b.scanInt(2) == 1)
        {
            //computer to start
            // pick one of the best 5 moves
            b.UpdateBoard(b.bestPoints.get(b.rand.nextInt(5)), 1);
        }
        else
        {
            //display empty board
            b.displayBoard();
        }
        //main game loop
        while (!b.isGameOver())
        {
            //update the board with the players move
            b.UpdateBoard(b.getPlayerMove(), 2);
            if (b.isGameOver())
            {
                break;
            }
            //update the board with the AI calculated players move
            b.UpdateBoard(AI.returnBestMove(b), 1);
        }
        // Print the result of the game
        b.printWinner();
    }
}