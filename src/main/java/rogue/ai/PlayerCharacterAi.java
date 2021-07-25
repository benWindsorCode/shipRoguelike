package rogue.ai;

import com.badlogic.ashley.core.Entity;
import rogue.components.*;
import rogue.components.actions.AttackActionComponent;
import rogue.components.actions.HarvestActionComponent;
import rogue.components.actions.MovingComponent;
import rogue.components.player.PlayerShipComponent;
import rogue.components.ship.PlayerOnboardComponent;
import rogue.components.traits.CanBeAttackedComponent;
import rogue.components.traits.CanBeHarvestedComponent;
import rogue.components.traits.CannotEnterComponent;
import rogue.entities.PlayerCharacter;
import rogue.entities.PlayerShip;
import rogue.factories.MapperFactory;
import rogue.render.RenderGrid;
import rogue.util.EntityUtil;

public class PlayerCharacterAi extends BaseAi<PlayerCharacter> {

    public PlayerCharacterAi(PlayerCharacter entity) {
        super(entity);
    }

    @Override
    public void onEnter(int x, int y, Entity renderEntity, Entity worldEntity) {
        // TODO: ideally we also pass and use the List<Entity> on this tile too
        TileComponent renderTile = MapperFactory.tileComponent.get(renderEntity);
        CannotEnterComponent cannotEnterComponent = MapperFactory.cannotEnterComponent.get(renderEntity);
        PlayerShipComponent playerShipComponent = MapperFactory.playerShipComponent.get(entity);

        // attack as first action if possible
        CanBeAttackedComponent canBeAttackedComponent = MapperFactory.canAttackComponent.get(renderEntity);
        if(canBeAttackedComponent != null && !renderEntity.equals(playerShipComponent.playerShip)) {
            entity.add(new AttackActionComponent(renderEntity));
            return;
        }

        if(EntityUtil.isLand(worldEntity) && cannotEnterComponent == null) {
            PositionComponent pos = MapperFactory.positionComponent.get(entity);
            pos.x = x;
            pos.y = y;
            return;
        }

        // TODO: differentiate between any ship and player owned ship, to allow hijacking docked ships
        //if(TileUtil.isPlayerShip(renderTile)) {
        if(playerShipComponent.playerShip.equals(renderEntity)) {
            // move player onto ship
            PositionComponent pos = MapperFactory.positionComponent.get(entity);
            pos.x = x;
            pos.y = y;

            // After embarking we now control the ship
            entity.remove(PlayerControlledComponent.class);

            // Put player on ship and make it controlled by player
            PlayerShip playerShip = playerShipComponent.playerShip;
            playerShip.add(new PlayerControlledComponent());
            playerShip.add(new PlayerOnboardComponent(entity));

            // Player invisible as now onboard ship
            RenderableComponent renderableComponent = MapperFactory.renderableComponent.get(entity);
            renderableComponent.visible = false;
            return;
        }

        CanBeHarvestedComponent canBeHarvestedComponent = MapperFactory.harvestableComponent.get(renderEntity);
        if(canBeHarvestedComponent != null) {
            renderEntity.add(new HarvestActionComponent(entity));
            return;
        }
    }

    @Override
    public MovingComponent nextMove(RenderGrid renderGrid, Entity renderEntity, Entity targetEntity, Entity targetWorldEntity) {
        return null;
    }
}
