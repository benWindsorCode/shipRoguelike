package rogue.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.utils.ImmutableArray;
import rogue.components.PositionComponent;
import rogue.components.world.SpawnLootComponent;
import rogue.factories.FamilyFactory;
import rogue.factories.MapperFactory;
import rogue.loot.LootTable;

import java.util.List;

// System to determine spawning new NPCs into the world
public class SpawnSystem extends EntitySystem {
    private ImmutableArray<Entity> spawnLoot;

    public void addedToEngine(Engine engine) {
        spawnLoot = engine.getEntitiesFor(FamilyFactory.spawnLoot);
    }

    public void update(float deltaTime) {
        spawnLoot.forEach(this::spawnLoot);
    }

    private void spawnLoot(final Entity e) {
        SpawnLootComponent spawnLootComponent = MapperFactory.spawnLootComponent.get(e);
        LootTable lootTable = spawnLootComponent.lootTable;
        PositionComponent positionToSpawn = spawnLootComponent.positionComponent;

        e.remove(SpawnLootComponent.class);

        List<Entity> lootList = lootTable.dropLoot();
        for(Entity loot: lootList) {
            PositionComponent lootPos = MapperFactory.positionComponent.get(loot);
            lootPos.x = positionToSpawn.x;
            lootPos.y = positionToSpawn.y;

            getEngine().addEntity(loot);
        }
    }
}