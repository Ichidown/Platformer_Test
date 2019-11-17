package Entity;


import GameState.LevelState;

public class DeadEntity
{
    int  width,height;

    int StartAnimationDelay,LoopAnimationDelay,EndAnimationDelay;/** to implement : start/loop/end Animations **/

    String startSpritePath,loopSpritePath,endSpritePath;

    int startNumFrames,LoopNumFrames,EndNumFrames;

    LevelState levelState;

    ShapeShifter shape;


    public DeadEntity(int width, int height, int startAnimationDelay, String startSpritePath, int NumFrames, LevelState lv, ShapeShifter shape)
    {
        this.width = width;
        this.height = height;
        StartAnimationDelay = startAnimationDelay;
        this.startSpritePath = startSpritePath;
        this.startNumFrames=NumFrames;
        levelState=lv;
        this.shape=shape;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getStartAnimationDelay() {
        return StartAnimationDelay;
    }

    public String getStartSpritePath() {
        return startSpritePath;
    }

    public int getStartNumFrames() {
        return startNumFrames;
    }

    public ShapeShifter getShape() {
        return shape;
    }
}
