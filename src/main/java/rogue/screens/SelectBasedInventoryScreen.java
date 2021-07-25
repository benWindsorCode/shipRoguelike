package rogue.screens;

import asciiPanel.AsciiPanel;
import com.badlogic.ashley.core.Entity;
import rogue.components.ExamineComponent;
import rogue.components.HealthComponent;
import rogue.components.InventoryComponent;
import rogue.components.RecipeBookComponent;
import rogue.components.actions.DeconstructActionComponent;
import rogue.components.actions.UseItemActionComponent;
import rogue.components.items.UseItemEffectComponent;
import rogue.components.player.PlayerShipComponent;
import rogue.components.ship.PlayerOnboardComponent;
import rogue.entities.PlayerCharacter;
import rogue.entities.PlayerShip;
import rogue.entities.crafting.RepairKit;
import rogue.entities.food.RatMeat;
import rogue.entities.food.RawFish;
import rogue.factories.MapperFactory;
import rogue.util.UseTarget;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Map;

public class SelectBasedInventoryScreen extends SelectBasedScreen {

    public SelectBasedInventoryScreen(AsciiPanel terminal, Entity player) {
        super(terminal, player);
    }

    @Override
    protected Entity[] getItems() {
        InventoryComponent inventoryComponent = MapperFactory.inventoryComponent.get(player);
        return inventoryComponent.inventory.toArray(new Entity[0]);
    }

    @Override
    protected String getMessage() {
        HealthComponent healthComponent = MapperFactory.healthComponent.get(player);
        InventoryComponent inventoryComponent = MapperFactory.inventoryComponent.get(player);
        return String.format(
                "Player inventory. %d/%d items. %d/%d health. 'enter' to use, 'd' to drop, 'c' to craft, 'k' to deconstruct",
                inventoryComponent.currentSize,
                inventoryComponent.maxSize,
                healthComponent.hitpoints,
                healthComponent.maxHitpoints
        );
    }

    @Override
    protected Screen extraInputResponses(KeyEvent key) {

        switch(key.getKeyCode()) {
            case KeyEvent.VK_D:
                if(getItems().length == 0)
                    return this;

                Entity dropItem = getItems()[currentItem];
                return new DirectionChoiceScreen(terminal, player, dropItem);
            case KeyEvent.VK_K:
                if(getItems().length == 0)
                    return this;

                Entity deconstructItem = getItems()[currentItem];
                player.add(new DeconstructActionComponent(deconstructItem));
                break;
            case KeyEvent.VK_C:
                if(player.getClass().equals(PlayerCharacter.class)) {
                    RecipeBookComponent recipeBookComponent = MapperFactory.recipeBookComponent.get(player);
                    return new DualCraftingScreen(terminal, player, recipeBookComponent.recipeBook);
                } else if(player.getClass().equals(PlayerShip.class)) {
                    PlayerOnboardComponent playerOnboardComponent = MapperFactory.playerOnboardComponent.get(player);
                    RecipeBookComponent recipeBookComponent = MapperFactory.recipeBookComponent.get(playerOnboardComponent.player);
                    return new DualCraftingScreen(terminal, playerOnboardComponent.player, recipeBookComponent.recipeBook);
                }
                break;
            case KeyEvent.VK_ENTER:
                if(getItems().length == 0)
                    return this;
                Entity item = getItems()[currentItem];

                UseItemEffectComponent useItemEffectComponent = MapperFactory.useItemEffectComponent.get(item);
                UseTarget useTarget = useItemEffectComponent.useTarget;

                Entity playerCharacter;
                Entity playerShip;
                if(player.getClass().equals(PlayerCharacter.class)) {
                    PlayerShipComponent playerShipComponent = MapperFactory.playerShipComponent.get(player);
                    playerCharacter = player;
                    playerShip = playerShipComponent.playerShip;
                } else if(player.getClass().equals(PlayerShip.class)) {
                    PlayerOnboardComponent playerOnboardComponent = MapperFactory.playerOnboardComponent.get(player);
                    playerCharacter = playerOnboardComponent.player;
                    playerShip = player;
                } else {
                    playerCharacter = playerShip = player;
                }

                Entity targetEntity;
                if(useTarget == UseTarget.PLAYER) {
                    targetEntity = playerCharacter;
                } else if(useTarget == UseTarget.PLAYER_SHIP) {
                    targetEntity = playerShip;
                } else {
                    targetEntity = player;
                }

                player.add(new UseItemActionComponent(item, targetEntity));
                break;
        }
        return null;
    }
}
