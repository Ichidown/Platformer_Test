package Entity.Character.Bots.Minions;

import Entity.Ability;
import Entity.Character.Unit;
import Entity.DeadEntity;
import GameState.LevelState;
import TileMap.TileMap;
import static Tools.Ai.SimpleMovementAi;

public class Minion extends Unit
{

    //private BufferedImage[] sprites;

    public Minion(TileMap Tm,LevelState Lv, int team)
    {
        super(Tm,Lv,team);
        InitialiseMinion();
        //LoadSprites();
    }


    private void InitialiseMinion()
    {
        //for dying sprites : color per team difference
        String dp="";

        //resources path
        if(Team != this.getLevelState().GetControlledPlayer().getTeam())
        {
            dp = "/Sprites/Enemies/explosion_red.png";
            PlayerSpritesPath = "/Sprites/Enemies/minion_red.png";
        }
        else
        {
            dp = "/Sprites/Enemies/explosion_blue.png";
            PlayerSpritesPath = "/Sprites/Enemies/minion_blue.png";
        }
        //animations
        numFrames = new int[]{3};//number of sprites for each animation
        //numDyingFrames=6;
        IdleAnimationDelay=10;

        //dyingAnimationDelay=100;
        //animationDelay =100;

        //animation actions
        IDLE=0;
        WALKING=0;
        JUMPING=0;
        FALLING=0;
        GLIDING=0;
        //FIREBALL=0;
        //SCRATCHING=0; creates a bug (must know why)


        Abilities.add(new Ability("SLASH",6,0,true ,false,50,180,true,true,false,0,false,true,0,null,null));

        //player Object
        //OriginalPlayer=this;//..................................................

        //stats

        maxSpeed = 1.5;
        moveSpeed = maxSpeed/20;

        health = maxHealth = 3000;
        damage = 2000;
        ExperienceGain=450;
        /** GoldGain=15; **/


        MaxJumps=1;
        jumpSpeed = -1.5;//need to be changed into distance jump & not jump timer
        jumpDistance = remainingJumpDistance = 40;
        fallSpeed=0.2;

        healthRegen = 2;

        cwidth = originalCwidth = 50;
        cheight = originalCheight = 50;

        /*stopSpeed = 0.8;........................................................
        glideSlow=0.1;
        MaxJumps=2;
        jumpSpeed = -14.4;//X3
        stopJumpSpeed = 1;//X3

        healthRegen = 1;
        Energy = maxEnergy = 2500;
        EnergyRegen = 4;*/



        left = true;//direction

        deadEntity = new DeadEntity(width,height,10,dp,6,levelState, shapeShifter);


    }

    public void updateAiBehaviour()
    {
        SimpleMovementAi(this);
    }




}


