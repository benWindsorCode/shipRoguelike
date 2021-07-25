package rogue.crafting;

import com.badlogic.ashley.core.Entity;

import java.util.ArrayList;
import java.util.List;

// Made an entity to be able to pass to DualListBasedScreen, is this wise?
public class RecipeBook extends Entity {
    private final List<Recipe> recipes;

    public RecipeBook() {
        recipes = new ArrayList<>();
    }

    public void addRecipe(final Recipe recipe) {
        recipes.add(recipe);
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }
}
