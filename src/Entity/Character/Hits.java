package Entity.Character;


public class Hits
{
    private Unit unit;
    private int hitTimes;
    protected int localHitDelay,TempraryLocalHitDelay;
    public Hits(Unit unit,int hitTimes,int localHitDelay)
    {
        this.unit=unit;
        this.hitTimes=hitTimes;
        this.localHitDelay=localHitDelay;
        TempraryLocalHitDelay=localHitDelay;
    }

    public Unit getUnit()
    {
        return unit;
    }
    public int getHitTimes()
    {
        return hitTimes;
    }
    public void incrementHitTimes()
    {
        hitTimes++;
    }
    public int getTempraryLocalHitDelay()
    {
        return TempraryLocalHitDelay;
    }
    public void reinitialiseTemporaryLocalHitDelay()
    {
        TempraryLocalHitDelay=localHitDelay;
    }
    public  void decrementTempraryLocalHitDelay()
    {
        if(TempraryLocalHitDelay!=0)
            TempraryLocalHitDelay--;
    }
}
