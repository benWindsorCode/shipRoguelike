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

public class WorldGrass extends Entity {
    public WorldGrass(int x, int y) {
        super();
        this.add(new WorldTileComponent());
        this.add(new IdComponent(EntityId.WORLD_GRASS));
        this.add(new RenderableComponent());
        this.add(new TileComponent(TileFactory.grass.glyph, TileFactory.grass.color));
        this.add(new PositionComponent(x, y));
        this.add(new ExamineComponent(
                "Grass",
                "Grass",
                "A swath of green grass."
        ));
    }
}
