package rogue.environment;

import com.badlogic.ashley.core.Entity;

import java.util.*;
import java.util.function.Supplier;

// A point in a voronoi diagram, representing a set of entities and how likely they are to be spawned there
public class VoronoiPoint {
    Map<Supplier<Entity>, Double> entityRates;

    public VoronoiPoint() {
        this.entityRates = new HashMap<>();
    }

    public VoronoiPoint(Map<Supplier<Entity>, Double> entityRates) {
        this.entityRates = entityRates;
    }

    public void add(Supplier<Entity> entitySupplier, Double prob) {
        entityRates.put(entitySupplier, prob);
    }

    public Map<Supplier<Entity>, Double> getEntityRates() {
        return entityRates;
    }

    public List<Entity> generateEntities() {
        List<Entity> entities = new ArrayList<>();
        Random r = new Random();
        for(Map.Entry<Supplier<Entity>, Double> entry: entityRates.entrySet()) {
            double roll = r.nextDouble();
            if(roll < entry.getValue()) {
                entities.add(entry.getKey().get());
            }
        }

        return entities;
    }
}
