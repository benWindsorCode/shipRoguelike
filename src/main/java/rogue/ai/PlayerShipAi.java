package rogue.ai;

import com.badlogic.ashley.core.Entity;
import rogue.ai.pathfinding.PathfindingGraph;
import rogue.components.*;
import rogue.components.actions.AttackActionComponent;
import rogue.components.actions.MovingComponent;
import rogue.components.ship.PlayerOnboardComponent;
import rogue.components.traits.CanBeAttackedComponent;
import rogue.entities.PlayerCharacter;
import rogue.entities.PlayerShip;
import rogue.factories.MapperFactory;
import rogue.render.RenderGrid;
import rogue.util.TileUtil;

public class PlayerShipAi extends BaseAi<PlayerShip> {

    public PlayerShipAi(PlayerShip entity) {
        super(entity);
    }

    @Override
    public void onEnter(int x, int y, Entity renderEntity, Entity worldEntity) {
        TileComponent tile = MapperFactory.tileComponent.get(renderEntity);

        if(TileUtil.isSea(tile)) {
            PositionComponent pos = MapperFactory.positionComponent.get(entity);
            pos.x = x;
            pos.y = y;
            return;
        }

        if(TileUtil.isLand(tile)) {
            PlayerOnboardComponent playerOnboardComponent = MapperFactory.playerOnboardComponent.get(entity);
            PlayerCharacter player = playerOnboardComponent.player;

            // Put player on tile that they leave ship
            PositionComponent pos = MapperFactory.positionComponent.get(player);
            pos.x = x;
            pos.y = y;

            // Player is now controlled and no longer onboard the ship
            entity.remove(PlayerControlledComponent.class);
            entity.remove(PlayerOnboardComponent.class);
            player.add(new PlayerControlledComponent());

            RenderableComponent renderableComponent = MapperFactory.renderableComponent.get(player);
            renderableComponent.visible = true;
        }

        CanBeAttackedComponent canBeAttackedComponent = MapperFactory.canAttackComponent.get(renderEntity);
        if(canBeAttackedComponent != null) {
            entity.add(new AttackActionComponent(renderEntity));
        }
    }

    @Override
    public MovingComponent nextMove(RenderGrid renderGrid, Entity renderEntity, Entity targetEntity) {
        return null;
    }
}
