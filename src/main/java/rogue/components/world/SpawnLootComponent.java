package rogue.components.world;

import com.badlogic.ashley.core.Component;
import rogue.components.PositionComponent;
import rogue.loot.LootTable;

// TODO: what if we kill two things in one turn? we cant add a component twice
// even dropped on the entity that killed another entity
public class SpawnLootComponent implements Component {
    public LootTable lootTable;
    public PositionComponent positionComponent;

    public SpawnLootComponent(LootTable lootTable, PositionComponent positionComponent) {
        this.lootTable = lootTable;
        this.positionComponent = positionComponent;
    }
}
