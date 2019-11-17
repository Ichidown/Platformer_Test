package Entity.Attacks.Inflictions;


import Entity.Character.Unit;

public class Inflicted
{
    int Delay;
    double amount;
    Unit unit;

    public Inflicted(int delay,double amount ,Unit unit)
    {
        Delay = delay;
        this.amount = amount;
        this.unit = unit;
    }

    public int getDelay() {
        return Delay;
    }

    public void setDelay(int delay) {
        Delay = delay;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public void decrementDelay()
    {
        Delay--;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
