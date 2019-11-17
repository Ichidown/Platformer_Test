package Entity.Attacks.Inflictions.PremadeInflictions;


import Entity.Attacks.Inflictions.Inflicted;
import Entity.Attacks.Inflictions.InflictionObj;
import Entity.Attacks.Inflictions.infliction;
import Entity.Character.Unit;

public class Silence extends infliction
{
    public Silence()
    {

    }

    public void ApplyInfliction(Unit u,InflictionObj x)//not yet tested
    {
        int TargetIndex = UnitExist(u);
        if(TargetIndex == -1)
        {
            for (int i = 1; i < u.Abilities.size(); i++)//i == 1 means the AA isn't affected
                u.setAbilityCanUse(i, false);

            targets.add(new Inflicted(x.getDelay(),0,u));
        }
        else
        {
            targets.get(TargetIndex).setDelay(Math.max(targets.get(TargetIndex).getDelay(),x.getDelay()));
        }
    }

    public void UndoInfliction(Inflicted t)
    {
        for(int i=1;i<t.getUnit().Abilities.size();i++)
            t.getUnit().setAbilityCanUse(i,true);
    }
}
