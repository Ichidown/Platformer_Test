package Entity.Character.Champions.Zed;


import Entity.*;
import Entity.Attacks.HitRules;
import Entity.Attacks.Inflictions.InflictionObj;
import Entity.Attacks.ProjectileGenerator;
import Entity.Character.Unit;
import Entity.Attacks.Projectile;
import Entity.ShapeShifter;
import TileMap.TileMap;

import java.awt.*;
import java.util.ArrayList;

public class Shuriken extends Projectile
{

    public Shuriken(TileMap Tm, boolean right, Unit p , Point SPoint, Point point, int deviation, ArrayList<InflictionObj> inflictions, HitRules hitRules)
    {
        super(Tm, right, p,SPoint,point,deviation,inflictions,hitRules);
        initialiseShuriken();

        CalculateCourse();
        LoadSprites();
    }

    /** must add setters to customise projectiles*/
    public void initialiseShuriken()//needs to be in Zed's Package
    {
        ProjectilePath ="/Sprites/Player/Shuriken.png";
        spriteNumber=1;
        //spriteNumber=1;
        //ProjectileHitPath="/Sprites/Player/ShurikenHit.png";
        //ProjectileHitSpriteNumber=4;

        //speedChange = -0.1;
        animationDelay=15;
        //animationHitDelay=500;


        //hitSpriteNumber=4;

        moveSpeed = 5; // need to define hard speed limit
        width = 90;
        height = 90;
        cwidth = originalCwidth =14;
        cheight = originalCheight = 14;

        gravity =0.05;
        Resistance=0;

        ignoreObjectsColision=true;
        ignoreMapCollision=false;

        //stopMidair=true;
        //minDistance=500;
        //maxDistance=100;

        //countDistance=true;

        rolls =false;/**not working properly*/
        bounces=5;
        bounceResistance=2;


        //maxLifeTime =100;  //add range : insted use this for levitating timer
        //minimumRange = 5;//not yet implemented //must modify to true range not a timer like in here

        shapeShifter = new ShapeShifter(0,0,0.4,true);
        //shapeShifter.setSize(Tools.Calculate.getRandomInsideRange(0.2,0.6));
        shapeShifter.setSize(0.5);

        ShapeShifter customHitShape = new ShapeShifter(-0.03,0,0,true);
        customHitShape.setSize(0.5);

        deadEntity = new DeadEntity(width,height,50,"/Sprites/Player/Shuriken.png",1,player.levelState,customHitShape);

        /** boolean resetHitRulesOnBounce **/

        //OrientedRotation = true;/**isnt working properly */


        //TO PUT IN PROJECTILE

        //canEffectAlly=boolean;
        //canEffectEnemy=boolean;

        //recoil=int;  this is an inverted dash

        //CastRange=int;//0 means any range

        //guided=boolean;//folow player click & not the mouse
        //guidancePrecision=int;

        //followObject=boolean;
        //followPrecision=int; follow degree , the followForce is movementSpeed

        //folowRange=int;

        //explosionTime=int;
        //AreaOfEffect=int;

        //stopAtMouseClicPoint=boolean;

        //castDelay=int;
        //castChargeTime=int;

        //rotationDegree=int;
        //rotationDelay=int;
        //rotationTimes=int; if -1 infinite

        //Freeze
        //FreezeTimer=int;//0 if it dose not stop
                //need to store direction + speed (needed if not freezed anymore)


        //Aplly Old_Infliction:root stun slow shield dmg ...
        //InflictionX=Old_Infliction;
    }

    protected void beforeHitEffect()/** add test : if unit is dead = stop generating projectiles **/
    {

        ProjectileGenerator goldC = new ProjectileGenerator(3,null);//,-1,0,this,player.getLockedTarget(),0,0,0,360,"random");
        //goldC.setFixedSource(new Point((int)x,(int)y));
        goldC.setStopWhenObjectDead(true);//to move/remove
        goldC.setNumber(100000);
        goldC.setInitialDelay(-10);
        goldC.setSource(this);
        goldC.setTarget(player.getLockedTarget());
        goldC.setRandomDisplacement(1);
        goldC.setAccuracy(360);
        player.newProjectileGenerator(goldC);

    }

    protected void afterHitEffect()
    {

        ProjectileGenerator goldC = new ProjectileGenerator(3,null);//,100,-20,this,player.getLockedTarget(),0,0,5,360,"random");
        goldC.setNumber(300);
        goldC.setInitialDelay(-1);
        goldC.setSource(this);
        goldC.setTarget(player.getLockedTarget());
        goldC.setRandomDisplacement(10);
        goldC.setAccuracy(360);
        player.newProjectileGenerator(goldC);//the 3 is for goldCoin

    }

    protected void bounceEffect()
    {
        ProjectileGenerator goldC = new ProjectileGenerator(3,null);//,100,-20,this,player.getLockedTarget(),0,0,5,360,"random");
        goldC.setNumber(100);
        goldC.setInitialDelay(-1);
        goldC.setSource(this);
        goldC.setTarget(player.getLockedTarget());
        goldC.setRandomDisplacement(5);
        goldC.setAccuracy(360);
        player.newProjectileGenerator(goldC);//the 3 is for goldCoin

    }

}