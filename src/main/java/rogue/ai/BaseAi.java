package rogue.ai;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ai.steer.utils.Path;
import rogue.ai.pathfinding.PathfindingGraph;
import rogue.components.TileComponent;
import rogue.components.actions.MovingComponent;
import rogue.entities.WorldTile;
import rogue.render.RenderGrid;

public abstract class BaseAi<T extends Entity> {

    protected T entity;

    public BaseAi(T entity) {
        this.entity = entity;
    }

    public abstract void onEnter(int x, int y, Entity renderEntity, Entity worldEntity);

    // TODO: is this generic enough to have at BaseAi level, shoudl I have a PathfindingAi subclass?
    public abstract MovingComponent nextMove(RenderGrid renderGrid, Entity renderEntity, Entity targetEntity);
}
