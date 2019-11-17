package GameState;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import TileMap.Background;

public class MenuState extends GameState
{
    private Background bg;
    private int curentChoise=0;
    private String[] options = {"Start","Help","Quit"};

    private Color titleColor;
    private Font titleFont,titleFontLarge;
    private Font font;//regular font

    public MenuState(GameStateManager gsm)
    {
        this.gsm=gsm;
        /*try
        {*/
            bg = new Background("/Backgrounds/menubg1.png",1);//1 for scale
            bg.setVector(-0.05,0);//move to the left by x pixel
            bg.loadRessource();

            initialiseText();



        //}catch(Exception e){e.printStackTrace();}
    }

    public void initialiseGame()
    {

    }
    private void initialiseText()
    {
        font = new Font("Arial",Font.PLAIN,12*3);//X3
        titleFontLarge = new Font("Century Gothic",Font.PLAIN,70);//X3
        titleFont = new Font("Century Gothic",Font.PLAIN,28);//X3

        titleColor = new Color(158, 0, 14);
    }
    public void update()
    {
        bg.update();
    }
    public void draw(Graphics2D g)
    {
        bg.draw(g);//draw background
        //draw title

        g.setColor(titleColor);
        g.setFont(titleFontLarge);
        g.drawString("League of Champions",40,60*3);


        g.setColor(titleColor);
        g.setFont(titleFont);
        g.drawString("0.5",750,210);

        //draw menu options
        g.setFont(font);
        for(int i=0;i<options.length;i++)
        {
            if(i==curentChoise)
            {
                g.setColor(Color.BLACK);
            }
            else
            {
                g.setColor(Color.RED);
            }
            g.drawString(options[i],145*3,140*3+i*15*3);// X3
        }
    }
    private void select()
    {
       if(curentChoise==0)
       {
           gsm.setState(GameStateManager.LEVELSTATE);//switch to state "inGame"
       }
        if(curentChoise==1)
        {
            //help
        }
        if(curentChoise==2)
        {
            //quit
            System.exit(0);
        }
    }
    public void keyPressed(int k)
    {
        if(k== KeyEvent.VK_ENTER)
        {
            select();
        }
        if(k== KeyEvent.VK_UP)
        {
            curentChoise--;
            if(curentChoise ==-1)
            {
                curentChoise= options.length-1;
            }
        }
        if(k== KeyEvent.VK_DOWN)
        {
            curentChoise++;
            if(curentChoise==options.length)
            {
                curentChoise = 0;
            }
        }
    }
    public void keyReleased(int k)
    {

    }



    public void mousePressed(int e,Point P)
    {
        if(e== MouseEvent.BUTTON1)
        {
            select();
        }
    }
    public void mouseReleased(int e,Point P)
    {

    }
    public void mouseEntered(Point P) {

    }
    public void mouseExited(Point P) {

    }

    public void mouseMoved(Point P) {

    }
    public void mouseDragged(Point P) {

    }




}
