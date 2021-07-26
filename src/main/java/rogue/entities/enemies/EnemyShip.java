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

public class EnemyShip extends Entity {
    public EnemyShip() {
        this(0, 0);
    }

    public EnemyShip(int x, int y) {
        super();

        PositionComponent shipPosition = new PositionComponent(x, y);
        LootTable shipLootTable = new LootTable();
        shipLootTable.addLoot(new LootTableEntry(Iron::new, 1.0, 2));

        this.add(new RenderableComponent());
        this.add(new IdComponent(EntityId.ENEMY_SHIP));
        this.add(new TileComponent(TileFactory.enemyShip.glyph, TileFactory.enemyShip.color));
        this.add(shipPosition);
        this.add(new HealthComponent(7));
        //this.add(new AiComponent<>(new WanderingSeaAi<EnemyShip>(this)));
        this.add(new AiComponent<>(new NaivePlayerSeaAttackAi<>(this)));
        this.add(new ComputerControlledComponent());
        this.add(new StrengthComponent(3));
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
