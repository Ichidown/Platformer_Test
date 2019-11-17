package Entity.Attacks.Inflictions;


import Entity.Character.Unit;
import Entity.MapObject;

public class Old_Infliction
{//terminate useless booleans !!!

    /*protected String name;
    protected int dmg;
    protected double duration;
    protected int Ntimes;
    protected int period;*/
    protected Unit ETarget;

    protected void shieldTarget(int shield,String type)//anti ad / anti ap/anti dmg/no cc ...
    {
        //add shield to shield list of the target
    }

    protected void noAtackTarget()
    {
        //set the dmg of the auto atack to 0
    }
    protected void noSpellTarget()
    {
        //set the dmg of the spells to 0
    }
    protected void orderMouveTarget(int x , int y)
    {
        //make the player move to (x,y) in map
    }
    protected void makeTargetFolow(MapObject Object,int Team)
    {
        //set the target to folow Object
    }
    protected void setTeamTarget(MapObject Object)
    {
        //make the targeted ally of targeter
    }
    protected void setBotTarget()
    {
        //make the target a bot
    }
    protected void disorientateTarget()//movement of spells or both
    {
        //switch all mouvement controls of the target
    }
    protected void knockTarget(double Xdiff,double Ydiff,double force)
    {
        //move the target in a direction(Xdiff,Ydiff)with a force/knock time+ speed
    }
    protected void markTarget(String MarkName,int maxStacks)
    {
        //Add MarkName to player MarkList; + maybe a markTime/infliction time
    }


    //Contains the infliction customisations that get to be applied on player/minion/monster...

    //selfCast/EnemyCast/...

    //Nogravity=boolean;
    //Disorientate=boolean;(Swap movement and abilities controls)
}
