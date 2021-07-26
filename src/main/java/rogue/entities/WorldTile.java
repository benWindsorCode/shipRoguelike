package rogue.entities;

import com.badlogic.ashley.core.Entity;
import rogue.components.ExamineComponent;
import rogue.components.PositionComponent;
import rogue.components.render.RenderableComponent;
import rogue.components.render.TileComponent;
import rogue.components.traits.IdComponent;
import rogue.components.traits.WorldTileComponent;
import rogue.util.EntityId;

import java.awt.*;

public class WorldTile extends Entity {
    public WorldTile(final char glyph, final Color color, int x, int y) {
        super();
        this.add(new WorldTileComponent());
        this.add(new IdComponent(EntityId.WORLD_TILE));
        this.add(new RenderableComponent());
        this.add(new TileComponent(glyph, color));
        this.add(new PositionComponent(x, y));
        this.add(new ExamineComponent(
                "The world",
                "The world",
                "The world"
        ));
    }
}
