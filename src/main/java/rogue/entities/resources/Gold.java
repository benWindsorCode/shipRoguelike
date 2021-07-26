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

public class Gold extends Entity {
    public Gold() {
        this(0, 0);
    }

    public Gold(int x, int y) {
        this(x, y, true);
    }

    public Gold(int x, int y, boolean isVisible) {
        this(new PositionComponent(x, y), isVisible);
    }

    public Gold(PositionComponent positionComponent, boolean isVisible) {
        // shares Position Component with the object that spawns it
        this.add(positionComponent);
        this.add(new IdComponent(EntityId.GOLD));
        this.add(new RenderableComponent(isVisible));
        this.add(new CanAddToInventoryComponent());
        this.add(new TileComponent(TileFactory.gold.glyph, TileFactory.gold.color));
        this.add(new ExamineComponent(
                "Gold Nugget",
                "Gold Nuggets",
                "A shining nugget of gold"
        ));
    }
}
