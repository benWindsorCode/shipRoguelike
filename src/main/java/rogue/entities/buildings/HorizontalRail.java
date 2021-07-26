package rogue.entities.buildings;

import com.badlogic.ashley.core.Entity;
import rogue.components.ExamineComponent;
import rogue.components.PositionComponent;
import rogue.components.render.RenderableComponent;
import rogue.components.render.TileComponent;
import rogue.components.traits.IdComponent;
import rogue.factories.TileFactory;
import rogue.util.EntityId;

public class HorizontalRail extends Entity {
    public HorizontalRail() {
        this(0, 0);
    }

    public HorizontalRail(int x, int y) {
        this.add(new PositionComponent(x, y));
        this.add(new IdComponent(EntityId.HORIZONTAL_RAIL));
        this.add(new TileComponent(TileFactory.horizontalRail.glyph, TileFactory.horizontalRail.color));
        this.add(new RenderableComponent());
        this.add(new ExamineComponent(
                "Horizontal Rail",
                "Horizontal Rails",
                "A horizontal section of rail track."
        ));
    }
}
