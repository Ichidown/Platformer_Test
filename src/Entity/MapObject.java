package Entity;

import GameState.LevelState;
import Main.Game_Panel;
import TileMap.TileMap;
import TileMap.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;

import static Tools.Calculate.calculateAnglePrecise;
import static Tools.Render.rotateImage;
import static Tools.Render.setOpacity;

public abstract class MapObject
{
    //needs health-healthregen
    // for example boxes needs health so that we can destroy them

    //!!!!
    public LevelState levelState;
    //tile stuff
    public TileMap tileMap;
    protected int tileSize;
    public double xmap;//for the screen position
    public double ymap;//
    //exact in map position
    /*protected double actualXmap=;
    protected double actualYmap=;*/

    //position and vector
    public double x;//position x
    public double y;//position y
    public double dx;//direction x
    public double dy;//direction y
    //protected double initialDX,initialDY;//direction xy

    //dimentions
    public int width;
    public int height;

    //collision box
    public int cwidth,originalCwidth;
    public int cheight,originalCheight;

    //collision
    public int currentRow;
    public int currentCol;
    protected double xdest;//next position: destination
    protected double ydest;
    public double xtemp;//temporary position
    public double ytemp;

    protected boolean topLeft;
    protected boolean topRight;
    protected boolean bottomLeft;
    protected boolean bottomRight;

    //animation
    public Animation animation;
    public int currentAction;//curent animation
    protected int previousAction;
    public boolean facingRight;//if right (do nothing) if not:left (miror image)

    //movement
    public boolean left;
    public boolean right;
    public boolean up; //never working ???
    public boolean down;//never working ???
    public boolean jumping;
    public boolean falling;

    //movement restrictions
    protected boolean canDown=true;
    protected boolean canUp=true;
    protected boolean canMoveLeft_Right =true;

    //movement attributes
    public double moveSpeed;//gota link with animation speed !!!
    public double maxSpeed;
    public double stopSpeed;//the less value the less terain resistance
    public double fallSpeed;
    public double maxFallSpeed;
    public double jumpSpeed,jumpDistance, remainingJumpDistance;
    //public double stopJumpSpeed;
    //restrictions
    public boolean Move,Jump;//allways working ???
    protected boolean ignoreMapCollision,dropPointCalibration=false;
    protected boolean fixPerfectHV;//fix the perfect horizontal/vertical proectile & add player ability to stay in wall

    //movement stuff
    protected int bounces=0;//needs to be working on units
    protected double bounceResistance=0;

    //distance management
    protected double distance=0;
    protected int maxDistance=0;
    protected int minDistance=0;
    protected boolean stopAtMinDistance=false;
    protected boolean countDistance=true;//over calculation !! (not allways needed)

    //new things
    public int maxLifeTime =-1;
    //public int lifeTime=0;
    public boolean dead;
    protected DeadEntity deadEntity;

    //rotation zoom opacity
    public ShapeShifter shapeShifter =new ShapeShifter(0,0,0,false);
    protected boolean OrientedRotation=false;



    //constructor
    public MapObject(TileMap tm)
    {
        tileMap=tm;
        tileSize=tm.getTileSize();
    }

    /*public MapObject(LevelState ls)
    {
        levelState=ls;
        tileMap=ls.getTileMap();
    }*/

    /*public MapObject()
    {

    }*/
    public void canChangePosition(boolean move, boolean up, boolean down)
    {
        canMoveLeft_Right=move;
        canUp=up;
        canDown=down;
    }

    public boolean intersects(MapObject o)//colision with object computer
    {
        Rectangle r1 = getRectangle();
        Rectangle r2 = o.getRectangle();

        return r1.intersects(r2);//intersects predefined

    }
    public Rectangle getRectangle()
    {
        return new Rectangle((int)x-cwidth,(int)y-cheight,cwidth,cheight);
    }

