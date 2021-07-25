package rogue.entities.resources;

import com.badlogic.ashley.core.Entity;
import rogue.components.*;
import rogue.components.traits.CanBeHarvestedComponent;
import rogue.components.traits.CannotEnterComponent;
import rogue.components.traits.IdComponent;
import rogue.factories.TileFactory;
import rogue.loot.LootTable;
import rogue.loot.LootTableEntry;
import rogue.util.EntityId;

public class WheatPlant extends Entity {
    public WheatPlant() {
        this(0, 0);
    }

    public WheatPlant(int x, int y) {
        super();

        PositionComponent wheatPosition = new PositionComponent(x, y);
        LootTable wheatPlantLootTable = new LootTable();
        wheatPlantLootTable.addLoot(new LootTableEntry(Wheat::new, 1.0, 1));
        wheatPlantLootTable.addLoot(new LootTableEntry(WheatSeed::new, 0.8, 2));

        this.add(new CanBeHarvestedComponent());
        this.add(new IdComponent(EntityId.WHEAT_PLANT));
        this.add(new LootableComponent(wheatPlantLootTable));
        this.add(new RenderableComponent());
        this.add(wheatPosition);
        this.add(new TileComponent(TileFactory.wheatPlant.glyph, TileFactory.wheatPlant.color));
        this.add(new CannotEnterComponent());
        this.add(new ExamineComponent(
                "Wheat",
                "Wheat",
                "Wheat, ready to harvest."
        ));
    }
}
