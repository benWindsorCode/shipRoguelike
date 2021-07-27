package rogue.entities.animals;

import com.badlogic.ashley.core.Entity;
import rogue.ai.WanderingLandAi;
import rogue.components.*;
import rogue.components.render.RenderableComponent;
import rogue.components.render.TileComponent;
import rogue.components.traits.CanBeAttackedComponent;
import rogue.components.traits.IdComponent;
import rogue.entities.food.RawBeef;
import rogue.entities.food.RawChicken;
import rogue.entities.resources.Leather;
import rogue.factories.TileFactory;
import rogue.loot.LootTable;
import rogue.loot.LootTableEntry;
import rogue.stats.Stat;
import rogue.stats.StatType;
import rogue.util.EntityId;

public class Chicken extends Entity {
    public Chicken() {
        this(0, 0);
    }

    public Chicken(int x, int y) {
        super();

        LootTable chickenLootTable = new LootTable();
        chickenLootTable.addLoot(new LootTableEntry(RawChicken::new, 1.0, 1));

        StatsComponent chickenStats = new StatsComponent();
        chickenStats.addStat(new Stat(StatType.HEALTH, 3, 3));
        chickenStats.addStat(new Stat(StatType.STRENGTH, 0, 0));

        this.add(new IdComponent(EntityId.CHICKEN));
        this.add(new RenderableComponent());
        this.add(new TileComponent(TileFactory.chicken.glyph, TileFactory.chicken.color));
        this.add(new PositionComponent(x, y));
        this.add(chickenStats);
        this.add(new AiComponent<>(new WanderingLandAi<>(this)));
        this.add(new ComputerControlledComponent());
        this.add(new CanBeAttackedComponent());
        this.add(new LootableComponent(chickenLootTable));
        this.add(new ExamineComponent(
                "Chicken",
                "Chickens",
                "A small clucking chicken."
        ));
    }
}
