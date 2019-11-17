package Entity.Attacks.Inflictions;


public class InflictionObj
{
    private int delay;
    private infliction type;
    private int amount;

    public InflictionObj(int delay, int amount , infliction type )
    {
        this.delay = delay;
        this.type = type;
        this.amount=amount;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public infliction getType() {
        return type;
    }

    public void setType(infliction type) {
        this.type = type;
    }
}
