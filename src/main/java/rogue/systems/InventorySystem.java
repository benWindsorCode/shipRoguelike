package rogue.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.utils.ImmutableArray;
import rogue.components.InventoryComponent;
import rogue.components.PositionComponent;
import rogue.components.RenderableComponent;
import rogue.components.TileComponent;
import rogue.components.actions.*;
import rogue.environment.WorldGrid;
import rogue.factories.FamilyFactory;
import rogue.factories.MapperFactory;
import rogue.util.TileUtil;

import java.util.List;

public class InventorySystem extends EntitySystem {
    private ImmutableArray<Entity> entitiesToAddTo;
    private ImmutableArray<Entity> entitiesToRemoveFrom;
    private ImmutableArray<Entity> entitiesToDropFrom;
    private ImmutableArray<Entity> entitiesToDirectionalDropFrom;
    private ImmutableArray<Entity> entitiesToPerformTransfer;

    public void addedToEngine(Engine engine) {
        entitiesToAddTo = engine.getEntitiesFor(FamilyFactory.inventoryAdd);
        entitiesToRemoveFrom = engine.getEntitiesFor(FamilyFactory.inventoryRemove);
        entitiesToPerformTransfer = engine.getEntitiesFor(FamilyFactory.transferring);
        entitiesToDropFrom = engine.getEntitiesFor(FamilyFactory.dropping);
        entitiesToDirectionalDropFrom = engine.getEntitiesFor(FamilyFactory.directionalDropping);
    }

    public void update(float deltaTime) {
        entitiesToAddTo.forEach(this::performAdd);

        entitiesToRemoveFrom.forEach(this::performRemove);

        entitiesToDropFrom.forEach(this::performDrop);

        entitiesToPerformTransfer.forEach(this::performTransfer);

        entitiesToDirectionalDropFrom.forEach(this::performDirectionalDrop);
    }

    private void performAdd(Entity e) {
        InventoryComponent inventoryComponent = MapperFactory.inventoryComponent.get(e);
        InventoryAddActionComponent inventoryAddActionComponent = MapperFactory.inventoryAddActionComponent.get(e);

        // TODO: handle not being able to fit all in inventory
        List<Entity> entitiesToAdd = inventoryAddActionComponent.entitiesToAdd;
        boolean succeeded = true;
        for(Entity entityToAdd: entitiesToAdd) {
            succeeded &= inventoryComponent.add(entityToAdd);

            // Item is no longer in world so no longer has position
            entityToAdd.remove(PositionComponent.class);
        }
        e.remove(InventoryAddActionComponent.class);

        if(!succeeded) {
            System.out.println("Failed to pick up, inventory full");
            return;
        }

        System.out.println("Picked up");
        System.out.println(inventoryComponent.inventory);
    }

    private void performRemove(Entity e) {
        InventoryComponent inventoryComponent = MapperFactory.inventoryComponent.get(e);
        InventoryRemoveActionComponent inventoryRemoveActionComponent = MapperFactory.inventoryRemoveActionComponent.get(e);
        boolean destroy = inventoryRemoveActionComponent.destroyEntity;

        List<Entity> entitiesToRemove = inventoryRemoveActionComponent.entitiesToRemove;
        e.remove(InventoryRemoveActionComponent.class);

        for(Entity entityToRemove: entitiesToRemove) {
            boolean succeeded = inventoryComponent.remove(entityToRemove);

            if(!succeeded) {
                System.out.println("Failed to remove item");
            }

            if(destroy) {
                getEngine().removeEntity(entityToRemove);
            }
        }

        System.out.println("Removed from inventory");
        System.out.println(inventoryComponent.inventory);
    }

    // TODO: The drop action creates an inventory remove action, does two actions make this not doable in one update?
    // TODO: should drop just directly call 'performRemove' on e? so it all happens in one update
    private void performDrop(Entity e) {
        DropActionComponent dropActionComponent = MapperFactory.dropActionComponent.get(e);
        e.remove(DropActionComponent.class);

        drop(e, dropActionComponent.toDrop, 0, 0);
    }

    // TODO: should this perform a remove and an add action? in the same way the drop creates a remove action
    private void performTransfer(Entity e) {
        InventoryComponent inventoryComponent = MapperFactory.inventoryComponent.get(e);
        InventoryTransferActionComponent transferAction = MapperFactory.inventoryTransferActionComponent.get(e);

        Entity reciever = transferAction.reciever;
        InventoryComponent recieverInventory = MapperFactory.inventoryComponent.get(reciever);
        Entity entityToTransfer = transferAction.entityToTransfer;

        e.remove(InventoryTransferActionComponent.class);

        boolean removeSucceeded = inventoryComponent.remove(entityToTransfer);
        if(!removeSucceeded) {
            System.out.println("Transfer couldn't complete as item couldn't be removed");
            return;
        }

        boolean addSucceeded = recieverInventory.add(entityToTransfer);
        if(!addSucceeded) {
            System.out.println("Transfer couldn't complete as item couldn't be added. Attempting to put back.");

            // TODO: some error checking here?
            inventoryComponent.add(entityToTransfer);
        }
    }

    private void performDirectionalDrop(Entity e) {
        DropDirectionActionComponent dropDirectionActionComponent = MapperFactory.dropDirectionActionComponent.get(e);
        e.remove(DropDirectionActionComponent.class);

        drop(e, dropDirectionActionComponent.toDrop, dropDirectionActionComponent.mx, dropDirectionActionComponent.my );
    }

    private void drop(Entity e, Entity toDrop, int mx, int my) {
        PositionComponent pos = MapperFactory.positionComponent.get(e);

        int dropX = pos.x + mx;
        int dropY = pos.y + my;
        WorldSystem worldSystem = getEngine().getSystem(WorldSystem.class);
        WorldGrid renderGrid = worldSystem.getWorldGrid();
        TileComponent tileComponent = renderGrid.getTileComponent(dropX, dropY);

        // TODO: replace with an 'is empty' check rather than just checking under you
        if(!TileUtil.isLand(tileComponent)) {
            System.out.println("Cant drop on non-land");
            return;
        }

        // Drop item on ground where player is
        toDrop.add(new PositionComponent(dropX, dropY));
        RenderableComponent toDropRenderableComponent = MapperFactory.renderableComponent.get(toDrop);
        if(toDropRenderableComponent != null) {
            toDropRenderableComponent.visible = true;
        }

        e.add(new InventoryRemoveActionComponent(toDrop));
    }
}
