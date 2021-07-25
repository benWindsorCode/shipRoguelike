package rogue.components.actions;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

// Placed on entity, who is holding the entityToDeconstruct;
public class DeconstructActionComponent implements Component {
    public Entity entityToDeconstruct;

    public DeconstructActionComponent(final Entity entityToDeconstruct) {
        this.entityToDeconstruct = entityToDeconstruct;
    }
}
