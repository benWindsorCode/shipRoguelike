package rogue.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.utils.ImmutableArray;
import rogue.ai.NaivePlayerLandAttackAi;
import rogue.ai.NaivePlayerSeaAttackAi;
import rogue.components.AiComponent;
import rogue.components.PositionComponent;
import rogue.components.actions.MovingComponent;
import rogue.factories.FamilyFactory;
import rogue.factories.MapperFactory;

public class NaivePlayerAiAttackSystem extends EntitySystem {
    private ImmutableArray<Entity> entitiesToUpdate;

    public void addedToEngine(Engine engine) {
        entitiesToUpdate = engine.getEntitiesFor(FamilyFactory.ai);
    }

    public void update(float deltaTime) {
        if(deltaTime >= 1)
            entitiesToUpdate.forEach(this::processUpdates);
    }

    private void processUpdates(final Entity e) {
        AiComponent<?> aiComponent = MapperFactory.aiComponent.get(e);

        // TODO: its not nice to manually have to flag which Ai are wondering or not
        // skip any ai that isn't wandering ai
        if(!(aiComponent.ai.getClass().equals(NaivePlayerSeaAttackAi.class)
                || aiComponent.ai.getClass().equals(NaivePlayerLandAttackAi.class))
        )
            return;

        WorldSystem worldSystem = getEngine().getSystem(WorldSystem.class);
        Entity player = worldSystem.getPlayerEntity();
        PositionComponent playerPos = MapperFactory.positionComponent.get(player);

        MovingComponent nextMove = aiComponent.ai.nextMove(worldSystem.getRenderGrid(), null, player, worldSystem.getWorldGrid().get(playerPos.x, playerPos.y));

        e.add(nextMove);
    }
}
