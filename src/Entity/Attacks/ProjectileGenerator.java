package Entity.Attacks;


import Entity.Attacks.Inflictions.InflictionObj;
import Entity.MapObject;

import java.awt.*;
import java.util.ArrayList;

public class ProjectileGenerator
{
    private int number=1,initialNumber;
    private int initialDelay=0;
    private int delay=0;
    //private int precision;
    private int ProjectileId;
    private MapObject source;
    private Point fixedSource=new Point();

    private int XDisplacement=0,YDisplacement=0;
    private int RandomDisplacement=0;
    private int accuracy=0;
    private boolean stopWhenObjectDead=false;
    private String order="random";

    private int currentDeviation=-1;
    private int additionalDeviation;//additional deviation is the degree added between each projectile creation
    private boolean shouldIncrementDeviation=true;
    private MapObject target;
    private ArrayList<InflictionObj> inflictions;


    public ProjectileGenerator(int ProjectileId, ArrayList<InflictionObj> inflictions)//, int number, int initialDelay, MapObject source, MapObject target , int XDisplacement, int YDisplacement, int RandomDisplacement, int accuracy, String order)
    {/**each time we add a variable here , we must add codes in projectile generator transfer (in Unit-newProjectileGenerator)*/
        this.ProjectileId=ProjectileId;
        this.inflictions=inflictions;


        /*this.initialDelay = initialDelay;
        this.source=source;
        this.target=target;

        this.XDisplacement = XDisplacement;
        this.YDisplacement = YDisplacement;
        this.RandomDisplacement = RandomDisplacement;

        this.accuracy=accuracy;
        this.order=order;*/
    }



    public void setNumber(int number) {
        if(number >=0)
            this.number = number;

        else
        {
            this.number = number;
            this.initialNumber=number;
        }
    }

    public void setInitialDelay(int initialDelay) {
        this.initialDelay = initialDelay;
    }

    public void setXDisplacement(int XDisplacement) {
        this.XDisplacement = XDisplacement;
    }

    public void setYDisplacement(int YDisplacement) {
        this.YDisplacement = YDisplacement;
    }

    public void setRandomDisplacement(int randomDisplacement) {
        RandomDisplacement = randomDisplacement;
    }

    public void setAccuracy(int accuracy) {
        this.accuracy = accuracy;
    }

    public ArrayList<InflictionObj> getInflictions()
    {
        return inflictions;
    }

    public void decrementNumber()/** when number is negative , negative delay don't work properly**/
    {
        if(number > 0)//when negative : don't decrement
            number--;
    }

    public void emptyNumber()/** when number is negative , negative delay don't work properly**/
    {
            number=0;
    }

    public void loopDelay()
    {
        if(delay>0)
        {
            delay--;
        }
        else
        {
            delay=initialDelay;
        }
    }

    public int getNumber() {
        return number;
    }

    public int getDelay() {
        return delay;
    }

    public int getProjectileId() {
        return ProjectileId;
    }

    public void setProjectileId(int projectileId) {
        ProjectileId = projectileId;
    }

    public int getInitialDelay() {
        return initialDelay;
    }

    public MapObject getSource()
    {
        return source;
    }

    public void setSource(MapObject source) {
        this.source = source;
    }

    public MapObject getTarget() {
        return target;
    }

    public void setTarget(MapObject target) {
        this.target = target;
    }

    public int getXDisplacement() {
        return XDisplacement;
    }

    public int getYDisplacement() {
        return YDisplacement;
    }

    public int getRandomDisplacement() {
        return RandomDisplacement;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public Point getFixedSource() {
        return fixedSource;
    }

    public void setFixedSource(Point fixedSource) {
        this.fixedSource = fixedSource;
    }

    public boolean isStopWhenObjectDead() {
        return stopWhenObjectDead;
    }

    public void setStopWhenObjectDead(boolean stopWhenObjectDead) {
        this.stopWhenObjectDead = stopWhenObjectDead;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public int getAdditionalDeviation() {
        return additionalDeviation;
    }

    public void setAdditionalDeviation(int additionalDeviation) {
        this.additionalDeviation = additionalDeviation;
    }

    public int getCurrentDeviation() {
        return currentDeviation;
    }

    public void setCurrentDeviation(int currentDeviation) {
        this.currentDeviation = currentDeviation;
    }

    public boolean isShouldIncrementDeviation() {
        return shouldIncrementDeviation;
    }

    public void setShouldIncrementDeviation(boolean shouldIncrementDeviation) {
        this.shouldIncrementDeviation = shouldIncrementDeviation;
    }
}
