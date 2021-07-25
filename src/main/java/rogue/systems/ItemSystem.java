package rogue.systems;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.utils.ImmutableArray;
import rogue.components.InventoryComponent;
import rogue.components.actions.UseItemActionComponent;
import rogue.components.items.UseItemEffectComponent;
import rogue.factories.FamilyFactory;
import rogue.factories.MapperFactory;

public class ItemSystem extends EntitySystem {
    private ImmutableArray<Entity> itemsUsed;

    public void addedToEngine(Engine engine) {
        itemsUsed = engine.getEntitiesFor(FamilyFactory.itemsUsed);

    }

    public void update(float deltaTime) {
        itemsUsed.forEach(this::useItem);
    }

    private void useItem(Entity e) {
        UseItemActionComponent useItemActionComponent = MapperFactory.useItemActionComponent.get(e);
        Entity itemUsed = useItemActionComponent.item;
        Entity target = useItemActionComponent.targetEntity;

        InventoryComponent inventoryComponent = MapperFactory.inventoryComponent.get(e);

        e.remove(UseItemActionComponent.class);

        UseItemEffectComponent useItemEffectComponent = MapperFactory.useItemEffectComponent.get(itemUsed);
        Component itemEffect = useItemEffectComponent.componentSupplier.get();
        inventoryComponent.remove(itemUsed);

        // TODO: could have a 'destroyed on use' field to check if we want to do this or not
        getEngine().removeEntity(itemUsed);

        target.add(itemEffect);
    }
}
