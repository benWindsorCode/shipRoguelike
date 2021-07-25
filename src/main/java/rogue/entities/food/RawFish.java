package rogue.entities.food;

import com.badlogic.ashley.core.Entity;
import rogue.components.ExamineComponent;
import rogue.components.PositionComponent;
import rogue.components.RenderableComponent;
import rogue.components.TileComponent;
import rogue.components.actions.HealActionComponent;
import rogue.components.items.UseItemEffectComponent;
import rogue.components.traits.CanAddToInventoryComponent;
import rogue.components.traits.IdComponent;
import rogue.factories.TileFactory;
import rogue.util.EntityId;
import rogue.util.UseTarget;

public class RawFish extends Entity {
    public RawFish() {
        this(0, 0);
    }

    public RawFish(int x, int y) {
        this(new PositionComponent(x, y), true);
    }

    public RawFish(PositionComponent positionComponent, boolean isVisible) {
        // shares Position Component with the object that spawns it
        this.add(positionComponent);
        this.add(new IdComponent(EntityId.RAW_FISH));
        this.add(new RenderableComponent(isVisible));
        this.add(new CanAddToInventoryComponent());
        this.add(new TileComponent(TileFactory.rawFish.glyph, TileFactory.rawFish.color));
        this.add(new UseItemEffectComponent(UseTarget.PLAYER, () -> new HealActionComponent(4)));
        this.add(new ExamineComponent(
                "Raw Fish",
                "Raw Fish",
                "A hunk of raw fish, fresh from the ocean."
        ));
    }
}