package rogue.entities.resources;

import com.badlogic.ashley.core.Entity;
import rogue.components.ExamineComponent;
import rogue.components.PositionComponent;
import rogue.components.RenderableComponent;
import rogue.components.TileComponent;
import rogue.components.traits.CanAddToInventoryComponent;
import rogue.components.traits.IdComponent;
import rogue.factories.TileFactory;
import rogue.util.EntityId;

public class WheatSeed extends Entity {
    public WheatSeed() {
        this(0, 0);
    }

    public WheatSeed(int x, int y) {
        super();

        this.add(new IdComponent(EntityId.WHEAT));
        this.add(new RenderableComponent());
        this.add(new PositionComponent(x, y));
        this.add(new TileComponent(TileFactory.wheatSeed.glyph, TileFactory.wheatSeed.color));
        this.add(new CanAddToInventoryComponent());
        this.add(new ExamineComponent(
                "Wheat Seed",
                "Wheat Seeds",
                "A seed for a wheat plant. Can be planted."
        ));
    }
}
