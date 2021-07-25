package rogue.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.utils.ImmutableArray;
import rogue.components.*;
import rogue.components.actions.MovingComponent;
import rogue.components.ship.PlayerOnboardComponent;
import rogue.entities.PlayerCharacter;
import rogue.factories.FamilyFactory;
import rogue.factories.MapperFactory;
import rogue.render.RenderGrid;

public class MovementSystem extends EntitySystem {
    private ImmutableArray<Entity> entitiesToMove;

    public MovementSystem(int priority) {
        super(priority);

        System.out.println("Initialised MovementSystem");
    }

    public void addedToEngine(Engine engine) {
        entitiesToMove = engine.getEntitiesFor(FamilyFactory.moving);
    }

    public void update(float deltaTime) {
        entitiesToMove.forEach(this::processMoves);
    }

    private void processMoves(Entity e) {
        WorldSystem worldSystem = getEngine().getSystem(WorldSystem.class);

        // TODO: this should be a set of render tiles only?
        RenderGrid renderGrid = worldSystem.getRenderGrid();

        PositionComponent pos = MapperFactory.positionComponent.get(e);
        MovingComponent mov = MapperFactory.movingComponent.get(e);

        int newX = pos.x + mov.mx;
        int newY = pos.y + mov.my;

        // TODO: better to get a bounds tile, then not allow players to move to it
        if(newX < 0 || newX >= worldSystem.getWidth() || newY < 0 || newY >= worldSystem.getHeight())
            return;

        AiComponent<?> aiComponent = MapperFactory.aiComponent.get(e);

        if(aiComponent != null) {
            aiComponent.ai.onEnter(newX, newY, renderGrid.get(newX, newY), worldSystem.getWorldGrid().get(newX, newY));
        } else {
            pos.x = newX;
            pos.y = newY;
        }
        e.remove(MovingComponent.class);

        // also update the pos of any entities this entity carries
        PlayerOnboardComponent container = MapperFactory.playerOnboardComponent.get(e);
        if(container != null) {
            PlayerCharacter player = container.player;
            PositionComponent insidePos = MapperFactory.positionComponent.get(player);
            if(insidePos == null)
                return;

            insidePos.x = pos.x;
            insidePos.y = pos.y;

        }
    }
}
