package Entity.Attacks.Inflictions;


import Entity.Character.Unit;

import java.util.ArrayList;

public class infliction
{
    protected ArrayList<Inflicted> targets= new ArrayList<Inflicted>();

    public infliction()
    {
    }
    public void ApplyInfliction(Unit u,InflictionObj x)
    {

    }
    public void UndoInfliction(Inflicted t)
    {

    }

    public int UnitExist(Unit u)
    {
        for(int i = 0 ;i<targets.size();i++)
        {
            if(u==targets.get(i).getUnit())
            {
                return i;
            }
        }
        return -1;
    }

    public void updateDelays()
    {
        for(int i = 0;i<targets.size();i++)
        {
            if(targets.get(i).getDelay()!=0)
            {
                targets.get(i).decrementDelay();
            }
            else
            {
                UndoInfliction(targets.get(i));
                targets.remove(i);
                i--;
            }
        }
    }


}
