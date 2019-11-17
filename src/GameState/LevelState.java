package GameState;

import Entity.Attacks.Inflictions.InflictionObj;
import Entity.Attacks.Inflictions.infliction;
import Entity.Character.Bots.Minions.Minion;
import Entity.Character.Champions.Zed.Zed;
import Entity.Effect;
import Entity.Character.Unit;
import Main.Game_Panel;
import TileMap.TileMap;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import Hud.HUD_InGame;

import TileMap.Background;

public class LevelState extends GameState
{
    public TileMap tileMap;
    private Background bg,bg1,bg2,bg3;
    private int selectionPrecision;

    /** LINK this to camera focussed player */
    private int controledPlayer=0;//to modify to an identifier & not an index (maybe the object itself)

    private ArrayList<Unit> players=new ArrayList<>();
    private ArrayList<Unit> Dummies=new ArrayList<>();
    private ArrayList<Unit> DeadPlayers=new ArrayList<>();
    private ArrayList<Unit> enemies=new ArrayList<>();
    public ArrayList<Effect> deathAnimations=new ArrayList<Effect>();

    /********************************************************************
     //maybe delete this & set all the dead entities into a cache table : so that we avoid entity creation when not necessary
    public ArrayList<Projectile> projectilesWithoutOwner = new ArrayList<Projectile>();
    public ArrayList<Projectile> particles = new ArrayList<Projectile>();
    *******************************************************************/

    //private Unit FirstPlayer;
    private double minStelectedLength;
    private HUD_InGame hud;
    private int screenFocusType;

    public Point cursor_Pos;

    public ArrayList<infliction> Inflictions=new ArrayList<infliction>();

    public ResourceManager resourceManager;

    //private int PlayerInitialDeathTimer,PlayerDeathTimer;

    public LevelState(GameStateManager gsm)
    {
        this.gsm=gsm;
        initialiseGame();

        cursor_Pos = new java.awt.Point(0, 0);
    }
    public void initialiseGame()
    {
        tileMap = new TileMap(90);


        newChampion(new Zed(tileMap,this,1),400,400,players);
        newChampion(new Zed(tileMap,this,1),500,400,players);

        populateMap();

        addAllInflictions();/**we didnt add the inflictions of the minions & monsters*/



        if(players.get(controledPlayer)!=null)
            hud = new HUD_InGame(players.get(controledPlayer),this);

        setScreenFocusType(3);
        tileMap.setTween(7);

        selectionPrecision=45;

        loadAllRessources();
    }

    private void loadAllRessources()
    {
        tileMap.loadTiles("/Tilesets/grasstileset.png");
        tileMap.loadMap("/Maps/level1-1.map");


        //backgrounds
        bg = new Background("/Backgrounds/jungle.png",0.1);//10% tile map speed (must optimise: speed must be for horizontal / another one for vertical)
        bg1 = new Background("/Backgrounds/foreground.png",0.1);
        bg2 = new Background("/Backgrounds/background-entities.png",0.1);
        bg3 = new Background("/Backgrounds/trees.png",0.1);

        bg.loadRessource();
        bg1.loadRessource();
        bg2.loadRessource();
        bg3.loadRessource();

        //Hud
        if(hud!=null)
            hud.LoadResources();

        //R M
        resourceManager=new ResourceManager();

        //units
        for (int i =0;i<players.size();i++)
        {
            players.get(i).LoadSprites();
        }
        for (int i =0;i<enemies.size();i++)
        {
            enemies.get(i).LoadSprites();
        }

        //dummies

        //projectiles






    }

    public Unit newChampion(Unit p, int x, int y,ArrayList<Unit> championsList)
    {
        //Unit newPlayer=null;

        Unit newPlayer = p;
        newPlayer.setPosition(x,y);

        championsList.add(newPlayer);
        return newPlayer;
    }



    public ArrayList<Unit> GetDummies(){return Dummies;}
    public ArrayList<Unit> GetEnemies(){return enemies;}
    public ArrayList<Unit> GetPlayers(){return players;}
    public ArrayList<Unit> GetDeadPlayers(){return DeadPlayers;}
    public Unit GetControlledPlayer(){return players.get(controledPlayer);}
    //public Unit getSelectedTarget(){return SelectedTarget;}
    public double getMinStelectedLength(){return minStelectedLength;}
    public void setScreenFocusType(int i){screenFocusType =i;}

    public void addAllInflictions()
    {
        for(int j=0;j<players.size();j++)//for each player
        {
            for(int x=0;x<players.get(j).Abilities.size();x++)//for each ability
            {
                for(int z=0;z<players.get(j).Abilities.get(x).getInflictions().size();z++)//for each infliction
                {
                    boolean exist = false;
                    for (int i = 0; i < Inflictions.size(); i++)
                    {
                        if (Inflictions.get(i).getClass() == players.get(j).Abilities.get(x).getInflictions().get(z).getType().getClass()) {
                            exist = true;
                        }
                    }
                    if (!exist)
                    {
                        Inflictions.add(players.get(j).Abilities.get(x).getInflictions().get(z).getType());
                    }
                }
            }
        }
    }

