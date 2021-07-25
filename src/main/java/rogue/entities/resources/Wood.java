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

public class Wood extends Entity {

    public Wood() {
        this(0, 0);
    }

    public Wood(int x, int y) {
        this(x, y, true);
    }

    public Wood(int x, int y, boolean isVisible) {
        this(new PositionComponent(x, y), isVisible);
    }

    public Wood(PositionComponent positionComponent, boolean isVisible) {
        // shares Position Component with the object that spawns it
        this.add(positionComponent);
        this.add(new IdComponent(EntityId.WOOD));
        this.add(new RenderableComponent(isVisible));
        this.add(new CanAddToInventoryComponent());
        this.add(new TileComponent(TileFactory.wood.glyph, TileFactory.wood.color));
        this.add(new ExamineComponent(
                "Wood",
                "Wood",
                "A piece of wood from a tree"
        ));
    }
}
