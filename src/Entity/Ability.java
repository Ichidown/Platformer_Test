package Entity;


//import java.util.ArrayList;

import Entity.Attacks.Inflictions.InflictionObj;
import Entity.Attacks.Inflictions.infliction;
import Entity.Attacks.ProjectileGenerator;

import java.util.ArrayList;

public class Ability
{
    //contains the code of customisable attacks(mainly CaC)

    //can customise:
    //Ability range-Area of effect-speed-cast time- charge time-cooldown-Knocks-canSelfEffect-can effect enemy . . .

    //private int Damage;
    private int Cost;
    //private int Range;// not in use
    private boolean MeleType;// not in use
    private boolean InUse;
    private String Name;// not in use
    private int Position;//posision in tileSheet
    private int AnimationDelay;//Delay of animation : the less the faster
    private int TileWidth;// not in use
    private boolean PositionAbilityRestriction;
    private boolean CanUse;
    private boolean CantMove;
    private int frameWidth,frameHeight;
    private int actionFrame;
    private boolean canTurn;
    private boolean cantFall;
    private int couldown;
    private  int InitialCouldown;
    private ArrayList<InflictionObj> inflictions = new ArrayList<InflictionObj>();
    private ProjectileGenerator projectileGenerator;



    public Ability(String Name, int Position, int Cost, boolean MeleType, boolean InUse, int AnimationDelay, int TileWidth, boolean PositionAbilityRestriction, boolean CanUse, boolean CanMove, int actionFrame, boolean canTurn, boolean cantFall , int couldown, ArrayList<InflictionObj> inflictions, ProjectileGenerator projectileGenerator)//,int frameWidth , int frameHeight)//,Projectile projectile)
    {
        this.Name=Name;
        this.Position=Position;
        //this.Damage=Damage;
        this.Cost=Cost;
        //this.Range=Range;
        this.MeleType=MeleType;
        this.InUse=InUse;
        //couldown !!!
        this.AnimationDelay=AnimationDelay;
        //WIDTH
        this.TileWidth =TileWidth;
        this.PositionAbilityRestriction=PositionAbilityRestriction;
        this.CanUse=CanUse;
        this.CantMove =CanMove;
        this.actionFrame=actionFrame;
        this.canTurn=canTurn;/**we got a problem with this*/
        //this.projectile=projectile;
        this.cantFall=cantFall;
        this.couldown=0;
        this.InitialCouldown=couldown;

        if(inflictions!=null)
            this.inflictions=inflictions;

        this.projectileGenerator=projectileGenerator;

    }

    public String getName()
    {
        return Name;
    }
    public int getPosition()
    {
        return Position;
    }
    public void setInUse(boolean r)
    {
        InUse=r;
    }
    public boolean isInUse()
    {
        return InUse;
    }
    public boolean isMeleType()
    {
        return MeleType;
    }

    public ProjectileGenerator getProjectileGenerator()
    {
        return projectileGenerator;
    }

    public int getCost()
    {
        return Cost;
    }
    public void setCost(int X)
    {
        Cost=X;
    }
    public int getAnimationDelay()
    {
        return AnimationDelay;
    }
    public void setAnimationDelay(int X)
    {
        AnimationDelay=X;
    }
    public int getTileWidth()
    {
        return TileWidth;
    }
    public boolean getPositionAbilityRestriction()
    {
        return PositionAbilityRestriction;
    }
    public void setPositionAbilityRestriction(boolean r)
    {
        PositionAbilityRestriction=r;
    }
    public boolean getCanUse(){return CanUse;}
    public void setCanUse(boolean r){CanUse=r;}
    public boolean getCantMove(){return CantMove;}
    public int getActionFrame()
    {
        return actionFrame;
    }
    public void setCanTurn(boolean t)
    {
        canTurn=t;
    }
    public boolean getCanTurn()
    {
        return canTurn;
    }
    //public Projectile getProjectile(){return projectile;}
    public boolean getCantFall()
    {
        return cantFall;
    }
    public void dectementCouldown()
    {
        if(couldown!=0)
        {
            couldown--;
        }
    }
    public int getCouldown()
    {
        return  couldown;
    }
    public void reinitialiseCouldown()
    {
        couldown=InitialCouldown;
    }
    public int getInitialCouldown()
    {
        return InitialCouldown;
    }

    public  ArrayList<InflictionObj> getInflictions()
    {
        return inflictions;
    }


}
