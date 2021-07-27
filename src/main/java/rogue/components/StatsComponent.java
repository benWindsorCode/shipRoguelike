package rogue.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import rogue.modifier.Modifier;
import rogue.stats.Stat;
import rogue.stats.StatType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// container for various stats an entity can have
public class StatsComponent implements Component {
    private Map<StatType, Stat> stats;
    private Map<StatType, List<Modifier<? extends Entity>>> modifiers;

    public StatsComponent() {
        stats = new HashMap<>();
        modifiers = new HashMap<>();
    }

    public StatsComponent(final Stat healthStat, final Stat strengthStat) {
        stats = new HashMap<>();
        modifiers = new HashMap<>();
        addStat(healthStat);
        addStat(strengthStat);
    }

    public void addStat(final Stat stat) {
        stats.put(stat.getStatType(), stat);
    }

    public void adjustCurrentValue(final StatType statType, final double delta) {
        Stat stat = stats.get(statType);
        stat.adjustCurrentValue(delta);
    }

    public void adjustMaxValue(final StatType statType, final double delta) {
        Stat stat = stats.get(statType);
        stat.adjustMaxValue(delta);
    }

    public double getStatCurrentValue(final StatType statType) {
        return stats.get(statType).getCurrentValue();
    }

    public double getStatMaxValue(final StatType statType) {
        return stats.get(statType).getMaxValue();
    }

    public boolean hasStat(final StatType statType) {
        return stats.containsKey(statType);
    }

    // TODO: should we do some hasStat checks here? and perhaps return bool on success or failure
    public void addModifier(final Modifier<? extends Entity> modifier) {
        StatType statType = modifier.getStatToModify();

        if(modifiers.containsKey(statType)) {
            modifiers.get(statType).add(modifier);
        } else {
            List<Modifier<? extends Entity>> modifierList = new ArrayList<>();
            modifierList.add(modifier);

            modifiers.put(statType, modifierList);
        }

        Stat statToModify = stats.get(statType);
        statToModify.adjustMaxValue(modifier.getMaxDelta());
        statToModify.adjustMinValue(modifier.getMinDelta());
        statToModify.adjustCurrentValue(modifier.getCurrentDelta());
    }

    // TODO: should we do check modifier exists? and perhaps return bool on success or failure
    // TODO: what if removing a health modifier kills the player?! how do we deal with this
    public void removeModifier(final Modifier<? extends Entity> modifier) {
        StatType statType = modifier.getStatToModify();

        List<Modifier<? extends Entity>> modifierList = modifiers.get(statType);
        modifierList.remove(modifier);

        Stat statToModify = stats.get(statType);
        statToModify.adjustMaxValue(-1 * modifier.getMaxDelta());
        statToModify.adjustMinValue(-1 * modifier.getMinDelta());
        statToModify.adjustCurrentValue(-1 * modifier.getCurrentDelta());
    }
}
