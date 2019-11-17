package Entity.Attacks;


import Entity.Ability;
import Entity.Attacks.Inflictions.InflictionObj;
import Entity.Character.Unit;

import java.util.ArrayList;

public class Melee
{
    private int range;
    private boolean MultiTarget;
    private Unit unit;
    //private Ability ability;
    private ArrayList<InflictionObj> inflictions;
    private HitRules hitRules;


    public Melee(int range, boolean multyTarget, Unit unit, ArrayList<InflictionObj> inflictions, HitRules hitRules)
    {
        this.range = range;
        MultiTarget = multyTarget;
        this.unit = unit;
        this.inflictions=inflictions;
        this.hitRules = hitRules;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public boolean isMultiTarget() {
        return MultiTarget;
    }

    public void setMultiTarget(boolean multiTarget) {
        MultiTarget = multiTarget;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }
/*
    public Ability getAbility() {
        return ability;
    }

    public void setAbility(Ability ability) {
        this.ability = ability;
    }
*/

    public ArrayList<InflictionObj> getInflictions() {
        return inflictions;
    }

    public void setInflictions(ArrayList<InflictionObj> inflictions) {
        this.inflictions = inflictions;
    }

    public HitRules getHitRules() {
        return hitRules;
    }

    public void setHitRules(HitRules hitRules) {
        this.hitRules = hitRules;
    }
}
