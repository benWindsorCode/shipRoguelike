package rogue.systems;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.utils.ImmutableArray;
import rogue.components.InventoryComponent;
import rogue.components.actions.DeconstructActionComponent;
import rogue.components.actions.InventoryAddActionComponent;
import rogue.components.actions.InventoryRemoveActionComponent;
import rogue.components.actions.UseItemActionComponent;
import rogue.components.items.UseItemEffectComponent;
import rogue.components.traits.CanBeCraftedComponent;
import rogue.crafting.Recipe;
import rogue.factories.FamilyFactory;
import rogue.factories.MapperFactory;
import rogue.loot.LootTable;

import java.util.List;

public class ItemSystem extends EntitySystem {
    private ImmutableArray<Entity> itemsUsed;
    private ImmutableArray<Entity> itemsToDeconstruct;

    public void addedToEngine(Engine engine) {
        itemsUsed = engine.getEntitiesFor(FamilyFactory.itemsUsed);
        itemsToDeconstruct = engine.getEntitiesFor(FamilyFactory.deconstruct);
    }

    public void update(float deltaTime) {
        itemsUsed.forEach(this::useItem);
        itemsToDeconstruct.forEach(this::deconstructItem);
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

    private void deconstructItem(Entity e) {
        DeconstructActionComponent deconstructActionComponent = MapperFactory.deconstructActionComponent.get(e);
        Entity toDeconstruct = deconstructActionComponent.entityToDeconstruct;

        e.remove(DeconstructActionComponent.class);

        CanBeCraftedComponent canBeCraftedComponent = MapperFactory.canBeCraftedComponent.get(toDeconstruct);
        LootTable deconstructLootTable = canBeCraftedComponent.recipe.getDeconstructLootTable();

        List<Entity> deconstructLoot = deconstructLootTable.dropLoot();
        deconstructLoot.forEach(loot -> getEngine().addEntity(loot));

        e.add(new InventoryAddActionComponent(deconstructLoot));
        e.add(new InventoryRemoveActionComponent(toDeconstruct, true));
    }
}
