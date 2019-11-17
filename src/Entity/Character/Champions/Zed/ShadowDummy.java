package Entity.Character.Champions.Zed;


import GameState.LevelState;
import TileMap.TileMap;

import java.awt.*;

public class ShadowDummy extends Zed
{
    //int OriginalPlayerFire;


    public ShadowDummy(TileMap Tm, LevelState Lv, int team)
    {
        super(Tm,Lv,team);
        OriginalPlayer=Lv.GetControlledPlayer();//??? !!!
        facingRight=OriginalPlayer.facingRight;
        InitialisePlayerClones();
        LoadSprites();//must deleate this
    }

    protected void InitialisePlayerClones()
    {
        health = maxHealth = 100000000;
        //resources path
        PlayerSpritesPath = "/Sprites/Player/shadow_dummy.png";

        //clone damage
        //scratchDamage = 2;
        //fireBallDamage = 1;

        //stamina
        Energy =OriginalPlayer.getEnergy();

        //overall restrictions
        //Ability0=Ability1=true;
        //Ability2=Ability3=Ability4=false;

        //Abilities.get(2).setCanUse(false);//may not be necessary

        Move=Jump=false;

        //maxLifeTime before terminated
        //haveTimer=true;
        //InitialLifeTimer=250;
        maxLifeTime =500; //InitialLifeTimer may not be needed : if maxLifeTime = -1 then we dont have a life timer
        //maxLifeTime=250;
        //if not a player
        //dummy=true;
    }

    public void setFiring(int x, int y,int i)
    {
            Abilities.get(i).setInUse(true);
            //if(!Abilities.get(i).isMeleType()) //may be necessary for optimisation
            MousePos = new Point(x, y);
    }

}
