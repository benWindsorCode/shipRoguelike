package rogue.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.utils.ImmutableArray;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import rogue.components.InventoryComponent;
import rogue.components.StatsComponent;
import rogue.components.render.TileComponent;
import rogue.components.actions.InventoryRemoveActionComponent;
import rogue.components.actions.UpgradeShipComponent;
import rogue.components.player.PlayerShipComponent;
import rogue.factories.FamilyFactory;
import rogue.factories.MapperFactory;
import rogue.factories.TileFactory;
import rogue.stats.StatType;
import rogue.util.EntityUtil;

import java.util.ArrayList;
import java.util.List;

public class UpgradeSystem extends EntitySystem {
    private ImmutableArray<Entity> upgradeRequested;
    private final static Logger logger = LogManager.getLogger(UpgradeSystem.class);

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
            logger.error("Couldn't locate player ship to upgrade");
            return;
        }

        Entity playerShip = playerShipComponent.playerShip;
        TileComponent tileComponent = MapperFactory.tileComponent.get(playerShip);
        InventoryComponent shipInventory = MapperFactory.inventoryComponent.get(playerShip);
        tileComponent.glyph = TileFactory.shipV2.glyph;
        tileComponent.color = TileFactory.shipV2.color;
        shipInventory.maxSize += 10;

        StatsComponent statsComponent = MapperFactory.statsComponent.get(playerShip);

        statsComponent.adjustMaxValue(StatType.STRENGTH, +5);
        statsComponent.adjustCurrentValue(StatType.STRENGTH, +5);

        statsComponent.adjustMaxValue(StatType.HEALTH, +10);
        statsComponent.adjustCurrentValue(StatType.HEALTH, +10);

        InventoryComponent inventoryComponent = MapperFactory.inventoryComponent.get(e);
        List<Entity> inventory = inventoryComponent.inventory;

        List<Entity> inventoryToRemove = new ArrayList<>();
        for(Entity item: inventory) {
            if(EntityUtil.isIron(item) && ironToRemove > 0) {
                inventoryToRemove.add(item);
                ironToRemove--;
            }

            if(EntityUtil.isWood(item) && woodToRemove > 0) {
                inventoryToRemove.add(item);
                woodToRemove--;
            }
        }

        logger.info(String.format("Removing %d items after upgrade", inventoryToRemove.size()));
        e.add(new InventoryRemoveActionComponent(inventoryToRemove, true));
    }
}
