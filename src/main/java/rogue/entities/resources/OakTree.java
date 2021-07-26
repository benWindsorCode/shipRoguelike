package rogue.entities.resources;

import com.badlogic.ashley.core.Entity;
import rogue.components.*;
import rogue.components.render.RenderableComponent;
import rogue.components.render.TileComponent;
import rogue.components.traits.CanBeHarvestedComponent;
import rogue.components.traits.CannotEnterComponent;
import rogue.components.traits.IdComponent;
import rogue.factories.TileFactory;
import rogue.loot.LootTable;
import rogue.loot.LootTableEntry;
import rogue.util.EntityId;

public class OakTree extends Entity {
    public OakTree() {
        this(0, 0);
    }

    public OakTree(int x, int y) {
        super();

        PositionComponent treePosition = new PositionComponent(x, y);
        LootTable treeLootTable = new LootTable();
        treeLootTable.addLoot(new LootTableEntry(Wood::new, 1.0, 1));

        this.add(new CanBeHarvestedComponent());
        this.add(new IdComponent(EntityId.OAK_TREE));
        this.add(new LootableComponent(treeLootTable));
        this.add(new RenderableComponent());
        this.add(treePosition);
        this.add(new TileComponent(TileFactory.oakTree.glyph, TileFactory.oakTree.color));
        this.add(new CannotEnterComponent());
        this.add(new ExamineComponent(
                "Oak Tree",
                "Oak Trees",
                "A tall old oak tree"
        ));
    }
}
