package Main;

import GameState.GameStateManager;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Arc2D;
import java.awt.image.BufferedImage;
import java.security.Key;

public class Game_Panel extends JPanel implements Runnable//,KeyListener,MouseListener,MouseMotionListener
{
    //dimensions
    public static final int WIDTH = 900;//X3
    public static final int HEIGHT = 600;//X3
    public static final int SCALE = 1;//scale the size of window x2
    public static final int borders = 20;

    //game thread
    private Thread thread;
    private boolean running;
    public static int FPS = 60;
    private long targetTime = 1000/FPS ;
    //public static double averageFPS;
    //private int frameCount;

    //image
    private BufferedImage image;
    private Graphics2D g;

    //game state manager
    public GameStateManager gsm;

    //mouse
    Point mouse_pos=new Point(10,10);
    public static int navigationSpeed=50;
    //public int maxNavigationSpeed=5; !!!! to do !!!!

    long start;
    long elapsed;
    long wait;

    Game_Logic GLogic;


    public Game_Panel()
    {
        super();// ???
        setPreferredSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE));
        setFocusable(true);//???
        requestFocus();//???
    }


    public void addNotify()// ?
    {
        super.addNotify();
        if(thread == null)
        {
            thread = new Thread(this,"DrawThread");
            //.......init Controls.......
            Controls C=new Controls(this);

            addKeyListener(C);//this);
            addMouseListener(C);//this);
            addMouseMotionListener(C);//this);
            //...........................
            thread.start();
        }
    }

    public void initialiseGame()
    {
        image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);//???
        g = (Graphics2D) image.getGraphics();// ???
        running =true;

        gsm = new GameStateManager();

        GLogic = new Game_Logic(gsm);
    }

    public void run()
    {
        initialiseGame();

        //game loop
        while(running)
        {
                start = System.nanoTime();//???

                //update();
                draw();
                drawToScreen();

                elapsed = System.nanoTime() - start;
                //frameCount++;

                wait = targetTime - elapsed / 1000000;//targetTime:mili seconds //elapsed:nano seconds


                if (wait < 0) {
                    wait = 0;//time to wait before start drawing //was 5
                    //frameCount=0;
                }

                try {
                    Thread.sleep(wait);
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
    }
    private void draw()
    {
        if(gsm!=null)
            gsm.draw(g);
    }
    private void drawToScreen()
    {
        Graphics2D g2 = (Graphics2D) getGraphics();
        //g2.scale(1.2,1.2);
        g2.drawImage(image,0,0,WIDTH *SCALE,HEIGHT*SCALE,null);
        g2.dispose();
    }
}
