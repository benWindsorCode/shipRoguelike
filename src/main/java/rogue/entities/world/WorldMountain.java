package rogue.entities.world;

import com.badlogic.ashley.core.Entity;
import rogue.components.ExamineComponent;
import rogue.components.PositionComponent;
import rogue.components.render.RenderableComponent;
import rogue.components.render.TileComponent;
import rogue.components.traits.IdComponent;
import rogue.components.traits.WorldTileComponent;
import rogue.factories.TileFactory;
import rogue.util.EntityId;

public class WorldMountain extends Entity {
    public WorldMountain(int x, int y) {
        super();
        this.add(new WorldTileComponent());
        this.add(new IdComponent(EntityId.WORLD_MOUNTAIN));
        this.add(new RenderableComponent());
        this.add(new TileComponent(TileFactory.mountain.glyph, TileFactory.mountain.color));
        this.add(new PositionComponent(x, y));
        this.add(new ExamineComponent(
                "Mountain",
                "Mountains",
                "A mountain."
        ));
    }
}
