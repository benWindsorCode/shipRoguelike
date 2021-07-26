package rogue.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.utils.ImmutableArray;

// System to deal with logic for interaction with world objects: grab, examine, deconstruct
public class InteractSystem extends EntitySystem {
    private ImmutableArray<Entity> entitiesToGrab;

    public void addedToEngine(Engine engine) {
    }

    public void update(float deltaTime) {
        for(Entity e: entitiesToGrab) {

        }
    }
}
