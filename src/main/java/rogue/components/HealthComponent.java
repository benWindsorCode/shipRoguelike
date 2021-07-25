package rogue.components;

import com.badlogic.ashley.core.Component;

public class HealthComponent implements Component {
    public int hitpoints;
    public int maxHitpoints;

    public HealthComponent(int maxHitpoints) {
        this.maxHitpoints = maxHitpoints;
        this.hitpoints = maxHitpoints;
    }
}
