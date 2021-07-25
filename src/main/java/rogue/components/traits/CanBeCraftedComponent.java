package rogue.components.traits;

import com.badlogic.ashley.core.Component;
import rogue.components.TileComponent;
import rogue.crafting.Recipe;

import java.util.Map;

// Tag entities that can be built by player
public class CanBeCraftedComponent implements Component {
    public Recipe recipe;

    public CanBeCraftedComponent(Recipe recipe) {
        this.recipe = recipe;
    }
}
