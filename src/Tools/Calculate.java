package Tools;


import java.awt.*;
import java.util.Random;

public class Calculate
{
    private static double xdiff,ydiff,distance;
    public static double Distance(Point P1, Point P2)
    {
        xdiff = (P1.getX()-P2.getX());
        ydiff = (P1.getY()-P2.getY());
        distance = Math.sqrt(xdiff*xdiff+ydiff*ydiff);
        return distance;
    }

    public static Point DistanceHV(Point P1, Point P2)
    {
        xdiff = (P1.getX()-P2.getX());
        ydiff = (P1.getY()-P2.getY());

        return new Point((int)xdiff,(int)ydiff);
    }

    public static int getRandomIntPercentage(int percent)
    {
        //this randomise from -percent/2 to percent/2
        Random Rnumber = new Random();
        return  Rnumber.nextInt(percent+1)-percent/2;
    }
    public static Float getRandomFloatPercentage(double percent)
    {
        //this randomise a float from 0 to percent
        Random Rnumber = new Random();/** this is duplicated **/
        int i = (int)(percent*10000);
        int x = Rnumber.nextInt(i+1);
        int x2 = Rnumber.nextInt(10000+1);

        Float f = (float) (x-(x2/10000))/10000;

        if(f<0)
            return (float)0;
        else
            return f;
    }

    public static double getRandomInsideRange(double rangeBorder1,double rangeBorder2)/**this one is ok but other randomizers do not accept negative values*/
    {
        //this randomise a float from rangeBorder1 to rangeBorder2
        Random Rnumber = new Random();
        double rb1 = rangeBorder1;
        double rb2 = rangeBorder2;

        //switch if not in order
        if(rb1>rb2)
        {/** create a switch tool if used a lot "use as double & then change its type here" **/
            double x = rb2;
            rb2=rb1;
            rb1=x;
        }

        double difference = rb2-rb1;

        int randomNumber = Rnumber.nextInt((int)(difference*10000)+1);

        return rb1+(double)randomNumber/10000;
    }



    /*public static float getRandomDoublePercentage(int degree)/**if u change return type rename the method name */
    /*{
        Random Rnumber = new Random();
        //return  Rnumber.nextDouble(degree*2+1)-degree;//from -degree to degree
        return Rnumber.nextInt();
    }*/

    public static double calculateAngle(Point StartPoint,Point EndPoint)
    {
        double xdiff = EndPoint.x-StartPoint.x;
        double ydiff = EndPoint.y-StartPoint.y;

        return Math.toDegrees(Math.atan2(ydiff , xdiff));
    }

    public static double calculateAnglePrecise(double x1,double y1,double x2,double y2)
    {
        double xdiff = x2-x1;
        double ydiff = y2-y1;

        return Math.toDegrees(Math.atan2(ydiff , xdiff));
    }


}
