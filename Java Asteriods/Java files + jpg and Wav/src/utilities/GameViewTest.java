package utilities;

import java.util.Random;


public class GameViewTest
{
    public static void main(String[] args)
    {
        // test the view component
        Random r = new Random();
        int w = 20;
        int h = 20;
        int[][] a = new int[w][h];
        for (int i = 0; i < w; i++)
        {
            for (int j = h/2; j < h; j++)
            {
                a[i][j] =
                r.nextInt(GameView.colors.length);
            }
        }
        GameView tv = new GameView(a);
        new JEasyFrame(tv, "Game View");
    }
}