    public void updateInflictions()//decrement TimeLapse then undo Infliction
    {
        for(int l=0;l<Inflictions.size();l++)
        {
            Inflictions.get(l).updateDelays();
        }
    }

    public int getInflictionIndex(InflictionObj I)
    {
        for(int i=0;i<Inflictions.size();i++)
        {
            if(I.getType().getClass()==Inflictions.get(i).getClass())
            {
                return i;
            }
        }
        return 0;
    }


    private void populateMap()/** to re implement *///enemy spawner
    {
        Minion M ;
        enemies = new ArrayList<Unit>();

        //Creating Allys

        Point[] points2 = new Point[]//X3
                {
                        new Point(860*4,200*3),
                        new Point(1525*4,200*3),
                        new Point(1680*4,200*3),
                        new Point(1800*4,200*3),
                        new Point(200*4,200*3),
                        new Point(220*4,200*3),
                        new Point(240*4,200*3)

                };

        for(int i = 0; i < points2.length; i++)
        {
            M = new Minion(tileMap,this,1);
            M.setPosition(points2[i].x, points2[i].y);
            enemies.add(M);
        }

        //Creating enemies

        Point[] points = new Point[]//X3
        {
            new Point(860*3,200*3),
            new Point(1525*3,200*3+50),
            new Point(1680*3,200*3+50),
            new Point(1800*3,200*3+50),
            new Point(200*3,200*3),
            new Point(220*3,200*3),
            new Point(240*3,200*3),
                new Point(860*4+50,200*2),
                new Point(1525*3+50,200*2+50),
                new Point(1680*3+50,200*2+50),
                new Point(1800*3+50,200*2+50),
                new Point(200*4+50,200*2),
                new Point(220*4+100,200*2),
                new Point(240*4+100,200*2)
        };

        for(int i = 0; i < points.length; i++)
        {
            M = new Minion(tileMap,this,2);
            M.setPosition(points[i].x, points[i].y);
            enemies.add(M);
        }

    }

    public void updateUnits(ArrayList<Unit> Units)/** enemies don't hit dummies after creating this*/
    {
        for(int i = 0;i<Units.size();i++)
        {
            //update players
            Units.get(i).update();

            // attack enemies
            Units.get(i).checkAttack(enemies);

            //if player is dead terminate
            if(Units.get(i).isDead())
            {
                //new death animation
                deathAnimations.add(
                        new Effect(Units.get(i))
                );

                if(Units.get(i).getOriginalPlayer()==Units.get(i))//.getClass for optimisation ?
                {
                    DeadPlayers.add(Units.get(i));
                    DeadPlayers.get(DeadPlayers.size() - 1).setDeathTimer(DeadPlayers.get(DeadPlayers.size() - 1).getInitialDeathTimer());
                    //initialising Death timer of the last Dead player (this one)

                    //create a method killPlayer == change stats , freeze him & make him immune to hits  then change cameraFocus to free focus
                    Units.get(i).forceChangeMovement(false, false, false, false, false, false, false);//so we can stop the player movement after death "state blocking"
                    Units.get(i).canChangePosition(false, false, false);//so we can force stop the player positioning "physical blocking"
                }
                else
                {
                    Units.get(i).transferProjectiles(Units.get(i).getOriginalPlayer());
                }
                //decrement Units list
                Units.remove(i);// remove after all projectiles are gone  & remove previous line ..
                i--;

            }
        }
    }

    public void updateEnemies(ArrayList<Unit> Units)/** must be removed */
    {
        for(int i = 0; i < Units.size(); i++)
        {
            //enemies.get(i).update();
            Unit e = Units.get(i);
            e.update();
            if(e.isDead())
            {
                Units.remove(i);
                i--;//??
                deathAnimations.add(new Effect(e));
            }
        }
    }

    public void updateDeadUnits(ArrayList<Unit> Units)
    {
        for(int i = 0;i<Units.size();i++)
        {
            /**We don't need these 2 lines**/
            //Units.get(i).update();//check attack if dead(case of no termination of projectile if dead)
            //Units.get(i).checkAttack(enemies);//check attack damage if dead
            /**Maybe needed if the projectile of a dead unit don't do dmg*/


            if(Units.get(i).getMaxLifeTime()<=0)
            {
                if(Units.get(i).getDeathTimer()>0)//decrement deathTimer
                {
                    Units.get(i).setDeathTimer(Units.get(i).getDeathTimer() - 1);
                }
                else   //revive original Units
                {
                    Units.get(i).Revive();
                    Units.get(i).setPosition(100 * 3, 100 * 4);
                    players.add(Units.get(i));
                    Units.remove(i);
                    i--;
                }
            }

        }
    }

