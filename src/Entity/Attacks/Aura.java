package Entity.Attacks;


import Entity.Effect;
import Entity.MapObject;


public class Aura
{

    private int range;
    private boolean isScaling;
    private Effect effect;
    private MapObject source;




    public Aura(Effect effect, MapObject source)
    {
        this.effect=effect;
        this.source=source;
    }


    public void HardInitialiseAura()
    {
        range = 0;
        isScaling=false;
        //effect = new Effect();
        //source=new MapObject();

        /**localHitTimes=1;
        globalHitTimes=1;
        localHitDelay=0;
        globalHitDelay=0;

        auraPath="";
        animationDelay=0;
        width  = 9;
        height = 0;

        lifeTimer=0;
        targeted=false;*/

    }

    public int getRange()
    {
        return range;
    }
    public boolean isScaling()
    {
        return isScaling;
    }
    public Effect getEffect()
    {
        return effect;
    }
    public MapObject getSource()
    {
        return source;
    }


    public void setRange(int r)
    {
        range=r;
    }
    public void setScaling(boolean r)
    {
        isScaling=r;
    }
    public void setEffect(Effect e)
    {
        effect=e;
    }
    public void setSource(MapObject s)
    {
        source=s;
    }


}
