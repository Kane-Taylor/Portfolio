package Task2;
import java.util.*;

/**
 * AI player Class
 * Optionally using AlphaBeta pruning
 */
class AIplayer
{
    List<Point> availablePoints;
    List<PointsAndScores> rootsChildrenScores;

    Integer alpha=Integer.MIN_VALUE;
    Integer beta=Integer.MAX_VALUE;
    private final boolean useAlphaBeta;

    /**+
     * called if Alpha Beta is going to be used
     * @param useAlphaBeta
     */
    public AIplayer(boolean useAlphaBeta)
    {
        this.useAlphaBeta=useAlphaBeta;
    }

    /**+
     * loops through calculated scores to find the best move
     * @return the best move
     */
    public Point returnBestMove(Board b)
    {
        callMinimax(0, 1, b );
        printScores();
        int MAX = -100000;
        int best = -1;
        for (int i = 0; i < rootsChildrenScores.size(); ++i)
        {
            if (MAX < rootsChildrenScores.get(i).score)
            {
                MAX = rootsChildrenScores.get(i).score;
                best = i;
            }
        }
        return rootsChildrenScores.get(best).point;
    }


    /**+
     * return the minimum value in the list
     * assumed non empty list
     * @param list
     * @return smallest value
     */
    public int returnMin(List<Integer> list)
    {
        int min = Integer.MAX_VALUE;
        int index = -1;
        for (int i = 0; i < list.size(); ++i)
        {
            if (list.get(i) < min)
            {
                min = list.get(i);
                index = i;
            }
        }
        return list.get(index);
    }

    /**+
     * return the Maximum value in the list
     * assumed non empty list
     * @param list
     * @return Max value
     */
    public int returnMax(List<Integer> list)
    {
        int max = Integer.MIN_VALUE;
        int index = -1;
        for (int i = 0; i < list.size(); ++i)
        {
            if (list.get(i) > max)
            {
                max = list.get(i);
                index = i;
            }
        }
        return list.get(index);
    }

    /**+
     * Top level minimax call initializes root scores
     * @param depth
     * @param turn
     * @param b
     */

    public void callMinimax(int depth, int turn, Board b)
    {
        rootsChildrenScores = new ArrayList<>();
        minimax(depth, turn, b, alpha,beta);
    }


    /**+
     * main minimax routine
     * @param depth
     * @param turn
     * @param b
     * @param alpha
     * @param beta
     * @return min or max score
     */
    public int minimax(int depth, int turn, Board b, Integer alpha, Integer beta)
    {
        if (depth>b.boardSize+2)
        {
            return b.currentScore();
        }
        if (b.hasWon(1) ) return b.maxScore;
        if (b.hasWon(2) ) return -b.maxScore;
        List<Point> pointsAvailable = b.getAvailablePoints();
        if (pointsAvailable.isEmpty()) return 0;

        List<Integer> scores = new ArrayList<>();
        for (int i = 0; i < pointsAvailable.size(); ++i)
        {
            Point point = pointsAvailable.get(i);
            if (turn == 1)
            {
                b.placeAMove(point, 1);
                int currentScore = minimax(depth + 1, 2, b, alpha,beta);
                scores.add(currentScore);
                if (depth == 0)
                {
                    rootsChildrenScores.add(new PointsAndScores(currentScore, point));
                }
                if (useAlphaBeta)
                {
                    if (currentScore > alpha)
                    {
                        //System.out.println("alpha="+currentScore+"->"+alpha);
                        alpha = currentScore;
                    }
                }
            }
            else if (turn == 2)
            {
                b.placeAMove(point, 2);
                int currentScore = minimax(depth + 1, 1, b, alpha,beta);
                scores.add(currentScore);
                if (useAlphaBeta)
                {
                    if (currentScore < beta)
                    {
                        //System.out.println("beta="+currentScore+"->"+beta);
                        beta = currentScore;
                    }
                }
            }
            b.placeAMove(point, 0);
            if (useAlphaBeta && alpha >= beta)
            {
                //System.out.println("break "+(i+1)+"/"+pointsAvailable.size()+" , "+alpha+">="+beta);
                break;
            }
        }
        if (useAlphaBeta)
            return turn == 1 ? alpha : beta;
        else
            return turn == 1 ? returnMax(scores) : returnMin(scores);
    }

    /**
     * helper to print current scores of available moves
     */
    public void printScores()
    {
        rootsChildrenScores.forEach((pas)->{System.out.println(pas);});
    }
}
