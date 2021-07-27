package rogue.factories;

import com.badlogic.ashley.core.ComponentMapper;
import rogue.components.*;
import rogue.components.actions.*;
import rogue.components.hunger.HungerComponent;
import rogue.components.items.UseItemEffectComponent;
import rogue.components.player.PlayerShipComponent;
import rogue.components.render.RenderableComponent;
import rogue.components.render.TileComponent;
import rogue.components.ship.PlayerOnboardComponent;
import rogue.components.traits.*;
import rogue.components.world.SpawnLootComponent;
import rogue.components.world.SpawnPortalComponent;

public class MapperFactory {
    public static final ComponentMapper<PositionComponent> positionComponent = ComponentMapper.getFor(PositionComponent.class);
    public static final ComponentMapper<RenderableComponent> renderableComponent = ComponentMapper.getFor(RenderableComponent.class);
    public static final ComponentMapper<TileComponent> tileComponent = ComponentMapper.getFor(TileComponent.class);
    public static final ComponentMapper<MovingComponent> movingComponent = ComponentMapper.getFor(MovingComponent.class);
    public static final ComponentMapper<PlayerOnboardComponent> playerOnboardComponent = ComponentMapper.getFor(PlayerOnboardComponent.class);
    public static final ComponentMapper<AiComponent> aiComponent = ComponentMapper.getFor(AiComponent.class);
    public static final ComponentMapper<PlayerShipComponent> playerShipComponent = ComponentMapper.getFor(PlayerShipComponent.class);
    public static final ComponentMapper<CanBeHarvestedComponent> harvestableComponent = ComponentMapper.getFor(CanBeHarvestedComponent.class);
    public static final ComponentMapper<HarvestActionComponent> harvestableActionComponent = ComponentMapper.getFor(HarvestActionComponent.class);
    public static final ComponentMapper<CanHarvestComponent> canHarvestComponent = ComponentMapper.getFor(CanHarvestComponent.class);
    public static final ComponentMapper<InventoryComponent> inventoryComponent = ComponentMapper.getFor(InventoryComponent.class);
    public static final ComponentMapper<InventoryAddActionComponent> inventoryAddActionComponent = ComponentMapper.getFor(InventoryAddActionComponent.class);
    public static final ComponentMapper<CanAddToInventoryComponent> canAddToInventoryComponent = ComponentMapper.getFor(CanAddToInventoryComponent.class);
    public static final ComponentMapper<CanBeAttackedComponent> canAttackComponent = ComponentMapper.getFor(CanBeAttackedComponent.class);
    public static final ComponentMapper<AttackActionComponent> attackComponent = ComponentMapper.getFor(AttackActionComponent.class);
    public static final ComponentMapper<LootableComponent> lootableComponent = ComponentMapper.getFor(LootableComponent.class);
    public static final ComponentMapper<DropActionComponent> dropActionComponent = ComponentMapper.getFor(DropActionComponent.class);
    public static final ComponentMapper<DropDirectionActionComponent> dropDirectionActionComponent = ComponentMapper.getFor(DropDirectionActionComponent.class);
    public static final ComponentMapper<InventoryRemoveActionComponent> inventoryRemoveActionComponent = ComponentMapper.getFor(InventoryRemoveActionComponent.class);
    public static final ComponentMapper<InventoryTransferActionComponent> inventoryTransferActionComponent = ComponentMapper.getFor(InventoryTransferActionComponent.class);
    public static final ComponentMapper<UpgradeShipComponent> upgradeShipComponent = ComponentMapper.getFor(UpgradeShipComponent.class);
    public static final ComponentMapper<SpawnPortalComponent> spawnPortalComponent = ComponentMapper.getFor(SpawnPortalComponent.class);
    public static final ComponentMapper<ExamineComponent> examineComponent = ComponentMapper.getFor(ExamineComponent.class);
    public static final ComponentMapper<ReturnControlComponent> returnControlComponent = ComponentMapper.getFor(ReturnControlComponent.class);
    public static final ComponentMapper<CanBeCraftedComponent> canBeCraftedComponent = ComponentMapper.getFor(CanBeCraftedComponent.class);
    public static final ComponentMapper<CraftActionComponent> craftActionComponent = ComponentMapper.getFor(CraftActionComponent.class);
    public static final ComponentMapper<RecipeBookComponent> recipeBookComponent = ComponentMapper.getFor(RecipeBookComponent.class);
    public static final ComponentMapper<SendMessageComponent> sendMessageComponent = ComponentMapper.getFor(SendMessageComponent.class);
    public static final ComponentMapper<CanStoreItemsInComponent> canStoreItemsInComponentComponentMapper = ComponentMapper.getFor(CanStoreItemsInComponent.class);
    public static final ComponentMapper<CannotEnterComponent> cannotEnterComponent = ComponentMapper.getFor(CannotEnterComponent.class);
    public static final ComponentMapper<SpawnLootComponent> spawnLootComponent = ComponentMapper.getFor(SpawnLootComponent.class);
    public static final ComponentMapper<HealthActionComponent> healActionComponent = ComponentMapper.getFor(HealthActionComponent.class);
    public static final ComponentMapper<UseItemActionComponent> useItemActionComponent = ComponentMapper.getFor(UseItemActionComponent.class);
    public static final ComponentMapper<UseItemEffectComponent> useItemEffectComponent = ComponentMapper.getFor(UseItemEffectComponent.class);
    public static final ComponentMapper<IdComponent> idComponent = ComponentMapper.getFor(IdComponent.class);
    public static final ComponentMapper<CanBeDeconstructedComponent> canBeDeconstructedComponent = ComponentMapper.getFor(CanBeDeconstructedComponent.class);
    public static final ComponentMapper<DeconstructActionComponent> deconstructActionComponent = ComponentMapper.getFor(DeconstructActionComponent.class);
    public static final ComponentMapper<HungerComponent> hungerComponent = ComponentMapper.getFor(HungerComponent.class);
    public static final ComponentMapper<HungerActionComponent> hungerActionComponent = ComponentMapper.getFor(HungerActionComponent.class);
    public static final ComponentMapper<EquipActionComponent> equipActionComponent = ComponentMapper.getFor(EquipActionComponent.class);
    public static final ComponentMapper<EquipmentComponent> equipmentComponent = ComponentMapper.getFor(EquipmentComponent.class);
    public static final ComponentMapper<CanEquipComponent> canEquipComponent = ComponentMapper.getFor(CanEquipComponent.class);
    public static final ComponentMapper<StatsComponent> statsComponent = ComponentMapper.getFor(StatsComponent.class);
}
