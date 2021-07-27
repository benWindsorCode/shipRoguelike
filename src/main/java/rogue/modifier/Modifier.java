package rogue.modifier;

import com.badlogic.ashley.core.Entity;
import rogue.stats.StatType;

import java.util.Optional;

public class Modifier<T extends Entity> {
    private final T modifyingEntity;
    private final StatType statToModify;

    // delta to apply to stat min
    private final double minDelta;

    // delta to apply to stat current value
    private final double currentDelta;

    // delta to apply to stat max
    private final double maxDelta;

    public Modifier(T modifyingEntity, StatType statToModify, double currentDelta, double maxDelta) {
        this.modifyingEntity = modifyingEntity;
        this.statToModify = statToModify;
        this.currentDelta = currentDelta;
        this.maxDelta = maxDelta;
        this.minDelta = 0;
    }

    public Modifier(T modifyingEntity, StatType statToModify, double minDelta, double currentDelta, double maxDelta) {
        this.modifyingEntity = modifyingEntity;
        this.statToModify = statToModify;
        this.minDelta = minDelta;
        this.currentDelta = currentDelta;
        this.maxDelta = maxDelta;
    }

    public T getModifyingEntity() {
        return modifyingEntity;
    }

    public StatType getStatToModify() {
        return statToModify;
    }

    public double getMinDelta() {
        return minDelta;
    }

    public double getCurrentDelta() {
        return currentDelta;
    }

    public double getMaxDelta() {
        return maxDelta;
    }
}
