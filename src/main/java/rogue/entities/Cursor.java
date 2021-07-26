package rogue.entities;

import com.badlogic.ashley.core.Entity;
import rogue.ai.CursorAi;
import rogue.components.*;
import rogue.components.render.RenderableComponent;
import rogue.components.render.TileComponent;
import rogue.components.traits.IdComponent;
import rogue.factories.TileFactory;
import rogue.util.EntityId;

// Cursor for player to control for examining etc.
public class Cursor extends Entity {
    public Cursor(Entity returnControlTo, int x, int y) {
        this.add(new RenderableComponent());
        this.add(new IdComponent(EntityId.CURSOR));
        this.add(new TileComponent(TileFactory.examine.glyph, TileFactory.examine.color));
        this.add(new PositionComponent(x, y));
        this.add(new PlayerControlledComponent());
        this.add(new ReturnControlComponent(returnControlTo));
        this.add(new AiComponent<>(new CursorAi(this)));
    }
}
