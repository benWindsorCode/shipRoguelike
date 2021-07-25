package rogue.ai;

import com.badlogic.ashley.core.Entity;
import rogue.components.PositionComponent;
import rogue.components.actions.AttackActionComponent;
import rogue.components.traits.CanBeAttackedComponent;
import rogue.factories.MapperFactory;
import rogue.util.EntityUtil;

public class WanderingSeaAi<T extends Entity> extends WanderingAi<T> {

    public WanderingSeaAi(T entity) {
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
}
