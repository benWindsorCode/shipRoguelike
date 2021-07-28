package rogue.entities.equipment;

import com.badlogic.ashley.core.Entity;
import rogue.components.ExamineComponent;
import rogue.components.PositionComponent;
import rogue.components.render.RenderableComponent;
import rogue.components.render.TileComponent;
import rogue.components.traits.CanBeCraftedComponent;
import rogue.components.traits.CanEquipComponent;
import rogue.components.traits.IdComponent;
import rogue.equipment.EquipmentSlot;
import rogue.factories.RecipeFactory;
import rogue.factories.TileFactory;
import rogue.modifier.Modifier;
import rogue.stats.StatType;
import rogue.util.EntityId;

public class LeatherShoes extends Entity {
    public LeatherShoes() {
        this(0, 0);
    }

    public LeatherShoes(int x, int y) {
        CanEquipComponent canEquipComponent = new CanEquipComponent(EquipmentSlot.FEET);
        canEquipComponent.addModifier(() -> new Modifier<>(this, StatType.HEALTH, 10, 10));

        // Create components
        this.add(new RenderableComponent());
        this.add(new PositionComponent(x, y));
        this.add(new IdComponent(EntityId.LEATHER_SHOES));
        this.add(new TileComponent(TileFactory.leatherShoes.glyph, TileFactory.leatherShoes.color));
        this.add(new CanBeCraftedComponent(RecipeFactory.leatherShoesRecipe()));
        this.add(canEquipComponent);
        this.add(new ExamineComponent(
                "Leather Shoes",
                "Leather Shoes",
                "A pair of shoes crafted from hardened leather."
        ));
    }
}
