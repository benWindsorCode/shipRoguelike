package rogue.components.actions;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

// Action to remove an item from an inventory, component added to the entity with the inventory
public class InventoryRemoveActionComponent implements Component {
    public List<Entity> entitiesToRemove;

    // if as a result of a drop we don't want to destroy, but a 'bin' option should destroy
    public boolean destroyEntity;

    public InventoryRemoveActionComponent(Entity entityToRemove) {
        this.entitiesToRemove = Collections.singletonList(entityToRemove);
        this.destroyEntity = false;
    }

    public InventoryRemoveActionComponent(Entity entityToRemove, boolean destroyEntity) {
        this.entitiesToRemove = Collections.singletonList(entityToRemove);
        this.destroyEntity = destroyEntity;
    }

    public InventoryRemoveActionComponent(List<Entity> entitiesToRemove) {
        this.entitiesToRemove = entitiesToRemove;
        this.destroyEntity = false;
    }

    public InventoryRemoveActionComponent(List<Entity> entitiesToRemove, boolean destroyEntity) {
        this.entitiesToRemove = entitiesToRemove;
        this.destroyEntity = destroyEntity;
    }
}
