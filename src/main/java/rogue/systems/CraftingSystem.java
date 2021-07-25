package rogue.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.utils.ImmutableArray;
import rogue.components.InventoryComponent;
import rogue.components.PositionComponent;
import rogue.components.TileComponent;
import rogue.components.actions.CraftActionComponent;
import rogue.components.actions.InventoryRemoveActionComponent;
import rogue.components.traits.CanBeCraftedComponent;
import rogue.components.world.SpawnPortalComponent;
import rogue.entities.crafting.StoneAltar;
import rogue.factories.FamilyFactory;
import rogue.factories.MapperFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CraftingSystem extends EntitySystem {
    private ImmutableArray<Entity> craftingInstructions;

    public void addedToEngine(Engine engine) {
        craftingInstructions = engine.getEntitiesFor(FamilyFactory.crafting);
    }

    public void update(float deltaTime) {
        for(Entity e: craftingInstructions) {
            InventoryComponent inventoryComponent = MapperFactory.inventoryComponent.get(e);
            List<Entity> inventory = inventoryComponent.inventory;

            Entity toCraft = MapperFactory.craftActionComponent.get(e).entityToCraft;
            CanBeCraftedComponent canBeCraftedComponent = MapperFactory.craftableComponent.get(toCraft);

            Map<TileComponent, Integer> recipeLeft = canBeCraftedComponent.recipe.getRecipe();

            e.remove(CraftActionComponent.class);

            // TODO: this assumes player has enough in inv to craft
            List<Entity> inventoryToRemove = new ArrayList<>();
            for(Entity item: inventory) {
                TileComponent tile = MapperFactory.tileComponent.get(item);

                if(recipeLeft.containsKey(tile)) {
                    int currentLeft = recipeLeft.get(tile);
                    if(currentLeft <= 0)
                        continue;
                    inventoryToRemove.add(item);
                    recipeLeft.put(tile, currentLeft - 1);
                }
            }

            System.out.println(String.format("Removing %d items after crafting", inventoryToRemove.size()));
            e.add(new InventoryRemoveActionComponent(inventoryToRemove, true));

            // old logic for placing under player, commented out to test adding straight to inventory
            //PositionComponent pos = MapperFactory.positionComponent.get(e);
            //PositionComponent toCraftPos = MapperFactory.positionComponent.get(toCraft);
            //toCraftPos.x = pos.x;
            //toCraftPos.y = pos.y;
            // TODO: BUG if inventory full, remove doesn't happen until next tick, but add attempts right away
            //       perhaps do remove here too, why do via an event if we dont have to? or do the add via an event too
            //       but somehow ensure the add happens after, or make a new event that does add + remove together
            //       so that it can always do the remove first...
            inventoryComponent.add(toCraft);
            getEngine().addEntity(toCraft);

            // TODO: really this should be dropped on some WorldEntity not the player...
            if(toCraft.getClass().equals(StoneAltar.class))
                e.add(new SpawnPortalComponent());
        }
    }
}
