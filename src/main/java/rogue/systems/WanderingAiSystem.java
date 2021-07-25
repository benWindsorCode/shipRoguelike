package rogue.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.utils.ImmutableArray;
import rogue.ai.WanderingLandAi;
import rogue.ai.WanderingPeacefulSeaAi;
import rogue.ai.WanderingSeaAi;
import rogue.components.AiComponent;
import rogue.components.actions.MovingComponent;
import rogue.factories.FamilyFactory;
import rogue.factories.MapperFactory;
import rogue.util.RandomUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WanderingAiSystem extends EntitySystem {
    private ImmutableArray<Entity> entitiesToUpdate;

    public void addedToEngine(Engine engine) {
        entitiesToUpdate = engine.getEntitiesFor(FamilyFactory.ai);
    }

    public void update(float deltaTime) {
        for(Entity e: entitiesToUpdate) {
            AiComponent<?> aiComponent = MapperFactory.aiComponent.get(e);

            // TODO: its not nice to manually have to flag which Ai are wondering or not
            // skip any ai that isn't wandering ai
            if(!(aiComponent.ai.getClass().equals(WanderingSeaAi.class)
                    || aiComponent.ai.getClass().equals(WanderingLandAi.class)
                    || aiComponent.ai.getClass().equals(WanderingPeacefulSeaAi.class))
            )
                continue;

            List<Double> directions = Arrays.asList(-1d, 1d);
            double mxDirection = RandomUtil.getRandom(directions);
            double myDirection = RandomUtil.getRandom(directions);

            int moveSize = 2;
            int mx = (int)(Math.random() * moveSize*mxDirection);
            int my = (int)(Math.random() * moveSize*myDirection);

            e.add(new MovingComponent(mx, my));
        }
    }

}
