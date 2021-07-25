package rogue.screens;

import asciiPanel.AsciiPanel;
import com.badlogic.ashley.core.Entity;
import rogue.components.ExamineComponent;
import rogue.components.InventoryComponent;
import rogue.components.RecipeBookComponent;
import rogue.components.TileComponent;
import rogue.components.actions.CraftActionComponent;
import rogue.crafting.Recipe;
import rogue.crafting.RecipeBook;
import rogue.entities.PlayerCharacter;
import rogue.factories.MapperFactory;
import rogue.util.EntityId;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DualCraftingScreen extends DualListBasedScreen{

    // first == player, second == recipeBook
    public DualCraftingScreen(AsciiPanel terminal, Entity player, Entity recipeBook) {
        super(terminal, player, recipeBook);
    }

    @Override
    protected String getVerb() {
        return "craft";
    }

    // TODO: really we should filter this based on the ability to craft, rather than doing that in list construction
    @Override
    protected boolean isAcceptable(Entity item) {
        return true;
    }

    @Override
    protected Screen firstItemUse(Entity item) {
        first.add(new CraftActionComponent(item));
        return null;
    }

    @Override
    protected Screen secondItemUse(Entity item) {
        return null;
    }

    @Override
    protected String getMessage() {
        return "What would you like to craft?    Craftable <-> Recipe Book";
    }

    @Override
    protected ArrayList<String> getFirstList() {
        ArrayList<String> list = new ArrayList<>();

        for(Map.Entry<String, Entity> entry : getCraftableRecipes().entrySet())
            list.add(entry.getKey());

        ArrayList<String> listWithLetters = new ArrayList<>();
        for(int i = 0; i < list.size(); i ++) {
            String toAdd = String.format("%s) %s", lowerLetters.charAt(i), list.get(i));
            listWithLetters.add(toAdd);
        }

        return listWithLetters;
    }

    @Override
    protected ArrayList<String> getSecondList() {
        ArrayList<String> list = new ArrayList<>();

        for(Map.Entry<String, Recipe> entry : getAllRecipes().entrySet()) {
            list.add(String.format("Recipe: %s = %s", entry.getKey(), entry.getValue().getRecipeAsString()));
        }


        return list;
    }

    @Override
    protected Entity[] getFirstItems() {
        ArrayList<Entity> list = new ArrayList<>();

        for(Map.Entry<String, Entity> entry : getCraftableRecipes().entrySet())
            list.add(entry.getValue());

        Entity[] items = list.toArray(new Entity[0]);
        return items;
    }

    @Override
    protected Entity[] getSecondItems() {
        // TODO: not very nice copied from getFirstItems... ah well
        ArrayList<Entity> list = new ArrayList<>();

        for(Map.Entry<String, Recipe> entry : getAllRecipes().entrySet())
            list.add(entry.getValue());

        Entity[] items = list.toArray(new Entity[0]);
        return items;
    }

    @Override
    protected boolean passesValidation(AsciiPanel terminal) {
        if(!first.getClass().equals(PlayerCharacter.class)) {
            terminal.clear(' ', 0, terminal.getHeightInCharacters() - 1, terminal.getWidthInCharacters(), 1);
            terminal.write("Cannot craft while on Ship", 2, terminal.getHeightInCharacters() - 1);
            return false;
        }

        RecipeBookComponent recipeBookComponent = MapperFactory.recipeBookComponent.get(first);
        if(recipeBookComponent == null) {
            terminal.clear(' ', 0, 23, 80, 1);
            terminal.write("No recipe book owned", 2, 22);
            return false;
        }

        return true;
    }

    private Map<String, Entity> getCraftableRecipes() {
        Map<String, Entity> craftableRecipes = new HashMap<>();

        RecipeBookComponent recipeBookComponent = MapperFactory.recipeBookComponent.get(first);

        if(recipeBookComponent == null) {
            return craftableRecipes;
        }

        RecipeBook recipeBook = recipeBookComponent.recipeBook;
        InventoryComponent inventoryComponent = MapperFactory.inventoryComponent.get(first);
        Map<EntityId, Integer> itemCounts = inventoryComponent.itemCountsByEntityId();
        System.out.println(itemCounts);

        // For each recipe check if player has ingredients
        for(Recipe recipe: recipeBook.getRecipes()) {
            boolean canCraft = true;
            for(Map.Entry<EntityId, Integer> entry: recipe.getRecipeByEntityId().entrySet()) {
                canCraft = canCraft && itemCounts.containsKey(entry.getKey()) && itemCounts.get(entry.getKey()) >= entry.getValue();
            }

            if(canCraft) {
                Entity creates = recipe.getEntityCreator().get();
                ExamineComponent examineComponent = MapperFactory.examineComponent.get(creates);
                craftableRecipes.put(examineComponent.name, creates);
            }
        }

        return craftableRecipes;
    }

    private Map<String, Recipe> getAllRecipes() {
        Map<String, Recipe> allRecipes = new HashMap<>();

        RecipeBookComponent recipeBookComponent = MapperFactory.recipeBookComponent.get(first);

        if(recipeBookComponent == null) {
            return allRecipes;
        }

        RecipeBook recipeBook = recipeBookComponent.recipeBook;
        List<Recipe> recipes = recipeBook.getRecipes();

        for(Recipe recipe: recipes) {
            Entity creates = recipe.getEntityCreator().get();
            ExamineComponent examineComponent = MapperFactory.examineComponent.get(creates);
            allRecipes.put(examineComponent.name, recipe);
        }

        return allRecipes;
    }
}
