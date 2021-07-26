package rogue.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.utils.ImmutableArray;
import rogue.components.actions.HungerActionComponent;
import rogue.components.hunger.HungerComponent;
import rogue.components.actions.HealthActionComponent;
import rogue.factories.FamilyFactory;
import rogue.factories.MapperFactory;

public class HungerSystem extends EntitySystem {
    private ImmutableArray<Entity> hungerUpdates;
    private ImmutableArray<Entity> hungerActions;

    public void addedToEngine(Engine engine) {
        hungerActions = engine.getEntitiesFor(FamilyFactory.hungerActions);
        hungerUpdates = engine.getEntitiesFor(FamilyFactory.hungerUpdates);
    }

    public void update(float deltaTime) {
        hungerActions.forEach(this::processHungerAction);

        if(deltaTime >= 1)
            hungerUpdates.forEach(this::updateHunger);
    }

    private void updateHunger(final Entity e) {
        HungerComponent hungerComponent = MapperFactory.hungerComponent.get(e);

        hungerComponent.turnsUntilIncrease -= 1;

        if(hungerComponent.maxHunger == hungerComponent.currentHunger) {
            e.add(new HealthActionComponent(-1));
            return;
        }

        if(hungerComponent.turnsUntilIncrease == 0) {
            hungerComponent.currentHunger = Math.min(hungerComponent.maxHunger, hungerComponent.currentHunger + hungerComponent.increaseAmount);
            hungerComponent.turnsUntilIncrease = hungerComponent.turnsPerIncrease;
        }
    }

    private void processHungerAction(final Entity e) {
        HungerActionComponent hungerActionComponent = MapperFactory.hungerActionComponent.get(e);
        HungerComponent hungerComponent = MapperFactory.hungerComponent.get(e);

        hungerComponent.currentHunger = Math.max(0, Math.min(hungerComponent.maxHunger, hungerComponent.currentHunger + hungerActionComponent.pointsDelta));

        e.remove(HungerActionComponent.class);
    }
}
