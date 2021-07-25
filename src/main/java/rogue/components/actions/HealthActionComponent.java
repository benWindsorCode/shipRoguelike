package rogue.components.actions;

import com.badlogic.ashley.core.Component;

public class HealthActionComponent implements Component {
    public int pointsDelta;

    public HealthActionComponent(int pointsDelta) {
        this.pointsDelta = pointsDelta;
    }
}
