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

public class Rock extends Entity {
    public Rock() {
        this(0, 0);
    }

    public Rock(int x, int y) {
        super();

        PositionComponent rockPosition = new PositionComponent(x, y);
        LootTable rockLootTable = new LootTable();
        rockLootTable.addLoot(new LootTableEntry(Gold::new, 0.3, 1));
        rockLootTable.addLoot(new LootTableEntry(Stone::new, 1.0, 1));

        this.add(new CanBeHarvestedComponent());
        this.add(new IdComponent(EntityId.ROCK));
        this.add(new LootableComponent(rockLootTable));
        this.add(new RenderableComponent());
        this.add(rockPosition);
        this.add(new TileComponent(TileFactory.rock.glyph, TileFactory.rock.color));
        this.add(new CannotEnterComponent());
        this.add(new ExamineComponent(
                "Rock",
                "Rocks",
                "A large grey rock"
        ));
    }
}
