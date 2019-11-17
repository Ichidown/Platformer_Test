package Entity.Particles;


import Entity.*;
import Entity.Attacks.HitRules;
import Entity.Attacks.Inflictions.InflictionObj;
import Entity.Attacks.Projectile;
import Entity.Character.Unit;
import Entity.ShapeShifter;
import TileMap.TileMap;

import java.awt.*;
import java.util.ArrayList;

public class GoldCoin extends Projectile
{
    public GoldCoin(TileMap Tm, boolean right, Unit p , Point SPoint, Point point, int deviation, ArrayList<InflictionObj> inflictions, HitRules hitRules) {
        super(Tm, right, p,SPoint, point,deviation ,inflictions, hitRules);
        initialiseShuriken();
        LoadSprites();
        CalculateCourse();
    }

    /**
     * must add setters to customise projectiles
     */
    public void initialiseShuriken()//needs to be in Zed's Package
    {
        ProjectilePath = "/Sprites/Particles/goldCoin.png";
        //ProjectileHitPath = "/Sprites/Particles/goldCoinHit.png";
        moveSpeed =  Tools.Calculate.getRandomInsideRange(1,2); // need to define hard speed limit
        speedChange= Tools.Calculate.getRandomInsideRange(-0.3,-0.001);
        /**
          // maxSpeed seems not to work with speedChange

        maxSpeed = 0;
         */

        animationDelay  = -1;
        //animationHitDelay=500;

        spriteNumber=7;
        //hitSpriteNumber=1;

        width = 8;
        height = 8;
        cwidth = originalCwidth= 14;
        cheight = originalCheight = 14;
        gravity = 0;//Tools.Calculate.getRandomInsideRange(0.02,0.03);
        Resistance = 0;
        rolls = false;/**not working properly*/
        ignoreObjectsColision = true;
        ignoreMapCollision = true;
        bounces=3;/**when the bounce isnt that strong (x or y speed is close to 0 or equal) all the bounces get depleated*/

        //bounceResistance=2.5;
        maxLifeTime = (int) Tools.Calculate.getRandomInsideRange(200,150);; //add range : insted use this for levitating timer
        //maxDistance=20;
        //stopMidair=true;

        shapeShifter = new ShapeShifter(
                Tools.Calculate.getRandomInsideRange(-0.001,-0.008),
                Tools.Calculate.getRandomInsideRange(-0.01,-0.005),
                0,
                true);//true for if updatable

        //shapeShifter.setOpacityLimit(0.1);
        //shapeShifter.setSizeLimit(0.2);
        //shapeShifter.setSize(Tools.Calculate.getRandomInsideRange(1,2));
        shapeShifter.setTrensparency(0.9);
        shapeShifter.setSize(Tools.Calculate.getRandomInsideRange(1,0.1));

        deadEntity = new DeadEntity(width,height,100,"/Sprites/Particles/goldCoinHit.png",1,player.levelState, shapeShifter);

    }
}
