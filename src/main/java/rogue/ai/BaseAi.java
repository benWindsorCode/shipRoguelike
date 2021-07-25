package rogue.ai;

import com.badlogic.ashley.core.Entity;
import rogue.components.actions.MovingComponent;
import rogue.render.RenderGrid;

public abstract class BaseAi<T extends Entity> {

    protected T entity;

    public BaseAi(T entity) {
        this.entity = entity;
    }

    public abstract void onEnter(int x, int y, Entity renderEntity, Entity worldEntity);

    // TODO: is this generic enough to have at BaseAi level, should I have a PathfindingAi subclass?
    public abstract MovingComponent nextMove(RenderGrid renderGrid, Entity renderEntity, Entity targetEntity, Entity targetWorldEntity);
}
