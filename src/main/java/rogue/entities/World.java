package rogue.entities;

import com.badlogic.ashley.core.Entity;
import rogue.components.traits.IdComponent;
import rogue.util.EntityId;

// Singleton entity to drop world events onto such as loot and creature spawning
public class World extends Entity {
    public World() {
        this.add(new IdComponent(EntityId.WORLD));
    }
}
