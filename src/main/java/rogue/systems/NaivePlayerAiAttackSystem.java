package rogue.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.utils.ImmutableArray;
import rogue.ai.NaivePlayerLandAttackAi;
import rogue.ai.NaivePlayerSeaAttackAi;
import rogue.components.AiComponent;
import rogue.components.PositionComponent;
import rogue.components.TileComponent;
import rogue.components.actions.MovingComponent;
import rogue.factories.FamilyFactory;
import rogue.factories.MapperFactory;
import rogue.util.RandomUtil;
import rogue.util.TileUtil;

import java.util.Arrays;
import java.util.List;

public class NaivePlayerAiAttackSystem extends EntitySystem {
    private ImmutableArray<Entity> entitiesToUpdate;

    public void addedToEngine(Engine engine) {
        entitiesToUpdate = engine.getEntitiesFor(FamilyFactory.ai);
    }

    public void update(float deltaTime) {
        for(Entity e: entitiesToUpdate) {
            AiComponent<?> aiComponent = MapperFactory.aiComponent.get(e);

            // TODO: its not nice to manually have to flag which Ai are wondering or not
            // skip any ai that isn't wandering ai
            if(!(aiComponent.ai.getClass().equals(NaivePlayerSeaAttackAi.class)
                || aiComponent.ai.getClass().equals(NaivePlayerLandAttackAi.class))
            )
                continue;

            WorldSystem worldSystem = getEngine().getSystem(WorldSystem.class);
            Entity player = worldSystem.getPlayerEntity();

            PositionComponent pos = MapperFactory.positionComponent.get(e);
            PositionComponent playerPos = MapperFactory.positionComponent.get(player);
            TileComponent targetPlayerTile = worldSystem.getWorldGrid().getTileComponent(playerPos.x, playerPos.y);
            double distToPlayer = Math.abs((playerPos.x - pos.x)*(playerPos.x - pos.x)) + Math.abs((playerPos.y - pos.y)*(playerPos.y - pos.y));

            // if 10 away from player then do wandering AI
            // TODO: this logic should be in the nextMove function of the AI
            if(distToPlayer > 150
                    || (aiComponent.ai.getClass().equals(NaivePlayerSeaAttackAi.class) && TileUtil.isLand(targetPlayerTile))
                    || (aiComponent.ai.getClass().equals(NaivePlayerLandAttackAi.class) && TileUtil.isSea(targetPlayerTile))
            ) {
                List<Double> directions = Arrays.asList(-1d, 1d);
                double mxDirection = RandomUtil.getRandom(directions);
                double myDirection = RandomUtil.getRandom(directions);

                int moveSize = 2;
                int mx = (int)(Math.random() * moveSize*mxDirection);
                int my = (int)(Math.random() * moveSize*myDirection);

                e.add(new MovingComponent(mx, my));
            } else {

                // TODO: we dont use the middle field yet so ignore for now
                MovingComponent nextMove = aiComponent.ai.nextMove(worldSystem.getRenderGrid(), null, player);

                e.add(nextMove);
            }

        }
    }
}
