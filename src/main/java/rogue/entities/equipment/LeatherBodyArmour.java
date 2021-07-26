package rogue.entities.equipment;

import com.badlogic.ashley.core.Entity;
import rogue.components.ExamineComponent;
import rogue.components.PositionComponent;
import rogue.components.render.RenderableComponent;
import rogue.components.render.TileComponent;
import rogue.components.traits.*;
import rogue.equipment.EquipmentSlot;
import rogue.factories.RecipeFactory;
import rogue.factories.TileFactory;
import rogue.util.EntityId;

public class LeatherBodyArmour extends Entity {
    public LeatherBodyArmour() {
        this(0, 0);
    }

    public LeatherBodyArmour(int x, int y) {
        // Create components
        this.add(new RenderableComponent());
        this.add(new PositionComponent(x, y));
        this.add(new IdComponent(EntityId.LEATHER_BODY_ARMOUR));
        this.add(new TileComponent(TileFactory.leatherBodyArmour.glyph, TileFactory.leatherBodyArmour.color));
        this.add(new CanBeCraftedComponent(RecipeFactory.leatherBodyArmourRecipe()));
        this.add(new CanEquipComponent(EquipmentSlot.BODY));
        this.add(new ExamineComponent(
                "Leather Body Armour",
                "Leather Body Armour",
                "A piece of hardened leather body armour."
        ));
    }
}
