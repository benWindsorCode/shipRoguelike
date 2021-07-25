package rogue.components.actions;

import com.badlogic.ashley.core.Component;

public class HungerActionComponent implements Component {
    public int pointsDelta;

    public HungerActionComponent(int pointsDelta) {
        this.pointsDelta = pointsDelta;
    }
}
