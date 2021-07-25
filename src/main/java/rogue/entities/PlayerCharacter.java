package rogue.entities;

import com.badlogic.ashley.core.Entity;
import rogue.ai.PlayerCharacterAi;
import rogue.components.*;
import rogue.components.player.PlayerShipComponent;
import rogue.components.traits.CanBeAttackedComponent;
import rogue.components.traits.CanHarvestComponent;
import rogue.components.traits.IdComponent;
import rogue.factories.RecipeFactory;
import rogue.factories.TileFactory;
import rogue.util.EntityId;

public class PlayerCharacter extends Entity {
    public PlayerCharacter(int x, int y, PlayerShip playerShip, boolean inControlOfPlayer, boolean isVisible) {
        super();
        this.add(new RenderableComponent(isVisible));
        this.add(new IdComponent(EntityId.PLAYER_CHARACTER));
        this.add(new TileComponent(TileFactory.character.glyph, TileFactory.character.color));
        this.add(new PositionComponent(x, y));
        this.add(new HealthComponent(10));
        this.add(new AiComponent<>(new PlayerCharacterAi(this)));
        this.add(new CanBeAttackedComponent());
        this.add(new PlayerShipComponent(playerShip));
        this.add(new CanHarvestComponent());
        this.add(new InventoryComponent(12));
        this.add(new StrengthComponent(5));
        this.add(new RecipeBookComponent(RecipeFactory.playerBook()));
        this.add(new ExamineComponent(
                "Player",
                "Players",
                "The player character. Just like looking in the mirror."
        ));

        if(inControlOfPlayer)
            this.add(new PlayerControlledComponent());
    }
}
