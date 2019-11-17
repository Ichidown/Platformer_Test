package Hud;

import Entity.Character.Unit;
import GameState.LevelState;
import Main.Game_Panel;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class HUD_InGame
{

    private Unit player;
    private LevelState LvlState;

    private BufferedImage image,healthBar,staminaBar,expBar;
    private BufferedImage tempHealthBar,tempStaminaBar,tempExpBar,mouseCrosshair;
    private Font font;
    //bots
    private BufferedImage healthBarEmptyBots,TEMPhealthBarBotsBLUE,healthBarBotsBLUE,TEMPhealthBarBotsRED,healthBarBotsRED;
    //needed values
    ArrayList<Unit> Enemies;

    int i=0;//for counting number of entities
    int newI;


    //

    private Point XY;
    private ArrayList<Unit> Players;

    public HUD_InGame(Unit p, LevelState LV)
    {
        player = p;
        LvlState=LV;
        //LoadResources();
    }

    public void LoadResources()
    {

        try
        {
                //player Hud-
                image = ImageIO.read(getClass().getResourceAsStream("/HUD/hud.png"));
                //Champions icon
                healthBar = ImageIO.read(getClass().getResourceAsStream("/HUD/health_bar.png"));
                staminaBar = ImageIO.read(getClass().getResourceAsStream(player.getHudBar()));
                expBar = ImageIO.read(getClass().getResourceAsStream("/HUD/exp_bar.png"));
                font = new Font("Arial", Font.PLAIN, 12);

                tempHealthBar = healthBar;
                tempStaminaBar = staminaBar;
                tempExpBar = expBar;




                //bots hud bars
                healthBarEmptyBots = ImageIO.read(getClass().getResourceAsStream("/HUD/in_map/boot_healthbar_empty.png"));
                healthBarBotsBLUE = ImageIO.read(getClass().getResourceAsStream("/HUD/in_map/boot_healthbar_full_ally.png"));
                healthBarBotsRED = ImageIO.read(getClass().getResourceAsStream("/HUD/in_map/boot_healthbar_full_enemy.png"));

                TEMPhealthBarBotsBLUE=healthBarBotsBLUE;
                TEMPhealthBarBotsRED=healthBarBotsRED;


                //mouse
                mouseCrosshair = ImageIO.read(getClass().getResourceAsStream("/HUD/Mouse.png"));
        }
        catch(Exception e) {e.printStackTrace();}


    }

    public void draw(Graphics2D g)
    {
            drawBotsHud(g);
            //Existing bug where : if the player dies hud/debug hud bugs(cause:no player data found/no player found)

            g.drawImage(image, 10, 10, null);
            //****************************************************
            if (healthBar.getWidth() * player.getHealth() / player.getMaxHealth() != 0)
                tempHealthBar = healthBar.getSubimage(0, 0, healthBar.getWidth() * player.getHealth() / player.getMaxHealth(), healthBar.getHeight());
            if (staminaBar.getWidth() * player.getEnergy() / player.getMaxEnergy() != 0)
                tempStaminaBar = staminaBar.getSubimage(0, 0, staminaBar.getWidth() * player.getEnergy() / player.getMaxEnergy(), staminaBar.getHeight());
            if (player.getPlayerLevel() == player.getMaxPlayerLevel())
                tempExpBar = expBar.getSubimage(0, 0, 1, 1);// at lvl 18 exp bar is full(actual exp bat isnt showing)
            else if ((player.getExperience() - 1000) / 1000 != player.getPlayerLevel())// to avoid glitch when leveling up , not sure if this is working
                tempExpBar = expBar.getSubimage(0, 0, expBar.getWidth(), expBar.getHeight() - (expBar.getHeight() * (player.getExperience() - (1000 * player.getPlayerLevel())) / 1000));//we have a problem when leveling up more than 1 level in an instant

            /**if health or stamina hit 0 we got a glitch in player stats draw**/
        /*if(tempHealthBar!=null&&tempStaminaBar!=null&&tempExpBar!=null)
        {*/
        if (player.getHealth() != 0)
            g.drawImage(tempHealthBar, 117, 83, null);

        if (player.getEnergy() != 0)
            g.drawImage(tempStaminaBar, 117, 96, null);

            g.drawImage(tempExpBar, 80, 21, null);

            //}
            //****************************************************
            g.setFont(font);
            g.setColor(Color.WHITE);
            g.drawString(player.getHealth() / 100 + "/" + player.getMaxHealth() / 100, ((healthBar.getWidth() + 107) / 2) + 47, 94);
            g.drawString(player.getEnergy() / 100 + "/" + player.getMaxEnergy() / 100, ((staminaBar.getWidth() + 107) / 2) + 45, 108);
            g.drawString(Integer.toString(player.getLevel()), 74, 104);


            DebugHudDraw(g);
            drawMouse(g);
    }

    public void drawMouse(Graphics2D g)
    {
        g.drawImage(mouseCrosshair,(int)LvlState.getMouse_pos().getX()-mouseCrosshair.getWidth()/2,(int)LvlState.getMouse_pos().getY()-mouseCrosshair.getHeight()/2,null);
    }

    public void drawBotsHud(Graphics2D g)
    {
        //****************************************************
        Enemies = LvlState.GetEnemies();
        for(int i = 0;i<Enemies.size();i++)
        {
                g.drawImage(healthBarEmptyBots, (int) (Enemies.get(i).getx() + Enemies.get(i).getXmap() - Enemies.get(i).getCWidth() / 2),
                        (int) (Enemies.get(i).gety() + Enemies.get(i).getYmap() - Enemies.get(i).getCHeight() / 1.5), null);
            if(Enemies.get(i).getHealth()>0)
            {
                if (healthBarBotsBLUE.getWidth() * Enemies.get(i).getHealth() / Enemies.get(i).getMaxHealth() != 0)
                    TEMPhealthBarBotsBLUE = healthBarBotsBLUE.getSubimage(0, 0, healthBarBotsBLUE.getWidth() * Enemies.get(i).getHealth() / Enemies.get(i).getMaxHealth(), healthBarBotsBLUE.getHeight());
                if (healthBarBotsRED.getWidth() * Enemies.get(i).getHealth() / Enemies.get(i).getMaxHealth() != 0)
                    TEMPhealthBarBotsRED = healthBarBotsRED.getSubimage(0, 0, healthBarBotsRED.getWidth() * Enemies.get(i).getHealth() / Enemies.get(i).getMaxHealth(), healthBarBotsRED.getHeight());

                if (Enemies.get(i).getTeam() == player.getTeam())
                    g.drawImage(TEMPhealthBarBotsBLUE, (int) (Enemies.get(i).getx() + Enemies.get(i).getXmap() - Enemies.get(i).getCWidth() / 2),
                            (int) (Enemies.get(i).gety() + Enemies.get(i).getYmap() - Enemies.get(i).getCHeight() / 1.5), null);
                else
                    g.drawImage(TEMPhealthBarBotsRED, (int) (Enemies.get(i).getx() + Enemies.get(i).getXmap() - Enemies.get(i).getCWidth() / 2),
                            (int) (Enemies.get(i).gety() + Enemies.get(i).getYmap() - Enemies.get(i).getCHeight() / 1.5), null);
            }
        }
        //****************************************************
    }

    private void InitialiseDebugHud()
    {

    }

    public void DebugHudDraw(Graphics2D g)
    {
        XY=LvlState.getMouse_pos();

        //--mouse position in screen--
        //g.drawString(String.valueOf(XY.x)+" - "+String.valueOf(XY.y),850,650);
        //-------------------

        //max number of mapObjects existing at a time ......................................................

        /**newI=LvlState.GetDummies().size()
                +LvlState.GetEnemies().size()
                +LvlState.GetPlayers().size();*/
        for(int x=0;x<LvlState.GetPlayers().size();x++)
        {
            //newI=LvlState.GetPlayers().get(0).CollidedProjectilesAnimations.size();
            newI+=LvlState.GetPlayers().get(x).Projectiles.size();
        }

        if(i<newI)
        {
            i = newI;
        }
        g.drawString(String.valueOf(newI),750,540);
        newI=0;
        g.drawString(String.valueOf(i),750,550);

        //..................................................................................................



        //nbr of jumps done
        //g.drawString(String.valueOf(player.getJumps()),750,650);

        //Draw colision blocs
        Enemies = LvlState.GetEnemies();
        Players = LvlState.GetPlayers();



        //draw enemy Colision blocs + Length
        for(int i = 0;i<Enemies.size();i++)
        {

            //CBoxes
            if(player.getTeam()==Enemies.get(i).getTeam())
                g.setColor(Color.blue);
            else
                g.setColor(Color.red);


            g.drawRect((int) (Enemies.get(i).getx()+Enemies.get(i).xmap-Enemies.get(i).getCWidth()/2),
                    (int) (Enemies.get(i).gety()+Enemies.get(i).ymap-Enemies.get(i).getCHeight()/2),
                    Enemies.get(i).getCWidth(),
                    Enemies.get(i).getCHeight());

            //lines from player
            /*g.drawLine( (int)(Champions.get(0).getx()+Champions.get(0).xmap),
                    (int)(Champions.get(0).gety()+Champions.get(0).ymap),
                    (int)(Enemies.get(i).getx()+Enemies.get(i).xmap),
                    (int)(Enemies.get(i).gety()+Enemies.get(i).ymap));*/

            //lines from mouse / minions
                /*g.drawLine((int) (XY.getX()),
                        (int) (XY.getY()),
                        (int) (Enemies.get(i).getx() + Enemies.get(i).xmap),
                        (int) (Enemies.get(i).gety() + Enemies.get(i).ymap));*/
        }

        //draw projectile Colision blocs
        /**g.setColor(Color.red);
        for(int i = 0;i<Players.get(0).Projectiles.size();i++)
        {
            g.drawRect((int) (Players.get(0).Projectiles.get(i).getx()+Players.get(0).Projectiles.get(i).xmap-Players.get(0).Projectiles.get(i).getCWidth()/2),
                    (int) (Players.get(0).Projectiles.get(i).gety()+Players.get(0).Projectiles.get(i).ymap-Players.get(0).Projectiles.get(i).getCHeight()/2),
                    Players.get(0).Projectiles.get(i).getCWidth(),
                    Players.get(0).Projectiles.get(i).getCHeight());

        }*/

        //draw player pointer length
        g.setColor(Color.green);
        g.drawLine((int) (XY.getX()),
                (int) (XY.getY()),
                (int) (player.getx() + player.xmap),
                (int) (player.gety() + player.ymap));

        //player pointer length value
        double xdiff = (XY.getX()-player.getx()-player.xmap);
        double ydiff = (XY.getY()-player.gety()-player.ymap);
        double tempLength = Math.sqrt(xdiff*xdiff+ydiff*ydiff);
        g.drawString(String.valueOf((int)tempLength),820,580);

        //Mark selected

        if(player.getSelectedTarget()!=null)
        {

            g.setColor(Color.yellow);
            g.drawRect((int) (player.getSelectedTarget().getx() + player.getSelectedTarget().xmap - player.getSelectedTarget().getCWidth() / 2),
                    (int) (player.getSelectedTarget().gety() + player.getSelectedTarget().ymap - player.getSelectedTarget().getCHeight() / 2),
                    player.getSelectedTarget().getCWidth(),
                    player.getSelectedTarget().getCHeight());

            /*g.drawLine((int) (XY.getX()),
                    (int) (XY.getY()),
                    (int) (SelectedEnemy.getx() + SelectedEnemy.xmap),
                    (int) (SelectedEnemy.gety() + SelectedEnemy.ymap));*/
        }

        if(player.getLockedTarget()!=null)
        {
            g.setColor(Color.green);
            g.drawRect((int) (player.getLockedTarget().getx() + player.getLockedTarget().xmap - player.getLockedTarget().getCWidth() / 2),
                    (int) (player.getLockedTarget().gety() + player.getLockedTarget().ymap - player.getLockedTarget().getCHeight() / 2),
                    player.getLockedTarget().getCWidth(),
                    player.getLockedTarget().getCHeight());
        }


        /*//map mouse position
        g.setColor(Color.yellow);
        g.drawString(
                (int) (XY.getX()-Players.get(0).getXmap())+ " - " +
                (int) (XY.getY()-Players.get(0).getYmap()),600,650);
        //player position
        g.setColor(Color.green);
        g.drawString(Players.get(0).getx()+"-"+Players.get(0).gety(),600,664);*/

        /** PLAYERS STATES **/
        g.setColor(Color.green);

        if(player.jumping)
            g.drawString(String.valueOf("jumping"),750,530);
        if(player.gliding)
            g.drawString(String.valueOf("gliding"),750,565);
        if(player.falling)
            g.drawString(String.valueOf("falling"),750,585);
        if(player.left)
            g.drawString(String.valueOf("left"),700,550);
        if(player.right)
            g.drawString(String.valueOf("right"),800,550);


        g.setColor(Color.red);

        if(!player.jumping)
            g.drawString(String.valueOf("jumping"),750,530);
        if(!player.gliding)
            g.drawString(String.valueOf("gliding"),750,565);
        if(!player.falling)
            g.drawString(String.valueOf("falling"),750,585);
        if(!player.left)
            g.drawString(String.valueOf("left"),700,550);
        if(!player.right)
            g.drawString(String.valueOf("right"),800,550);

    }

}













