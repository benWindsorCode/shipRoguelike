package rogue.entities;

import com.badlogic.ashley.core.Entity;
import rogue.ai.PlayerShipAi;
import rogue.components.*;
import rogue.components.render.RenderableComponent;
import rogue.components.render.TileComponent;
import rogue.components.traits.CanBeAttackedComponent;
import rogue.components.traits.IdComponent;
import rogue.factories.TileFactory;
import rogue.stats.Stat;
import rogue.stats.StatType;
import rogue.util.EntityId;

public class PlayerShip extends Entity {
    public PlayerShip(int x, int y, boolean inControlOfPlayer, boolean isVisible) {
        super();

        StatsComponent shipStats = new StatsComponent();
        shipStats.addStat(new Stat(StatType.HEALTH, 40, 40));
        shipStats.addStat(new Stat(StatType.STRENGTH, 10, 10));

        this.add(new RenderableComponent(isVisible));
        this.add(new IdComponent(EntityId.PLAYER_SHIP));
        this.add(new TileComponent(TileFactory.shipV1.glyph, TileFactory.shipV1.color));
        this.add(new PositionComponent(x, y));
        this.add(shipStats);
        this.add(new CanBeAttackedComponent());
        this.add(new AiComponent<>(new PlayerShipAi(this)));
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
