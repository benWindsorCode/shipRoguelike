package rogue.crafting;

import com.badlogic.ashley.core.Entity;
import rogue.components.TileComponent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.function.Function;
import java.util.function.Supplier;

// TODO: do we really want to do this by TileComponent? but at same time feels wrong making new entities each time
// TODO: one reason to change mapping is to be able to get examine names from the ingredients
// Made Recipe an entity to use in DualListBasedScreen, was that a good idea?
public class Recipe extends Entity {
    private Map<TileComponent, Integer> recipe;

    private Supplier<Entity> entityCreator;

    public Recipe(Supplier<Entity> entityCreator) {
        this.entityCreator = entityCreator;
    }

    public Recipe(Supplier<Entity> entityCreator, Map<TileComponent, Integer> ingredients) {
        this.entityCreator = entityCreator;
        this.recipe = ingredients;
    }

    public void addIngredient(TileComponent tile, Integer amount) {
        if(recipe.containsKey(tile)) {
            amount = recipe.get(tile) + amount;
        }
        recipe.put(tile, amount);
    }

    public Map<TileComponent, Integer> getRecipe() {
        return recipe;
    }

    public Supplier<Entity> getEntityCreator() {
        return entityCreator;
    }

    public String getRecipeAsString() {
        List<String> components = new ArrayList<>();
        for(Map.Entry<TileComponent, Integer> entry : recipe.entrySet()) {
            components.add(String.format("%s x %d", entry.getKey().glyph, entry.getValue()));
        }

        return String.join( ", ", components);
    }

    @Override
    public boolean equals(Object obj) {
        if(!obj.getClass().equals(Recipe.class)) {
            return false;
        }

        Recipe other = (Recipe) obj;

        return this.recipe.equals(other.recipe);
    }
}
