package rogue.entities.crafting;

import com.badlogic.ashley.core.Entity;
import rogue.components.*;
import rogue.components.traits.CanBeCraftedComponent;
import rogue.components.traits.CannotEnterComponent;
import rogue.factories.RecipeFactory;
import rogue.factories.TileFactory;

public class StoneAltar extends Entity {
    public StoneAltar() {
        this(0, 0);
    }

    public StoneAltar(int x, int y) {

        // Create components
        this.add(new RenderableComponent());
        this.add(new PositionComponent(x, y));
        this.add(new TileComponent(TileFactory.stoneAltar.glyph, TileFactory.stoneAltar.color));
        this.add(new CanBeCraftedComponent(RecipeFactory.stoneAlterRecipe()));
        this.add(new CannotEnterComponent());
        this.add(new ExamineComponent(
                "Stone Altar",
                "Stone Altars",
                "The altar is cold and menacing. A PORTAL has opened in your world."
        ));
    }
}
