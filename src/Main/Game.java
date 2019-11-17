package Main;

import javax.swing.JFrame;
import java.awt.*;

public class Game
{
    public static void main(String args[])
    {
        JFrame window = new JFrame("League of Champions");

        Game_Panel GPanel = new Game_Panel();

        window.setContentPane(GPanel);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.pack();
        window.setVisible(true);

        /** to make the mouse invisible*/
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Cursor cursor = toolkit.createCustomCursor(toolkit.getImage(""),new Point(0,0),"cursor");//define a blanc cursor
        window.setCursor(cursor);
        /******************************/
    }
}
