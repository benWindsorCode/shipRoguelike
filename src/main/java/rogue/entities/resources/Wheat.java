package rogue.entities.resources;

import com.badlogic.ashley.core.Entity;
import rogue.components.*;
import rogue.components.actions.HealthActionComponent;
import rogue.components.actions.HungerActionComponent;
import rogue.components.items.UseItemEffectComponent;
import rogue.components.render.RenderableComponent;
import rogue.components.render.TileComponent;
import rogue.components.traits.CanAddToInventoryComponent;
import rogue.components.traits.IdComponent;
import rogue.factories.TileFactory;
import rogue.util.EntityId;
import rogue.util.UseTarget;

public class Wheat extends Entity {
    public Wheat() {
        this(0, 0);
    }

    public Wheat(int x, int y) {
        super();

        UseItemEffectComponent useItemEffectComponent = new UseItemEffectComponent(UseTarget.PLAYER);
        useItemEffectComponent.addEffect(() -> new HealthActionComponent(4));
        useItemEffectComponent.addEffect(() -> new HungerActionComponent(-25));

        this.add(new IdComponent(EntityId.WHEAT));
        this.add(new RenderableComponent());
        this.add(new PositionComponent(x, y));
        this.add(useItemEffectComponent);
        this.add(new TileComponent(TileFactory.wheat.glyph, TileFactory.wheat.color));
        this.add(new CanAddToInventoryComponent());
        this.add(new ExamineComponent(
                "Wheat",
                "Wheat",
                "A bundle of wheat."
        ));
    }
}
