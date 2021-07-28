package rogue.systems;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.utils.ImmutableArray;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import rogue.components.EquipmentComponent;
import rogue.components.InventoryComponent;
import rogue.components.StatsComponent;
import rogue.components.actions.*;
import rogue.components.items.UseItemEffectComponent;
import rogue.components.traits.CanBeCraftedComponent;
import rogue.components.traits.CanEquipComponent;
import rogue.equipment.EquipmentSlot;
import rogue.factories.FamilyFactory;
import rogue.factories.MapperFactory;
import rogue.loot.LootTable;
import rogue.modifier.Modifier;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class ItemSystem extends EntitySystem {
    private ImmutableArray<Entity> itemsUsed;
    private ImmutableArray<Entity> itemsToDeconstruct;
    private ImmutableArray<Entity> itemsToEquip;
    private final static Logger logger = LogManager.getLogger(ItemSystem.class);

    public void addedToEngine(Engine engine) {
        itemsUsed = engine.getEntitiesFor(FamilyFactory.itemsUsed);
        itemsToDeconstruct = engine.getEntitiesFor(FamilyFactory.deconstruct);
        itemsToEquip = engine.getEntitiesFor(FamilyFactory.itemsToEquip);
    }

    public void update(float deltaTime) {
        itemsUsed.forEach(this::useItem);
        itemsToDeconstruct.forEach(this::deconstructItem);
        itemsToEquip.forEach(this::equipItem);
    }

    private void useItem(Entity e) {
        UseItemActionComponent useItemActionComponent = MapperFactory.useItemActionComponent.get(e);
        Entity itemUsed = useItemActionComponent.item;
        Entity target = useItemActionComponent.targetEntity;

        InventoryComponent inventoryComponent = MapperFactory.inventoryComponent.get(e);

        e.remove(UseItemActionComponent.class);

        UseItemEffectComponent useItemEffectComponent = MapperFactory.useItemEffectComponent.get(itemUsed);
        List<Component> itemEffects = useItemEffectComponent.componentSuppliers.stream()
                .map(Supplier::get)
                .collect(Collectors.toList());

        inventoryComponent.remove(itemUsed);

        // TODO: could have a 'destroyed on use' field to check if we want to do this or not
        getEngine().removeEntity(itemUsed);

        itemEffects.forEach(target::add);
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

    private void equipItem(final Entity e) {
        EquipActionComponent equipActionComponent = MapperFactory.equipActionComponent.get(e);
        EquipmentComponent equipmentComponent = MapperFactory.equipmentComponent.get(e);

        e.remove(EquipActionComponent.class);

        Entity toEquip = equipActionComponent.itemToEquip;
        EquipmentSlot slot = MapperFactory.canEquipComponent.get(toEquip).slot;
        CanEquipComponent canEquipComponent = MapperFactory.canEquipComponent.get(toEquip);

        if(equipmentComponent.slotFull(slot)) {
            logger.info(String.format("Couldn't equip item: %s, slot %s full", toEquip, slot));
        }
        equipmentComponent.equipItem(toEquip, slot);

        e.add(new InventoryRemoveActionComponent(toEquip));
        StatsComponent statsComponent = MapperFactory.statsComponent.get(e);

        canEquipComponent.modifiers.stream().map(Supplier::get).forEach(statsComponent::addModifier);

        logger.info(String.format("Equipped item: %s to slot: %s", toEquip, slot));
    }
}
