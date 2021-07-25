package rogue.components.ship;

import com.badlogic.ashley.core.Component;
import rogue.entities.PlayerCharacter;

public class PlayerOnboardComponent implements Component {
    public PlayerCharacter player;

    public PlayerOnboardComponent(PlayerCharacter player) {
        this.player = player;
    }
}
