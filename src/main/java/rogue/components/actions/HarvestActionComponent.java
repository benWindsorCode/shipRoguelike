package rogue.components.actions;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

// To be put on a HarvestableComponent when an Entity tries to harvest it
public class HarvestActionComponent implements Component {
    public Entity harvester;

    public HarvestActionComponent(Entity harvester) {
        this.harvester = harvester;
    }
}
