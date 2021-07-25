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

public class WorldHighMountain extends Entity {
    public WorldHighMountain(int x, int y) {
        super();
        this.add(new WorldTileComponent());
        this.add(new IdComponent(EntityId.WORLD_HIGH_MOUNTAIN));
        this.add(new RenderableComponent());
        this.add(new TileComponent(TileFactory.highMountain.glyph, TileFactory.highMountain.color));
        this.add(new PositionComponent(x, y));
        this.add(new ExamineComponent(
                "High Mountain",
                "High Mountains",
                "An extremely tall mountain."
        ));
    }
}
