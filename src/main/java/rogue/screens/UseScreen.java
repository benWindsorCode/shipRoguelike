package rogue.screens;

import asciiPanel.AsciiPanel;
import com.badlogic.ashley.core.Entity;
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

public class UseScreen extends InventoryBasedScreen {
    public UseScreen(AsciiPanel terminal, Entity player) {
        super(terminal, player);
    }

    @Override
    protected String getVerb() {
        return "use";
    }

    @Override
    protected boolean isAcceptable(Entity item) {
        UseItemEffectComponent useItemEffectComponent = MapperFactory.useItemEffectComponent.get(item);

        return useItemEffectComponent != null;
    }

    @Override
    protected Screen use(Entity item) {
        // TODO: how can we target more elegantly on item use?
        if(item.getClass().equals(RepairKit.class)) {
            if(player.getClass().equals(PlayerShip.class)) {
                player.add(new UseItemActionComponent(item, player));
            } else if(player.getClass().equals(PlayerCharacter.class)) {
                PlayerShipComponent playerShipComponent = MapperFactory.playerShipComponent.get(player);
                player.add(new UseItemActionComponent(item, playerShipComponent.playerShip));
            } else {
                throw new RuntimeException("Cant use repair kit if not player or ship");
            }
        } else if(item.getClass().equals(RatMeat.class) || item.getClass().equals(RawFish.class)) {
            if(player.getClass().equals(PlayerShip.class)) {
                PlayerOnboardComponent playerOnboardComponent = MapperFactory.playerOnboardComponent.get(player);
                player.add(new UseItemActionComponent(item, playerOnboardComponent.player));
            } else if(player.getClass().equals(PlayerCharacter.class)) {
                player.add(new UseItemActionComponent(item, player));
            } else {
                throw new RuntimeException("Cant use repair kit if not player or ship");
            }
        } else {
            throw new RuntimeException("Dont know how to use this item");
        }

        return null;
    }
}
