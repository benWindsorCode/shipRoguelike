package rogue.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.utils.ImmutableArray;
import rogue.components.HealthComponent;
import rogue.components.InventoryComponent;
import rogue.components.StrengthComponent;
import rogue.components.TileComponent;
import rogue.components.actions.InventoryRemoveActionComponent;
import rogue.components.actions.UpgradeShipComponent;
import rogue.components.player.PlayerShipComponent;
import rogue.components.world.SpawnPortalComponent;
import rogue.factories.FamilyFactory;
import rogue.factories.MapperFactory;
import rogue.factories.TileFactory;
import rogue.util.TileUtil;

import java.util.ArrayList;
import java.util.List;

public class UpgradeSystem extends EntitySystem {
    private ImmutableArray<Entity> upgradeRequested;

    public void addedToEngine(Engine engine) {
        upgradeRequested = engine.getEntitiesFor(FamilyFactory.upgrading);
    }
    public void update(float deltaTime) {
        // TODO: stop user upgrading if ship is already upgraded
        upgradeRequested.forEach(this::performUpgrade);
    }

    private void performUpgrade(Entity e) {
        UpgradeShipComponent upgradeShipComponent = MapperFactory.upgradeShipComponent.get(e);
        int woodToRemove = upgradeShipComponent.woodUsed;
        int ironToRemove = upgradeShipComponent.ironUsed;

        // Remove upgrade request as we are processing it
        e.remove(UpgradeShipComponent.class);

        PlayerShipComponent playerShipComponent = MapperFactory.playerShipComponent.get(e);
        if(playerShipComponent == null) {
            System.out.println("Couldn't locate player ship to upgrade");
            return;
        }

        Entity playerShip = playerShipComponent.playerShip;
        TileComponent tileComponent = MapperFactory.tileComponent.get(playerShip);
        InventoryComponent shipInventory = MapperFactory.inventoryComponent.get(playerShip);
        tileComponent.glyph = TileFactory.shipV2.glyph;
        tileComponent.color = TileFactory.shipV2.color;
        shipInventory.maxSize += 10;

        StrengthComponent strengthComponent = MapperFactory.strengthComponent.get(playerShip);
        strengthComponent.strength += 5;

        HealthComponent healthComponent = MapperFactory.healthComponent.get(playerShip);
        healthComponent.maxHitpoints += 10;
        healthComponent.hitpoints = healthComponent.maxHitpoints;

        InventoryComponent inventoryComponent = MapperFactory.inventoryComponent.get(e);
        List<Entity> inventory = inventoryComponent.inventory;

        List<Entity> inventoryToRemove = new ArrayList<>();
        for(Entity item: inventory) {
            TileComponent tile = MapperFactory.tileComponent.get(item);

            if(TileUtil.isIron(tile) && ironToRemove > 0) {
                System.out.println("Marking iron for remove");
                inventoryToRemove.add(item);
                ironToRemove--;
            }

            if(TileUtil.isWood(tile) && woodToRemove > 0) {
                System.out.println("Marking wood for remove");
                inventoryToRemove.add(item);
                woodToRemove--;
            }
        }

        System.out.println(String.format("Removing %d items after upgrade", inventoryToRemove.size()));
        e.add(new InventoryRemoveActionComponent(inventoryToRemove, true));
    }
}
