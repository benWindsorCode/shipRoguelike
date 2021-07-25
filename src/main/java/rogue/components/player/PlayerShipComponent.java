package rogue.components.player;

import com.badlogic.ashley.core.Component;
import rogue.entities.PlayerCharacter;
import rogue.entities.PlayerShip;

public class PlayerShipComponent implements Component {
    public PlayerShip playerShip;

    public PlayerShipComponent(PlayerShip playerShip) {
        this.playerShip = playerShip;
    }
}