    public void updateScreenFocus()
    {
        //use switch to optimise
        switch (screenFocusType)
        {
            case 0:
                if (players.get(controledPlayer).facingRight)
                    tileMap.setPosition(Game_Panel.WIDTH / 2 - players.get(controledPlayer).getx() - 150, Game_Panel.HEIGHT / 2 - players.get(controledPlayer).gety());
                else
                    tileMap.setPosition(Game_Panel.WIDTH / 2 - players.get(controledPlayer).getx() + 150, Game_Panel.HEIGHT / 2 - players.get(controledPlayer).gety());
                break;

            case 1 :
                tileMap.setPosition(Game_Panel.WIDTH / 2 - players.get(controledPlayer).getx(), Game_Panel.HEIGHT / 2 - players.get(controledPlayer).gety());
                break;

            case 2 :
                tileMap.setPosition(Game_Panel.WIDTH / 2 -(cursor_Pos.getX() - players.get(controledPlayer).getXmap()),
                        Game_Panel.HEIGHT / 2 - (cursor_Pos.getY()- players.get(controledPlayer).getYmap()));
                break;

            case 3 :
                tileMap.setPosition(Game_Panel.WIDTH - cursor_Pos.getX() - players.get(controledPlayer).getx(),
                        Game_Panel.HEIGHT - cursor_Pos.getY() - players.get(controledPlayer).gety());
                break;

            case 4 :
                double navigationX = tileMap.getx();
                double navigationY = tileMap.gety();

                if (cursor_Pos.getX() >= Game_Panel.WIDTH - Game_Panel.borders)
                    navigationX -= Game_Panel.navigationSpeed;
                else if (cursor_Pos.getX() <= Game_Panel.borders)
                    navigationX += Game_Panel.navigationSpeed;

                if (cursor_Pos.getY() >= Game_Panel.HEIGHT - Game_Panel.borders)
                    navigationY -= Game_Panel.navigationSpeed;
                else if (cursor_Pos.getY() <= Game_Panel.borders)
                    navigationY += Game_Panel.navigationSpeed;

                tileMap.setPosition(navigationX, navigationY);

                break;
        }
    }

    public void update()
    {
        //update units
        updateUnits(players);
        updateUnits(Dummies);
        updateEnemies(enemies);

        updateInflictions();


        //Decrement death timers of dead players & change revive when death timer == 0
        updateDeadUnits(DeadPlayers);


        // update hits CollidedProjectiles & deathAnimations **************
        updateEffectAnimation(deathAnimations);

        for(int i=0;i<enemies.size();i++)
        {
            updateEffectAnimation(enemies.get(i).CollidedProjectilesAnimations);
        }
        for(int i=0;i<DeadPlayers.size();i++)
        {
            updateEffectAnimation(DeadPlayers.get(i).CollidedProjectilesAnimations);
        }
        for(int i=0;i<players.size();i++)
        {
            updateEffectAnimation(players.get(i).CollidedProjectilesAnimations);
        }
        for(int i=0;i<Dummies.size();i++)
        {
            updateEffectAnimation(Dummies.get(i).CollidedProjectilesAnimations);
        }
        //****************************************************************

    }

    public void updateDraw()
    {
        //acquire a target
        SetTarget();


        updateScreenFocus();

        // set background
        bg.setPosition(tileMap.getx(), tileMap.gety());
        // set background entities
        bg2.setPosition(tileMap.getx()*9,tileMap.gety()*10+4800);//this must me a multiple entities drawn not a simple pic
        // set background entities 2
        bg3.setPosition(tileMap.getx()*4,tileMap.gety()*3-150);//this must me a multiple entities drawn not a simple pic
        //set foreground
        bg1.setPosition(tileMap.getx()+tileMap.getx()*2, -tileMap.gety()-tileMap.gety()*2+3700);//must automatise
    }

