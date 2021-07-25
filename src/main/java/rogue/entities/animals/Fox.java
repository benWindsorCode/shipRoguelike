package rogue.entities.animals;

import com.badlogic.ashley.core.Entity;
import rogue.ai.WanderingLandAi;
import rogue.components.*;
import rogue.components.traits.IdComponent;
import rogue.factories.TileFactory;
import rogue.util.EntityId;

public class Fox extends Entity {
    public Fox() {
        this(0, 0);
    }

    public Fox(int x, int y) {
        super();

        PositionComponent foxPosition = new PositionComponent(x, y);
        this.add(new RenderableComponent());
        this.add(new IdComponent(EntityId.FOX));
        this.add(new TileComponent(TileFactory.fox.glyph, TileFactory.fox.color));
        this.add(foxPosition);
        this.add(new HealthComponent(5));
        this.add(new AiComponent<>(new WanderingLandAi<>(this)));
        this.add(new ComputerControlledComponent());
        this.add(new ExamineComponent(
                "Fox",
                "Foxes",
                "A small red-brown curious fox"
        ));
    }
}
