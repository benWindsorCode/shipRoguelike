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

public class WanderingPeacefulSeaAi<T extends Entity> extends BaseAi<T> {
    public WanderingPeacefulSeaAi(T entity) {
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
    }

    @Override
    public MovingComponent nextMove(RenderGrid renderGrid, Entity renderEntity, Entity targetEntity) {
        return null;
    }
}
