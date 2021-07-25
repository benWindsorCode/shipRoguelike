package rogue.render;

import com.badlogic.ashley.core.Entity;
import rogue.components.TileComponent;
import rogue.environment.Coordinate;
import rogue.factories.MapperFactory;
import rogue.factories.TileFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RenderGrid {
    private final Entity[][] renderGrid;
    private final int width;
    private final int height;

    public RenderGrid(int width, int height) {
        renderGrid = new Entity[width][height];
        this.width = width;
        this.height = height;
    }

    public RenderGrid(Entity[][] renderGrid) {
        this.height = renderGrid[0].length;
        this.width = renderGrid.length;

        this.renderGrid = new Entity[width][height];

        // Avoid ArrayStoreException, e.g. passing a WorldTile[][] will fail when then adding an EnemyShip
        for(int x = 0; x < width; x ++) {
            for(int y = 0; y < height; y++) {
                this.renderGrid[x][y] = renderGrid[x][y];
            }
        }
        //System.out.println(String.format("Created render grid: height %d, width %d", height, width));
    }

    public void set(int x, int y, Entity e) {
        renderGrid[x][y] = e;
    }

    // TODO: should this get and return a TileFactory.bounds entity if out of bounds?
    public Entity get(int x, int y) {
        return renderGrid[x][y];
    }

    public Entity[][] getRenderGrid() {
        return renderGrid;
    }

    public TileComponent[][] getTileComponents() {
        TileComponent[][] tiles = new TileComponent[width][height];

        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                TileComponent tile = MapperFactory.tileComponent.get(renderGrid[x][y]);
                tiles[x][y] = tile;
            }
        }

        return tiles;
    }

    public TileComponent getTileComponent(int x, int y) {
        TileComponent[][] tiles = getTileComponents();

        // TODO: is this the right place for this check?
        if(x < 0 || x >= width || y < 0 || y >= height)
            return new TileComponent(TileFactory.bounds.glyph, TileFactory.bounds.color);

        return tiles[x][y];
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    // Get surrounding grid up, down, left, right
    public List<Entity> getCardinalSurroundingEntities(int x, int y) {
        List<Coordinate> pairsToCheck = new ArrayList<>();
        List<Coordinate> deviations = Arrays.asList(
                new Coordinate(0, -1),
                new Coordinate(0, +1),
                new Coordinate(-1, 0),
                new Coordinate(+1, 0)
        );
        for(Coordinate deviation: deviations) {
            int newX = x + deviation.getX();
            int newY = y + deviation.getY();

            if(newX >= 0 && newX < width && newY >= 0 && newY < height) {
                pairsToCheck.add(new Coordinate(newX, newY));
            }
        }

        List<Entity> surroundingEntities = new ArrayList<>();
        for(Coordinate coord: pairsToCheck) {
            surroundingEntities.add(get(coord.getX(), coord.getY()));
        }

        return surroundingEntities;
    }
}
