package Exercise1;


//You are supposed to add your comments

import java.util.*;

/**+
 * calculator for AI move
 */
class AIplayer {
    List<Point> availablePoints;     // available points (not used?)
    List<PointsAndScores> rootsChildrenScores;  //place to put scores for possible moves
    Board b = new Board();  // board (not used?)

    public AIplayer() { // empty constructor
    }

    /**+
     * loops through calculated scores to find the best move
     * @return the best move
     */
    public Point returnBestMove() {
        int MAX = -100000;
        int best = -1;

        // picks first move with best score
        for (int i = 0; i < rootsChildrenScores.size(); ++i) {
            if (MAX < rootsChildrenScores.get(i).score) {
                // this is the best so far
                MAX = rootsChildrenScores.get(i).score;
                best = i;
            }
        }
        // returns the point with the best score
        return rootsChildrenScores.get(best).point;
    }

    /**+
     * return the minimum value in the list
     * assumed non empty list
     * @param list
     * @return smallest value
     */
    public int returnMin(List<Integer> list) {
        int min = Integer.MAX_VALUE;
        int index = -1;

        for (int i = 0; i < list.size(); ++i) {
            if (list.get(i) < min) {
                //smallest so far
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
    public int returnMax(List<Integer> list) {
        int max = Integer.MIN_VALUE;
        int index = -1;

        for (int i = 0; i < list.size(); ++i) {
            if (list.get(i) > max) {
                //Biggest so far
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
    public void callMinimax(int depth, int turn, Board b){
        rootsChildrenScores = new ArrayList<>();
        minimax(depth, turn, b);
    }

    /**+
     * main minimax routine
     * @param depth
     * @param turn
     * @param b
     * @return min or max score
     */
    public int minimax(int depth, int turn, Board b) {
        // check for game over
        if (b.hasXWon()) return 1;  //posative score for winning
        if (b.hasOWon()) return -1; //negative score for lossing
        // get avaible points at that stage
        List<Point> pointsAvailable = b.getAvailablePoints();
        // return draw if no more points avaible and no winner
        if (pointsAvailable.isEmpty()) return 0;
        // create list for scores
        List<Integer> scores = new ArrayList<>();

        // for each avaible point calculate a score
        for (int i = 0; i < pointsAvailable.size(); ++i) {
            // get points
            Point point = pointsAvailable.get(i);

            if (turn == 1) {
                // place the point as a computer move
                b.placeAMove(point, 1);
                // get the score of the new board with a new depth and player 2
                int currentScore = minimax(depth + 1, 2, b);
                // add to scores
                scores.add(currentScore);
                if (depth == 0)
                    // save the root scores
                    rootsChildrenScores.add(new PointsAndScores(currentScore, point));

            } else if (turn == 2) {
                // place the point as a player 2 move
                b.placeAMove(point, 2);
                // calculate the score of the new board
                scores.add(minimax(depth + 1, 1, b));
            }
            // undo the move to reset the board
            b.placeAMove(point, 0);
        }
        // return the maximum or minimum score found depedning on whos turn it is
        // if turn equals 1 for the ai player return max score otherwise return the min value as it is players turn
        return turn == 1 ? returnMax(scores) : returnMin(scores);
    }
}


