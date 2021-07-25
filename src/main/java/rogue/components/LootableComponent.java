package rogue.components;

import com.badlogic.ashley.core.Component;
import rogue.loot.LootTable;

public class LootableComponent implements Component {

    public LootTable lootTable;

    public LootableComponent(LootTable lootTable) {
        this.lootTable = lootTable;
    }
}
