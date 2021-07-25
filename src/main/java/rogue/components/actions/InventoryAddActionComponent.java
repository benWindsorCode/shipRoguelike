package rogue.components.actions;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import rogue.components.InventoryComponent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// TODO: allow List<Entity> just like remove action
// Action to add an item to an inventory, added to the entity with the inventory
public class InventoryAddActionComponent implements Component {
    public List<Entity> entitiesToAdd;

    public InventoryAddActionComponent() {
        entitiesToAdd = new ArrayList<>();
    }

    public InventoryAddActionComponent(Entity entity) {
        entitiesToAdd = new ArrayList<>();
        entitiesToAdd.add(entity);
    }

    public InventoryAddActionComponent(List<Entity> entitiesToAdd) {
        this.entitiesToAdd = entitiesToAdd;
    }
}
