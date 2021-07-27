package rogue.stats;

public class Stat {
    private final StatType statType;
    private double minValue;
    private double maxValue;
    private double currentValue;

    public Stat(StatType statType, double maxValue, double currentValue) {
        this.statType = statType;
        this.maxValue = maxValue;
        this.currentValue = currentValue;
        this.minValue = 0;
    }

    public StatType getStatType() {
        return statType;
    }

    public double getMinValue() {
        return minValue;
    }

    public double getMaxValue() {
        return maxValue;
    }

    public double getCurrentValue() {
        return currentValue;
    }

    public void adjustCurrentValue(double delta) {
        this.currentValue = Math.max(this.minValue, Math.min(this.currentValue + delta, this.maxValue));
    }

    public void adjustMaxValue(double delta) {
        this.maxValue = this.maxValue + delta;
    }

    public void adjustMinValue(double delta) {
        this.minValue = this.minValue + delta;
    }
}
