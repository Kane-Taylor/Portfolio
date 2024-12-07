import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class TicTacToe {
    //boardsize
    int width = 800;
    int height = 850;

    //set frame and label
    JFrame window = new JFrame("Tic-Tac-Toe");
    JLabel label = new JLabel();
    JPanel panel = new JPanel();

    //create board
    JPanel board = new JPanel();

    //2D array for buttons
    JButton[][] tiles = new JButton[3][3];
    String player1 = "X";
    String player2 = "O";
    String currentPlayer = player1;

    //check for winner
    boolean gameOver = false;

    //check if draw
    int turns = 0;

    //restart button
    JPanel resetPannel = new JPanel();
    JButton reset = new JButton();


    TicTacToe()
    {
        //window settings
        window.setVisible(true);
        window.setSize(width, height);
        window.setLocationRelativeTo(null);
        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLayout(new BorderLayout());
        //label
        label.setBackground(Color.LIGHT_GRAY);
        label.setForeground(Color. BLACK);
        label.setFont(new Font("Arial", Font.BOLD, 50));
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setText("Tic-Tak-Toe");
        label.setOpaque(true);
        //set title at top of screen
        panel.setLayout((new BorderLayout()));
        panel.add(label);
        window.add(panel, BorderLayout.NORTH);

        //reset button
        resetPannel.add(reset);
        reset.setFont(new Font("Arial", Font.BOLD, 60));
        reset.setText("Reset");
        window.add(resetPannel, BorderLayout.SOUTH);

        //set up board
        board.setLayout(new GridLayout(3, 3));
        board.setBackground(Color.LIGHT_GRAY);
        window.add(board);

        //for buttons
        for (int r = 0; r < 3; r++)
        {
            for (int c = 0; c < 3; c++){
                JButton tile = new JButton();
                tiles[r][c] = tile;
                board.add(tile);

                tile.setBackground(Color.BLACK);
                tile.setForeground(Color.WHITE);
                tile.setFont(new Font("Arial", Font.BOLD, 150));
                tile.setFocusable(false);

                //action listener for tiles
                tile.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if(gameOver)return;
                        JButton tile = (JButton) e.getSource();
                            if (tile.getText() == "") {
                                tile.setText(currentPlayer);
                                turns++;
                                gameEnd();
                                if (!gameOver) {
                                    currentPlayer = currentPlayer == player1 ? player2 : player1;
                                    label.setText(currentPlayer + "'s turn.");
                                }
                            }
                        }
                });
                reset.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        tile.setBackground(Color.BLACK);
                        tile.setForeground(Color.WHITE);
                        tile.setText("");
                        gameOver = false;
                        int turns = 0;
                        currentPlayer = player1;
                        label.setText(currentPlayer + "'s turn.");
                        }
                });
            }
        }

    }

    //used to check if someone has won or game has ended
    void gameEnd()
    {
        //check horizontal
        for (int r = 0; r < 3; r++)
        {
            if (tiles[r][0].getText() == "")continue;
            if (tiles[r][0].getText() == tiles[r][1].getText() && tiles[r][1].getText() == tiles[r][2].getText())
            {
                for (int i = 0; i < 3; i++){
                    setVictor(tiles[r][i]);
                }
                gameOver = true;
                return;
            }
        }
        //check column
        for (int c = 0; c < 3; c++)
        {
            if (tiles[0][c].getText() == "")continue;
            if (tiles[0][c].getText() == tiles[1][c].getText() && tiles[1][c].getText() == tiles[2][c].getText())
            {
                for (int i = 0; i < 3; i++){
                    setVictor(tiles[i][c]);
                }
                gameOver = true;
                return;
            }
        }
        //check diagonal
        if (tiles[0][0].getText() == tiles[1][1].getText() && tiles[1][1].getText() == tiles[2][2].getText() && tiles[0][0].getText() != "")
        {
            for (int i = 0; i < 3; i++)
            {
                setVictor(tiles[i][i]);
            }
            gameOver = true;
            return;
        }

        //anti-diagonal
        if (tiles[0][2].getText() == tiles[1][1].getText() && tiles[1][1].getText() == tiles[2][0].getText() && tiles[0][2].getText() != "")
        {
            setVictor(tiles[0][2]);
            setVictor(tiles[1][1]);
            setVictor(tiles[2][0]);
            gameOver = true;
            return;
        }

        if (turns == 9)
        {
            setTie();
            gameOver = true;
        }
    }

    //show winning tile and player
    void setVictor(JButton tile)
    {
        tile.setForeground(Color.RED);
        tile.setBackground(Color.GRAY);
        label.setText(currentPlayer + " wins!");
    }

    void setTie()
    {
        label.setText("Tie!");
    }
}
