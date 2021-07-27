package rogue.factories;

import com.badlogic.ashley.core.Family;
import rogue.components.*;
import rogue.components.actions.*;
import rogue.components.hunger.HungerComponent;
import rogue.components.render.RenderableComponent;
import rogue.components.traits.WorldTileComponent;
import rogue.components.world.SpawnLootComponent;
import rogue.components.world.SpawnPortalComponent;

public class FamilyFactory {
    // Special family of actions that occur instantly and will always be processed by the end of current time step
    public static final Family instantActions = Family.one(
            InventoryRemoveActionComponent.class,
            InventoryAddActionComponent.class,
            InventoryTransferActionComponent.class,
            CraftActionComponent.class,
            EquipActionComponent.class,
            UseItemActionComponent.class
    ).get();

    public static final Family renderable = Family.all(RenderableComponent.class, PositionComponent.class).get();
    public static final Family nonWorld = Family.all(PositionComponent.class).exclude(WorldTileComponent.class).get();
    public static final Family renderableNonWorld = Family.all(RenderableComponent.class, PositionComponent.class).exclude(WorldTileComponent.class).get();
    public static final Family playerControlled = Family.all(PlayerControlledComponent.class).get();
    public static final Family moving = Family.all(MovingComponent.class).get();
    public static final Family ai = Family.all(AiComponent.class, PositionComponent.class).get();
    public static final Family harvest = Family.all(HarvestActionComponent.class, PositionComponent.class).get();
    public static final Family crafting = Family.all(CraftActionComponent.class).get();
    public static final Family inventoryAdd = Family.all(InventoryAddActionComponent.class, InventoryComponent.class).get();
    public static final Family inventoryRemove = Family.all(InventoryRemoveActionComponent.class, InventoryComponent.class).get();
    public static final Family attacking = Family.all(AttackActionComponent.class).get();
    public static final Family healthUpdates = Family.all(HealthActionComponent.class, StatsComponent.class).get();
    public static final Family dropping = Family.all(DropActionComponent.class, InventoryComponent.class).get();
    public static final Family directionalDropping = Family.all(DropDirectionActionComponent.class, InventoryComponent.class).get();
    public static final Family transferring = Family.all(InventoryTransferActionComponent.class, InventoryComponent.class).get();
    public static final Family upgrading = Family.all(UpgradeShipComponent.class, PlayerControlledComponent.class).get();
    public static final Family spawnPortal = Family.all(SpawnPortalComponent.class).get();
    public static final Family messagesWaiting = Family.all(SendMessageComponent.class).get();
    public static final Family spawnLoot = Family.all(SpawnLootComponent.class).get();
    public static final Family itemsUsed = Family.all(UseItemActionComponent.class).get();
    public static final Family deconstruct = Family.all(DeconstructActionComponent.class, InventoryComponent.class).get();
    public static final Family hungerUpdates = Family.all(HungerComponent.class, StatsComponent.class).get();
    public static final Family hungerActions = Family.all(HungerActionComponent.class, HungerComponent.class).get();
    public static final Family itemsToEquip = Family.all(EquipActionComponent.class, EquipmentComponent.class).get();
}
