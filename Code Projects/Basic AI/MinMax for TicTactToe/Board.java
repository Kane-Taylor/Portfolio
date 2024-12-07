package Task2;

import java.util.*;

/**+
 * class that holds a point and provides an override for toString
 */
class Point
{
    int x, y;

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

    /**
     *generated hashCode and equals to be used with collections
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + x;
        result = prime * result + y;
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Point other = (Point) obj;
        if (x != other.x)
            return false;
        if (y != other.y)
            return false;
        return true;
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
class PointsAndScores
{
    int score;
    Point point;

    /**+
     * constructor for point and its score
     * @param score
     * @param point
     */
    PointsAndScores(int score, Point point)
    {
        this.score = score;
        this.point = point;
    }

    /**+
     * Used to return string that displays point and score
     * @return point and score
     */
    @Override
    public String toString()
    {
        return "Point: " + point + " Score: " + score;

    }
}

/**+
 * this represents the playing board
 */
class Board
{
    final int boardSize; // size of board as set at creation
    final int maxScore; //represent wine/lose value
    Random rand = new Random();

    List<Point> availablePoints;// current list of available points

    final Scanner scan = new Scanner(System.in); // scanner for user input
    final int[][] board; // board
    final List<Point> bestPoints;// semi-ordered list of preferred point moves

    /**+
     * Initialise the board and size related members based on parameter
     * @param size
     */
    public Board(int size)
    {
        boardSize=5;
        maxScore=boardSize*1000;
        board = new int[boardSize][boardSize];
        bestPoints=generateSemiRandomBestPoints();
    }

    /**+
     * checks to see if the game has finished
     * this means checking for available moves and weather either side has got a winning board
     * @return GameOver
     */
    public boolean isGameOver()
    {
        return (hasWon(1) || hasWon(2) || getAvailablePoints().isEmpty());
    }

    /**+
     * This is used to calculate the value of the score for a move for the ai against the value of that space for the player
     * @return final scores
     */
    public int currentScore()
    {
        return evaluateScore(1)-evaluateScore(2);
    }

    /**+
     * used to iterate through the points and give scores based on different conditions
     * @param turn
     * @return Return scores
     */
    public int evaluateScore(int turn)
    {
        int eval=0;
        //tweakable weightings for scoring lines
        int isMine=boardSize*10;
        int isEmpty=boardSize;
        int isNotMine=-1;
        //eval diags
        int evalDiag1=0;
        int evalDiag2=0;
        for (int i = 0; i < boardSize; ++i)
        {
            if (board[i][i]==turn)
                evalDiag1+=isMine;
            else
            if (board[i][i]!=0)
            {
                evalDiag1=isNotMine;
                break;
            }
            else
            {
                evalDiag1+=isEmpty;
            }
            if (board[boardSize-(i+1)][i]==turn)
                evalDiag2+=isMine;
            else
            if (board[boardSize-(i+1)][i]!=0)
            {
                evalDiag2=isNotMine;
                break;
            }
            else
            {
                evalDiag2+=isEmpty;
            }
        }
        eval+=evalDiag1+evalDiag2;
        // eval rows and cols
        for (int i = 0; i < boardSize; ++i)
        {
            int evalRow=0;
            int evalCol=0;
            for (int j = 0; j < boardSize; ++j)
            {
                if (evalRow>=0)
                    if (board[i][j]==turn)
                        evalRow+=isMine;
                    else
                    if (board[i][j]!=0)
                        evalRow=isNotMine;
                    else
                        evalRow+=isEmpty;
                if (evalCol>=0)
                    if (board[j][i]==turn)
                        evalCol+=isMine;
                    else
                    if (board[j][i]!=0)
                        evalCol=isNotMine;
                    else
                        evalCol+=isEmpty;
                if (evalCol<0&&evalCol<0)
                    break;
            }
            eval+=evalRow+evalCol;
        }
        return eval;
    }

    /**+
     * Checks to see if a player has a row or col
     * @param who
     * @return if the game has ended
     */
    public boolean hasWon(int who)
    {
        boolean windiag1=true;
        boolean windiag2=true;
        for (int i = 0; i < boardSize && (windiag1 || windiag2); ++i)
        {
            windiag1&=board[i][i]==who;
            windiag2&=board[boardSize-(i+1)][i]==who;
        }
        if (windiag1 || windiag2)
            return true;
        for (int i = 0; i < boardSize; ++i)
        {
            boolean winRow=true;
            boolean winCol=true;
            for (int j = 0; j < boardSize && (winRow || winCol); ++j)
            {
                winRow&=board[i][j]==who;
                winCol&=board[j][i]==who;
            }
            if (winRow || winCol)
                return true;
        }
        return false;
    }

