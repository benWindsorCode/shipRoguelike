package rogue.entities.enemies;

import com.badlogic.ashley.core.Entity;
import rogue.ai.NaivePlayerSeaAttackAi;
import rogue.components.*;
import rogue.components.render.RenderableComponent;
import rogue.components.render.TileComponent;
import rogue.components.traits.CanBeAttackedComponent;
import rogue.components.traits.IdComponent;
import rogue.entities.resources.Iron;
import rogue.factories.TileFactory;
import rogue.loot.LootTable;
import rogue.loot.LootTableEntry;
import rogue.stats.Stat;
import rogue.stats.StatType;
import rogue.util.EntityId;

public class EnemyShip extends Entity {
    public EnemyShip() {
        this(0, 0);
    }

    public EnemyShip(int x, int y) {
        super();

        PositionComponent shipPosition = new PositionComponent(x, y);
        LootTable shipLootTable = new LootTable();
        shipLootTable.addLoot(new LootTableEntry(Iron::new, 1.0, 2));

        StatsComponent shipStats = new StatsComponent();
        shipStats.addStat(new Stat(StatType.HEALTH, 7, 7 ));
        shipStats.addStat(new Stat(StatType.STRENGTH, 3, 3));

        this.add(new RenderableComponent());
        this.add(new IdComponent(EntityId.ENEMY_SHIP));
        this.add(new TileComponent(TileFactory.enemyShip.glyph, TileFactory.enemyShip.color));
        this.add(shipPosition);
        this.add(shipStats);
        this.add(new AiComponent<>(new NaivePlayerSeaAttackAi<>(this)));
        this.add(new ComputerControlledComponent());
        this.add(new CanBeAttackedComponent());
        this.add(new LootableComponent(shipLootTable));
        this.add(new InventoryComponent(5));
        this.add(new ExamineComponent(
                "Enemy Ship",
                "Enemy Ships",
                "An aggressive looking enemy ship"
        ));
    }
}
