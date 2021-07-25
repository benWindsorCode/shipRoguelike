package rogue.ai;

import com.badlogic.ashley.core.Entity;
import rogue.components.PositionComponent;
import rogue.components.TileComponent;
import rogue.components.actions.AttackActionComponent;
import rogue.components.actions.MovingComponent;
import rogue.components.traits.CanBeAttackedComponent;
import rogue.entities.enemies.EnemyShip;
import rogue.factories.MapperFactory;
import rogue.render.RenderGrid;
import rogue.util.EntityUtil;
import rogue.util.TileUtil;

// If player close enough move in their general direction
public class NaivePlayerSeaAttackAi<T extends Entity> extends BaseAi<T> {

    public NaivePlayerSeaAttackAi(T entity) {
        super(entity);
    }

    @Override
    public void onEnter(int x, int y, Entity renderEntity, Entity worldEntity) {
        // TODO: Need the not equals self check to ensure ships dont attack their own entity, bit awkward their render entity is themselves? Do they have moves of zero?
        // Enemies should be able to attack us and others
        CanBeAttackedComponent canBeAttackedComponent = MapperFactory.canAttackComponent.get(renderEntity);
        if(canBeAttackedComponent != null && !renderEntity.equals(entity)) {
            entity.add(new AttackActionComponent(renderEntity));
            return;
        }

        if(EntityUtil.isSea(renderEntity)) {
            PositionComponent pos = MapperFactory.positionComponent.get(entity);
            pos.x = x;
            pos.y = y;
            return;
        }
    }

    @Override
    public MovingComponent nextMove(RenderGrid renderGrid, Entity renderEntity, Entity targetEntity) {
        PositionComponent pos = MapperFactory.positionComponent.get(entity);
        PositionComponent targetPos = MapperFactory.positionComponent.get(targetEntity);

        int xDiff = targetPos.x - pos.x;
        int yDiff = targetPos.y - pos.y;

        int mx = Integer.compare(xDiff, 0);
        int my = Integer.compare(yDiff, 0);

        MovingComponent nextMove = new MovingComponent(mx, my);
        return nextMove;
    }
}
