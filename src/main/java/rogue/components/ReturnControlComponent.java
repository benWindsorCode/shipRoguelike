package rogue.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

// Mark an entity to return control of player to when current entity destroyed
public class ReturnControlComponent implements Component {
    public Entity entity;

    public ReturnControlComponent(Entity entity) {
        this.entity = entity;
    }
}
