package Entity;

import Main.Game_Logic;
import Main.Game_Panel;

import java.awt.image.BufferedImage;

public class Animation
{
    private BufferedImage[] frames;
    private int currentFrame;
    private long startTime;//timer between frames
    private long delay;
    private boolean playedOnce;//for animation that need to be played 1 time like atack


    public Animation()
    {
        playedOnce=false;
    }
    public void setFrames(BufferedImage[] frames)
    {
        this.frames=frames;
        currentFrame=0;
        startTime=System.nanoTime();
        playedOnce=false;
    }

    public void setDelay(long d)
    {
        delay=d;
    }
    public void setFrame(int i){currentFrame = i;}

    public void update()/** review this **/
    {
        if(delay==-1) return;//there is no animation

        long elapsed = (System.nanoTime()-startTime)* (Game_Logic.FPS/70)/10000000;//time elapsed since last frame came up

        if(elapsed>delay)//move on to the next frame
        {
            currentFrame++;
            startTime = System.nanoTime();
        }
        if(currentFrame==frames.length)//to loop the animation
        {
            currentFrame = 0;//reset index or frameList
            playedOnce = true;
        }

    }
    public int getCurrentFrame()
    {
        return currentFrame;
    }
    public BufferedImage getCurrentImage()
    {
        /** frames may not have an index ==  currentFrame cause to an exception when frames might be null*/
        try {//remove this try/catch
            return frames[currentFrame];
        }catch (Exception e)
        {e.printStackTrace();
            return null;
        }
    }

    public boolean hasPlayedOnce()
    {
        return playedOnce;
    }
    public void setPlayedOnce()
    {
        playedOnce=true;
    }
}
