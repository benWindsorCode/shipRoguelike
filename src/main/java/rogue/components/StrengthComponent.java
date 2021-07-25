package rogue.components;

import com.badlogic.ashley.core.Component;

public class StrengthComponent implements Component {
    public int strength;

    public StrengthComponent(int strength) {
        this.strength = strength;
    }
}
