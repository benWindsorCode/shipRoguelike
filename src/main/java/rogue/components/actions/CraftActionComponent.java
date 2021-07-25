package rogue.components.actions;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

// Action to put on entity that is going to craft the 'toCraft' entity
public class CraftActionComponent implements Component {
    public Entity entityToCraft;

    public CraftActionComponent(Entity entityToCraft) {
        this.entityToCraft = entityToCraft;
    }

}
