package rogue.factories;

import com.badlogic.ashley.core.Entity;
import rogue.components.TileComponent;
import rogue.crafting.Recipe;
import rogue.crafting.RecipeBook;
import rogue.entities.crafting.*;
import rogue.entities.resources.*;
import rogue.loot.LootTable;
import rogue.loot.LootTableEntry;

import java.util.HashMap;
import java.util.Map;

public class RecipeFactory {

    public static Recipe stoneAlterRecipe() {
        Map<Entity, Integer> ingredients = new HashMap<>();
        ingredients.put(new Stone(), 3);
        ingredients.put(new Gold(), 1);

        LootTable deconstructLootTable = new LootTable();
        deconstructLootTable.addLoot(new LootTableEntry(Stone::new, 1.0, 3));
        deconstructLootTable.addLoot(new LootTableEntry(Gold::new, 1.0, 1));

        return new Recipe(StoneAltar::new, ingredients, deconstructLootTable);
    }

    public static Recipe chestRecipe() {
        Map<Entity, Integer> ingredients = new HashMap<>();
        ingredients.put(new Wood(), 3);

        LootTable deconstructLootTable = new LootTable();
        deconstructLootTable.addLoot(new LootTableEntry(Wood::new, 1.0, 3));

        return new Recipe(Chest::new, ingredients, deconstructLootTable);
    }

    public static Recipe breadRecipe() {
        Map<Entity, Integer> ingredients = new HashMap<>();
        ingredients.put(new Wheat(), 4);

        return new Recipe(Bread::new, ingredients);
    }

    public static Recipe ironChestRecipe() {
        Map<Entity, Integer> ingredients = new HashMap<>();
        ingredients.put(new Wood(), 3);
        ingredients.put(new Iron(), 2);

        LootTable deconstructLootTable = new LootTable();
        deconstructLootTable.addLoot(new LootTableEntry(Wood::new, 1.0, 3));
        deconstructLootTable.addLoot(new LootTableEntry(Iron::new, 1.0, 2));

        return new Recipe(IronChest::new, ingredients, deconstructLootTable);
    }

    public static Recipe repairKitRecipe() {
        Map<Entity, Integer> ingredients = new HashMap<>();
        ingredients.put(new Wood(), 5);

        LootTable deconstructLootTable = new LootTable();
        deconstructLootTable.addLoot(new LootTableEntry(Wood::new, 1.0, 5));

        return new Recipe(RepairKit::new, ingredients, deconstructLootTable);
    }

    public static Recipe shipUpgradeKitRecipe() {
        Map<Entity, Integer> ingredients = new HashMap<>();
        ingredients.put(new Wood(), 3);
        ingredients.put(new Iron(), 1);

        LootTable deconstructLootTable = new LootTable();
        deconstructLootTable.addLoot(new LootTableEntry(Wood::new, 1.0, 3));
        deconstructLootTable.addLoot(new LootTableEntry(Iron::new, 1.0, 1));

        return new Recipe(ShipUpgradeKit::new, ingredients, deconstructLootTable);
    }

    public static RecipeBook playerBook() {
        RecipeBook recipeBook = new RecipeBook();

        recipeBook.addRecipe(RecipeFactory.stoneAlterRecipe());
        recipeBook.addRecipe(RecipeFactory.chestRecipe());
        recipeBook.addRecipe(RecipeFactory.ironChestRecipe());
        recipeBook.addRecipe(RecipeFactory.repairKitRecipe());
        recipeBook.addRecipe(RecipeFactory.shipUpgradeKitRecipe());
        recipeBook.addRecipe(RecipeFactory.breadRecipe());

        return recipeBook;
    }
}