    public void calculateCorners(double x,double y)
    {
        if(!ignoreMapCollision)
        {
            int leftTile =   (int) (x - cwidth  / 2 + 1)      / tileSize;//were no +1
            int rightTile =  (int) (x + cwidth  / 2 - 1)  / tileSize;
            int topTile =    (int) (y - cheight / 2 + 1)     / tileSize;//were no +1
            int bottomTile = (int) (y + cheight / 2 - 1) / tileSize;// why -1

            int TL = tileMap.getType(topTile, leftTile);
            int TR = tileMap.getType(topTile, rightTile);
            int BL = tileMap.getType(bottomTile, leftTile);
            int BR = tileMap.getType(bottomTile, rightTile);

            topLeft = TL == Tile.BLOCKED;
            topRight = TR == Tile.BLOCKED;
            bottomLeft = BL == Tile.BLOCKED;
            bottomRight = BR == Tile.BLOCKED;
        }
    }
    public boolean collideCorner(double x,double y)//determine if we got a collision with a tile
    {//problem with getting no colision while there is when moving horizontally
            int leftTile =   (int) (x - tileSize/2) / tileSize;
            int rightTile =  (int) (x + tileSize/2) / tileSize;
            int topTile =    (int) (y - tileSize/2) / tileSize;
            int bottomTile = (int) (y + tileSize/2) / tileSize;

            int TL = tileMap.getType(topTile, leftTile);
            int TR = tileMap.getType(topTile, rightTile);
            int BL = tileMap.getType(bottomTile, leftTile);
            int BR = tileMap.getType(bottomTile, rightTile);

            if(TL == Tile.BLOCKED||TR == Tile.BLOCKED||BL == Tile.BLOCKED||BR == Tile.BLOCKED)
                return true;
            else
                return false;
    }


    public Point getCalibratedDropPoint(int xdiff,int ydiff,int x , int y)//must optimise
    {
        //calculateCornersSpecial(calibratedX,calibratedY);
        /**boolean colided1=true;
        boolean colided2=true;
        boolean colided3=true;
        boolean colided4=true;*/

        //get no-colision positions
        int calibratedX=x;
        int calibratedY=y;
        System.out.println(collideCorner(calibratedX,calibratedY));
        for(int i=0;i<xdiff && collideCorner(calibratedX,calibratedY);i++)//going left
        {
            calibratedX=x-i;
            calibratedY=y;
        }
        Point p1=new Point(calibratedX,calibratedY);
        System.out.println(p1);


        //....................................
        calibratedX=x;
        calibratedY=y;
        System.out.println(collideCorner(calibratedX,calibratedY));
        for(int i=0;i<xdiff && collideCorner(calibratedX,calibratedY);i++)//going right
        {
            calibratedX=x+i;
            calibratedY=y;
        }
        Point p2=new Point(calibratedX,calibratedY);
        System.out.println(p2);


        //....................................
        calibratedX=x;
        calibratedY=y;
        for(int i=0;i<ydiff && collideCorner(calibratedX,calibratedY);i++)//gowing up
        {
            calibratedX=x;
            calibratedY=y-i;
        }
        Point p3=new Point(calibratedX,calibratedY);


        //....................................
        calibratedX=x;
        calibratedY=y;
        for(int i=0;i<ydiff && collideCorner(calibratedX,calibratedY);i++)//going down
        {
            calibratedX=x;
            calibratedY=y+i;
        }
        Point p4=new Point(calibratedX,calibratedY);

        /**  i1 i2 i3 i4   */

        //calculate lengths
        double xdiff1 = x-p1.getX();
        double ydiff1 = y-p1.getY();
        double length1 = Math.sqrt(xdiff1*xdiff1+ydiff1*ydiff1);

        double xdiff2 = x-p2.getX();
        double ydiff2 = y-p2.getY();
        double length2 = Math.sqrt(xdiff2*xdiff2+ydiff2*ydiff2);

        double xdiff3 = x-p3.getX();
        double ydiff3 = y-p3.getY();
        double length3 = Math.sqrt(xdiff3*xdiff3+ydiff3*ydiff3);

        double xdiff4 = x-p4.getX();
        double ydiff4 = y-p4.getY();
        double length4 = Math.sqrt(xdiff4*xdiff4+ydiff4*ydiff4);


        double minLength= Double.min(length1,Double.min(length2,Double.min(length3,length4)));//get the min length

        //System.out.println(length1+" "+length2+" "+length3+" "+length4);
        /*System.out.println(p1);
        System.out.println(p2);
        System.out.println(p3);
        System.out.println(p4);*/

        //return point of minlength
        if(minLength==length1)
        {//System.out.print(p1+"   left");
            return p1;}
        if(minLength==length2)
        {//System.out.print(p2+"   right");
            return p2;}
        if(minLength==length3)
        {//System.out.print(p3+"   up");
            return p3;}
        if(minLength==length4)
        {//System.out.print(p4+"   down");
            return p4;}

        else return new Point(x,y);//in case
    }

