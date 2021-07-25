package rogue.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.utils.ImmutableArray;
import rogue.components.*;
import rogue.components.actions.AttackActionComponent;
import rogue.components.actions.DropActionComponent;
import rogue.components.actions.HealActionComponent;
import rogue.components.actions.InventoryAddActionComponent;
import rogue.components.traits.CanBeAttackedComponent;
import rogue.components.world.SpawnLootComponent;
import rogue.factories.FamilyFactory;
import rogue.factories.MapperFactory;
import rogue.util.EntityUtil;
import rogue.util.TileUtil;

import java.util.List;
import java.util.Map;

public class CombatSystem extends EntitySystem {
    private ImmutableArray<Entity> entitiesAttacking;
    private ImmutableArray<Entity> entitiesHealing;

    public void addedToEngine(Engine engine) {
        entitiesAttacking = engine.getEntitiesFor(FamilyFactory.attacking);
        entitiesHealing = engine.getEntitiesFor(FamilyFactory.healing);
    }

    public void update(float deltaTime) {
        entitiesHealing.forEach(this::processHealing);
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
            System.out.println("Cant attack this entity");
            return;
        }

        System.out.println("Attacking");
        HealthComponent attackedHealth = MapperFactory.healthComponent.get(attackedEntity);

        StrengthComponent entityStrength = MapperFactory.strengthComponent.get(e);
        int remainingHealth = Math.max(0, attackedHealth.hitpoints - entityStrength.strength);

        System.out.println(String.format(
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
            System.out.println("Killed entity");
            LootableComponent lootableComponent = MapperFactory.lootableComponent.get(attackedEntity);

            if(lootableComponent != null) {
                WorldSystem worldSystem = getEngine().getSystem(WorldSystem.class);
                Entity worldEntityUnderEnemy = worldSystem.getWorldGrid().get(attackedEntityPos.x, attackedEntityPos.y);
                if(EntityUtil.isLand(worldEntityUnderEnemy)) {
                    e.add(new SpawnLootComponent(lootableComponent.lootTable, new PositionComponent(attackedEntityPos.x, attackedEntityPos.y)));
                } else {
                    // If loot going straight to inventory, then first register it with engine
                    List<Entity> lootList = lootableComponent.lootTable.dropLoot();
                    lootList.forEach(lootItem -> getEngine().addEntity(lootItem));
                    e.add(new InventoryAddActionComponent(lootList));
                }
            }

            getEngine().removeEntity(attackedEntity);
        }
    }

    private void processHealing(Entity e) {
        HealActionComponent healActionComponent = MapperFactory.healActionComponent.get(e);
        HealthComponent healthComponent = MapperFactory.healthComponent.get(e);

        healthComponent.hitpoints = Math.min(
                healthComponent.maxHitpoints,
                healthComponent.hitpoints + healActionComponent.pointsToHeal
        );

        e.remove(HealActionComponent.class);
    }
}
