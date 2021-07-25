package rogue.ai;

import com.badlogic.ashley.core.Entity;
import rogue.ai.pathfinding.PathfindingGraph;
import rogue.components.PositionComponent;
import rogue.components.TileComponent;
import rogue.components.actions.AttackActionComponent;
import rogue.components.actions.MovingComponent;
import rogue.components.traits.CanBeAttackedComponent;
import rogue.factories.MapperFactory;
import rogue.render.RenderGrid;
import rogue.util.TileUtil;

public class WanderingSeaAi<T extends Entity> extends BaseAi<T> {

    public WanderingSeaAi(T entity) {
        super(entity);
    }

    @Override
    public void onEnter(int x, int y, Entity renderEntity, Entity worldEntity) {
        TileComponent tile = MapperFactory.tileComponent.get(renderEntity);
        // TODO: Need the not equals self check to ensure ships dont attack their own entity, bit awkward their render entity is themselves? Do they have moves of zero?
        // Enemies should be able to attack us and others
        CanBeAttackedComponent canBeAttackedComponent = MapperFactory.canAttackComponent.get(renderEntity);
        if(canBeAttackedComponent != null && !renderEntity.equals(entity)) {
            entity.add(new AttackActionComponent(renderEntity));
            return;
        }

        if(TileUtil.isSea(tile)) {
            PositionComponent pos = MapperFactory.positionComponent.get(entity);
            pos.x = x;
            pos.y = y;
            return;
        }
    }

    @Override
    public MovingComponent nextMove(RenderGrid renderGrid, Entity renderEntity, Entity targetEntity) {
        return null;
    }
}