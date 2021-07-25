package rogue.environment.prefabs;

import com.badlogic.ashley.core.Entity;
import rogue.components.PositionComponent;
import rogue.environment.Coordinate;
import rogue.factories.MapperFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

// Class to represent a set of world tiles which can be placed as a block in the world
// coordinates of prefab start at (0,0) for top left, and are all relative to the prefabs top left
public class Prefab {
    private int width;
    private int height;
    private Map<Coordinate, Supplier<? extends Entity>> entitySuppliers;

    public Prefab(int width, int height) {
        entitySuppliers = new HashMap<>();
        this.width = width;
        this.height = height;
    }

    public Map<Coordinate, Supplier<? extends Entity>> getEntitySuppliers() {
        return entitySuppliers;
    }

    public void add(int x, int y, Supplier<? extends Entity> entitySupplier) {
        if(x > width - 1 || y > height - 1 || x < 0 || y < 0) {
            throw new RuntimeException("Prefab creation outside its bounds.");
        }

        Coordinate coordinate = new Coordinate(x, y);
        entitySuppliers.put(coordinate, entitySupplier);
    }

    public Map<Coordinate, Entity> generateEntities(int topLeftX, int topLeftY) {
        Map<Coordinate, Entity> generatedEntities = new HashMap<>();

        for(Map.Entry<Coordinate, Supplier<? extends Entity>> entry: entitySuppliers.entrySet()) {
            Coordinate relativeCoord = entry.getKey();
            Coordinate worldCoord = new Coordinate(topLeftX + relativeCoord.getX(), topLeftY + relativeCoord.getY());
            Entity entity = entry.getValue().get();
            PositionComponent pos = MapperFactory.positionComponent.get(entity);
            pos.x = worldCoord.getX();
            pos.y = worldCoord.getY();

            generatedEntities.put(worldCoord, entity);
        }

        return generatedEntities;
    }
}
