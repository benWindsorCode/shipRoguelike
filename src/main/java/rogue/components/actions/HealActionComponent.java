package rogue.components.actions;

import com.badlogic.ashley.core.Component;

public class HealActionComponent implements Component {
    public int pointsToHeal;

    public HealActionComponent(int pointsToHeal) {
        this.pointsToHeal = pointsToHeal;
    }
}