    public void checkTileMapCollision()
    {
        currentCol=(int)x/tileSize;
        currentRow=(int)y/tileSize;

        xdest=x+dx;
        ydest=y+dy;

        xtemp=x;
        ytemp=y;


        calculateCorners(x, ydest);


        if(dy<0&&canUp)//we r going up
        {
            if(topLeft || topRight)//we hit something
            {
                if(bounces==0)
                {
                    dy = 0;
                    if (fixPerfectHV)
                        dx = 0;//dx=0; is added
                }
                else
                {
                    bounceEffect();
                    dy= -(dy+bounceResistance);
                    bounces--;
                }

                    ytemp = currentRow * tileSize + cheight / 2;

            }
            else //we can keep going
            {
                ytemp+=dy;
            }
        }

        //Duplicated with falling
        if(dy>0&&canDown)//we r going down
        {
            if(bottomLeft || bottomRight)// we hit a tile
            {
                if(bounces==0)
                {
                    dy = 0;
                    if (fixPerfectHV)
                        dx = 0;//dx=0; is added
                }
                else
                {
                    bounceEffect();
                    dy= -(dy-bounceResistance);
                    bounces--;
                }

                    falling = false;
                    ytemp = (currentRow + 1) * tileSize - cheight / 2;

            }
            else//we keep falling
            {
                ytemp+=dy;
            }
        }
        //Duplicated with falling

        calculateCorners(xdest,y);//calculate corners x direction
        {
            if(dx<0&& canMoveLeft_Right)
            {
                if(topLeft || bottomLeft)//hit blocked tile == stopp moving
                {
                    if(bounces==0)
                    {
                    dx = 0;
                        if (fixPerfectHV)
                            dy = 0;//dy=0; is added
                    }
                    else
                    {
                        bounceEffect();
                        dx= -(dx+bounceResistance);
                        bounces--;
                    }

                        xtemp = currentCol * tileSize + cwidth / 2;
                }
                else
                {
                    xtemp+=dx;
                }
            }
            if(dx>0&& canMoveLeft_Right)
            {
                if(topRight || bottomRight)//hit blocked tile == stopp moving
                {
                    if(bounces==0)
                    {
                        dx = 0;
                        if(fixPerfectHV)
                            dy=0;//dy=0; is added
                    }
                    else
                    {
                        bounceEffect();
                        dx= -(dx-bounceResistance);
                        bounces--;
                    }

                    xtemp = (currentCol+1)*tileSize-cwidth/2;
                }
                else
                {
                    xtemp+=dx;
                }
            }
            //Duplicated with go down
            if(!falling&&canDown)
            {
                calculateCorners(x,ydest+1);
                if(!bottomLeft&&!bottomRight)
                {
                    falling = true;
                }
            }
            //Duplicated with go down

        }
    }


    public int getx(){return (int)x;}
    public int gety(){return (int)y;}
    public int getWidth(){return width;}
    public int getHeight(){return height;}
    public int getCWidth(){return cwidth;}
    public int getCHeight(){return cheight;}
    public double getXmap(){return xmap;}
    public double getYmap(){return ymap;}

    //Velocity .............................................
    public void setMoveSpeed(double s){moveSpeed=s;}
    public void setMaxSpeed(double s){maxSpeed=s;}
    public void setStopSpeed(double s){stopSpeed=s;}
    public void setFallSpeed(double s){fallSpeed=s;}
    public void setMaxFallSpeed(double s){maxFallSpeed=s;}
    /*public void setJumpSpeed(double s){
        jumpSpeed =s;}*/
    //public void setStopJumpSpeed(double s){stopJumpSpeed=s;}

