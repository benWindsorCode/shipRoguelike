package rogue.ai;

import com.badlogic.ashley.core.Entity;
import rogue.ai.pathfinding.PathfindingGraph;
import rogue.components.PositionComponent;
import rogue.components.actions.MovingComponent;
import rogue.factories.MapperFactory;
import rogue.render.RenderGrid;

// TODO: this shouldn't be needed if we had an itemGrid/nonWorldTiles grid in the world
public class CursorAi extends BaseAi {

    // cache entered tile, to allow printing description when cursor on top of it
    private Entity tileEntered;

    public CursorAi(Entity entity) {
        super(entity);
    }

    @Override
    public void onEnter(int x, int y, Entity renderEntity, Entity worldEntity) {
        tileEntered = renderEntity;

        PositionComponent pos = MapperFactory.positionComponent.get(entity);
        pos.x = x;
        pos.y = y;
    }

    @Override
    public MovingComponent nextMove(RenderGrid renderGrid, Entity renderEntity, Entity targetEntity) {
        return null;
    }

    public Entity getTileEntered() {
        return tileEntered;
    }
}
