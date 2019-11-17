package Entity.Attacks;

import Entity.Animation;
import Entity.Attacks.Inflictions.InflictionObj;
import Entity.Character.Hits;
import Entity.Character.Unit;
import Entity.MapObject;
import TileMap.TileMap;
import Tools.Calculate;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Projectile extends MapObject
{
    private boolean remove;
    protected String ProjectilePath;//,ProjectileHitPath;
    //public int ProjectileSpriteNumber=1;
    private BufferedImage[] sprites;
    protected int spriteNumber;
    public int animationDelay;//,animationHitDelay;

    protected Unit player;
    protected ArrayList<InflictionObj> inflictions;
    //protected Ability ability;//
    //private Point MousePosition;
    //private boolean PlayerVLookUp,PlayerHLookRight;
    protected double gravity,Resistance;
    protected double speedChange=0;
    protected boolean rolls,ignoreObjectsColision;
    protected boolean stopMidair;
    protected int minimumRange =0;
    //protected int maxLifeTime=-1;

    protected Point StartPoint;//the starting point of the projectile
    protected Point EndPoint;

    protected boolean goingUP;//not in use
    protected boolean goingRight;//not in use

    protected HitRules hitRules;
    protected Point displacedAmount= new Point(0,0);/** this isn't working **/

    protected int deviation;

    private BufferedImage spritesheet;



    public Projectile(TileMap Tm, boolean right, Unit p , Point startPoint, Point destination, int deviation, ArrayList<InflictionObj> inflictions, HitRules hitRules)
    {
        super(Tm);
        //MousePosition=destination;
        player=p;
        this.inflictions=inflictions;
        //ability=Ab;
        facingRight = right;
        fixPerfectHV=true;
        this.hitRules=hitRules;
        //setStartPoint(p);//must make usable in different ways + dont need player input
        //setEndPoint(destination);
        StartPoint=startPoint;
        EndPoint=destination;
        this.deviation=deviation;

        beforeHitEffect();

        //setOrientation();
    }

    public ArrayList<InflictionObj> getInflictions()
    {
        return inflictions;
    }

    /*public Ability getAbility()
    {
        return ability;
    }*/

    /*public void setStartPoint(Unit source)//duplicated with player position  + how come some projectiles start from another projectile ?
    {
        StartPoint=new Point((int)(source.getx()+source.getXmap()),(int)(source.gety()+source.getYmap()));
        //StartPoint=new Point(player.getx(),player.gety());
        //StartPoint=source;

    }*///xmap & ymap are double ???
    /*public void setEndPoint(Point destination)
    {
        //EndPoint=new Point((int)(MousePosition.getX()-player.getXmap()),(int)(MousePosition.getY()-player.getYmap()));
        //Point dd = new Point(-(int)destination.getX(),-(int)destination.getY());
        EndPoint=destination;
    }*/

    protected void CalculateCourse()
    {
        double degree =  Calculate.calculateAngle(StartPoint,EndPoint)+deviation;

        //System.out.println(degree);

        /** we add here the degree restriction */
        dx = Math.cos(Math.toRadians(degree))*moveSpeed;
        dy = Math.sin(Math.toRadians(degree))*moveSpeed;

    }



    public HitRules getHitRules() {
        return hitRules;
    }

    public Point getEndPoint(){return EndPoint;}
    public Point getStartPoint(){return StartPoint;}
    public Unit getPlayer(){return player;}
    public boolean isDead(){return dead;}
    public void addUnitHit(Unit u)
    {
        hitRules.getHitUnits().add(new Hits(u,1,hitRules.getLocalHitDelay()));
    }
    public void decrementGlobalHitTimes()//to optimise
    {
        if(hitRules.getGlobalHitTimes()>0)
            hitRules.setGlobalHitTimes(hitRules.getGlobalHitTimes()-1);//create decrement method
        else
            hitRules.setGlobalHitTimes(0);
    }


    /*public void setOrientation()//define the clic orientation : projectile orientation
    {
        if(StartPoint.getX()>EndPoint.getX())
            goingRight=false;
        else
            goingRight=true;

        if(StartPoint.getY()>EndPoint.getY())//precision problemd
            goingUP=true;
        else
            goingUP=false;

        //System.out.println("goingUP :"+goingUP+"    "+"goingRight :"+goingRight);
    }*/

    public boolean getGoingUp(){return goingUP;}//not in use
    public boolean getGoingRight(){return goingRight;}//not in use


    public String getProjectilePath() {
        return ProjectilePath;
    }


    public Point getDisplacedAmount()
    {
        return displacedAmount;
    }

    public void setDisplacedAmount(Point displacedAmount)
    {
        this.displacedAmount = displacedAmount;
    }


    public void LoadSprites()
    {
        // load sprites
        try
        {
            spritesheet=player.levelState.resourceManager.getRessource(ProjectilePath,ProjectilePath);

            sprites = player.levelState.resourceManager.getExistingRessource(ProjectilePath).loadProjectileResource(spriteNumber, width, height);

            animation = new Animation();
            animation.setFrames(sprites);
            animation.setDelay(animationDelay);

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public void StartEffect(int x , int y)
    {

    }

    protected void afterHitEffect()
    {

    }

    protected void beforeHitEffect()
    {

    }

    public void setHit()
    {
        if(dead) return;// try to remove this

        afterHitEffect();

        dead = true;
        remove=true;
    }

    public boolean shouldRemove()// see if we should remove it
    {
        return remove;
    }
    public boolean getIgnoreObjectsColision()
    {
        return ignoreObjectsColision;
    }

    public  boolean getStopMidair(){return stopMidair;}
    public int getMinimumRange(){return minimumRange;}


    public int didHitUnit(Unit e)
    {
        for(int i=0;i<hitRules.getHitUnits().size();i++)
        {
            if(hitRules.getHitUnits().get(i).getUnit().equals(e))
            {
                return i;
            }
        }
        return -1;
    }

    public void update()
    {
        updateShape();
        //********************************
        //Projectile effects
        if(dead)// && animation.hasPlayedOnce())   why ???
        {
            remove = true;
        }
        else
        {
            dy += gravity; // gravity
            //change speed
            dx+=dx*speedChange;
            dy+=dy*speedChange;
        }

        if(!facingRight) dx+=Resistance;//wind gota fix
        else  dx-=Resistance;

        if(minimumRange >0)
        {minimumRange--;}

        updateTimers();

        //*******************************

        checkTileMapCollision();

        hitRules.loopGlobalHitDelay();

        //decrement Local dead delays : the reLoop is from player gotten dead
        for(int h=0;h<hitRules.getHitUnits().size();h++)
        {
            hitRules.getHitUnits().get(h).decrementTempraryLocalHitDelay();
        }

        setPosition(xtemp, ytemp);

        if(dx ==0 && dy == 0 && !dead && !rolls) //&& !stopMidair)
        {
            setHit();
        }

        updateDistance();

        animation.update();

    }

    public void draw(Graphics2D g)
    {
        setMapPosition();// why ?
        super.draw(g);
    }

}