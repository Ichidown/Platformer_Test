package Entity.Attacks.Inflictions.PremadeInflictions;

import Entity.Attacks.Inflictions.Inflicted;
import Entity.Attacks.Inflictions.InflictionObj;
import Entity.Attacks.Inflictions.infliction;
import Entity.Character.Unit;

public class Stun extends infliction
{
    public Stun()
    {

    }

    public void ApplyInfliction(Unit u,InflictionObj x)
    {
        int TargetIndex = UnitExist(u);
        if(TargetIndex == -1)
        {
            u.forceChangeMovement(true, true, false, false, false, true, false);//get state before getting stunned !!!

            u.dx = 0;

            //stop all unit stuff like : targeting - ai ... for more performance

            for (int i = 0; i < u.Abilities.size(); i++)
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
        t.getUnit().forceChangeMovement(true,true,true,true,true,true,true);//return to the previous state

        for(int i=0;i<t.getUnit().Abilities.size();i++)
            t.getUnit().setAbilityCanUse(i,true);
    }

}
