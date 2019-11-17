package Main;

import GameState.GameStateManager;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Arc2D;
import java.awt.image.BufferedImage;
import java.security.Key;

public class Game_Logic implements Runnable/** after adding this we got a new problem : we can ,sometimes, see spaces between map tiles **/
{
    //game thread
    private Thread thread;
    private boolean running;
    public static int FPS = 120;
    private long targetTime = 1000/FPS ;
    public GameStateManager gsm;

    long start;
    long elapsed;
    long wait;


    public Game_Logic(GameStateManager gsm)
    {
        super();//not needed .. maybe
        this.gsm=gsm;
        addNotify();
    }


    public void addNotify()// ?
    {
        if(thread == null)
        {
            thread = new Thread(this,"LogicThread");
            thread.start();/**pause this & u pause the game */
            /** need to synchronize the two threads & prioritise the logic one : this one */
        }
    }

    private void initialiseGame()
    {
        running =true;
    }

    public GameStateManager getGsm()
    {
        return gsm;
    }

    public void run()
    {
        initialiseGame();

        while(running)
        {
            start = System.nanoTime();//???

            update();

            elapsed=System.nanoTime()- start;

            wait = targetTime - elapsed / 1000000;//targetTime:mili seconds //elapsed:nano seconds


            if(wait<0)
            {
                wait = 0;//time to wait before start drawing //was 5
            }

            try
            {
                Thread.sleep(wait);
            }catch(Exception e){e.printStackTrace();}
        }
    }
    private void update()
    {
        gsm.update();
    }
}
