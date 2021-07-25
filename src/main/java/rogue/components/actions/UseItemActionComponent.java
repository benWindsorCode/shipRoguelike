package rogue.components.actions;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

// TODO: do we want some kind of Validation lambda to run before we apply effect?
// Component, dropped on entity using the item, item applies its effect to the targetEntity
public class UseItemActionComponent implements Component {
    public Entity item;
    public Entity targetEntity;

    public UseItemActionComponent(Entity item, Entity targetEntity) {
        this.item = item;
        this.targetEntity = targetEntity;
    }
}
