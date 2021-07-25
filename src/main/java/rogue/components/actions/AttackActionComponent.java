package rogue.components.actions;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

// Action placed on component doing the attack
public class AttackActionComponent implements Component {
    public Entity other;

    public AttackActionComponent(Entity other) {
        this.other = other;
    }
}
