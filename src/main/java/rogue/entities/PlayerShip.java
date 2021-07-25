package rogue.entities;

import com.badlogic.ashley.core.Entity;
import rogue.ai.PlayerShipAi;
import rogue.components.*;
import rogue.components.traits.CanBeAttackedComponent;
import rogue.components.traits.IdComponent;
import rogue.factories.TileFactory;
import rogue.util.EntityId;

// TODO: probably not wise having lot of if class == PlayerShip, as then we are tied to this entity
//       better to somehow always check if entity == PlayerShipComponent.ship on the PlayerCharacter
public class PlayerShip extends Entity {
    public PlayerShip(int x, int y, boolean inControlOfPlayer, boolean isVisible) {
        super();
        this.add(new RenderableComponent(isVisible));
        this.add(new IdComponent(EntityId.PLAYER_SHIP));
        this.add(new TileComponent(TileFactory.shipV1.glyph, TileFactory.shipV1.color));
        this.add(new PositionComponent(x, y));
        this.add(new HealthComponent(40));
        this.add(new CanBeAttackedComponent());
        this.add(new AiComponent<>(new PlayerShipAi(this)));
        this.add(new StrengthComponent(10));
        this.add(new InventoryComponent(25));
        this.add(new ExamineComponent(
                "Your Ship",
                "Your Ships",
                "Standing tall in the water you see your ship ready to sail"
        ));

        if(inControlOfPlayer)
            this.add(new PlayerControlledComponent());
    }
}
