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
import rogue.entities.resources.Wool;
import rogue.factories.TileFactory;
import rogue.loot.LootTable;
import rogue.loot.LootTableEntry;
import rogue.stats.Stat;
import rogue.stats.StatType;
import rogue.util.EntityId;

public class Sheep extends Entity {
    public Sheep() {
        this(0, 0);
    }

    public Sheep(int x, int y) {
        super();

        LootTable sheepLootTable = new LootTable();
        sheepLootTable.addLoot(new LootTableEntry(Wool::new, 1.0, 1));

        StatsComponent sheepStats = new StatsComponent();
        sheepStats.addStat(new Stat(StatType.HEALTH, 9, 9));
        sheepStats.addStat(new Stat(StatType.STRENGTH, 0, 0));

        this.add(new IdComponent(EntityId.SHEEP));
        this.add(new RenderableComponent());
        this.add(new TileComponent(TileFactory.sheep.glyph, TileFactory.sheep.color));
        this.add(new PositionComponent(x, y));
        this.add(sheepStats);
        this.add(new AiComponent<>(new WanderingLandAi<>(this)));
        this.add(new ComputerControlledComponent());
        this.add(new CanBeAttackedComponent());
        this.add(new LootableComponent(sheepLootTable));
        this.add(new ExamineComponent(
                "Sheep",
                "Sheep",
                "A wondering sheep. So fluffy!"
        ));
    }
}
