package Entity.Character.Champions.Zed;


import Entity.*;
import Entity.Attacks.HitRules;
import Entity.Attacks.Inflictions.InflictionObj;
import Entity.Character.Unit;
import Entity.Attacks.Projectile;
import Entity.ShapeShifter;
import TileMap.TileMap;

import java.awt.*;
import java.util.ArrayList;

public class Shadow extends Projectile
{

    public Shadow(TileMap Tm, boolean right, Unit p, Point SPoint, Point point, int deviation, ArrayList<InflictionObj> inflictions, HitRules hitRules)
    {
        super(Tm, right, p,SPoint, point,deviation,inflictions,hitRules);
        initialiseShadow();

        CalculateCourse();
        LoadSprites();
    }


    public void initialiseShadow()//needs to be in Zed's Package
    {
        ProjectilePath ="/Sprites/Player/shadow_projectile.png";
        //ProjectileHitPath="/Sprites/Player/shadow_projectile_hit.png";

        spriteNumber=4;
        //hitSpriteNumber=4;

        //animationHitDelay=100;
        animationDelay=5;

        moveSpeed = 10;
        width = 90;
        height =90;
        cwidth = originalCwidth =60;
        cheight = originalCheight = 60;

        gravity =0;
        Resistance=0;

        ignoreObjectsColision=true;
        ignoreMapCollision=true;

        stopMidair=true;
        maxDistance=300;
        minDistance=100;

        //rolls =false;

        //dropPointCalibration=true;
        //maxLifeTime =20;


        shapeShifter = new ShapeShifter(0.4,0,0,true);
        shapeShifter.setTrensparency(0);

        deadEntity = new DeadEntity(width,height,10,"/Sprites/Player/shadow_projectile_hit.png",4,player.levelState,new ShapeShifter(0,0,0,false));


        //OrientedRotation=true;
    }

    public void StartEffect(int x,int y)
    {
            if(dropPointCalibration)
            {
                int xdiff = (StartPoint.x-EndPoint.x);//p.cox);
                int ydiff = (StartPoint.y-EndPoint.y);//p.coy);

                Point CalibratedPoint = getCalibratedDropPoint(xdiff,ydiff,x,y);
                player.getLevelState().newChampion(new ShadowDummy(tileMap,player.getLevelState(),player.getTeam()), (int) CalibratedPoint.getX(), (int) CalibratedPoint.getY(),player.getLevelState().GetDummies());//weird inputs
            }
            else
                player.getLevelState().newChampion(new ShadowDummy(tileMap,player.getLevelState(),player.getTeam()), x, y,player.getLevelState().GetDummies());//weird inputs

    }

}
