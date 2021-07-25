package rogue.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import rogue.factories.MapperFactory;
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

    public Map<TileComponent, Integer> itemCounts() {
        List<TileComponent> tiles = this.inventory.stream()
                .map(MapperFactory.tileComponent::get)
                .collect(Collectors.toList());

        Set<TileComponent> tileSet = new HashSet<>(tiles);
        Map<TileComponent, Integer> counts = new HashMap<>();
        for(TileComponent tile: tileSet) {
            int count = Collections.frequency(tiles, tile);
            counts.put(tile, count);
        }

        return counts;
    }

    public boolean isFull() {
        return maxSize == currentSize;
    }
}
