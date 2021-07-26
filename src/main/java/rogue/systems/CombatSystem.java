package rogue.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.utils.ImmutableArray;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import rogue.components.*;
import rogue.components.actions.AttackActionComponent;
import rogue.components.actions.HealthActionComponent;
import rogue.components.actions.InventoryAddActionComponent;
import rogue.components.traits.CanBeAttackedComponent;
import rogue.components.world.SpawnLootComponent;
import rogue.factories.FamilyFactory;
import rogue.factories.MapperFactory;
import rogue.util.EntityUtil;

import java.util.List;

public class CombatSystem extends EntitySystem {
    private ImmutableArray<Entity> entitiesAttacking;
    private ImmutableArray<Entity> healthUpdates;
    private final static Logger logger = LogManager.getLogger(CombatSystem.class);

    public void addedToEngine(Engine engine) {
        entitiesAttacking = engine.getEntitiesFor(FamilyFactory.attacking);
        healthUpdates = engine.getEntitiesFor(FamilyFactory.healthUpdates);
    }

    public void update(float deltaTime) {
        healthUpdates.forEach(this::processHealthUpdate);

        if(deltaTime >= 1)
            entitiesAttacking.forEach(this::processAttack);
    }

    private void processAttack(Entity e) {
        ExamineComponent attackingExamineComponent = MapperFactory.examineComponent.get(e);
        Entity attackedEntity = MapperFactory.attackComponent.get(e).other;
        ExamineComponent attackedExamineComponent = MapperFactory.examineComponent.get(attackedEntity);

        CanBeAttackedComponent canBeAttackedComponent = MapperFactory.canAttackComponent.get(attackedEntity);
        PositionComponent attackedEntityPos = MapperFactory.positionComponent.get(attackedEntity);
        e.remove(AttackActionComponent.class);

        if(canBeAttackedComponent == null) {
            logger.warn("Cant attack this entity");
            return;
        }

        HealthComponent attackedHealth = MapperFactory.healthComponent.get(attackedEntity);

        StrengthComponent entityStrength = MapperFactory.strengthComponent.get(e);
        int remainingHealth = Math.max(0, attackedHealth.hitpoints - entityStrength.strength);

        logger.info(String.format(
                "%s Attacked opponent %s. %d -> %d (strength %d)",
                attackingExamineComponent.name,
                attackedExamineComponent.name,
                attackedHealth.hitpoints,
                remainingHealth,
                entityStrength.strength
        ));
        attackedHealth.hitpoints = remainingHealth;

        // TODO: in combat on sea which inventory to drop loot into? ship or player?
        if(attackedHealth.hitpoints <= 0) {
            logger.info(String.format("%s Killed entity %s", attackingExamineComponent.name, attackedExamineComponent.name));
            LootableComponent lootableComponent = MapperFactory.lootableComponent.get(attackedEntity);


            if(lootableComponent != null) {
                WorldSystem worldSystem = getEngine().getSystem(WorldSystem.class);
                Entity worldEntityUnderEnemy = worldSystem.getWorldGrid().get(attackedEntityPos.x, attackedEntityPos.y);
                if(EntityUtil.isLand(worldEntityUnderEnemy)) {
                    e.add(new SpawnLootComponent(lootableComponent.lootTable, new PositionComponent(attackedEntityPos.x, attackedEntityPos.y)));
                } else {
                    InventoryComponent inventoryComponent = MapperFactory.inventoryComponent.get(e);

                    if(inventoryComponent != null) {
                        // If loot going straight to inventory, then first register it with engine
                        List<Entity> lootList = lootableComponent.lootTable.dropLoot();
                        lootList.forEach(lootItem -> getEngine().addEntity(lootItem));
                        e.add(new InventoryAddActionComponent(lootList));
                    } else {
                        System.out.println("Looted at sea by entity with no inventory, so no loot dropping");
                    }
                }
            }

            getEngine().removeEntity(attackedEntity);
        }
    }

    private void processHealthUpdate(Entity e) {
        HealthActionComponent healthActionComponent = MapperFactory.healActionComponent.get(e);
        HealthComponent healthComponent = MapperFactory.healthComponent.get(e);

        healthComponent.hitpoints = Math.max( 0, Math.min(
                healthComponent.maxHitpoints,
                healthComponent.hitpoints + healthActionComponent.pointsDelta
        ) );

        if(healthComponent.hitpoints == 0) {
            getEngine().removeEntity(e);
        }

        e.remove(HealthActionComponent.class);
    }
}
