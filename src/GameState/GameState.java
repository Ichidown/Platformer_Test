package GameState;

import java.awt.*;

public abstract class GameState
{

    protected GameStateManager gsm;

    public abstract void initialiseGame ();

    public abstract void update();

    public abstract void draw(Graphics2D g);

    public abstract void keyPressed(int k);

    public abstract void keyReleased(int k);

    public abstract void mouseMoved(Point P);

    public abstract void mouseDragged(Point P);

    public abstract void mousePressed(int e,Point P);

    public abstract void mouseReleased(int e,Point P);



    //public abstract void mouseEntered(Point P);

    //public abstract void mouseExited(Point P);

}
