package rogue.components.actions;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

// Component on the entity with the inventory, performing action to drop item in inventory in a direction
public class DropDirectionActionComponent implements Component {
    public int mx;
    public int my;
    public Entity toDrop;

    public DropDirectionActionComponent(int mx, int my, Entity toDrop) {
        this.mx = mx;
        this.my = my;
        this.toDrop = toDrop;
    }
}
