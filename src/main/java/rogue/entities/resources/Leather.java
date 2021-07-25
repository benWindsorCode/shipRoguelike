package rogue.entities.resources;

import com.badlogic.ashley.core.Entity;
import rogue.components.ExamineComponent;
import rogue.components.PositionComponent;
import rogue.components.RenderableComponent;
import rogue.components.TileComponent;
import rogue.components.actions.HealthActionComponent;
import rogue.components.actions.HungerActionComponent;
import rogue.components.items.UseItemEffectComponent;
import rogue.components.traits.CanAddToInventoryComponent;
import rogue.components.traits.IdComponent;
import rogue.factories.TileFactory;
import rogue.util.EntityId;
import rogue.util.UseTarget;

public class Leather extends Entity {
    public Leather() {
        this(0, 0);
    }

    public Leather(int x, int y) {
        this(new PositionComponent(x, y), true);
    }

    public Leather(PositionComponent positionComponent, boolean isVisible) {

        // shares Position Component with the object that spawns it
        this.add(positionComponent);
        this.add(new IdComponent(EntityId.LEATHER));
        this.add(new RenderableComponent(isVisible));
        this.add(new CanAddToInventoryComponent());
        this.add(new TileComponent(TileFactory.leather.glyph, TileFactory.leather.color));
        this.add(new ExamineComponent(
                "Leather",
                "Leather",
                "A large piece of leather. Good for crafting with."
        ));
    }
}
