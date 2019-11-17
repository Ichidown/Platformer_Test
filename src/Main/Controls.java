package Main;

import Entity.Character.Unit;

import java.awt.*;
import java.awt.event.*;

public class Controls implements KeyListener,MouseListener,MouseMotionListener
{
    Game_Panel Gp;

    public Controls(Game_Panel Gp)
    {
        this.Gp=Gp;
    }


    //set methods of controls

    /*public void EnableA0(Unit p)
    {
        p.setAbilityCanUse(0,true);
    }*/

    /*public void EnableA1(Player p)
    {
        p.setAbility1(true);
    }
    public void EnableA2(Player p)
    {
        p.setAbility2(true);
    }
    public void EnableA3(Player p)
    {
        p.setAbility3(true);
    }
    public void EnableA4(Player p)
    {
        p.setAbility4(true);
    }
    public void EnableMovement(Player p)
    {
        p.setMove(true);
    }
    public void EnableJumping(Player p)
    {
        p.setJump(true);
    }


    public void DisableA0(Player p)
    {
        p.setAbility0(false);
    }
    public void DisableA1(Player p)
    {
        p.setAbility1(false);
    }
    public void DisableA2(Player p)
    {
        p.setAbility2(false);
    }
    public void DisableA3(Player p)
    {
        p.setAbility3(false);
    }
    public void DisableA4(Player p)
    {
        p.setAbility4(false);
    }
    public void DisableMovement(Player p)
    {
        p.setMove(false);
    }
    public void DisableJumping(Player p)
    {
        p.setJump(false);
    }*/


    //----------------------------CONTROLS---------------------------------------
    public void keyTyped(KeyEvent key)
    {

    }
    public void keyPressed(KeyEvent key)
    {
        Gp.gsm.keyPressed(key.getKeyCode());
    }
    public void keyReleased(KeyEvent key)
    {
        Gp.gsm.keyReleased(key.getKeyCode());
    }


    public void mouseClicked(MouseEvent e)
    {

    }

    public void mousePressed(MouseEvent e)
    {
        Gp.gsm.mousePressed(e.getButton(),Gp.mouse_pos);
    }

    public void mouseReleased(MouseEvent e)
    {
        Gp.gsm.mouseReleased(e.getButton(),Gp.mouse_pos);
    }

    public void mouseEntered(MouseEvent e)
    {
        //Gp.gsm.mouseEntered(new Point(10,10));//!!! ??? l'initialiser avan mouse listener
    }

    public void mouseExited(MouseEvent e)
    {
        //    gsm.mouseExited(new Point(10,10));
    }

    public void mouseDragged(MouseEvent e)
    {
        Gp.mouse_pos=new Point(e.getX(),e.getY());
        Gp.gsm.mouseDragged(Gp.mouse_pos);
    }

    public void mouseMoved(MouseEvent e)
    {
        if(Gp.gsm!=null)//to get rid of the errors where we move the mouse right when we lunched the game
        {
            Gp.mouse_pos = new Point(e.getX(), e.getY());
            Gp.gsm.mouseMoved(Gp.mouse_pos);
        }
    }


}
