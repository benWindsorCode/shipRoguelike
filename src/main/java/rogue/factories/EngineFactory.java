package rogue.factories;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import rogue.entities.World;
import rogue.systems.*;

public class EngineFactory {
    public static Engine makeEngine() {
        Engine engine = new Engine();

        MovementSystem movementSystem = new MovementSystem(0);
        engine.addSystem(movementSystem);

        HarvestSystem harvestSystem = new HarvestSystem();
        engine.addSystem(harvestSystem);

        InventorySystem inventorySystem = new InventorySystem();
        engine.addSystem(inventorySystem);

        CombatSystem combatSystem = new CombatSystem();
        engine.addSystem(combatSystem);

        UpgradeSystem upgradeSystem = new UpgradeSystem();
        engine.addSystem(upgradeSystem);

        CraftingSystem craftingSystem = new CraftingSystem();
        engine.addSystem(craftingSystem);

        SpawnSystem spawnSystem = new SpawnSystem();
        engine.addSystem(spawnSystem);

        NaivePlayerAiAttackSystem naivePlayerAiAttackSystem = new NaivePlayerAiAttackSystem();
        engine.addSystem(naivePlayerAiAttackSystem);

        ItemSystem itemSystem = new ItemSystem();
        engine.addSystem(itemSystem);

        HungerSystem hungerSystem = new HungerSystem();
        engine.addSystem(hungerSystem);

        return engine;
    }
}
