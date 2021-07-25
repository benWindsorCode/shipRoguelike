package rogue.ai;

import com.badlogic.ashley.core.Entity;
import rogue.components.PositionComponent;
import rogue.factories.MapperFactory;
import rogue.util.EntityUtil;

public class WanderingPeacefulSeaAi<T extends Entity> extends WanderingAi<T> {
    public WanderingPeacefulSeaAi(T entity) {
        super(entity);
    }

    @Override
    public void onEnter(int x, int y, Entity renderEntity, Entity worldEntity) {
        if(EntityUtil.isSea(renderEntity)) {
            PositionComponent pos = MapperFactory.positionComponent.get(entity);
            pos.x = x;
            pos.y = y;
            return;
        }
    }
}
