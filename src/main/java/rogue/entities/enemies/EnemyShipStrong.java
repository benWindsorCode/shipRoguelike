package rogue.entities.enemies;

import com.badlogic.ashley.core.Entity;
import rogue.ai.NaivePlayerSeaAttackAi;
import rogue.components.*;
import rogue.components.traits.CanBeAttackedComponent;
import rogue.components.traits.IdComponent;
import rogue.entities.resources.Iron;
import rogue.factories.TileFactory;
import rogue.loot.LootTable;
import rogue.loot.LootTableEntry;
import rogue.util.EntityId;

public class EnemyShipStrong extends Entity {
    public EnemyShipStrong() {
        this(0, 0);
    }

    public EnemyShipStrong(int x, int y) {
        super();

        PositionComponent shipPosition = new PositionComponent(x, y);
        LootTable shipLootTable = new LootTable();
        shipLootTable.addLoot(new LootTableEntry(Iron::new, 1.0, 4));

        this.add(new RenderableComponent());
        this.add(new IdComponent(EntityId.ENEMY_SHIP_STRONG));
        this.add(new TileComponent(TileFactory.enemyShipStrong.glyph, TileFactory.enemyShipStrong.color));
        this.add(shipPosition);
        this.add(new HealthComponent(12));
//        this.add(new AiComponent<>(new WanderingSeaAi<EnemyShipStrong>(this)));
        this.add(new AiComponent<>(new NaivePlayerSeaAttackAi<>(this)));
        this.add(new ComputerControlledComponent());
        this.add(new StrengthComponent(6));
        this.add(new CanBeAttackedComponent());
        this.add(new LootableComponent(shipLootTable));
        this.add(new InventoryComponent(10));
        this.add(new ExamineComponent(
                "Strong Enemy Ship",
                "Strong Enemy Ships",
                "An aggressive looking enemy ship, stronger than the others"
        ));
    }
}
