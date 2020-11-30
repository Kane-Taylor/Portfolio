package Exercise1;

//You are supposed to add your comments

import java.util.*;

/**+
 * class that holds a point and provides an override for toString
 */
class Point {
    int x, y;   // x and y coordinates

    /**+
     * constructor to initialize x and y
     * @param x
     * @param y
     */
    public Point(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
    /**+
     * override to provide a formated point string
     * @return formated string
     */
    @Override
    public String toString()
    {
        return "[" + (x+1) + ", " + (y+1) + "]";
    }
}

/**+
 * encapsulates a point with its score
 */
class PointsAndScores {
    int score;
    Point point;

    /**+
     * constructor for point and its score
     * @param score
     * @param point
     */
    PointsAndScores(int score, Point point) {
        this.score = score;
        this.point = point;
    }
}

/**+
 * this represents the playing board
 */
class Board {
    List<Point> availablePoints;    //list contains available points at any stage of the game
    Scanner scan = new Scanner(System.in); // utility scanner for recieving input
    int[][] board = new int[3][3];  // matrix represents the board (it would be better to have board size declared and used elsewhere)

    public Board() {
    }

    /**+
     * checks to see if the game has finished
     * this means checking for available moves and weather either side has got a winning board
     * @return GameOver
     */
    public boolean isGameOver() {
        return (hasXWon() || hasOWon() || getAvailablePoints().isEmpty());
    }

    /**+
     * checks for diagonal/horrizontal/vertical lines all being set for player 1
     * @return if a winning line has been found
     */
    public boolean hasXWon() {
        // checks diagonal
        if ((board[0][0] == board[1][1] && board[0][0] == board[2][2] && board[0][0] == 1) || (board[0][2] == board[1][1] && board[0][2] == board[2][0] && board[0][2] == 1)) {
            return true;
        }
        // checks horrizontal and vertical lines
        for (int i = 0; i < 3; ++i) {
            if (((board[i][0] == board[i][1] && board[i][0] == board[i][2] && board[i][0] == 1)
                    || (board[0][i] == board[1][i] && board[0][i] == board[2][i] && board[0][i] == 1))) {
                return true;
            }
        }
        return false;
    }
    /**+
     * checks for diagonal/horrizontal/vertical lines all being set for player 2
     * @return if a winning line has been found
     */
    public boolean hasOWon() {
        // checks diagonal
        if ((board[0][0] == board[1][1] && board[0][0] == board[2][2] && board[0][0] == 2) || (board[0][2] == board[1][1] && board[0][2] == board[2][0] && board[0][2] == 2)) {
            return true;
        }
        // checks horrizontal and vertical lines
        for (int i = 0; i < 3; ++i) {
            if ((board[i][0] == board[i][1] && board[i][0] == board[i][2] && board[i][0] == 2)
                    || (board[0][i] == board[1][i] && board[0][i] == board[2][i] && board[0][i] == 2)) {
                return true;
            }
        }
        return false;
    }

    /**=
     * gets a list of unused points
     * @return  list of available points
     */

    public List<Point> getAvailablePoints() {
        // Initialize available points list
        availablePoints = new ArrayList<>();
        // Loop through board arrays and check for unassigned points
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                if (board[i][j] == 0) {
                    // Point has not been used so add to available list
                    availablePoints.add(new Point(i, j));
                }
            }
        }
        return availablePoints;
    }

    /**+
     * gets the value of the point on the board
     * @param point
     * @return x and y coordiantes
     */
    public int getState(Point point){
        return board[point.x][point.y];
    }

    /**+
     * Sets the value of the point on the board
     * @param point
     * @param player
     */
    public void placeAMove(Point point, int player) {
        board[point.x][point.y] = player;
    }

    /**+
     * displays the board in the console using X, O and .
     */
    public void displayBoard() {
        System.out.println();
        // Loop around rows and colums of the board
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                //  print appropriate character depedning on value
                if (board[i][j]==1)
                    System.out.print("X ");
                else if (board[i][j]==2)
                    System.out.print("O ");
                else
                    System.out.print(". ");
            }
            System.out.println();
        }
    }
}
