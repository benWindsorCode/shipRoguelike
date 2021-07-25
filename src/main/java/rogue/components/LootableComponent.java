package rogue.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import rogue.loot.LootTable;

// TODO: allow for random loot and multiple items looted
// TODO: should this be a Supplier<Entity> to create loot, this garuantees that not already in engine,
//       or do we want to be able to loot already existing entities

public class LootableComponent implements Component {

    // TODO: should be able to drop List<Entity> but cant handle multiple in one place atm
    public LootTable lootTable;

    // TODO: should this be lazy, some sort of lambda to only create result obj on harvest
    public LootableComponent(LootTable lootTable) {
        this.lootTable = lootTable;
    }
}
