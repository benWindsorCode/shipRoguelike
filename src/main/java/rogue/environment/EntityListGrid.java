package rogue.environment;

import com.badlogic.ashley.core.Entity;

import java.util.*;

public class EntityListGrid {
    // TODO: Had i know about Object.hash to sort Coordinate obj hashing, I would have done Map<Coordinate, List<Entity>>
    // each x,y coord has a list of entities
    private final Map<Integer, Map<Integer, List<Entity>>> entityGrid;
    private final int width;
    private final int height;

    public EntityListGrid(int width, int height) {
        this.width = width;
        this.height = height;

        // create and populate grid
        entityGrid = new HashMap<>();
        for(int x = 0; x < width; x++) {
            Map<Integer, List<Entity>> innerMap = new HashMap<>();
            entityGrid.put(x, innerMap);
            for(int y = 0; y < height; y++) {
                List<Entity> innerList = new ArrayList<>();
                innerMap.put(y, innerList);
            }
        }
    }

    public void moveEntity(int oldX, int oldY, int newX, int newY, Entity e) {
        List<Entity> oldEntities = getEntities(oldX, oldY);
        List<Entity> newEntities = getEntities(newX, newY);

        oldEntities.remove(e);
        newEntities.add(e);
    }

    public void removeEntity(int x, int y, Entity e) {
        List<Entity> entities = getEntities(x, y);
        entities.remove(e);
    }

    public void addEntity(int x, int y, Entity e) {
        List<Entity> innerList = getEntities(x, y);
        innerList.add(e);
    }

    public List<Entity> getEntities(int x, int y) {
        Map<Integer, List<Entity>> innerMap = entityGrid.get(x);
        return innerMap.get(y);
    }

    public List<Entity> surroundingEntities(int x, int y) {
        List<Integer> deviations = Arrays.asList(0, -1, +1);
        List<Coordinate> pairsToCheck = new ArrayList<>();
        for(Integer xDev: deviations) {
            for(Integer yDev: deviations) {
                // dont count current cell
                if(xDev == 0 && yDev == 0)
                    continue;

                int newX = x + xDev;
                int newY = y + yDev;

                if(newX >= 0 && newX < width && newY >= 0 && newY < height) {
                    pairsToCheck.add(new Coordinate(newX, newY));
                }
            }
        }

        List<Entity> surroundingEntities = new ArrayList<>();
        for(Coordinate coord: pairsToCheck) {
            surroundingEntities.addAll(getEntities(coord.getX(), coord.getY()));
        }

        return surroundingEntities;
    }
}
