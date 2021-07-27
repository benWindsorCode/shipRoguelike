package rogue.entities.animals;

import com.badlogic.ashley.core.Entity;
import rogue.ai.WanderingSeaAi;
import rogue.components.*;
import rogue.components.render.RenderableComponent;
import rogue.components.render.TileComponent;
import rogue.components.traits.CanBeAttackedComponent;
import rogue.components.traits.IdComponent;
import rogue.entities.resources.Gold;
import rogue.entities.resources.Iron;
import rogue.factories.TileFactory;
import rogue.loot.LootTable;
import rogue.loot.LootTableEntry;
import rogue.stats.Stat;
import rogue.stats.StatType;
import rogue.util.EntityId;

public class CivilianShip extends Entity {
    public CivilianShip() {
        this(0, 0);
    }

    public CivilianShip(int x, int y) {
        super();

        PositionComponent shipPosition = new PositionComponent(x, y);
        LootTable shipLootTable = new LootTable();
        shipLootTable.addLoot(new LootTableEntry(Iron::new, 0.5, 1));
        shipLootTable.addLoot(new LootTableEntry(Gold::new, 0.5, 1));

        StatsComponent shipStats = new StatsComponent();
        shipStats.addStat(new Stat(StatType.HEALTH, 5, 5));
        shipStats.addStat(new Stat(StatType.STRENGTH, 0, 0));

        this.add(new RenderableComponent());
        this.add(new IdComponent(EntityId.CIVILIAN_SHIP));
        this.add(new TileComponent(TileFactory.civilianShip.glyph, TileFactory.civilianShip.color));
        this.add(shipPosition);
        this.add(new AiComponent<>(new WanderingSeaAi<>(this)));
        this.add(shipStats);
        this.add(new ComputerControlledComponent());
        this.add(new CanBeAttackedComponent());
        this.add(new LootableComponent(shipLootTable));
        this.add(new ExamineComponent(
                "Civilian Ship",
                "Civilian Ships",
                "A defenseless civilian ship."
        ));
    }
}
