package rogue.factories;

import com.badlogic.ashley.core.Entity;
import rogue.components.TileComponent;
import rogue.crafting.Recipe;
import rogue.crafting.RecipeBook;
import rogue.entities.crafting.*;
import rogue.entities.resources.Gold;
import rogue.entities.resources.Iron;
import rogue.entities.resources.Stone;
import rogue.entities.resources.Wood;

import java.util.HashMap;
import java.util.Map;

public class RecipeFactory {

    public static Recipe stoneAlterRecipe() {
        Map<Entity, Integer> ingredients = new HashMap<>();
        ingredients.put(new Stone(), 3);
        ingredients.put(new Gold(), 1);

        return new Recipe(StoneAltar::new, ingredients);
    }

    public static Recipe chestRecipe() {
        Map<Entity, Integer> ingredients = new HashMap<>();
        ingredients.put(new Wood(), 3);

        return new Recipe(Chest::new, ingredients);
    }

    public static Recipe ironChestRecipe() {
        Map<Entity, Integer> ingredients = new HashMap<>();
        ingredients.put(new Wood(), 3);
        ingredients.put(new Iron(), 2);

        return new Recipe(IronChest::new, ingredients);
    }

    public static Recipe repairKitRecipe() {
        Map<Entity, Integer> ingredients = new HashMap<>();
        ingredients.put(new Wood(), 5);

        return new Recipe(RepairKit::new, ingredients);
    }

    public static Recipe shipUpgradeKitRecipe() {
        Map<Entity, Integer> ingredients = new HashMap<>();
        ingredients.put(new Wood(), 3);
        ingredients.put(new Iron(), 1);

        return new Recipe(ShipUpgradeKit::new, ingredients);
    }

    public static RecipeBook playerBook() {
        RecipeBook recipeBook = new RecipeBook();

        recipeBook.addRecipe(RecipeFactory.stoneAlterRecipe());
        recipeBook.addRecipe(RecipeFactory.chestRecipe());
        recipeBook.addRecipe(RecipeFactory.ironChestRecipe());
        recipeBook.addRecipe(RecipeFactory.repairKitRecipe());
        recipeBook.addRecipe(RecipeFactory.shipUpgradeKitRecipe());

        return recipeBook;
    }
}
