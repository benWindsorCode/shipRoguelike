package rogue.entities.animals;

import com.badlogic.ashley.core.Entity;
import rogue.ai.WanderingSeaAi;
import rogue.components.*;
import rogue.components.traits.CanBeAttackedComponent;
import rogue.components.traits.IdComponent;
import rogue.entities.resources.Gold;
import rogue.entities.resources.Iron;
import rogue.factories.TileFactory;
import rogue.loot.LootTable;
import rogue.loot.LootTableEntry;
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

        this.add(new RenderableComponent());
        this.add(new IdComponent(EntityId.CIVILIAN_SHIP));
        this.add(new TileComponent(TileFactory.civilianShip.glyph, TileFactory.civilianShip.color));
        this.add(shipPosition);
        this.add(new HealthComponent(5));
        this.add(new AiComponent<>(new WanderingSeaAi<>(this)));
        this.add(new StrengthComponent(0));
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
