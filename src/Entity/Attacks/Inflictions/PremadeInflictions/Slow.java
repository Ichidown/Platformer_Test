package Entity.Attacks.Inflictions.PremadeInflictions;


import Entity.Attacks.Inflictions.Inflicted;
import Entity.Attacks.Inflictions.InflictionObj;
import Entity.Attacks.Inflictions.infliction;
import Entity.Character.Unit;

public class Slow extends infliction
{
    public Slow()/** implement the slow/speed up by value & not percentage **/
    {    }

    public void ApplyInfliction(Unit u,InflictionObj x)
    {
        double slowAmount=u.getMaxSpeed() * x.getAmount() / 100;
            targets.add(new Inflicted(x.getDelay(),slowAmount,u));
            u.setMaxSpeed(u.getMaxSpeed() - u.getMaxSpeed() * x.getAmount() / 100);
    }

    public void UndoInfliction(Inflicted t)
    {
        t.getUnit().setMaxSpeed(t.getUnit().getMaxSpeed()+t.getAmount());
    }
}
