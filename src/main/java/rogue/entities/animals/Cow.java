package rogue.entities.animals;

import com.badlogic.ashley.core.Entity;
import rogue.ai.WanderingLandAi;
import rogue.components.*;
import rogue.components.render.RenderableComponent;
import rogue.components.render.TileComponent;
import rogue.components.traits.CanBeAttackedComponent;
import rogue.components.traits.IdComponent;
import rogue.entities.food.RawBeef;
import rogue.entities.resources.Leather;
import rogue.factories.TileFactory;
import rogue.loot.LootTable;
import rogue.loot.LootTableEntry;
import rogue.stats.Stat;
import rogue.stats.StatType;
import rogue.util.EntityId;

public class Cow extends Entity {
    public Cow() {
        this(0, 0);
    }

    public Cow(int x, int y) {
        super();

        LootTable cowLootTable = new LootTable();
        cowLootTable.addLoot(new LootTableEntry(Leather::new, 1.0, 1));
        cowLootTable.addLoot(new LootTableEntry(RawBeef::new, 1.0, 1));

        StatsComponent cowStats = new StatsComponent();
        cowStats.addStat(StatType.HEALTH, new Stat(StatType.HEALTH, 12, 12));
        cowStats.addStat(StatType.STRENGTH, new Stat(StatType.STRENGTH, 0, 0));

        this.add(new IdComponent(EntityId.COW));
        this.add(new RenderableComponent());
        this.add(new TileComponent(TileFactory.cow.glyph, TileFactory.cow.color));
        this.add(new PositionComponent(x, y));
        this.add(cowStats);
        this.add(new AiComponent<>(new WanderingLandAi<>(this)));
        this.add(new ComputerControlledComponent());
        this.add(new CanBeAttackedComponent());
        this.add(new LootableComponent(cowLootTable));
        this.add(new ExamineComponent(
                "Cow",
                "Cows",
                "A happy brown cow."
        ));
    }
}
