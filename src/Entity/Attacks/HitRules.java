package Entity.Attacks;


import Entity.Character.Hits;

import java.util.ArrayList;

public class HitRules
{
    protected double range;

    private ArrayList<Hits> HitUnits=new ArrayList<>();//must add int to tell how much did we hit this unit
    private int localHitTimes =1;
    private int globalHitTimes =-1;// 0 means no globalHitcount

    private int localHitDelay=0;
    private int globalHitDelay=0;
    private int TemporaryGlobalHitDelay =0;

    private boolean targeted=false;

    public HitRules(double range, int localHitTimes, int globalHitTimes, int localHitDelay, int globalHitDelay, boolean targeted)
    {
        this.range = range;
        this.localHitTimes = localHitTimes;
        this.globalHitTimes = globalHitTimes;
        this.localHitDelay = localHitDelay;
        this.globalHitDelay = globalHitDelay;
        this.targeted = targeted;
    }

    public double getRange() {
        return range;
    }

    public void setRange(double range) {
        this.range = range;
    }

    public ArrayList<Hits> getHitUnits() {
        return HitUnits;
    }

    /*public void setHitUnits(ArrayList<Hits> hitUnits) {
        HitUnits = hitUnits;
    }*/

    public int getLocalHitTimes() {
        return localHitTimes;
    }

    public void setLocalHitTimes(int localHitTimes) {
        this.localHitTimes = localHitTimes;
    }

    public int getGlobalHitTimes() {
        return globalHitTimes;
    }

    public void setGlobalHitTimes(int globalHitTimes) {
        this.globalHitTimes = globalHitTimes;
    }

    public int getLocalHitDelay() {
        return localHitDelay;
    }

    public void setLocalHitDelay(int localHitDelay) {
        this.localHitDelay = localHitDelay;
    }

    public int getGlobalHitDelay() {
        return globalHitDelay;
    }

    public void setGlobalHitDelay(int globalHitDelay) {
        this.globalHitDelay = globalHitDelay;
    }

    public int getTemporaryGlobalHitDelay() {
        return TemporaryGlobalHitDelay;
    }

    public void setTemporaryGlobalHitDelay(int temporaryGlobalHitDelay) {
        TemporaryGlobalHitDelay = temporaryGlobalHitDelay;
    }

    public boolean isTargeted() {
        return targeted;
    }

    public void setTargeted(boolean targeted) {
        this.targeted = targeted;
    }



    //***********************************************


    public boolean getGlobalHitCount()
    {
        if(globalHitTimes<0)
            return false;
        else
            return true;
    }

    public void loopGlobalHitDelay()
    {//to optimise
        if(TemporaryGlobalHitDelay >0)
            TemporaryGlobalHitDelay--;
        else
            TemporaryGlobalHitDelay = globalHitDelay;
    }
}
