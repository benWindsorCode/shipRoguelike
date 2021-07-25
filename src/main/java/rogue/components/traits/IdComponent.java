package rogue.components.traits;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import rogue.util.EntityId;

public class IdComponent implements Component {
    public EntityId entityId;

    public IdComponent(EntityId entityId) {
        this.entityId = entityId;
    }
}
