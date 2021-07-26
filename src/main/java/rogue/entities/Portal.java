package rogue.entities;

import com.badlogic.ashley.core.Entity;
import rogue.components.ExamineComponent;
import rogue.components.PositionComponent;
import rogue.components.render.RenderableComponent;
import rogue.components.render.TileComponent;
import rogue.components.traits.IdComponent;
import rogue.factories.TileFactory;
import rogue.util.EntityId;

public class Portal extends Entity {
    public Portal(int x, int y) {
        this.add(new RenderableComponent());
        this.add(new IdComponent(EntityId.PORTAL));
        this.add(new TileComponent(TileFactory.portal.glyph, TileFactory.portal.color));
        this.add(new PositionComponent(x, y));
        this.add(new ExamineComponent(
                "Portal",
                "Portals",
                "A pulsating purple portal stands before you. You'll need a strong ship to enter!"
        ));
    }
}
