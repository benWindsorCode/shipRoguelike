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

// TODO: Bug: Iron chunk doesn't appear when dropped on ground.
public class Iron extends Entity {

    public Iron() {
        this(0, 0);
    }

    public Iron(int x, int y) {
        this(x, y, true);
    }

    public Iron(int x, int y, boolean isVisible) {
        this(new PositionComponent(x, y), isVisible);
    }

    public Iron(PositionComponent positionComponent, boolean isVisible) {
        // shares Position Component with the object that spawns it
        this.add(positionComponent);
        this.add(new IdComponent(EntityId.IRON));
        this.add(new RenderableComponent(isVisible));
        this.add(new CanAddToInventoryComponent());
        this.add(new TileComponent(TileFactory.iron.glyph, TileFactory.iron.color));
        this.add(new ExamineComponent(
                "Iron Chunk",
                "Iron Chunks",
                "A chunk of iron"
        ));
    }
}
