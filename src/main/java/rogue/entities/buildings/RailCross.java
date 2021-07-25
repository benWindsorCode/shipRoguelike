package rogue.entities.buildings;

import com.badlogic.ashley.core.Entity;
import rogue.components.ExamineComponent;
import rogue.components.PositionComponent;
import rogue.components.RenderableComponent;
import rogue.components.TileComponent;
import rogue.components.traits.IdComponent;
import rogue.factories.TileFactory;
import rogue.util.EntityId;

public class RailCross extends Entity {
    public RailCross() {
        this(0, 0);
    }

    public RailCross(int x, int y) {
        this.add(new PositionComponent(x, y));
        this.add(new IdComponent(EntityId.RAIL_CROSS));
        this.add(new TileComponent(TileFactory.railCross.glyph, TileFactory.railCross.color));
        this.add(new RenderableComponent());
        this.add(new ExamineComponent(
                "Rail Intersection",
                "Rail Intersections",
                "The intersection of two rail tracks."
        ));
    }
}