    public double getMoveSpeed(){return moveSpeed;}
    public double getMaxSpeed(){return maxSpeed;}
    public double getStopSpeed(){return stopSpeed;}
    public double getFallSpeed(){return fallSpeed;}
    public double getMaxFallSpeed(){return maxFallSpeed;}
    //public double getJumpSpeed(){return jumpSpeed;}
    //public double getStopJumpSpeed(){return stopJumpSpeed;}
    //......................................................


    public DeadEntity getDeadEntity() {
        return deadEntity;
    }

    //restrictions..........................................
    public void setMove(boolean r){Move=r;}
    public void setJump(boolean r){Jump=r;}

    public boolean getMove(){return Move;}
    public boolean getJump(){return Jump;}
    //......................................................

    public void setPosition(double x,double y)//regular position
    {
            this.x = x;
            this.y = y;
    }

    public void setVector(double dx,double dy)
    {
        this.dx=dx;
        this.dy=dy;
    }

    public void setMapPosition()// map position:were to draw character
    {
        xmap=tileMap.getx();
        ymap=tileMap.gety();
    }

    public void setLeft(boolean b){left=b;}
    public void setRight(boolean b){right=b;}
    public void setUp(boolean b){up=b;}
    public void setDown(boolean b){down=b;}
    public void setJumping(boolean b){jumping=b;}


    public void updateDistance()
    {
        if(countDistance)
        {
            distance += Math.sqrt((dx * dx) + (dy * dy));
            //System.out.println(distance);
        }
    }
    public void resetDistance()
    {
        distance=0;
    }

    public double getDistance()
    {
        return distance;
    }
    public int getMaxDistance()
    {
        return maxDistance;
    }
    public int getMinDistance()
    {
        return minDistance;
    }
    public boolean isStopAtMinDistance()
    {
        return stopAtMinDistance;
    }
    public void setStopAtMinDistance(boolean r)
    {
        stopAtMinDistance=r;
    }

    public int getMaxLifeTime() {
        return maxLifeTime;
    }

    public void setMaxLifeTime(int maxLifeTime) {
        this.maxLifeTime = maxLifeTime;
    }

    public boolean notOnScreen()//if the object is not on the screen : dont draw it
    {/** possible performance drop when used ? why ?*/
        return     x+xmap+width/2< 0
                || x+xmap-width/2> Game_Panel.WIDTH
                || y+ymap+height/2<0
                || y+ymap-height/2>Game_Panel.HEIGHT;
    }

    public void updateShape()
    {

        /**REPOSITION THIS*/
        //update colision blocs
        double scale = shapeShifter.getSize();
        cheight=(int)(originalCheight*scale);
        cwidth=(int)(originalCwidth*scale);


        if(OrientedRotation)
        {
            shapeShifter.setRotation(calculateAnglePrecise(0,0,dx,dy)+360);
        }

        shapeShifter.update();
    }

    public void updateTimers()
    {
        if(maxLifeTime!=-1)
        {
            if (maxLifeTime > 0)
                maxLifeTime--;
            else
                setHit();
        }
    }

    public void setHit()
    {

    }

    public void draw(Graphics2D g)
    {
        if(!notOnScreen())/** Must apply this on all drawings like health bar particles ...**/
        {
            //opacity
            setOpacity(g, shapeShifter.getTrensparency());

            //zoom
            double scale = shapeShifter.getSize();
            g.scale(scale, scale);

            //rotation
            BufferedImage rotatedImage =rotateImage(animation.getCurrentImage(), shapeShifter.getRotation(),
                                                    animation.getCurrentImage().getWidth()/2,
                                                    animation.getCurrentImage().getHeight()/2);//centered rotation
            if (facingRight)
            {
                g.drawImage(
                        rotatedImage,
                        (int) ((x + xmap - width / 2*scale)/scale),
                        (int) ((y + ymap - height / 2*scale)/scale),
                        null
                );
            } else
            {

                g.drawImage(
                        rotatedImage,
                        (int) ((x + xmap - width / 2*scale + width*scale)/scale),
                        (int) ((y + ymap - height / 2*scale)/scale),
                        -width,
                        height,
                        null
                            );
            }

            //return to default values
            g.scale(1/scale,1/scale);
            setOpacity(g,1);
        }
    }




    //ADDITIONAL THINGS
    protected void bounceEffect()
    {

    }

}