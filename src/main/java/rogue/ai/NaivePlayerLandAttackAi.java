package rogue.ai;

import com.badlogic.ashley.core.Entity;
import rogue.components.PositionComponent;
import rogue.components.TileComponent;
import rogue.components.actions.AttackActionComponent;
import rogue.components.actions.MovingComponent;
import rogue.components.traits.CanBeAttackedComponent;
import rogue.components.traits.CannotEnterComponent;
import rogue.factories.MapperFactory;
import rogue.render.RenderGrid;
import rogue.util.EntityUtil;
import rogue.util.RandomUtil;
import rogue.util.TileUtil;

import java.util.Arrays;
import java.util.List;

public class NaivePlayerLandAttackAi <T extends Entity> extends BaseAi<T> {

    public NaivePlayerLandAttackAi(T entity) {
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

        // Use world tile here so they can walk over chopped stone etc, but cant walk over if a tree is in the way
        CannotEnterComponent cannotEnterComponent = MapperFactory.cannotEnterComponent.get(renderEntity);
        if(EntityUtil.isLand(worldEntity) && cannotEnterComponent == null) {
            PositionComponent pos = MapperFactory.positionComponent.get(entity);
            pos.x = x;
            pos.y = y;
            return;
        }
    }

    // TODO: copied from the sea attack AI exactly other than the EnemyUtil.isSea vs EnemyUtil.isLand
    //       perhaps abstract to parent class which takes some kind of filter?
    // TODO: make the distToPlayer threshold a class parameter to set
    @Override
    public MovingComponent nextMove(RenderGrid renderGrid, Entity renderEntity, Entity targetEntity, Entity targetWorldEntity) {
        PositionComponent pos = MapperFactory.positionComponent.get(entity);
        PositionComponent playerPos = MapperFactory.positionComponent.get(targetEntity);
        double distToPlayer = Math.abs((playerPos.x - pos.x)*(playerPos.x - pos.x)) + Math.abs((playerPos.y - pos.y)*(playerPos.y - pos.y));

        if(distToPlayer > 150 || EntityUtil.isSea(targetWorldEntity)) {
            List<Double> directions = Arrays.asList(-1d, 1d);
            double mxDirection = RandomUtil.getRandom(directions);
            double myDirection = RandomUtil.getRandom(directions);

            int moveSize = 2;
            int mx = (int)(Math.random() * moveSize*mxDirection);
            int my = (int)(Math.random() * moveSize*myDirection);

            return new MovingComponent(mx, my);
        }

        // if player close then move in their direction
        int xDiff = playerPos.x - pos.x;
        int yDiff = playerPos.y - pos.y;

        int mx = Integer.compare(xDiff, 0);
        int my = Integer.compare(yDiff, 0);

        return new MovingComponent(mx, my);
    }
}
