package Entity.Attacks.Inflictions.PremadeInflictions;


import Entity.Attacks.Inflictions.InflictionObj;
import Entity.Attacks.Inflictions.infliction;
import Entity.Character.Unit;

public class Damage extends infliction
{
    public Damage()
    {
    }

    public void ApplyInfliction(Unit u,InflictionObj x)
    {
        u.setHealth(u.getHealth() - x.getAmount());//apply effect here
    }

}
