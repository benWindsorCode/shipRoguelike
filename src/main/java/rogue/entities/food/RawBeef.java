package rogue.entities.food;

import com.badlogic.ashley.core.Entity;
import rogue.components.ExamineComponent;
import rogue.components.PositionComponent;
import rogue.components.render.RenderableComponent;
import rogue.components.render.TileComponent;
import rogue.components.actions.HealthActionComponent;
import rogue.components.actions.HungerActionComponent;
import rogue.components.items.UseItemEffectComponent;
import rogue.components.traits.CanAddToInventoryComponent;
import rogue.components.traits.IdComponent;
import rogue.factories.TileFactory;
import rogue.util.EntityId;
import rogue.util.UseTarget;

public class RawBeef extends Entity {
    public RawBeef() {
        this(0, 0);
    }

    public RawBeef(int x, int y) {
        this(new PositionComponent(x, y), true);
    }

    public RawBeef(PositionComponent positionComponent, boolean isVisible) {
        UseItemEffectComponent useItemEffectComponent = new UseItemEffectComponent(UseTarget.PLAYER);
        useItemEffectComponent.addEffect(() -> new HealthActionComponent(4));
        useItemEffectComponent.addEffect(() -> new HungerActionComponent(-25));

        // shares Position Component with the object that spawns it
        this.add(positionComponent);
        this.add(new IdComponent(EntityId.RAW_BEEF));
        this.add(new RenderableComponent(isVisible));
        this.add(new CanAddToInventoryComponent());
        this.add(new TileComponent(TileFactory.rawBeef.glyph, TileFactory.rawBeef.color));
        this.add(useItemEffectComponent);
        this.add(new ExamineComponent(
                "Raw Beef",
                "Raw Beef",
                "A bloody hunk of raw beef."
        ));
    }
}
