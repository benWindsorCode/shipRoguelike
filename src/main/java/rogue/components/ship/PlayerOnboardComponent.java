package rogue.components.ship;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import rogue.entities.PlayerCharacter;

import java.util.ArrayList;
import java.util.List;

public class PlayerOnboardComponent implements Component {
    public PlayerCharacter player;

    public PlayerOnboardComponent(PlayerCharacter player) {
        this.player = player;
    }
}
