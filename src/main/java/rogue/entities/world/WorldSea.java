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

public class WorldSea extends Entity {
    public WorldSea(int x, int y) {
        super();
        this.add(new WorldTileComponent());
        this.add(new IdComponent(EntityId.WORLD_SEA));
        this.add(new RenderableComponent());
        this.add(new TileComponent(TileFactory.sea.glyph, TileFactory.sea.color));
        this.add(new PositionComponent(x, y));
        this.add(new ExamineComponent(
                "Sea",
                "Sea",
                "A section of sea."
        ));
    }
}