    /**+
     * used to display the end message depending on end game board state
     */
    public void printWinner()
    {
        if (hasWon(1))
        {
            System.out.println("Unfortunately, you lost!");
        }
        else if (hasWon(2))
        {
            System.out.println("You win!");
        }
        else
        {
            System.out.println("It's a draw!");
        }
    }

    /**
     * @return list of available points in preferred order
     */
    public List<Point> getAvailablePoints()
    {
        availablePoints = new ArrayList<>();
        bestPoints.forEach((p)->{if (getState(p)== 0) { availablePoints.add(p); }}); // java 8
        return availablePoints;
    }

    /**
     * Uses prior knowledge to order the board points into groups of the best moves (such as the center)
     *
     * @return list of points in semi randomised order
     *
     */
    private List<Point> generateSemiRandomBestPoints()
    {
        List<Point> bestPoints=new ArrayList<>(boardSize*boardSize);
        List<Point> nextBest=new ArrayList<>(boardSize*boardSize);
        int maxVal=boardSize-1;
        if ((maxVal%2)==0)
        {
            //for odd size boards, centre point gives most winning possibilities
            int centre=(maxVal+1)/2;
            bestPoints.add(new Point(centre,centre));
        }
        // Corners are next best
        nextBest.addAll(Arrays.asList(new Point(0,0),new Point(0,maxVal),new Point(maxVal,0),new Point(maxVal,maxVal)));
        addToBestPoints(bestPoints,nextBest);
        //other diagonal points
        for(int i=1,max=maxVal-1;i<max;i++,max--)
        {
            nextBest.addAll( Arrays.asList(new Point(i,i),new Point(i,max),new Point(max,i),new Point(max,max)));
        }
        addToBestPoints(bestPoints,nextBest);
        for(int i=0;i<boardSize;i++)
        {
            for(int j=0;j<boardSize;j++)
            {
                Point next=new Point(i,j);
                if (!bestPoints.contains(next))
                {
                    nextBest.add(next);
                }
            }
        }
        addToBestPoints(bestPoints,nextBest);
        printPoints(bestPoints);
        return bestPoints;
    }

    /**
     * randomises the sub list before moving into the main list.
     * clearing the sub list in the process
     *
     * @param bestPoints current collection of best points
     * @param pl         next set of preferred points to be randomised and added
     */
    private void addToBestPoints(List<Point> bestPoints,List<Point> pl)
    {
        Collections.shuffle(pl, rand);
        bestPoints.addAll(pl);
        pl.clear();
    }

    /**+
     * prints lines that are valued higher in generateSemiRandomBestPoints
     * @param pl
     */
    private void printPoints(List<Point> pl)
    {
        System.out.print("Best order = ");
        pl.forEach((p)->{System.out.print(p);});//java 8 version
        System.out.println("");
    }

    /**+
     *  gets a point on the board based on input
     * @param point
     * @return
     */
    public int getState(Point point)
    {
        return board[point.x][point.y];
    }

    /**+
     *  uses getState to set it to a certain players move
     * @param point
     * @param player
     */
    public void placeAMove(Point point, int player)
    {
        board[point.x][point.y] = player;
    }

    /**+
     *updates the board and displays new board
     * @param point
     * @param player
     */
    public void UpdateBoard(Point point, int player)
    {
        placeAMove(point, player);
        displayBoard();
    }

    /**+
     * displays the board in the console using X, O and .
     */
    public void displayBoard()
    {
        System.out.println();
        for (int i = 0; i < boardSize; ++i)
        {
            for (int j = 0; j < boardSize; ++j)
            {
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

    /**+
     *  checks to see if a valid move is made
     * @return userMove
     */
    public Point getPlayerMove()
    {
        Point userMove = scanMove();
        while (getState(userMove)!=0)
        {
            System.out.println("Invalid move. Make your move again: ");
            userMove=scanMove();
        }
        return userMove;
    }

    /**+
     *  takes input from user and changes its value to fit the board
     * @return point
     */
    public Point scanMove()
    {
        System.out.println("Your move: line (1.."+boardSize+") colunm (1.."+boardSize+")");
        return new Point(scanInt(boardSize)-1, scanInt(boardSize)-1);
    }

    /**+
     *  checks to see if valid int has been inputed
     * @param max
     * @return int
     */
    public int scanInt(int max)
    {
        int n=0;
        while (n<1||n>max)
        {
            try
            {
                n = scan.nextInt();
            }
            catch(Exception e)
            {
                System.out.println("Invalid number "+e.getMessage());
                scan.nextLine();
            }
        }
        return n;
    }
}