package rogue.components.actions;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

// Component on the entity with the inventory, performing action to drop item in inventory
public class DropActionComponent implements Component {
    public Entity toDrop;

    public DropActionComponent(Entity toDrop) {
        this.toDrop = toDrop;
    }
}
