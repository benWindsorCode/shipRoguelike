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

public class AshTree extends Entity {
    public AshTree() {
        this(0, 0);
    }

    public AshTree(int x, int y) {
        super();

        PositionComponent treePosition = new PositionComponent(x, y);
        LootTable treeLootTable = new LootTable();
        treeLootTable.addLoot(new LootTableEntry(Wood::new, 1.0, 1));

        this.add(new CanBeHarvestedComponent());
        this.add(new IdComponent(EntityId.ASH_TREE));
        this.add(new LootableComponent(treeLootTable));
        this.add(new RenderableComponent());
        this.add(treePosition);
        this.add(new TileComponent(TileFactory.ashTree.glyph, TileFactory.ashTree.color));
        this.add(new CannotEnterComponent());
        this.add(new ExamineComponent(
                "Ash Tree",
                "Ash Trees",
                "A slender ash tree"
        ));
    }
}
