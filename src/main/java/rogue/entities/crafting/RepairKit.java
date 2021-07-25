package rogue.entities.crafting;

import com.badlogic.ashley.core.Entity;
import rogue.components.*;
import rogue.components.actions.HealthActionComponent;
import rogue.components.items.UseItemEffectComponent;
import rogue.components.traits.CanBeCraftedComponent;
import rogue.components.traits.CanBeDeconstructedComponent;
import rogue.components.traits.IdComponent;
import rogue.factories.RecipeFactory;
import rogue.factories.TileFactory;
import rogue.util.EntityId;
import rogue.util.UseTarget;

public class RepairKit extends Entity {
    public RepairKit() {
        this(0, 0);
    }

    public RepairKit(int x, int y) {
        // Create components
        this.add(new RenderableComponent());
        this.add(new IdComponent(EntityId.REPAIR_KIT));
        this.add(new PositionComponent(x, y));
        this.add(new CanBeDeconstructedComponent());
        this.add(new TileComponent(TileFactory.ironChest.glyph, TileFactory.ironChest.color));
        this.add(new CanBeCraftedComponent(RecipeFactory.repairKitRecipe()));
        this.add(new UseItemEffectComponent(UseTarget.PLAYER_SHIP, () -> new HealthActionComponent(10)));
        this.add(new ExamineComponent(
                "Repair Kit",
                "Repair Kits",
                "A repair kit, useful for fixing things."
        ));
    }
}
