package rogue.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.utils.ImmutableArray;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import rogue.components.LootableComponent;
import rogue.components.PositionComponent;
import rogue.components.render.RenderableComponent;
import rogue.components.actions.HarvestActionComponent;
import rogue.components.traits.CanHarvestComponent;
import rogue.components.traits.CanBeHarvestedComponent;
import rogue.factories.FamilyFactory;
import rogue.factories.MapperFactory;

import java.util.List;

public class HarvestSystem extends EntitySystem {
    private ImmutableArray<Entity> entitiesToHarvest;
    private final static Logger logger = LogManager.getLogger(HarvestSystem.class);

    public void addedToEngine(Engine engine) {
        entitiesToHarvest = engine.getEntitiesFor(FamilyFactory.harvest);
    }

    public void update(float deltaTime) {
        entitiesToHarvest.forEach(this::processEntitiesToHarvest);
    }

    private void processEntitiesToHarvest(final Entity e) {
        HarvestActionComponent harvestActionComponent = MapperFactory.harvestableActionComponent.get(e);
        CanBeHarvestedComponent canBeHarvestedComponent = MapperFactory.harvestableComponent.get(e);
        Entity harvester = harvestActionComponent.harvester;
        PositionComponent harvestedPos = MapperFactory.positionComponent.get(e);

        CanHarvestComponent canHarvestComponent = MapperFactory.canHarvestComponent.get(harvester);

        if(canHarvestComponent == null) {
            logger.error(String.format("Cant harvest as harvester not enabled. Harvester: %s, Entity harvested: %s", harvester, e));
            return;
        }

        logger.info(String.format("Harvesting: %s", e));
        LootableComponent lootableComponent = MapperFactory.lootableComponent.get(e);
        List<Entity> harvestResult = lootableComponent.lootTable.dropLoot();
        harvestResult.forEach(loot -> {
            RenderableComponent harvestResultRender = MapperFactory.renderableComponent.get(loot);
            PositionComponent lootPos = MapperFactory.positionComponent.get(loot);
            lootPos.x = harvestedPos.x;
            lootPos.y = harvestedPos.y;

            harvestResultRender.visible = true;
            getEngine().addEntity(loot);
        });

        getEngine().removeEntity(e);
    }
}
