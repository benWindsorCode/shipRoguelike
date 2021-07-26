package rogue.entities.animals;

import com.badlogic.ashley.core.Entity;
import rogue.ai.WanderingPeacefulSeaAi;
import rogue.components.*;
import rogue.components.render.RenderableComponent;
import rogue.components.render.TileComponent;
import rogue.components.traits.CanBeAttackedComponent;
import rogue.components.traits.IdComponent;
import rogue.entities.food.RawFish;
import rogue.factories.TileFactory;
import rogue.loot.LootTable;
import rogue.loot.LootTableEntry;
import rogue.util.EntityId;

public class BigFish extends Entity {
    public BigFish() {
        this(0, 0);
    }

    public BigFish(int x, int y) {
        super();

        LootTable fishLootTable = new LootTable();
        fishLootTable.addLoot(new LootTableEntry(RawFish::new, 1.0, 2));

        PositionComponent fishPosition = new PositionComponent(x, y);
        this.add(new IdComponent(EntityId.BIG_FISH));
        this.add(new RenderableComponent());
        this.add(new TileComponent(TileFactory.bigFish.glyph, TileFactory.bigFish.color));
        this.add(fishPosition);
        this.add(new HealthComponent(5));
        this.add(new AiComponent<>(new WanderingPeacefulSeaAi<>(this)));
        this.add(new ComputerControlledComponent());
        this.add(new CanBeAttackedComponent());
        this.add(new StrengthComponent(0));
        this.add(new LootableComponent(fishLootTable));
        this.add(new ExamineComponent(
                "Big Fish",
                "Big Fish",
                "A big fish, some meat on this."
        ));
    }
}
