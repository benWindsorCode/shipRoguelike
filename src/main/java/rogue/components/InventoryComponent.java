package rogue.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import rogue.components.traits.IdComponent;
import rogue.factories.MapperFactory;
import rogue.util.EntityId;
import rogue.util.TileUtil;

import java.util.*;
import java.util.stream.Collectors;

public class InventoryComponent implements Component {
    public int maxSize;
    public int currentSize;
    public List<Entity> inventory;

    public InventoryComponent(int maxSize) {
        this.maxSize = maxSize;
        this.currentSize = 0;
        inventory = new ArrayList<>();
    }

    public boolean add(Entity entity) {
        if(maxSize == currentSize)
            return false;

        inventory.add(entity);
        currentSize++;
        return true;
    }

    public boolean remove(Entity entity) {
        boolean succeeded = inventory.remove(entity);
        if(!succeeded)
            return false;

        currentSize--;
        return true;
    }

    public Map<EntityId, Integer> itemCountsByEntityId() {
        // Get Entity Ids and reduce to a set
        List<EntityId> entityIds = getEntityIds();
        Set<EntityId> entityIdSet = new HashSet<>(entityIds);

        // Group the items by entity Id
        Map<EntityId, Integer> counts = new HashMap<>();
        for(EntityId entityId: entityIdSet) {
            int count = Collections.frequency(entityIds, entityId);
            counts.put(entityId, count);
        }

        return counts;
    }

    public Map<EntityId, List<Entity>> itemsByEntityId() {
        // Group the items by entity Id
        Map<EntityId, List<Entity>> itemsByEntityId = new HashMap<>();
        for (Entity item: inventory) {
            EntityId entityId = MapperFactory.idComponent.get(item).entityId;

            if(itemsByEntityId.containsKey(entityId)) {
                itemsByEntityId.get(entityId).add(item);
            } else {
                itemsByEntityId.put(entityId, new ArrayList<>());
                itemsByEntityId.get(entityId).add(item);
            }
        }

        return itemsByEntityId;
    }

    private List<EntityId> getEntityIds() {
        List<EntityId> entityIds = this.inventory.stream()
                .map(MapperFactory.idComponent::get)
                .map(idComponent -> idComponent.entityId)
                .collect(Collectors.toList());

        return entityIds;
    }
    public boolean isFull() {
        return maxSize == currentSize;
    }
}