    public void updateEffectAnimation(ArrayList<Effect> List)
    {
        for(int i = 0; i < List.size(); i++)
        {
            List.get(i).update();
            if(List.get(i).shouldRemove())
            {
                List.remove(i);
                i--;
            }
        }
    }
    public void draw(Graphics2D g)
    {
        updateDraw();

        //clear screen
        //g.setColor(Color.WHITE);
        //g.fillRect(0,0, Game_Panel.WIDTH,Game_Panel.HEIGHT);

        //draw background
        bg.draw(g);
        //draw background entities
        bg3.draw(g);
        bg2.draw(g);


        //draw tilemap
        tileMap.draw(g);

        //draw enemies
        for(int i = 0; i < enemies.size(); i++)
        {
            enemies.get(i).draw(g);
        }

        //draw player
        for(int i = 0;i<players.size();i++)
        {
            players.get(i).draw(g);
        }

        //draw dummies
        for(int i = 0;i<Dummies.size();i++)
        {
            Dummies.get(i).draw(g);
        }
        //draw Dead players projectiles before dying
        for(int i = 0;i<DeadPlayers.size();i++)
        {
            DeadPlayers.get(i).drawProjectiles(g);
        }

        // draw deathAnimations
        for(int i = 0; i < deathAnimations.size(); i++)
        {
            //g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)0.8));
            deathAnimations.get(i).setMapPosition((int)tileMap.getx(), (int)tileMap.gety());/**!!!UPDATE!!!*/
            deathAnimations.get(i).draw(g);
            //g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
        }

        //draw foreground
        /**bg1.draw(g);*/
        //draw hud
        //g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));//transparency set to 0.8
        if(hud!=null)
            hud.draw(g);//we got a glitch after leveling up
        //g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));//undo transparency
    }

    public void SetTarget()//get the selected target
    {
        double xDiff;
        double yDiff;
        double tempLength;
        minStelectedLength=selectionPrecision;

        if(players.get(controledPlayer)!=null)
        {
            players.get(controledPlayer).setSelectedTarget(null);

            for (int i = 0; i < enemies.size(); i++)
            {
                xDiff = (cursor_Pos.getX() - enemies.get(i).getx() - enemies.get(0).xmap);
                yDiff = (cursor_Pos.getY() - enemies.get(i).gety() - enemies.get(0).ymap);
                tempLength = Math.sqrt(xDiff * xDiff + yDiff * yDiff);

                if (minStelectedLength > tempLength)
                {
                    players.get(controledPlayer).setSelectedTarget(enemies.get(i));
                    minStelectedLength = tempLength;
                }
            }
        }
    }

    // CONTROLLS ........................................................................

    public void keyPressed(int k)
    {
        keyPressedControls(k,players);
        //keyPressedControls(k,Dummies);/**gotA do something about that*/
    }
    public void keyReleased(int k )
    {
        keyReleasedControls(k,players);
        //keyReleasedControls(k,Dummies);/**same here*/ //maybe implement this at AI_Engine
    }
    public void mouseReleased(int e,Point P)
    {

    }

    public void mousePressed(int e,Point P)
    {
        mousePressedControls(e,players);
        //mousePressedControls(e,Dummies);/**same here*/
    }

    public void mouseMoved(Point e)
    {
        cursor_Pos.setLocation(e.getX(), e.getY());
    }
    public void mouseDragged(Point e)
    {
        cursor_Pos.setLocation(e.getX(), e.getY());
    }

    public Point getMouse_pos(){return cursor_Pos;}

    //-------------------

    public void keyReleasedControls(int k,ArrayList<Unit> unitList)
    {
            if (k == KeyEvent.VK_Q) unitList.get(controledPlayer).CommandMovementH(false,true);
            if (k == KeyEvent.VK_D) unitList.get(controledPlayer).CommandMovementH(false,false);
            if (k == KeyEvent.VK_SPACE) unitList.get(controledPlayer).CommandJump(false);
    }
    public void keyPressedControls(int k,ArrayList<Unit> unitList)
    {
        if (k == KeyEvent.VK_Q) unitList.get(controledPlayer).CommandMovementH(true,true);
        if (k == KeyEvent.VK_D) unitList.get(controledPlayer).CommandMovementH(true,false);
        if (k == KeyEvent.VK_SPACE) unitList.get(controledPlayer).CommandJump(true);

        if(unitList.get(controledPlayer).isUsingAbility()==-1)
        {
            if (k == KeyEvent.VK_F ) unitList.get(controledPlayer).CommandAbility(cursor_Pos.x, cursor_Pos.y,0);
            if (k == KeyEvent.VK_G ) unitList.get(controledPlayer).CommandAbility(cursor_Pos.x, cursor_Pos.y,1);
            if (k == KeyEvent.VK_E ) unitList.get(controledPlayer).CommandAbility(cursor_Pos.x, cursor_Pos.y,2);
        }
    }
    public void mousePressedControls(int e,ArrayList<Unit> unitList)
    {
        if (e == MouseEvent.BUTTON1) unitList.get(controledPlayer).CommandAbility(cursor_Pos.x, cursor_Pos.y,1);
        if (e == MouseEvent.BUTTON3) unitList.get(controledPlayer).CommandAbility(cursor_Pos.x, cursor_Pos.y,0);
        if (e == MouseEvent.BUTTON2) unitList.get(controledPlayer).CommandTargetLock();
    }

}
