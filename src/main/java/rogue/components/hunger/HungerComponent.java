package rogue.components.hunger;

import com.badlogic.ashley.core.Component;

// Placed on entity who will experience hunger
public class HungerComponent implements Component {
    public int currentHunger;
    public int maxHunger;

    // Turns until currentHunger increases
    public int turnsUntilIncrease;

    // How many turns each increase should take
    public int turnsPerIncrease;

    public int increaseAmount;

    public HungerComponent(int turnsPerIncrease) {
        this(0, 100, 5, turnsPerIncrease, turnsPerIncrease);
    }

    public HungerComponent(int currentHunger, int maxHunger, int increaseAmount, int turnsUntilIncrease, int turnsPerIncrease) {
        this.currentHunger = currentHunger;
        this.maxHunger = maxHunger;
        this.increaseAmount = increaseAmount;
        this.turnsUntilIncrease = turnsUntilIncrease;
        this.turnsPerIncrease = turnsPerIncrease;
    }

    public int getCurrentHunger() {
        return currentHunger;
    }

    public int getMaxHunger() {
        return maxHunger;
    }
}
