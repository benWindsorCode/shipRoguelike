package rogue.entities.crafting;

import com.badlogic.ashley.core.Entity;
import rogue.components.ExamineComponent;
import rogue.components.PositionComponent;
import rogue.components.RenderableComponent;
import rogue.components.TileComponent;
import rogue.components.actions.UpgradeShipComponent;
import rogue.components.items.UseItemEffectComponent;
import rogue.components.traits.CanBeCraftedComponent;
import rogue.components.traits.IdComponent;
import rogue.factories.RecipeFactory;
import rogue.factories.TileFactory;
import rogue.util.EntityId;
import rogue.util.UseTarget;

import java.util.Map;

public class ShipUpgradeKit extends Entity {
    public ShipUpgradeKit() {
        this(0, 0);
    }

    public ShipUpgradeKit(int x, int y) {
        // Create components
        this.add(new RenderableComponent());
        this.add(new IdComponent(EntityId.SHIP_UPGRADE_KIT));
        this.add(new PositionComponent(x, y));
        this.add(new TileComponent(TileFactory.ironChest.glyph, TileFactory.ironChest.color));
        this.add(new CanBeCraftedComponent(RecipeFactory.repairKitRecipe()));

        Map<EntityId, Integer> recipe = RecipeFactory.shipUpgradeKitRecipe().getRecipeByEntityId();
        int shipUpgradeWoodAmount = recipe.get(EntityId.WOOD);
        int shipUpgradeIronAmount = recipe.get(EntityId.IRON);
        this.add(new UseItemEffectComponent(UseTarget.PLAYER, () -> new UpgradeShipComponent(shipUpgradeWoodAmount, shipUpgradeIronAmount)));
        this.add(new ExamineComponent(
                "Ship Upgrade Kit",
                "Ship Upgrade Kits",
                "A ship upgrade kit, use it to make your ship stronger."
        ));
    }
}
