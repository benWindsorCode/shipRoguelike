package rogue.loot;

import com.badlogic.ashley.core.Entity;

import java.util.ArrayList;
import java.util.List;

public class LootTable {
    private List<LootTableEntry> lootTable;

    public LootTable() {
        lootTable = new ArrayList<>();
    }

    public LootTable(List<LootTableEntry> lootTable) {
        this.lootTable = lootTable;
    }

    public void addLoot(LootTableEntry lootTableEntry) {
        lootTable.add(lootTableEntry);
    }

    public List<Entity> dropLoot() {
        List<Entity> drops = new ArrayList<>();
        for(LootTableEntry entry: lootTable) {
            drops.addAll(entry.dropLoot());
        }

        return drops;
    }
}
