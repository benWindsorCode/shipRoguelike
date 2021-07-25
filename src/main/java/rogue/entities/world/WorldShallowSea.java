package rogue.entities.world;

import com.badlogic.ashley.core.Entity;
import rogue.components.ExamineComponent;
import rogue.components.PositionComponent;
import rogue.components.RenderableComponent;
import rogue.components.TileComponent;
import rogue.components.traits.IdComponent;
import rogue.components.traits.WorldTileComponent;
import rogue.factories.TileFactory;
import rogue.util.EntityId;

public class WorldShallowSea extends Entity {
    public WorldShallowSea(int x, int y) {
        super();
        this.add(new WorldTileComponent());
        this.add(new IdComponent(EntityId.WORLD_SHALLOW_SEA));
        this.add(new RenderableComponent());
        this.add(new TileComponent(TileFactory.shallowSea.glyph, TileFactory.shallowSea.color));
        this.add(new PositionComponent(x, y));
        this.add(new ExamineComponent(
                "Shallow Sea",
                "Shallow Sea",
                "A shallow section of sea."
        ));
    }
}
