package rogue.ai;

import com.badlogic.ashley.core.Entity;
import rogue.components.actions.MovingComponent;
import rogue.render.RenderGrid;
import rogue.util.RandomUtil;

import java.util.Arrays;
import java.util.List;

public abstract class WanderingAi<T extends Entity> extends BaseAi<T> {
    public WanderingAi(T entity) {
        super(entity);
    }

    @Override
    public MovingComponent nextMove(RenderGrid renderGrid, Entity renderEntity, Entity targetEntity, Entity targetWorldEntity) {
        List<Double> directions = Arrays.asList(-1d, 1d);
        double mxDirection = RandomUtil.getRandom(directions);
        double myDirection = RandomUtil.getRandom(directions);

        int moveSize = 2;
        int mx = (int)(Math.random() * moveSize*mxDirection);
        int my = (int)(Math.random() * moveSize*myDirection);

        return new MovingComponent(mx, my);
    }
}
