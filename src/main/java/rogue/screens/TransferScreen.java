package rogue.screens;

import asciiPanel.AsciiPanel;
import com.badlogic.ashley.core.Entity;
import rogue.components.actions.InventoryTransferActionComponent;
import rogue.components.player.PlayerShipComponent;
import rogue.components.ship.PlayerOnboardComponent;
import rogue.entities.PlayerCharacter;
import rogue.entities.PlayerShip;
import rogue.entities.crafting.Chest;
import rogue.factories.MapperFactory;

// TODO: transfer screen needs uplift: needs to take two entities to transfer two and from
//       needs to render two lists
public class TransferScreen extends InventoryBasedScreen {

    public TransferScreen(AsciiPanel terminal, Entity player) {
        super(terminal, player);
    }

    @Override
    protected String getVerb() {
        String verb = "transfer";

        if(player.getClass().equals(PlayerShip.class) || player.getClass().equals(Chest.class)) {
            verb = "transfer to player";
        } else if(player.getClass().equals(PlayerCharacter.class)) {
            verb = "transfer to ship";
        }

        return verb;
    }

    @Override
    protected boolean isAcceptable(Entity item) {
        return true;
    }

    @Override
    protected Screen use(Entity item) {
        if(player.getClass().equals(PlayerShip.class) || player.getClass().equals(Chest.class)) {
            PlayerOnboardComponent playerOnboardComponent = MapperFactory.playerOnboardComponent.get(player);
            Entity destinationEntity = playerOnboardComponent.player;
            player.add(new InventoryTransferActionComponent(destinationEntity, item));
        } else if(player.getClass().equals(PlayerCharacter.class)) {
            PlayerShipComponent playerShipComponent = MapperFactory.playerShipComponent.get(player);
            Entity destinationEntity = playerShipComponent.playerShip;
            player.add(new InventoryTransferActionComponent(destinationEntity, item));
        } else {
            throw new RuntimeException("Cant use transfer screen for non player/ship entity");
        }

        return null;
    }
}
