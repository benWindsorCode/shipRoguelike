package rogue.components.actions;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

// Action to move an item from one Entity to another, component added to the entity with the inventory that has the item
public class InventoryTransferActionComponent implements Component {
    public Entity entityToTransfer;
    public Entity reciever;

    public InventoryTransferActionComponent(Entity reciever, Entity entityToTransfer) {
        this.reciever = reciever;
        this.entityToTransfer = entityToTransfer;
    }
}
