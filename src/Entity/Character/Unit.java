package Entity.Character;

import Entity.*;
import Entity.Attacks.Inflictions.*;
import Entity.Attacks.Melee;
import Entity.Attacks.Projectile;
import Entity.Attacks.ProjectileGenerator;
import GameState.LevelState;
import TileMap.TileMap;
import Tools.Calculate;

import java.awt.Point;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class Unit extends MapObject
{
    // Entities
    public Unit OriginalPlayer;// what about the original one ? is he his own original player ?
    public Melee meleObj;

    //stats & states
    public int Team;
    public int health;
    public int maxHealth;
    public int healthRegen;

    public int damage;// colision damage

    public int Energy;
    public int maxEnergy;
    public int EnergyRegen;

    public int Level;
    public int experience;
    public int maxExperience;

    public int MaxJumps;
    public int Jumps;
    public boolean jumpedOnce;

    public boolean flinching;
    public long flinchTimer;
    public long flinchingTime;//fix value
    public int ExperienceGain;//the experient we get from this entitys death

    //public int maxLifeTime;//timer before diying
    public int DeathTimer;//timer after diying

    //protected int InitialLifeTimer;
    public int InitialDeathTimer;
    //public int dyingAnimationDelay;

    //animations
    public ArrayList<BufferedImage[]> sprites = new ArrayList<BufferedImage[]>();
    //public BufferedImage[] deadSprites;
    public int[] numFrames;//number of sprites for each animation
    //public int numDyingFrames;
    //protected int animationDelay;
    //player sprites path
    public String PlayerSpritesPath;
    //public String PlayerDyingSpritesPath;
    //public String PlayerDeadSpritePath;
    public String Hud_Bar;

    //gliding
    public boolean gliding;
    public double glideSlow;
    //..................................................

    //animation actions
    public int IDLE=0;
    public int WALKING=1;
    public int JUMPING=2;//link
    public int FALLING=3;//    with
    public int GLIDING=4;//        number

    public int IdleWidth,WalkingWidth,JumpingWidth,FallingWidth,GlidingWidth;
    public int IdleAnimationDelay,WalkingAnimationDelay,JumpingAnimationDelay,FallingAnimationDelay,GlidingAnimationDelay;

    public Point MousePos=new Point(0,0);
    public Unit SelectedTarget;
    public Unit LockedTarget;

    public ArrayList<Ability> Abilities = new ArrayList<Ability>();
    public ArrayList<Projectile> Projectiles=new ArrayList<Projectile>();/**get this to level state*/
    public ArrayList<Effect> CollidedProjectilesAnimations =new ArrayList<Effect>();/**get this to level state*/
    //protected ArrayList<infliction> Inflictions=new ArrayList<infliction>();
    public ArrayList<ProjectileGenerator> ProjectileToGenerate = new ArrayList<ProjectileGenerator>();
    public ArrayList<ProjectileGenerator> ParticleToGenerate = new ArrayList<ProjectileGenerator>();

    private BufferedImage spriteSheet ;



////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////

    //Constructer
    public Unit(TileMap Tm, LevelState Lv, int Team)
    {
        super(Tm);
        HardInitialisePlayer();
        LightInitialisePlayer();
        //InitialisePlayer();
        //InitialisePlayerClones();

        this.Team=Team;
        levelState=Lv;
    }

    //-----------------GETTERS & SETTERS-------------------
    //entities
    public Unit getOriginalPlayer(){return OriginalPlayer;}
    public LevelState getLevelState()
    {
        return levelState;
    }
    //stats
    public void setHealth(int H){health=H;}
    public int getHealth(){return health;}
    public int getMaxHealth(){return maxHealth;}
    public int getLevel() { return Level; }
    public int getEnergy(){return Energy;}
    public int getMaxEnergy(){return maxEnergy;}
    public int getExperience(){return experience;}
    public int getMaxExperience(){return maxExperience;}
    public int getPlayerLevel(){return Level;}
    public int getMaxPlayerLevel(){return maxExperience/1000;}
    public int getTeam(){return Team;}
    public boolean isDead(){ return dead; }
    public int getDamage() { return damage; }//!!!!!!proper to the ability
    //movement
    public void setFalling(boolean F){falling=F;}
    public boolean getJumping(){return jumping;}
    public boolean getGliding(){return gliding;}
    public int getJumps(){return Jumps;}
    public void setJumps(int J){Jumps=J;}
    public int getMaxJumps(){return MaxJumps;}
    public boolean getjumpedOnce(){return jumpedOnce;}
    public void setjumpedOnce(boolean isIt){jumpedOnce=isIt;}
    //timers
    public void setDeathTimer(int T){DeathTimer=T;}
    public int getDeathTimer(){return DeathTimer;}
    //public int getInitialLifeTimer(){return InitialLifeTimer;}
    public int getInitialDeathTimer(){return InitialDeathTimer;}
    //paths
    /*public String getDeathAnimation() {return PlayerDyingSpritesPath;}*/
    public String getHudBar(){return Hud_Bar;}

    public void setAbilityCanUse(int x, boolean r){Abilities.get(x).setCanUse(r);}
    public boolean getAbilityCanUse(int x){return Abilities.get(x).getCanUse();}

    //Targetting
    public void setSelectedTarget(Unit T){SelectedTarget=T;}
    public Unit getSelectedTarget(){return SelectedTarget;}

    public Unit getLockedTarget() {
        return LockedTarget;
    }
    public void setLockedTarget(Unit lockedTarget) {
        LockedTarget = lockedTarget;
    }

    //--------------------------------------------



    //////////////////////////////////////////////////


    //---------------utility----------------------
    public void Revive()/**PLAYER KEEP LEVEL BUT LOSE STATS*/
    {
        this.setPosition(100*3,100*3);
        LightInitialisePlayer();
        InitialisePlayer();
        canChangePosition(true,true,true);//so we can force start the player positioning
    }

    public void forceChangeMovement(boolean UP,boolean  Down,boolean  Right,boolean  Left,boolean Jumping,boolean Falling,boolean Gliding)
    {
        up=UP;
        down=Down;
        right=Right;
        left=Left;
        jumping=Jumping;
        falling=Falling;
        gliding=Gliding;
    }

    public void transferProjectiles(Unit U)
    {
        U.Projectiles.addAll(0,Projectiles);
    }

    public void AddExp(int exp)
    {
        experience+=exp;

        if(experience>maxExperience)
            experience=maxExperience;
    }

    public void setFiring(int x, int y,int i)
    {
        if(Abilities.get(i).getCouldown()==0)
        {
            Abilities.get(i).setInUse(true);
            Abilities.get(i).reinitialiseCouldown();
            //if(!Abilities.get(i).isMeleType()) //may be necessary for optimisation
            MousePos = new Point(x, y);
        }
    }

    public void setGliding(boolean b)// b so that we can stop gliding at any moment(cant stop firing animation for exp)
    { gliding = b; }

    protected Projectile CallProjectile(TileMap tileMap,Point source,Point Target,int AbilityNumber,int accuracy)/**arrange this*/
    {
        return null;
    }

        protected Melee CallMelee(int AbilityNumber)
    {
        return null;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



    //----------------------------------INITIALISATIONS---------------------------------------
    private void LightInitialisePlayer()
    {
        dead=false;
        //??? //DeathTimer =InitialLifeTimer;
        Move=Jump=true;
        facingRight = true;//depend on Team number
        jumpedOnce=false;
        Jumps=0;
    }
    private void HardInitialisePlayer()
    {
        /**automatise this*/
        width = 90;
        height = 90;
        cwidth = originalCwidth = 60;
        cheight = originalCheight =60;
        /*****************/

        flinchingTime =5000;
        //maxLifeTime =-1;
        InitialDeathTimer =1000;

        fallSpeed = 0.3;/**fallSpeed & jump distance are related : must remove this */
        maxFallSpeed = 6;

        experience = 1000;
        maxExperience = 18000;
        Level = experience / 1000;
    }
    protected void InitialisePlayer()
    { }
    protected void InitialisePlayerClones()
    { }
    protected void InitialiseAnimation()
    {
        //-----initialising animation-----------------------------
        animation = new Animation();
        currentAction = IDLE;
        animation.setFrames(sprites.get(IDLE));
        animation.setDelay(IdleAnimationDelay);
    }

    public void LoadSprites() /** Automatize this && reimplement it**/
    {
            spriteSheet=levelState.resourceManager.getRessource(PlayerSpritesPath,PlayerSpritesPath);


            /**change the data loop to loop through abilities & movements get witch line of frames: X, then loop through numFrames[X] */

            sprites=levelState.resourceManager.getExistingRessource(PlayerSpritesPath).loadUnitRessource( numFrames, width, height);


        InitialiseAnimation();
    }

    //********************************************CHECK ATTACKS*********************************************************
    public void checkAbilitiesAttack()
    {
        for(int i=0;i<Abilities.size();i++)
        {
            if (Abilities.get(i).isInUse()&&
                    //animation.getCurrentFrame()==Abilities.get(i).getActionFrame() && // problem !! it dosent fires allways
                    currentAction != Abilities.get(i).getPosition() &&
                    /** REPLACE ABOVE WITH COULDOWN HERE */
                    !Abilities.get(i).isMeleType())
            {
                if (Energy > Abilities.get(i).getCost())
                {
                    Energy -= Abilities.get(i).getCost();

                    //if(proj!=null) //added to avoid null bug ... not sure
                    //{
                    /*******************************************/
                    newProjectileGenerator(Abilities.get(i).getProjectileGenerator());
                    /******************************************/
                    //}
                }
            }
        }
    }

    public void newProjectileGenerator(ProjectileGenerator pg)/** to automatise : so we dont use all the setX down here */
    {
        ProjectileGenerator newPg=new ProjectileGenerator(pg.getProjectileId(),pg.getInflictions());

        //transfer variables
        newPg.setNumber(pg.getNumber());
        newPg.setInitialDelay(pg.getInitialDelay());
        newPg.setSource(pg.getSource());
        newPg.setTarget(pg.getTarget());
        newPg.setXDisplacement(pg.getXDisplacement());
        newPg.setYDisplacement(pg.getYDisplacement());
        newPg.setRandomDisplacement(pg.getRandomDisplacement());
        newPg.setAccuracy(pg.getAccuracy());
        newPg.setOrder(pg.getOrder());
        newPg.setStopWhenObjectDead(pg.isStopWhenObjectDead());

        ProjectileToGenerate.add(newPg);
    }

    /*public void newParticleGenerator(int i)
    {
        //ProjectileGenerator pg =Abilities.get(i).getProjectileGenerator();//so we can copy the data
        ProjectileGenerator newPg=new ProjectileGenerator(5,10,this);
        newPg.setProjectileId(i);

        ParticleToGenerate.add(newPg);
    }*/

    public void GenerateProjectiles(ArrayList<ProjectileGenerator> ProjectileGList)// does it still work if this unit is dead
    {
        for(int i=0;i<ProjectileGList.size();i++)
        {
            if(ProjectileGList.get(i).isStopWhenObjectDead() && ProjectileGList.get(i).getSource().dead)
            {
                //ProjectileGList.get(i).emptyNumber();
                ProjectileGList.remove(i);
                i--;
            }
            else if(ProjectileGList.get(i).getDelay()<0)
            {
                if (ProjectileGList.get(i).getDelay() == -1)// when delay == -1 : creating all the projectiles at once
                {
                    if(ProjectileGList.get(i).getNumber()>0)
                    {
                        do {
                            createProjectile(ProjectileGList.get(i));
                        }
                        while (ProjectileGList.get(i).getNumber() != 0);
                    }

                } else//when delay is < -1 : creating x number of projectile per frame
                {
                    int z = -ProjectileGList.get(i).getDelay();

                    do {
                        createProjectile(ProjectileGList.get(i));
                        z--;
                    }
                    while (ProjectileGList.get(i).getNumber() > 0 && z > 0);
                }

                if (ProjectileGList.get(i).getNumber() == 0) {
                    ProjectileGList.remove(i);
                    i--;
                }
            }

            else if(ProjectileGList.get(i).getNumber()!=0)//create 1 projectile after each delay
            {
                if (ProjectileGList.get(i).getDelay() == 0)
                {
                    createProjectile(ProjectileGList.get(i));
                }
                ProjectileGList.get(i).loopDelay();
            }

            else
            {
                ProjectileGList.remove(i);
                i--;
            }
        }
    }

    public void createProjectile(ProjectileGenerator pg)
    {
        //SOURCE
        // x/y displacement
        int DisplacedX = pg.getXDisplacement();
        int DisplacedY = pg.getYDisplacement();

        //random displacement
        int rd = pg.getRandomDisplacement();
        if(rd!=0)
        {
            DisplacedX+=Calculate.getRandomIntPercentage(rd);
            DisplacedY+=Calculate.getRandomIntPercentage(rd);
        }

        //orientation
        if(!facingRight)
            DisplacedX = -DisplacedX;

        Point modifiedSource = new Point((int)(pg.getSource().getx()+pg.getSource().getXmap()+DisplacedX),(int)(pg.getSource().gety()+pg.getSource().getYmap()+DisplacedY));
        //DESTINATION

        //Point destination = new Point((int)levelState.cursor_Pos.getX(),(int)levelState.cursor_Pos.getY());
        Point destination;
        /*if(this.getLockedTarget()!=null)
            destination = new Point(
                    (int)(this.x+tileMap.getx()),
                    (int)(this.y+tileMap.gety()));
        else*/
            //destination = new Point((int)(this.xmap),(int)(this.ymap));

        if(pg.getTarget() != null)
            destination = new Point((int)(pg.getTarget().getx()-tileMap.getx()),
                                    (int)(pg.getTarget().gety()-tileMap.gety()));
        else
            destination = new Point((int)(levelState.cursor_Pos.getX()),//-tileMap.gety()+this.getXmap()),
                                    (int)(levelState.cursor_Pos.getY()));//-tileMap.gety()+this.getYmap()));

        //destination.setLocation(levelState.cursor_Pos.getX(),levelState.cursor_Pos.getY());

        //DEVIATION
        int calculatedDeviation = CalculateDeviation(pg);

        //CREATING PROJECTILE
        Projectile proj = CallProjectile(tileMap,modifiedSource,destination ,pg.getProjectileId(),calculatedDeviation);

        proj.setPosition(pg.getSource().getx()+DisplacedX,
                         pg.getSource().gety()+DisplacedY);// the source should be automatically the initial position

        Projectiles.add(proj);

        pg.decrementNumber();
    }

    private int CalculateDeviation(ProjectileGenerator pg)
    {
        switch (pg.getOrder())
        {
            case "random" :
                return Calculate.getRandomIntPercentage(pg.getAccuracy());

            case "scalingUp" :
                if(pg.getCurrentDeviation()==-1)//1st shoot
                {
                    int degree = pg.getAccuracy()/2;// /2 so that we start from one half of the full deviation

                    if(pg.getNumber()<=1)//i case of 1 projectile (or other)
                        return 0;
                    else //when more than one projectile
                    {
                        int PNumber=pg.getNumber();
                        if(degree*2>=-180 && degree*2<=180)//to avoid a bug where for exp: at 360 we get 2 projectiles with the same deviation
                            PNumber--;

                        pg.setAdditionalDeviation(Math.abs(degree * 2/PNumber));
                        pg.setCurrentDeviation(degree);
                        return degree;
                    }
                }
                else
                {
                    pg.setCurrentDeviation(pg.getCurrentDeviation()-pg.getAdditionalDeviation());
                    return pg.getCurrentDeviation();
                }



            case "scalingDown" :
                if(pg.getCurrentDeviation()==-1)//1st shoot
                {
                    int degree = pg.getAccuracy()/2;// /2 so that we start from one half of the full deviation

                    if(pg.getNumber()<=1)//i case of 1 projectile (or other)
                        return 0;
                    else //when more than one projectile
                    {
                        int PNumber=pg.getNumber();
                        if(degree*2>=-180 && degree*2<=180)//to avoid a bug where for exp: at 360 we get 2 projectiles with the same deviation
                            PNumber--;

                        pg.setAdditionalDeviation(Math.abs(degree * 2/PNumber));
                        pg.setCurrentDeviation(-degree);
                        return -degree;
                    }
                }
                else
                {
                    pg.setCurrentDeviation(pg.getCurrentDeviation()+pg.getAdditionalDeviation());
                    return pg.getCurrentDeviation();
                }



            case "scalingCenter" :/**it switch sides after each two projectiles (except the 1st one)*/
                if(pg.getCurrentDeviation()==-1)//1st shoot
                {
                    int degree = pg.getAccuracy() / 2;// /2 so that we start from one half of the full deviation

                    if (pg.getNumber() <= 1)//i case of 1 projectile (or other)
                        return 0;
                    else //when more than one projectile
                    {
                        int PNumber = pg.getNumber();
                        if(degree*2>=-180 && degree*2<=180)//to avoid a bug where for exp: at 360 we get 2 projectiles with the same deviation
                            PNumber--;

                        pg.setAdditionalDeviation(Math.abs(degree * 2 / PNumber));

                        if(pg.getNumber()%2==0) //pair
                        {
                            pg.setCurrentDeviation(pg.getAdditionalDeviation()/2);
                            pg.setShouldIncrementDeviation(false);
                            return pg.getCurrentDeviation();
                        }
                        else//impair
                        {
                            pg.setCurrentDeviation(0);
                            return 0;
                        }
                    }
                }
                else
                {
                    if(pg.isShouldIncrementDeviation())
                    {
                        pg.setCurrentDeviation(pg.getCurrentDeviation()+pg.getAdditionalDeviation());
                        pg.setShouldIncrementDeviation(false);
                    }
                    else
                    {
                        pg.setCurrentDeviation(-pg.getCurrentDeviation());
                        pg.setAdditionalDeviation(-pg.getAdditionalDeviation());
                        pg.setShouldIncrementDeviation(true);
                    }

                    return pg.getCurrentDeviation();
                }






            case  "scalingSides" :/**it switch sides after each two projectiles (except the 1st one)*/
                if(pg.getCurrentDeviation()==-1)//1st shoot
                {
                    int degree = pg.getAccuracy() / 2;// /2 so that we start from one half of the full deviation

                    if (pg.getNumber() <= 1)//i case of 1 projectile (or other)
                        return 0;
                    else //when more than one projectile
                    {
                        int PNumber = pg.getNumber();

                        if(degree*2>=-180 && degree*2<=180)//to avoid a bug where for exp: at 360 we get 2 projectiles with the same deviation
                            PNumber--;

                        pg.setAdditionalDeviation(Math.abs(degree * 2 / PNumber));

                        if(pg.getNumber()%2==0) //pair
                        {
                            if(degree*2>=-180 && degree*2<=180)
                                pg.setCurrentDeviation(degree);
                            else
                                pg.setCurrentDeviation(degree - pg.getAdditionalDeviation()/2);

                            pg.setShouldIncrementDeviation(false);
                            return pg.getCurrentDeviation();
                        }
                        else//impair
                        {
                            //pg.setShouldIncrementDeviation(false);
                            pg.setCurrentDeviation(0);
                            return 0;
                        }

                    }
                }
                else
                {
                    if(pg.isShouldIncrementDeviation())
                    {
                        pg.setCurrentDeviation(pg.getCurrentDeviation()-pg.getAdditionalDeviation());
                        pg.setShouldIncrementDeviation(false);
                    }
                    else
                    {
                        pg.setCurrentDeviation(-pg.getCurrentDeviation());
                        pg.setAdditionalDeviation(-pg.getAdditionalDeviation());
                        pg.setShouldIncrementDeviation(true);
                    }

                    return pg.getCurrentDeviation();
                }


            default :
                return 0;

        }
    }

    public void checkAttack(ArrayList<Unit> enemies)
    {
        Melee m = CallMelee(0);
        Point unitPosition;
        Point enemyPosition;
        /**double EnemyAngle;
        double MouseAngle;*/

        //for all melee abilities do this ...
        for(int i=0; i<enemies.size();i++)
        {
            unitPosition=new Point((int) (x - xmap), (int) (y - ymap));
            enemyPosition=new Point((int) (enemies.get(i).getx() - enemies.get(i).getXmap()), (int) (enemies.get(i).gety() - enemies.get(i).getYmap()));
            if(Abilities.get(0).isInUse()&& animation.getCurrentFrame()==Abilities.get(0).getActionFrame())
            {
                if (enemies.get(i).getTeam()!=Team &&m.getRange() >= Calculate.Distance(unitPosition,enemyPosition))
                {
                    /**EnemyAngle = calculateAngle(unitPosition,enemyPosition);
                    MouseAngle = calculateAngle(new Point((int)x,(int)y),new Point(MousePos.x,MousePos.y));*/

                    //System.out.println(MousePos.x +" & "+MousePos.y +"       " +xmap+" & "+ymap);

                    /**if((EnemyAngle>=MouseAngle-10 && EnemyAngle<=MouseAngle+10))// || (EnemyAngle<=MouseAngle-30 && EnemyAngle>=MouseAngle+30))
                     */
                    enemies.get(i).setHitBy(m.getInflictions(),this,enemies.get(i));
                }
            }
        }




        // projectiles hit check
        for(int i = 0; i < enemies.size(); i++)/**minimise the check list*/
        {
            Unit e = enemies.get(i);/**use this on other similar cases*/

            checkProjectilesStop(e);

            // check enemy collision (to remove or modify)
            if(intersects(e) && e.getTeam()!=Team)
            {
                setHitBy(e.Abilities.get(0).getInflictions(),OriginalPlayer,e);
            }
        }
    }

    public void checkAttackScratch(Unit e,Melee meleObj) /**to re implement*/
    {

    }

    public void checkProjectilesStop(Unit e) /** to re check*/
    {
        for(int p = 0; p < Projectiles.size(); p++)
        {

            /**
            if(Projectiles.get(p).getLifeTime()<=0)//when timer == 0 stop the projectile
            {
                Projectiles.get(p).setHit();
                break;//???
            }
            */

            /**if(Projectiles.get(p).getMaxLifeTime() == 0)
            {
                Projectiles.get(p).setHit();
                break;//???
            }*/

            if(Projectiles.get(p).getMaxDistance()!=0
                    &&
                    Projectiles.get(p).getDistance()+Projectiles.get(p).getMoveSpeed()>Projectiles.get(p).getMaxDistance())
            {
                Projectiles.get(p).setHit();
                break;
            }

            //loop global hit count
            if(Projectiles.get(p).getHitRules().getGlobalHitCount())/**may need to move this from here*/
            {
                Projectiles.get(p).decrementGlobalHitTimes();
            }

            //move "check mapColision" here

            /************************** to re implement *********************************************/
            if(Projectiles.get(p).getStopMidair())//stop the projectile where pointed + must add minimum range:needs distance counter
            {
                if(Projectiles.get(p).isStopAtMinDistance()
                        &&
                        Projectiles.get(p).getDistance()+ Projectiles.get(p).getMoveSpeed() > Projectiles.get(p).getMinDistance())
                {
                    Projectiles.get(p).setHit();
                    break;
                }
                else if (Tools.Calculate.Distance(
                        new Point((int) Projectiles.get(p).getEndPoint().getX(), (int) Projectiles.get(p).getEndPoint().getY()),
                        new Point((int) Projectiles.get(p).getStartPoint().getX(),(int)  Projectiles.get(p).getStartPoint().getY())) <
                        Projectiles.get(p).getDistance() + Projectiles.get(p).getMoveSpeed())//this plus is for precision
                {
                    if(Projectiles.get(p).getDistance() > Projectiles.get(p).getMinDistance())
                    {
                        Projectiles.get(p).setHit();
                        break;
                    }
                    else
                    {
                        Projectiles.get(p).setStopAtMinDistance(true);
                    }

                }
            }
            /****************************************************************************************/

            //hit enemy
            if(     e.getTeam()!=Team &&
                    Projectiles.get(p).intersects(e) &&
                    Projectiles.get(p).getHitRules().getGlobalHitTimes()!=0 &&
                    Projectiles.get(p).getHitRules().getTemporaryGlobalHitDelay()== 0)
            {
                //if targeted : dont hit the wrong target
                if(Projectiles.get(p).getHitRules().isTargeted() && getSelectedTarget()!=e)/**replace Selected Target by locked target*/
                {
                    break;
                }


                int theHitOne=Projectiles.get(p).didHitUnit(e);//get the index of the enemy hit if not found : return -1

                //if 1st time hit = not found in hit list
                if(theHitOne<0)
                {
                    Projectiles.get(p).addUnitHit(e);//add this unit to projectile hit list
                    e.setHitBy(Projectiles.get(p).getInflictions(), this, e);//start effect

                    Projectiles.get(p).getHitRules().getHitUnits().get(Projectiles.get(p).getHitRules().getHitUnits().size()-1).reinitialiseTemporaryLocalHitDelay();/**may not be needed*///reset delay

                }

                //if not 1st time & have local hit times && delay is 0
                else if(Projectiles.get(p).getHitRules().getHitUnits().get(theHitOne).getHitTimes()<Projectiles.get(p).getHitRules().getLocalHitTimes()
                        &&
                        Projectiles.get(p).getHitRules().getHitUnits().get(theHitOne).getTempraryLocalHitDelay()==0)
                {
                    Projectiles.get(p).getHitRules().getHitUnits().get(theHitOne).incrementHitTimes();
                    e.setHitBy(Projectiles.get(p).getInflictions(), this, e);//start effect
                    Projectiles.get(p).getHitRules().getHitUnits().get(theHitOne).reinitialiseTemporaryLocalHitDelay();
                }

                if(!Projectiles.get(p).getIgnoreObjectsColision())//if stops when hit enemy
                {
                    Projectiles.get(p).setHit();// terminate projectile if hit enemy
                    break;
                }

            }
        }
    }

    public void setHitBy(ArrayList<InflictionObj> infs, Unit player,Unit e)//, Projectile proj)//???
    {
        if(dead /**|| immuneToDmg || !targetable*/ ) return;/**try to remove this when creating imune state*///cant get hit  flinching remove
        //if(e.getTeam() == player.getTeam()) return;/**its implemented else-were so its useless*//might be in controls LevelState///cant get hit by ally

        if(infs!=null/**&& immuneToCC*/)
        {
            ///*/-*************-generateOnHitProjectiles(ablility);

            for(int i=0;i<infs.size();i++)
            {
                StartInfliction(infs.get(i));
            }

            checkIfKilledBy(player);
            //startFlinching();
        }
    }

    public void setHit()
    {
        if(dead) return;

        dead = true;
    }

    public void checkIfKilledBy(Unit u)
    {
        if(health < 0) health = 0;
        if(health == 0)
        {
            Energy =0;
            dead=true;
            DeathTimer= InitialDeathTimer;
            u.getOriginalPlayer().AddExp(ExperienceGain);
        }
    }

    /*public void startFlinching()/**not working*/
    /*{
        flinching = true;
        flinchTimer = System.nanoTime();
    }*/
    public void StartInfliction(InflictionObj x)
    {
        levelState.Inflictions.get(levelState.getInflictionIndex(x)).ApplyInfliction(this,x);
    }


    //******************************************************************************************************************

    //++++++++++++++++++++++++++++++++++++++GET POSITIONS+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    protected  void getNextPositionAbilityRestriction()
    {
        for(int i=0;i<Abilities.size();i++)
        {
            if (currentAction == Abilities.get(i).getPosition())
            {
                if (Abilities.get(i).getCantFall() && (jumping || falling))
                {
                    dy = 0;
                }

                if (Abilities.get(i).getCantMove() && dy==0) //!(jumping || falling) was in steed of dy==0
                {
                    dx = 0;
                }
            }
        }
    }

    protected void getNextPositionMovement()
    {
        if(left)//moving left
        {
            dx -= moveSpeed;
            if(dx<-maxSpeed)
            {
                dx = -maxSpeed;
            }
        }
        else if(right)//moving right
        {
            dx += moveSpeed;
            if(dx>maxSpeed)
            {
                dx = maxSpeed;
            }
        }

        else//when stopping
        {
            if(dx>0)
            {
                dx -= stopSpeed;
                if(dx <0)
                {
                    dx=0;
                }
            }
            else if(dx<0)
            {
                dx += stopSpeed;
                if(dx>0)
                {
                    dx=0;
                }
            }
        }
    }
    protected void getNextPositionJumping()/**if(Jump)   ??? in map object*/
    {
        if(jumping)
        {
                if (remainingJumpDistance >= jumpSpeed)
                {
                    dy -= jumpSpeed;
                    remainingJumpDistance += dy;
                }
                else
                {
                    dy -= remainingJumpDistance;
                    //remainingJumpDistance=0;
                }
        }
    }
    public void checkReJump()
    {
        if (!jumping && !gliding && Jumps < MaxJumps)
        {
            //setJumps(Jumps + 1);
            Jumps+=1;
            //setFalling(false);
            falling=false;
            remainingJumpDistance = jumpDistance;
        }
    }

    protected void getNextPositionFalling()
    {
        if(falling)
        {
            if(dy<=0 && !gliding && !jumping)
                dy += fallSpeed;

            if(dy>0)//we r no longer jumping
            {
                jumping = false;
            }

            if(dy>maxFallSpeed)
            {
                dy = maxFallSpeed;
            }
        }
        else if(!gliding && !jumping ) // refill number of air jumps when touch ground
        {
            Jumps=0;
            remainingJumpDistance =jumpDistance;
            gliding=false;
        }
    }

    protected void getNextPositionGliding()
    {
        /*if(gliding)//gliding //dy>0 was here but seams to be useless
        {
            dy += glideSlow;
        }*/
    }
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++


    //UPDATES
    public void updateAiBehaviour()// A.I
    {

    }

    public void update()
    {
        if(remainingJumpDistance != 40 )
        {
            System.out.println(remainingJumpDistance);
        }
        updateShape();

        updateStats();

        updateAiBehaviour();

        getNextPositionMovement();
        getNextPositionJumping();

        getNextPositionGliding();
        getNextPositionFalling();

        checkTileMapCollision();

        getNextPositionAbilityRestriction();

        setPosition(xtemp, ytemp);

        checkStoppedAbilitiesAnimation();

        checkAbilitiesAttack();

        updateProjectiles();

        CheckStillFlinching();

        setPlayerDirection();

        DefineSpriteAnimations();

        animation.update();
    }

    public void checkStoppedAbilitiesAnimation()
    {
        for(int i=0;i<Abilities.size();i++)
        {
            if (currentAction == Abilities.get(i).getPosition())//so that the attack plays once
            {
                if (animation.hasPlayedOnce())
                    Abilities.get(i).setInUse(false);
            }
        }
    }

    public void updateProjectiles()/**must check where to position this code*/
    {

        GenerateProjectiles(ProjectileToGenerate);

        for(int i = 0; i < Projectiles.size(); i++)
        {
            Projectiles.get(i).update();

            if(Projectiles.get(i).shouldRemove())
            {
                Projectiles.get(i).StartEffect(Projectiles.get(i).getx(),Projectiles.get(i).gety());//?????

                CollidedProjectilesAnimations.add(new Effect(Projectiles.get(i)));

                Projectiles.remove(i);
                i--;
            }
        }
    }

    public void setPlayerDirection()
    {
        if(isUsingAbility()==-1)
        {
            if (right) facingRight = true;
            if (left) facingRight = false;
        }
        else
        {
            for (int i = 0; i < Abilities.size(); i++)
                if (currentAction == Abilities.get(i).getPosition() && Abilities.get(i).getCanTurn())
                {
                    //canTurn=false;
                    if (right) facingRight = true;
                    if (left) facingRight = false;
                }
        }
    }

    public void updateStats()/**to automatise*/  /**fix the problem when dying causes the loss of these stats*/ //modify this or save stats when dead
    {
        //distance
        updateDistance();
        //timer part code
        /**if(maxLifeTime >0 && maxLifeTime !=-1)
            maxLifeTime--;*/

        updateTimers();

        // Energy & health regen
        if(!dead)
        {
            Energy += EnergyRegen; //Energy regen
            if (Energy > maxEnergy) Energy = maxEnergy;//Energy energy cap
            health += healthRegen; //health regen
            if (health > maxHealth) health = maxHealth;//health cap
        }

        //update characteristics
        if(Level!=experience/1000)//if there is a level up
        {
            Level = experience / 1000;//must customise the "1000"

            maxHealth+=maxHealth/2;
            health+=health/2;


            Energy+=Energy/2;
            maxEnergy+=maxEnergy/2;

            healthRegen += healthRegen/2;
            EnergyRegen += EnergyRegen/2;
        }

        //update couldowns
        for(int i=0;i<Abilities.size();i++)
        {
            Abilities.get(i).dectementCouldown();
        }
    }

    public int isUsingAbility()
    {
        int x=-1;
        for(int i=0;i<Abilities.size();i++)
        {
            if (Abilities.get(i).isInUse())
            {
                x=i;
            }
        }
        return x;
    }

    public void DefineSpriteAnimations()//automatise
    {
        int k =isUsingAbility();
        if(k!=-1)
        {
            if (currentAction != Abilities.get(k).getPosition())
            {
                //sfx.get("scratch").play();
                currentAction = Abilities.get(k).getPosition();
                animation.setFrames(sprites.get(Abilities.get(k).getPosition()));
                animation.setDelay(Abilities.get(k).getAnimationDelay());
                width = Abilities.get(k).getTileWidth();
            }
        }

        //.................................................................

        else if (dy > 0)//falling
        {
            if (gliding)//falling type gliding
            {
                if (currentAction != GLIDING)
                {
                    currentAction = GLIDING;
                    animation.setFrames(sprites.get(GLIDING));
                    animation.setDelay(GlidingAnimationDelay);
                    width = GlidingWidth;
                }
            }
            else if (currentAction != FALLING)//true falling
            {
                currentAction = FALLING;
                animation.setFrames(sprites.get(FALLING));
                animation.setDelay(FallingAnimationDelay);
                width = FallingWidth;
            }


        }

        else if (dy < 0)//jumping
        {
            if (currentAction != JUMPING)
            {
                currentAction = JUMPING;
                animation.setFrames(sprites.get(JUMPING));
                animation.setDelay(JumpingAnimationDelay);//because we have 1 sprite for this animation we need to make a spetial case "-1" for no looping
                width = JumpingWidth;
            }
        }

        else if (left || right)//walking left || right
        {
            if (currentAction != WALKING)
            {
                currentAction = WALKING;
                animation.setFrames(sprites.get(WALKING));
                animation.setDelay(WalkingAnimationDelay);
                width = WalkingWidth;
            }
        }
        else//idle
        {
            if (currentAction != IDLE)
            {
                currentAction = IDLE;
                animation.setFrames(sprites.get(IDLE));
                animation.setDelay(IdleAnimationDelay);
                width = IdleWidth;
            }
        }
    }

    public void draw(Graphics2D g)
    {
        setMapPosition();//??

        drawProjectiles(g);

        DrawFlinching();

        drawProjectileHitAnimations(g);

        super.draw(g);
    }

    public void drawProjectileHitAnimations(Graphics2D g)
    {
        for(int i = 0; i < CollidedProjectilesAnimations.size(); i++)
        {
            try {
                CollidedProjectilesAnimations.get(i).setMapPosition((int) tileMap.getx(), (int) tileMap.gety());/**null pointer exception*/
                CollidedProjectilesAnimations.get(i).draw(g);
            }catch (Exception e){System.out.println("object hit animation not found");}
        }
    }

    public void CheckStillFlinching()
    {
        if(flinching)
        {
            long elapsed =(System.nanoTime() - flinchTimer) / 1000000;
            if(elapsed > flinchingTime)
            {
                flinching = false;
            }
        }
    }

    public void DrawFlinching()
    {
        //not draw if flinching
        if(flinching)
        {
            long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
            if(elapsed/100 % 2 == 0)//blinking/not drawing every 100 mili seconds
            {
                return;
            }
        }
    }

    public void drawProjectiles(Graphics2D g) // must be in level state
    {
        for(int i = 0; i < Projectiles.size(); i++)
        {
            try
            {
                Projectiles.get(i).draw(g); /** "DrawThread" java.lang.NullPointerException **/
            }catch (Exception e){System.out.println("projectile object not found");}
        }
    }


    //*********************************************************************************************
    //                                          COMANDS
    //*********************************************************************************************

    public void CommandJump(boolean doJump)
    {
        if (doJump)
        {
            if(getJump())
            {
                checkReJump();
                setJumping(true);
                setGliding(true);
            }
        }
        else
        {
            setJumping(false);
            setGliding(false);
            setFalling(true);
        }
    }

    public void CommandAbility(int cursor_PosX,int cursor_PosY,int abilityNumber)
    {
        if (getAbilityCanUse(abilityNumber)&&!isDead()&&isUsingAbility()==-1)
            setFiring(cursor_PosX, cursor_PosY,abilityNumber);
    }

    public void CommandMovementH(boolean move,boolean isLeft) // move : stop/start move && isLeft/isRight
    {
        if(move)
        {
            if (getMove())
            {
                if (isLeft)
                    setLeft(move);
                else
                    setRight(move);
            }
        }
        else
        {
            if (isLeft)
                setLeft(move);
            else
                setRight(move);
        }
    }

    public void CommandTargetLock()
    {
        if(getSelectedTarget()!=null)
            setLockedTarget(getSelectedTarget());
        else
            setLockedTarget(null);
    }




}