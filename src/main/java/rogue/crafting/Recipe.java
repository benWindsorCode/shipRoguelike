package rogue.crafting;

import com.badlogic.ashley.core.Entity;
import rogue.components.ExamineComponent;
import rogue.components.TileComponent;
import rogue.components.traits.IdComponent;
import rogue.factories.MapperFactory;
import rogue.loot.LootTable;
import rogue.util.EntityId;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

// TODO: do we really want to do this by TileComponent? but at same time feels wrong making new entities each time
// TODO: one reason to change mapping is to be able to get examine names from the ingredients
// Made Recipe an entity to use in DualListBasedScreen, was that a good idea?
public class Recipe extends Entity {
    private Map<Entity, Integer> recipe;
    private LootTable deconstructLootTable;

    private Supplier<Entity> entityCreator;

    public Recipe(Supplier<Entity> entityCreator) {
        this.entityCreator = entityCreator;
    }

    public Recipe(Supplier<Entity> entityCreator, Map<Entity, Integer> ingredients, LootTable deconstructLootTable) {
        this.entityCreator = entityCreator;
        this.recipe = ingredients;
        this.deconstructLootTable = deconstructLootTable;
    }

    public void addIngredient(Entity tile, Integer amount) {
        if(recipe.containsKey(tile)) {
            amount = recipe.get(tile) + amount;
        }
        recipe.put(tile, amount);
    }

    public Map<Entity, Integer> getRecipe() {
        return recipe;
    }

    public Map<EntityId, Integer> getRecipeByEntityId() {
        Map<EntityId, Integer> recipeByEntityId = new HashMap<>();

        for(Map.Entry<Entity, Integer> entry: recipe.entrySet()) {
            IdComponent idComponent = MapperFactory.idComponent.get(entry.getKey());

            recipeByEntityId.put(idComponent.entityId, entry.getValue());
        }

        return recipeByEntityId;
    }

    public LootTable getDeconstructLootTable() {
        return deconstructLootTable;
    }

    public Supplier<Entity> getEntityCreator() {
        return entityCreator;
    }

    public String getRecipeAsString() {
        List<String> components = new ArrayList<>();
        for(Map.Entry<Entity, Integer> entry : recipe.entrySet()) {
            Entity entity = entry.getKey();
            TileComponent tile = MapperFactory.tileComponent.get(entity);
            ExamineComponent examineComponent = MapperFactory.examineComponent.get(entity);
            components.add(String.format("%s %s x %d", tile.glyph, examineComponent.name, entry.getValue()));
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
