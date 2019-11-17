package Entity;

public class ShapeShifter
{
    private double rotation,Rdegree;
    private double size,Sdegree;
    private double trensparency,Tdegree;
    private double sizeLimit=0.000001;
    private double opacityLimit=0;
    private boolean updatable;

    public ShapeShifter(double tdegree, double sdegree, double rdegree, boolean updatable)
    {
        Tdegree = tdegree;
        Sdegree = sdegree;
        Rdegree = rdegree;

        this.updatable=updatable;

        rotation=0;
        size=1;
        trensparency=1;
    }



    public void incrementRotation()
    {
        rotation+=Rdegree;
    }

    public void incrementSize()
    {
        size+=Sdegree;
        if(size<sizeLimit)
        {
            size=sizeLimit;
        }

    }

    public void incrementTrensparency()
    {
        trensparency+=Tdegree;
        if(trensparency<opacityLimit)
        {
            trensparency=opacityLimit;//find a way to work with inverted trensparency limit
        }
        else if(trensparency>1)
        {
            trensparency=1;
        }
    }

    public double getRotation() {
        return rotation;
    }

    public double getSize() {
        return size;
    }

    public double getTrensparency() {
        return trensparency;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public void setTrensparency(double trensparency) {
        this.trensparency = trensparency;
    }


    public void setOpacityLimit(double opacityLimit)
    {
        if(opacityLimit>0&&opacityLimit<=1)
            this.opacityLimit = opacityLimit;
    }

    public void setSizeLimit(double sizeLimit)
    {
        if(sizeLimit>this.sizeLimit)
            this.sizeLimit = sizeLimit;
    }


    public void setRdegree(double rdegree) {
        Rdegree = rdegree;
    }

    public void setSdegree(double sdegree) {
        Sdegree = sdegree;
    }

    public void setTdegree(double tdegree) {
        Tdegree = tdegree;
    }

    public void setUpdatable(boolean r)
    {
        updatable=r;
    }

    public boolean isUpdatable()
    {
        return updatable;
    }

    public void update()
    {
        if(updatable)
        {
            incrementRotation();
            incrementSize();
            incrementTrensparency();
        }
    }
}
