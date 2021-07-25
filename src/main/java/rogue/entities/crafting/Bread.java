package rogue.entities.crafting;

import com.badlogic.ashley.core.Entity;
import rogue.components.*;
import rogue.components.actions.HealthActionComponent;
import rogue.components.actions.HungerActionComponent;
import rogue.components.items.UseItemEffectComponent;
import rogue.components.traits.CanBeCraftedComponent;
import rogue.components.traits.CanStoreItemsInComponent;
import rogue.components.traits.CannotEnterComponent;
import rogue.components.traits.IdComponent;
import rogue.factories.RecipeFactory;
import rogue.factories.TileFactory;
import rogue.util.EntityId;
import rogue.util.UseTarget;

public class Bread extends Entity {
    public Bread() {
        this(0, 0);
    }

    public Bread(int x, int y) {
        UseItemEffectComponent useItemEffectComponent = new UseItemEffectComponent(UseTarget.PLAYER);
        useItemEffectComponent.addEffect(() -> new HealthActionComponent(7));
        useItemEffectComponent.addEffect(() -> new HungerActionComponent(-35));

        // Create components
        this.add(new RenderableComponent());
        this.add(new PositionComponent(x, y));
        this.add(new IdComponent(EntityId.BREAD));
        this.add(new TileComponent(TileFactory.bread.glyph, TileFactory.bread.color));
        this.add(new CanBeCraftedComponent(RecipeFactory.breadRecipe()));
        this.add(useItemEffectComponent);
        this.add(new ExamineComponent(
                "Bread",
                "Bread",
                "A loaf of bread."
        ));
    }
}
