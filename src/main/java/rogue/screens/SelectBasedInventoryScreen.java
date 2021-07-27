package rogue.screens;

import asciiPanel.AsciiPanel;
import com.badlogic.ashley.core.Entity;
import rogue.components.*;
import rogue.components.actions.DeconstructActionComponent;
import rogue.components.actions.EquipActionComponent;
import rogue.components.actions.UseItemActionComponent;
import rogue.components.items.UseItemEffectComponent;
import rogue.components.player.PlayerShipComponent;
import rogue.components.ship.PlayerOnboardComponent;
import rogue.components.traits.CanBeDeconstructedComponent;
import rogue.components.traits.CanEquipComponent;
import rogue.entities.PlayerCharacter;
import rogue.entities.PlayerShip;
import rogue.factories.MapperFactory;
import rogue.stats.StatType;
import rogue.util.EntityId;
import rogue.util.UseTarget;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SelectBasedInventoryScreen extends SelectBasedScreen {

    public SelectBasedInventoryScreen(AsciiPanel terminal, Entity player) {
        super(terminal, player);
    }

    @Override
    protected Entity[] getItems() {
        InventoryComponent inventoryComponent = MapperFactory.inventoryComponent.get(player);
        Map<EntityId, List<Entity>> itemsByEntityId = inventoryComponent.itemsByEntityId();

        // Take first item from list as sample
        List<Entity> items = new ArrayList<>();
        for(Map.Entry<EntityId, List<Entity>> entry: itemsByEntityId.entrySet()) {
            items.add(entry.getValue().get(0));
        }

        return items.toArray(new Entity[0]);
    }

    // Most common list is just the description of the items
    @Override
    protected ArrayList<String> getList() {
        ArrayList<String> list = new ArrayList<>();
        InventoryComponent inventoryComponent = MapperFactory.inventoryComponent.get(player);
        Map<EntityId, List<Entity>> itemsByEntityId = inventoryComponent.itemsByEntityId();

        for(Entity e: getItems()) {
            ExamineComponent examineComponent = MapperFactory.examineComponent.get(e);
            UseItemEffectComponent useItemEffectComponent = MapperFactory.useItemEffectComponent.get(e);
            CanBeDeconstructedComponent canBeDeconstructedComponent = MapperFactory.canBeDeconstructedComponent.get(e);
            CanEquipComponent canEquipComponent = MapperFactory.canEquipComponent.get(e);

            EntityId entityId = MapperFactory.idComponent.get(e).entityId;

            int multiplicity = itemsByEntityId.get(entityId).size();

            if(examineComponent != null) {
                String itemText = String.format( "%s x %d", examineComponent.name, multiplicity);
                List<String> actionsAvailable = new ArrayList<>();

                if(useItemEffectComponent != null)
                    actionsAvailable.add("usable");

                if(canBeDeconstructedComponent != null)
                    actionsAvailable.add("deconstructable");

                if(canEquipComponent != null)
                    actionsAvailable.add("wearable");

                if(actionsAvailable.size() > 0) {
                    String actionsAvailableString = String.join(", ", actionsAvailable);
                    itemText = String.format("%s (%s)", itemText, actionsAvailableString);
                }

                list.add(itemText);
            } else {
                list.add("No Description available");
            }
        }

        return list;
    }

    @Override
    protected String getMessage() {
        StatsComponent statsComponent = MapperFactory.statsComponent.get(player);

        InventoryComponent inventoryComponent = MapperFactory.inventoryComponent.get(player);
        return String.format(
                "Player inventory. %d/%d items. %f/%f health. 'enter' to use, 'd' to drop, 'c' to craft, 'k' to deconstruct, 'e' to wear",
                inventoryComponent.currentSize,
                inventoryComponent.maxSize,
                statsComponent.getStatCurrentValue(StatType.HEALTH),
                statsComponent.getStatMaxValue(StatType.HEALTH)
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
            case KeyEvent.VK_E:
                if(getItems().length == 0)
                    return this;

                Entity equipItem = getItems()[currentItem];
                if(player.getClass().equals(PlayerCharacter.class)) {
                    player.add(new EquipActionComponent(equipItem));
                } else {
                    // TODO: deal with equipping if ship, fetch player entity first
                    System.out.println("CANNOT EQUIP IN SHIP");
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
