package rogue.factories;

import rogue.components.TileComponent;
import rogue.crafting.Recipe;
import rogue.crafting.RecipeBook;
import rogue.entities.crafting.*;

import java.util.HashMap;
import java.util.Map;

public class RecipeFactory {

    public static Recipe stoneAlterRecipe() {
        Map<TileComponent, Integer> ingredients = new HashMap<>();
        ingredients.put(new TileComponent(TileFactory.stone.glyph, TileFactory.stone.color), 3);
        ingredients.put(new TileComponent(TileFactory.gold.glyph, TileFactory.gold.color), 1);

        return new Recipe(StoneAltar::new, ingredients);
    }

    public static Recipe chestRecipe() {
        Map<TileComponent, Integer> ingredients = new HashMap<>();
        ingredients.put(new TileComponent(TileFactory.wood.glyph, TileFactory.wood.color), 3);

        return new Recipe(Chest::new, ingredients);
    }

    public static Recipe ironChestRecipe() {
        Map<TileComponent, Integer> ingredients = new HashMap<>();
        ingredients.put(new TileComponent(TileFactory.wood.glyph, TileFactory.wood.color), 3);
        ingredients.put(new TileComponent(TileFactory.iron.glyph, TileFactory.iron.color), 2);

        return new Recipe(IronChest::new, ingredients);
    }

    public static Recipe repairKitRecipe() {
        Map<TileComponent, Integer> ingredients = new HashMap<>();
        ingredients.put(new TileComponent(TileFactory.wood.glyph, TileFactory.wood.color), 5);

        return new Recipe(RepairKit::new, ingredients);
    }

    public static Recipe shipUpgradeKitRecipe() {
        Map<TileComponent, Integer> ingredients = new HashMap<>();
        ingredients.put(new TileComponent(TileFactory.wood.glyph, TileFactory.wood.color), 3);
        ingredients.put(new TileComponent(TileFactory.iron.glyph, TileFactory.iron.color), 1);

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
