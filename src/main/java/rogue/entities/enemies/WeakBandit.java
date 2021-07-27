package rogue.entities.enemies;

import com.badlogic.ashley.core.Entity;
import rogue.ai.NaivePlayerLandAttackAi;
import rogue.components.*;
import rogue.components.render.RenderableComponent;
import rogue.components.render.TileComponent;
import rogue.components.traits.CanBeAttackedComponent;
import rogue.components.traits.IdComponent;
import rogue.entities.resources.Gold;
import rogue.factories.TileFactory;
import rogue.loot.LootTable;
import rogue.loot.LootTableEntry;
import rogue.stats.Stat;
import rogue.stats.StatType;
import rogue.util.EntityId;

public class WeakBandit extends Entity {
    public WeakBandit() {
        this(0, 0);
    }

    public WeakBandit(int x, int y) {
        super();

        PositionComponent banditPosition = new PositionComponent(x, y);
        LootTable banditLootTable = new LootTable();
        banditLootTable.addLoot(new LootTableEntry(Gold::new, 1.0, 1));

        StatsComponent banditStats = new StatsComponent();
        banditStats.addStat(StatType.HEALTH, new Stat(StatType.HEALTH, 3, 3));
        banditStats.addStat(StatType.STRENGTH, new Stat(StatType.STRENGTH, 2, 2));

        this.add(new RenderableComponent());
        this.add(new IdComponent(EntityId.WEAK_BANDIT));
        this.add(new TileComponent(TileFactory.weakBandit.glyph, TileFactory.weakBandit.color));
        this.add(banditPosition);
        this.add(banditStats);
        this.add(new AiComponent<>(new NaivePlayerLandAttackAi<>(this)));
        this.add(new ComputerControlledComponent());
        this.add(new CanBeAttackedComponent());
        this.add(new LootableComponent(banditLootTable));
        this.add(new ExamineComponent(
                "Weak Bandit",
                "Weak Bandits",
                "A weak but angry bandit."
        ));
    }
}
