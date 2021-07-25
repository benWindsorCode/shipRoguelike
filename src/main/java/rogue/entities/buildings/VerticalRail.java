package rogue.entities.buildings;

import com.badlogic.ashley.core.Entity;
import rogue.components.ExamineComponent;
import rogue.components.PositionComponent;
import rogue.components.RenderableComponent;
import rogue.components.TileComponent;
import rogue.components.traits.IdComponent;
import rogue.factories.TileFactory;
import rogue.util.EntityId;

public class VerticalRail extends Entity {
    public VerticalRail() {
        this(0, 0);
    }

    public VerticalRail(int x, int y) {
        this.add(new PositionComponent(x, y));
        this.add(new IdComponent(EntityId.VERTICAL_RAIL));
        this.add(new TileComponent(TileFactory.verticalRail.glyph, TileFactory.verticalRail.color));
        this.add(new RenderableComponent());
        this.add(new ExamineComponent(
                "Vertical Rail",
                "Vertical Rails",
                "A vertical section of rail track."
        ));
    }
}
