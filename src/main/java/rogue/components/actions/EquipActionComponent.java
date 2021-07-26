package rogue.components.actions;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

// Placed on the entity who is equipping an item
public class EquipActionComponent implements Component {
    public Entity itemToEquip;

    public EquipActionComponent(Entity itemToEquip) {
        this.itemToEquip = itemToEquip;
    }
}
