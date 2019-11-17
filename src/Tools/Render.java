package Tools;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;


public class Render
{
    public static void ModifyImageColor(BufferedImage image,String R)
    {
        for(int y=0;y<image.getHeight();y++)
            for(int x=0;x<image.getWidth();x++)
            {
                int p= image.getRGB(x,y);

                int a = (p>>24)&0xff;
                int r = (p>>16)&0xff;
                int g = (p>>8)&0xff;
                int b = p&0xff;

                if(R=="greyscale")
                {
                //calculate average
                int avg = (r+g+b)/3;
                //replace values
                p=(a<<24) | (avg<<16) | (avg<<8)| avg;
                }

                if(R=="negative")
                {
                    //calculate average
                    r=255-r;
                    g=255-g;
                    b=255-b;
                    //replace values
                    p=(a<<24) | (r<<16) | (g<<8)| b;
                }

                if(R=="red")
                {
                    //calculate average
                    g=0;
                    b=0;
                    //replace values
                    p=(a<<24) | (r<<16) | (g<<8)| b;
                }

                if(R=="blue")
                {
                    //calculate average
                    r=0;
                    g=0;
                    //replace values
                    p=(a<<24) | (r<<16) | (g<<8)| b;
                }

                if(R=="green")
                {
                    //calculate average
                    r=0;
                    b=0;
                    //replace values
                    p=(a<<24) | (r<<16) | (g<<8)| b;
                }



                image.setRGB(x,y,p);

            }
    }

    public static void setOpacity(Graphics2D g,double opacity)
    {
         //for opacity
            float op = (float) opacity;

            if(op>1)
                op=1;
            else if(op<0)
                op=0;
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, op));
        //.
        //.
        //.
        //must know how to automatise
    }

    public static BufferedImage rotateImage(BufferedImage image,double rotationRequired,double centerX,double centerY)//, Unit player)//replace player by projectile maybe
    {
        /**THIS CONSUMES A LOT**/
        if(rotationRequired!=0)
        {
            AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, centerX, centerY);
            AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);

            return op.filter(image, null);
        }
        return  image;
    }

    public static void scaleImage(Graphics2D g,BufferedImage image,double xscale,double yscale)/** CONSUMES A LOT WHEN SCALING TO BIG IMAGE */
    {
        /**+ need to implement a way where we can scale to a precise resolution
             using x/y scale generated from starting resolution to targeted resolution**/
        //g.scale(xscale, yscale);//this scales after drawing    !!! to avoid !!!

        //image.getScaledInstance(50,50,2);//this scales the image before drawing !!! prefered !!!
            // the two "50" are the targeted x/y & the "1" is a hint :  1 is hard transformation / -1 is smooth transformation
    }

}
