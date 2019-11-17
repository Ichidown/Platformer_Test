package GameState;

import java.awt.*;
import java.awt.event.MouseEvent;

public class GameStateManager
{
    //private ArrayList<GameState> gameStateList;
    private GameState[] gameStateList;
    public int currentState;
    public static final int MENUSTATE = 0;
    public static final int LEVELSTATE = 1;
    public static final int NUMGAMESTATES = 2;

    public GameStateManager()
    {
        gameStateList = new GameState[NUMGAMESTATES];
        currentState = LEVELSTATE;
        loadState(currentState);
    }
    private void loadState(int state)
    {
        if(state == MENUSTATE)
            gameStateList[state] = new MenuState(this);
        if(state == LEVELSTATE)
            gameStateList[state] = new LevelState(this);
    }

    private void unloadState(int state)
    {
        gameStateList[state] = null;
    }

    public void setState(int state)
    {
        //currentState = state;
        //gameStateList.get(currentState).initialiseGame();

        unloadState(currentState);
        currentState = state;
        loadState(currentState);
    }

    public void update()
    {
        //gameStateList.get(currentState).update();
        //try {
            gameStateList[currentState].update();
        //} catch(Exception e) {}
    }

    public void draw(Graphics2D g)//java.awt.Graphics2D
    {
        //gameStateList.get(currentState).draw(g);

        //try {
                gameStateList[currentState].draw(g);
            //} catch(Exception e) {}
    }

    public void keyPressed(int k)
    {
        //gameStateList.get(currentState).keyPressed(k);
        gameStateList[currentState].keyPressed(k);
    }
    public void keyReleased(int k)
    {
        //gameStateList.get(currentState).keyReleased(k);
        gameStateList[currentState].keyReleased(k);
    }

    public void mousePressed(int e, Point P)
    {
        gameStateList[currentState].mousePressed(e,P);
    }
    public void mouseReleased(int e, Point P)
    {
        gameStateList[currentState].mouseReleased(e,P);
    }
    public void mouseDragged(Point P)
    {
        gameStateList[currentState].mouseDragged(P);
    }
    public void mouseMoved(Point P)
    {
        gameStateList[currentState].mouseMoved(P);
    }

    public void mouseEntered(Point P)
    {
        gameStateList[currentState].mouseMoved(P);
    }

    public void mouseExited(Point P)
    {
        gameStateList[currentState].mouseMoved(P);
    }

}
