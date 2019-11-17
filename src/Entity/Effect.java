package Entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import static Tools.Render.rotateImage;
import static Tools.Render.setOpacity;

public class Effect
{

    private int x;
    private int y;
    private int xmap;
    private int ymap;

    private int width;
    private int height;

    private boolean facingRight;

    private Animation animation;

    private boolean remove;
    private MapObject mapObject;

    ShapeShifter shape;

    public Effect(MapObject p)
    {
        mapObject = p;
        //get the position
        this.x = p.getx();
        this.y = p.gety();
        //get the orientation
        facingRight=p.facingRight;
        //load sprites
        TryLoad(p.getDeadEntity());
    }



    private void TryLoad(DeadEntity DE)
    {
        //BufferedImage[] hitSprites=null;

        width = DE.getWidth();
        height = DE.getHeight();
        String path = DE.getStartSpritePath();

        //decide if we use shapeShifter of the dead entity / live entity
        if(DE.getShape()!=null)
            shape = DE.getShape();
        else
            shape = mapObject.shapeShifter;


        BufferedImage hitSpritesheet = DE.levelState.resourceManager.getRessource(path,path);

        BufferedImage[] hitSprites= DE.levelState.resourceManager.getExistingRessource(path).loadProjectileResource( DE.getStartNumFrames(), width, height);


        animation = new Animation();
        animation.setFrames(hitSprites);
        animation.setDelay(DE.getStartAnimationDelay());//automatise
    }

    public void update()
    {
        //updateShape();
        if(shape.isUpdatable())
            shape.update();

        animation.update();
        if(animation.hasPlayedOnce())/**add animation time + animation loops*/
        {
            remove = true;
        }
    }

    public boolean shouldRemove() { return remove; }

    public void setMapPosition(int x, int y)
    {
        xmap = x;
        ymap = y;
    }


    public void draw(Graphics2D g)
    {
        //if(!notOnScreen())/** Must apply this on all drawings like health bar particles ...**/
        /** need not on screen **/

            //opacity
            setOpacity(g,shape.getTrensparency());

            //zoom
            double scale = shape.getSize();
            g.scale(scale, scale);
            //rotation
            BufferedImage rotatedImage =rotateImage(animation.getCurrentImage(),shape.getRotation(),
                    animation.getCurrentImage().getWidth()/2,
                    animation.getCurrentImage().getHeight()/2);//centered rotation
            if (!facingRight)
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

            g.scale(1/scale,1/scale);
            setOpacity(g,1);

    }
}
