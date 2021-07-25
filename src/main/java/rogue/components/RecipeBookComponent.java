package rogue.components;

import com.badlogic.ashley.core.Component;
import rogue.crafting.RecipeBook;
import rogue.factories.RecipeFactory;

public class RecipeBookComponent implements Component {
    public RecipeBook recipeBook;

    public RecipeBookComponent(final RecipeBook recipeBook) {
        this.recipeBook = recipeBook;
    }
}
