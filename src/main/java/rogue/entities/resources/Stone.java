package rogue.entities.resources;

import com.badlogic.ashley.core.Entity;
import rogue.components.ExamineComponent;
import rogue.components.PositionComponent;
import rogue.components.render.RenderableComponent;
import rogue.components.render.TileComponent;
import rogue.components.traits.CanAddToInventoryComponent;
import rogue.components.traits.IdComponent;
import rogue.factories.TileFactory;
import rogue.util.EntityId;

public class Stone extends Entity {
    public Stone() {
        this(0, 0);
    }

    public Stone(int x, int y) {
        this(x, y, true);
    }

    public Stone(int x, int y, boolean isVisible) {
        this(new PositionComponent(x, y), isVisible);
    }

    public Stone(PositionComponent positionComponent, boolean isVisible) {
        // shares Position Component with the object that spawns it
        this.add(positionComponent);
        this.add(new IdComponent(EntityId.STONE));
        this.add(new RenderableComponent(isVisible));
        this.add(new CanAddToInventoryComponent());
        this.add(new TileComponent(TileFactory.stone.glyph, TileFactory.stone.color));
        this.add(new ExamineComponent(
                "Stone Chunk",
                "Stone Chunks",
                "A chunk of stone from a rock"
        ));
    }
}
