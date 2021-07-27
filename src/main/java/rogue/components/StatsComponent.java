package rogue.components;

import com.badlogic.ashley.core.Component;
import rogue.stats.Stat;
import rogue.stats.StatType;

import java.util.HashMap;
import java.util.Map;

// container for various stats an entity can have
public class StatsComponent implements Component {
    Map<StatType, Stat> stats;

    public StatsComponent() {
        stats = new HashMap<>();
    }

    public StatsComponent(final Stat healthStat, final Stat strengthStat) {
        stats = new HashMap<>();
        addStat(StatType.HEALTH, healthStat);
        addStat(StatType.STRENGTH, strengthStat);
    }

    public void addStat(final StatType statType, final Stat stat) {
        stats.put(statType, stat);
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
}
