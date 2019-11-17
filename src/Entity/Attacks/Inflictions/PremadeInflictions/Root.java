package Entity.Attacks.Inflictions.PremadeInflictions;


import Entity.Attacks.Inflictions.Inflicted;
import Entity.Attacks.Inflictions.InflictionObj;
import Entity.Attacks.Inflictions.infliction;
import Entity.Character.Unit;

public class Root extends infliction
{
    public Root()
    {

    }

    public void ApplyInfliction(Unit u, InflictionObj x)
    {
        int TargetIndex = UnitExist(u);
        if(TargetIndex == -1)
        {
            u.forceChangeMovement(true, true, false, false, false, true, false);//get player movements!!!

            u.dx = 0;

            // + stop all abilities with dash/teleport

            targets.add(new Inflicted(x.getDelay(),0,u));
        }
        else
        {
            targets.get(TargetIndex).setDelay(Math.max(targets.get(TargetIndex).getDelay(),x.getDelay()));
        }
    }

    public void UndoInfliction(Inflicted t)
    {
        t.getUnit().forceChangeMovement(true,true,true,true,true,true,true);//return to the previous state
    }
}
