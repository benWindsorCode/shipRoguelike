package rogue.entities.animals;

import com.badlogic.ashley.core.Entity;
import rogue.ai.WanderingLandAi;
import rogue.components.*;
import rogue.components.traits.CanBeAttackedComponent;
import rogue.components.traits.IdComponent;
import rogue.entities.food.RatMeat;
import rogue.factories.TileFactory;
import rogue.loot.LootTable;
import rogue.loot.LootTableEntry;
import rogue.util.EntityId;

public class Rat extends Entity {
    public Rat() {
        this(0, 0);
    }

    public Rat(int x, int y) {
        super();

        PositionComponent ratPosition = new PositionComponent(x, y);
        LootTable ratLootTable = new LootTable();
        ratLootTable.addLoot(new LootTableEntry(RatMeat::new, 1.0, 1));

        this.add(new RenderableComponent());
        this.add(new IdComponent(EntityId.RAT));
        this.add(new TileComponent(TileFactory.rat.glyph, TileFactory.rat.color));
        this.add(ratPosition);
        this.add(new HealthComponent(3));
        this.add(new AiComponent<>(new WanderingLandAi<Rat>(this)));
        this.add(new ComputerControlledComponent());
        this.add(new CanBeAttackedComponent());
        this.add(new LootableComponent(ratLootTable));
        this.add(new ExamineComponent(
                "Rat",
                "Rats",
                "A scraggly dirty rat"
        ));
    }
}
