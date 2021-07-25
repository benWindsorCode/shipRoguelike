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

public class WorldDeepSea extends Entity {
    public WorldDeepSea(int x, int y) {
        super();
        this.add(new WorldTileComponent());
        this.add(new IdComponent(EntityId.WORLD_DEEP_SEA));
        this.add(new RenderableComponent());
        this.add(new TileComponent(TileFactory.deepSea.glyph, TileFactory.deepSea.color));
        this.add(new PositionComponent(x, y));
        this.add(new ExamineComponent(
                "Deep Sea",
                "Deep Sea",
                "A section of deep sea."
        ));
    }
}
