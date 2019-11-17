package Entity.Character.Champions.Zed;

import Entity.Ability;
import Entity.Attacks.HitRules;
import Entity.Attacks.Inflictions.*;
import Entity.Attacks.Inflictions.PremadeInflictions.Damage;
import Entity.Attacks.Inflictions.PremadeInflictions.Slow;
import Entity.Attacks.Melee;
import Entity.Attacks.ProjectileGenerator;
import Entity.Character.Unit;
import Entity.Attacks.Projectile;
import Entity.DeadEntity;
import Entity.Particles.GoldCoin;
import GameState.LevelState;
import TileMap.TileMap;

import java.awt.*;
import java.util.ArrayList;


public class Zed extends Unit {

    HitRules hr1,hr2;

    public Zed(TileMap Tm,LevelState Lv, int Team)//,Point P)
    {
        super(Tm,Lv,Team);
        InitialisePlayer();
        InitialisePlayerClones();

        //LoadSprites();
    }

    protected void InitialisePlayer()
    {
        Team = 1;//modifiable by player

        //resources path
        Hud_Bar = "/HUD/stamina_bar.png";
        PlayerSpritesPath = "/Sprites/Player/playersprites.png";
        //PlayerDyingSpritesPath ="/Sprites/Player/shadow_projectile_hit.png";
        //PlayerDeadSpritePath="";

        //animations
        numFrames = new int[]{5, 8, 1, 2, 4, 2, 5};//number of sprites for each animation
        //numDyingFrames=4;
        IdleWidth=WalkingWidth=JumpingWidth=FallingWidth=GlidingWidth=90;

        /**link mouvement speed with movement animation delay & attack speed with attack animation delay & so on ... */
        //animation delays
        IdleAnimationDelay=8;
        WalkingAnimationDelay=3;
        JumpingAnimationDelay=-1;
        FallingAnimationDelay=4;
        GlidingAnimationDelay=4;

        //dyingAnimationDelay=50;

        //animation actions
        IDLE=0;
        WALKING=1;
        JUMPING=2;
        FALLING=3;
        GLIDING=4;

        //player Object
        OriginalPlayer=this;

        //character stats

        maxSpeed = 3;//X3
        moveSpeed = maxSpeed/30;//X3

        stopSpeed = 0.15;//stop movement speed
        glideSlow= -5;

        // need to fix the jump system + add jet pack mechanism : inverted gliding with limited time / distance use
        MaxJumps = 1;
        jumpSpeed = 1.1;
        jumpDistance = remainingJumpDistance = 200;
        fallSpeed = 10;

        /**Try to use this as the speed of jump degrades before/at max height **/
        //stopJumpSpeed = 0;/** ??? **/

        health = maxHealth = 20000;
        healthRegen = 2;

        Energy = maxEnergy = 2500;
        EnergyRegen = 2;

        //****************************************************
        deadEntity = new DeadEntity(width,height,10,"/Sprites/Player/shadow_projectile_hit.png",4,levelState, shapeShifter);

        //****************************************************
        ArrayList<InflictionObj> Inf=new ArrayList<>();
        //Inf.add(new InflictionObj(120,0,new Root()));
        Inf.add(new InflictionObj(1,1000,new Damage()));
        //Inf.add(new InflictionObj(100,99,new Slow()));

        ArrayList<InflictionObj> Inf2=new ArrayList<>();
        Inf2.add(new InflictionObj(0,100,new Damage()));
        Inf2.add(new InflictionObj(100,99,new Slow()));

        //****************************************************
        /** inflictions use is replicated */
        ProjectileGenerator shurikens = new ProjectileGenerator(1,Inf);//,10,0,this,null,0,0,0,90,"random");//getLockedTarget()
        shurikens.setNumber(1);
        shurikens.setSource(this);
        shurikens.setAccuracy(0);
        shurikens.setOrder("random");

        ProjectileGenerator shadows = new ProjectileGenerator(2,null);//,1,0,this,null,20,-20,0,0,"random");
        shadows.setSource(this);
        shadows.setXDisplacement(20);
        shadows.setYDisplacement(-20);

        //Ability(String Name,int Position,int Cost,boolean MeleType,boolean InUse,int AnimationDelay,int TileWidth,boolean PositionAbilityRestriction,boolean CanUse,boolean CanMove)

        // /Abilities.add(new Ability("DEATHMARK"   ,6,3,0  ,120,true  ,false,50,180,true,true,true));//0
        Abilities.add(new Ability("SLASH"   ,6,0,true  ,false,4,180,true,true,true,0,true,false,0,Inf2,null));//0
        Abilities.add(new Ability("SHURIKEN",5,300  ,false ,false,10,90,true,true,true,1,false,false,0,Inf,shurikens));//1
        Abilities.add(new Ability("SHADOW"  ,5,100  ,false ,false,10,90,true,true,true,1,false,false,10,null,shadows));//2
        //****************************************************

    }

    public void setFiring(int x, int y,int i)//!!!!! automatise !!!!!
    {
        if(Abilities.get(i).getCouldown()==0)
        {
            if (i == 2 && this.getLevelState().GetDummies().size() != 0)//switch to shadow
            {/**find a way to set this as a condition to a second ability activation*/
                /**if condition:X then use ability i2*/

                Abilities.get(i).reinitialiseCouldown();

                int TempX = this.getx();
                int TempY = this.gety();
                //switch player to dummy
                this.setPosition(this.getLevelState().GetDummies().get(0).getx(), this.getLevelState().GetDummies().get(0).gety());
                //switch dummy to player
                this.getLevelState().GetDummies().get(this.getLevelState().GetDummies().size() - 1).setPosition(TempX, TempY);
            }
            else//shoot
            {
                Abilities.get(i).setInUse(true);
                MousePos = new Point(x, y);

                for(int j=0;j<levelState.GetDummies().size();j++)
                {
                    if(levelState.GetDummies().get(j).getOriginalPlayer()==this)
                    {
                        levelState.GetDummies().get(j).setFiring(x,y,i);
                    }
                }

                if(Abilities.get(i)!=Abilities.get(2))
                {
                    Abilities.get(i).reinitialiseCouldown();
                }
            }
        }
    }

    protected Projectile CallProjectile(TileMap tileMap, Point source, Point Target, int AbilityNumber,int deviation)
    {
        switch(AbilityNumber)
        {
            case 1: return new Shuriken(tileMap, facingRight, this,source, Target,deviation,Abilities.get(1).getInflictions(),new HitRules(0,1,-1,0,0,false));
            case 2: return new Shadow(tileMap, facingRight, this,source,Target,deviation,Abilities.get(2).getInflictions(),new HitRules(0,1,-1,0,0,false));
            case 3: return new GoldCoin(tileMap, facingRight, this,source, Target,deviation,null,new HitRules(0,1,-1,0,0,false));
            default:return null;
        }
    }

    protected Melee CallMelee(int AbilityNumber)
    {
        switch(AbilityNumber)
        {
            case 0: meleObj = new Melee(150,false,this,Abilities.get(0).getInflictions(),new HitRules(0,1,-1,0,0,true));
                break;
        }
        return meleObj;
    }



}