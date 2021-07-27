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

public class Wool extends Entity {
    public Wool() {
        this(0, 0);
    }

    public Wool(int x, int y) {
        this(new PositionComponent(x, y), true);
    }

    public Wool(PositionComponent positionComponent, boolean isVisible) {

        // shares Position Component with the object that spawns it
        this.add(positionComponent);
        this.add(new IdComponent(EntityId.WOOL));
        this.add(new RenderableComponent(isVisible));
        this.add(new CanAddToInventoryComponent());
        this.add(new TileComponent(TileFactory.wool.glyph, TileFactory.wool.color));
        this.add(new ExamineComponent(
                "Wool",
                "Wool",
                "A fluffy bundle of wool. Good for crafting with."
        ));
    }
}
