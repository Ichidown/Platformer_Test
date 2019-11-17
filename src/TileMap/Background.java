package TileMap;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;

import GameState.LevelState;
import Main.Game_Panel;

public class Background
{
 private BufferedImage image;
    private double x,y,dx,dy;
    private double moveScale;//background movement scale
    private String imagePath;
    //private LevelState levelState;

    public Background(String s, double mouvescale)//,LevelState lv)
    {
        moveScale = mouvescale;
        imagePath=s;
        //levelState=lv;
    }

    /*public void loadRessource()
    {
        image = levelState.resourceManager.getRessource(imagePath,imagePath);
    }*/

    public void loadRessource()
    {
        try
        {
            InputStream in =getClass().getResourceAsStream(imagePath);
            image = ImageIO.read(in);
            in.close();

            //ModifyImageColor(image,"greyscale");
        }catch(Exception e){e.printStackTrace();}

    }

    public void setPosition(double x , double y)
    {
        this.x=x*moveScale % Game_Panel.WIDTH;//???
        this.y=y*moveScale % Game_Panel.HEIGHT;//???
    }
    public void setVector(double dx,double dy)//for bacground scroll
    {
        this.dx=dx;
        this.dy=dy;
    }
    public void update()
    {
        x+=dx;
        y+=dy;
    }
    public void draw(Graphics2D g)
    {
        g.drawImage(image,(int)x,(int)y,null);
        if(x<0)//to fill the screen after a scroll
        {
            g.drawImage(image,(int)x+Game_Panel.WIDTH,(int)y,null);
        }
        if(x>0)//draw extra image after scrolling
        {
            g.drawImage(image,(int)x-Game_Panel.WIDTH,(int)y,null);
        }
    }
}
