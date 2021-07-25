package rogue.environment;

import com.badlogic.ashley.core.Entity;
import rogue.components.TileComponent;
import rogue.entities.WorldTile;
import rogue.factories.MapperFactory;

public class WorldGrid {
    private WorldTile[][] worldTiles;
    private TileComponent[][] tileComponents;

    public WorldGrid(WorldTile[][] tiles) {
        this.worldTiles = tiles;

        int height = tiles[0].length;
        int width = tiles.length;
        System.out.println(String.format("Created WorldGrid with: height %d, width %d", height, width));
        this.tileComponents = new TileComponent[width][height];

        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                TileComponent tileComponent = MapperFactory.tileComponent.get(worldTiles[x][y]);
                this.tileComponents[x][y] = tileComponent;
            }
        }
    }

    public WorldTile get(int x, int y) {
        return worldTiles[x][y];
    }

    public WorldTile[][] getWorldTiles() {
        return this.worldTiles;
    }

    public TileComponent[][] getTileComponents() {
        return this.tileComponents;
    }

    public TileComponent getTileComponent(int x, int y) {
        return this.tileComponents[x][y];
    }
}