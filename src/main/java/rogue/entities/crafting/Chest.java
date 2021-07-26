package rogue.entities.crafting;

import com.badlogic.ashley.core.Entity;
import rogue.components.*;
import rogue.components.render.RenderableComponent;
import rogue.components.render.TileComponent;
import rogue.components.traits.CanBeCraftedComponent;
import rogue.components.traits.CanStoreItemsInComponent;
import rogue.components.traits.CannotEnterComponent;
import rogue.components.traits.IdComponent;
import rogue.factories.RecipeFactory;
import rogue.factories.TileFactory;
import rogue.util.EntityId;

public class Chest extends Entity {
    public Chest() {
        this(0, 0);
    }

    public Chest(int x, int y) {
        // Create components
        this.add(new RenderableComponent());
        this.add(new PositionComponent(x, y));
        this.add(new IdComponent(EntityId.CHEST));
        this.add(new TileComponent(TileFactory.chest.glyph, TileFactory.chest.color));
        this.add(new InventoryComponent(8));
        this.add(new CanStoreItemsInComponent());
        this.add(new CanBeCraftedComponent(RecipeFactory.chestRecipe()));
        this.add(new CannotEnterComponent());
        this.add(new ExamineComponent(
                "Chest",
                "Chests",
                "A simple wooden chest"
        ));
    }
}
