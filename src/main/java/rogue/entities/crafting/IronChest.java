package rogue.entities.crafting;

import com.badlogic.ashley.core.Entity;
import rogue.components.*;
import rogue.components.traits.CanBeCraftedComponent;
import rogue.components.traits.CanStoreItemsInComponent;
import rogue.components.traits.CannotEnterComponent;
import rogue.components.traits.IdComponent;
import rogue.factories.RecipeFactory;
import rogue.factories.TileFactory;
import rogue.util.EntityId;

public class IronChest extends Entity {
    public IronChest() {
        this(0, 0);
    }

    public IronChest(int x, int y) {
        // Create components
        this.add(new RenderableComponent());
        this.add(new PositionComponent(x, y));
        this.add(new IdComponent(EntityId.IRON_CHEST));
        this.add(new TileComponent(TileFactory.ironChest.glyph, TileFactory.ironChest.color));
        this.add(new InventoryComponent(15));
        this.add(new CanStoreItemsInComponent());
        this.add(new CanBeCraftedComponent(RecipeFactory.ironChestRecipe()));
        this.add(new CannotEnterComponent());
        this.add(new ExamineComponent(
                "Iron Chest",
                "Iron Chests",
                "A strong large iron chest"
        ));
    }
}
