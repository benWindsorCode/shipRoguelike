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
import rogue.stats.Stat;
import rogue.stats.StatType;
import rogue.util.EntityId;

public class SmallFish extends Entity {
    public SmallFish() {
        this(0, 0);
    }

    public SmallFish(int x, int y) {
        super();

        LootTable fishLootTable = new LootTable();
        fishLootTable.addLoot(new LootTableEntry(RawFish::new, 1.0, 1));

        StatsComponent fishStats = new StatsComponent();
        fishStats.addStat(new Stat(StatType.HEALTH, 5, 5));
        fishStats.addStat(new Stat(StatType.STRENGTH, 0, 0));

        PositionComponent fishPosition = new PositionComponent(x, y);
        this.add(new RenderableComponent());
        this.add(new IdComponent(EntityId.SMALL_FISH));
        this.add(new TileComponent(TileFactory.smallFish.glyph, TileFactory.smallFish.color));
        this.add(fishPosition);
        this.add(fishStats);
        this.add(new AiComponent<>(new WanderingPeacefulSeaAi<>(this)));
        this.add(new ComputerControlledComponent());
        this.add(new CanBeAttackedComponent());
        this.add(new LootableComponent(fishLootTable));
        this.add(new ExamineComponent(
                "Small Fish",
                "Small Fish",
                "A small fish, not much meat on this."
        ));
    }
}
