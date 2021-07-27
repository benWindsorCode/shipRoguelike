package rogue.environment;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.noise.model.Plane;
import org.spongepowered.noise.module.source.Perlin;
import rogue.components.PositionComponent;
import rogue.entities.*;
import rogue.entities.resources.AshTree;
import rogue.entities.resources.Rock;
import rogue.entities.resources.OakTree;
import rogue.entities.resources.WheatPlant;
import rogue.entities.world.*;
import rogue.environment.prefabs.Prefab;
import rogue.factories.MapperFactory;
import rogue.factories.TileFactory;
import rogue.systems.WorldSystem;
import rogue.util.EntityUtil;
import rogue.util.RandomUtil;

import java.util.*;
import java.util.function.Supplier;

public class WorldBuilder {
    private final Entity[][] tiles;
    private final List<Entity> entities;
    private final int width;
    private final int height;
    private final Map<Supplier<Entity>, Integer> entitiesForEmptyLandLocation;
    private final Map<Supplier<Entity>, Integer> entitiesForEmptySeaLocation;
    private final Engine engine;
    private boolean withVoronoiBiomes;
    private int voronoiCells;
    private final World worldEntity;
    private final static Logger logger = LogManager.getLogger(WorldSystem.class);

    public WorldBuilder(Engine engine, int width, int height, World worldEntity) {
        this.engine = engine;
        this.width = width;
        this.height = height;
        this.entitiesForEmptyLandLocation = new HashMap<>();
        this.entitiesForEmptySeaLocation = new HashMap<>();
        this.withVoronoiBiomes = false;
        this.voronoiCells = 0;
        this.worldEntity = worldEntity;

        // tiles to form the background WorldGrid
        tiles = new Entity[width][height];

        // entities to live on top of the WorldGrid
        entities = new ArrayList<>();
    }

    public void build() {
        Entity[][] worldTiles = new Entity[width][height];
        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                Entity tile = tiles[x][y];

                worldTiles[x][y] = tile;

                engine.addEntity(tile);
            }
        }

        WorldGrid worldGrid = new WorldGrid(worldTiles);

        WorldSystem worldSystem = new WorldSystem(worldGrid, width, height, worldEntity);
        engine.addSystem(worldSystem);

        // Now add entities
        for(Entity e: entities)
            engine.addEntity(e);

        for(Map.Entry<Supplier<Entity>, Integer> entityToSpawn: entitiesForEmptyLandLocation.entrySet()) {
            for(int i = 0; i < entityToSpawn.getValue(); i++) {
                Entity newEntity = entityToSpawn.getKey().get();
                worldSystem.addAtEmptyLandLocation(newEntity);
            }
        }

        for(Map.Entry<Supplier<Entity>, Integer> entityToSpawn: entitiesForEmptySeaLocation.entrySet()) {
            for(int i = 0; i < entityToSpawn.getValue(); i++) {
                Entity newEntity = entityToSpawn.getKey().get();
                worldSystem.addAtEmptySeaLocation(newEntity);
            }
        }

        if(withVoronoiBiomes)
            addVoronoiBiomes();
    }

    public WorldBuilder addEntityAtEmptyLandLocation(Supplier<Entity> entitySupplier, int numEntities) {
        entitiesForEmptyLandLocation.put(entitySupplier, numEntities);
        return this;
    }

    public WorldBuilder addEntityAtEmptySeaLocation(Supplier<Entity> entitySupplier, int numEntities) {
        entitiesForEmptySeaLocation.put(entitySupplier, numEntities);
        return this;
    }

    public WorldBuilder buildIslands(int numIslands) {
        // Fill with sea
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                tiles[x][y] = new WorldSea(x, y);
            }
        }

        List<Integer> heights = Arrays.asList(10, 12, 15, 25, 50);
        List<Integer> widths = Arrays.asList(10, 12, 15, 25, 50);

        for (int i = 0; i < numIslands; i++) {
            int islandHeight = RandomUtil.getRandom(heights);
            int islandWidth = RandomUtil.getRandom(widths);

            int x = RandomUtil.getRandomInt(0, width - 1);
            int y = RandomUtil.getRandomInt(0, height - 1);

            boolean withTrees = Math.random() > 0.2;
            boolean withRocks = Math.random() > 0.2;
            addSquareIsland(x, y, islandHeight, islandWidth, withTrees, withRocks);
        }

        return this;
    }

    public WorldBuilder buildNoiseIslands() {
        logger.info("Building from noise");
        Perlin perlin = new Perlin();
        perlin.setOctaveCount(6);
        perlin.setFrequency(4);
        Plane noisePlane = new Plane(perlin);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                double noiseX = x/(double)width;
                double noiseY = y/(double)height;
                double noise = noisePlane.getValue(noiseX, noiseY);
                if(noise > 1.27) {
                    tiles[x][y] = new WorldHighMountain(x, y);
                }else if(noise > 1.25) {
                    tiles[x][y] = new WorldMountain(x, y);
                }else if(noise > 1.0) {
                    tiles[x][y] = new WorldGrass(x, y);
                } else if( noise <= 1.0 && noise > 0.95) {
                    tiles[x][y] = new WorldShallowSea(x, y);
                } else if(noise <= 0.95 && noise > 0.8) {
                    tiles[x][y] = new WorldSea(x, y);
                } else {
                    tiles[x][y] = new WorldDeepSea(x, y);
                }
            }
        }

        logger.info("Finished building from noise");
        return this;
    }

    // TODO: Generation ideas
    //          - cellular atutomata, perhaps a runAutomataOn(Tree.class, Rules, iterations) function
    //          - noise for entities accross land

    // Given a function () -> new Entity, spread across land tiles
    public WorldBuilder addUniformlyToLand(Supplier<Entity> entitySupplier) {
        double density = RandomUtil.getRandomDouble(0.9, 1.0);

        for (int newX = 0; newX < width; newX++) {
            for (int newY = 0; newY < height; newY++) {
                if(EntityUtil.isLand(tiles[newX][newY]) && Math.random() > density) {
                    Entity entity = entitySupplier.get();
                    PositionComponent pos = MapperFactory.positionComponent.get(entity);
                    pos.x = newX;
                    pos.y = newY;
                    entities.add(entity);
                }
            }
        }

        return this;
    }

    public WorldBuilder addPrefabUniformlyToLand(Prefab prefab, int prefabCount) {

        for(int i = 0; i < prefabCount; i++) {
            int landTopLeftX;
            int landTopLeftY;

            do {
                landTopLeftX = (int)(Math.random() * width);
                landTopLeftY = (int)(Math.random() * height);
            } while (!EntityUtil.isLand(tiles[landTopLeftX][landTopLeftY]));

            // Add prefab to the world, only where it still continues to be on land
            Map<Coordinate, Entity> prefabEntities = prefab.generateEntities(landTopLeftX, landTopLeftY);
            for(Map.Entry<Coordinate, Entity> entry: prefabEntities.entrySet()) {
                Coordinate prefabCoord = entry.getKey();

                if (prefabCoord.getX() >= width || prefabCoord.getY() >= height)
                    continue;

                Entity worldTile = tiles[prefabCoord.getX()][prefabCoord.getY()];
                if(EntityUtil.isSea(worldTile) || EntityUtil.isMountain(worldTile))
                    continue;

                entities.add(entry.getValue());
            }
        }

        return this;
    }

    // Adds island centered at x, y (for now just a square
    private void addSquareIsland(int x, int y, int islandHeight, int islandWidth, boolean withTrees, boolean withRocks) {
        int x_diff = islandWidth / 2;
        int y_diff = islandHeight / 2;
        int top_x = Math.max(0, Math.min(x - x_diff, width - 1));
        int bottom_x = Math.max(0, Math.min(x + x_diff, width - 1));
        int top_y = Math.max(0, Math.min(y - y_diff, height - 1));
        int bottom_y = Math.max(0, Math.min(y + y_diff, height - 1));

        fillSquare(top_x, top_y, bottom_x, bottom_y, () -> new WorldGrass(0, 0));

        double treeDensity = RandomUtil.getRandomDouble(0.9, 1.0);
        if(withTrees) {
            for (int tree_x = top_x; tree_x < bottom_x; tree_x++) {
                for (int tree_y = top_y; tree_y < bottom_y; tree_y++) {
                    if(Math.random() > treeDensity)
                        entities.add(new OakTree(tree_x, tree_y));
                }
            }
        }

        // TODO: fix it so cant have trees and rocks on same square
        double rockDensity = RandomUtil.getRandomDouble(0.9, 1.0);
        if(withRocks) {
            for (int tree_x = top_x; tree_x < bottom_x; tree_x++) {
                for (int tree_y = top_y; tree_y < bottom_y; tree_y++) {
                    if(Math.random() > rockDensity)
                        entities.add(new Rock(tree_x, tree_y));
                }
            }
        }
    }

    private void fillSquare(int top_x, int top_y, int bottom_x, int bottom_y, final Supplier<Entity> tileSupplier) {
        for (int x = top_x; x < bottom_x; x++) {
            for (int y = top_y; y < bottom_y; y++) {
                Entity tile = tileSupplier.get();
                PositionComponent pos = MapperFactory.positionComponent.get(tile);
                pos.x = x;
                pos.y = y;
                tiles[x][y] = tile;
            }
        }
    }

    private List<Coordinate> generateVoronoiCells(int numCells) {
        List<Coordinate> cells = new ArrayList<>();
        Random rand = new Random();
        for(int i = 0; i < numCells; i++) {
            int x = rand.nextInt(width);
            int y = rand.nextInt(height);
            cells.add(new Coordinate(x, y));
        }

        return cells;
    }

    private void addVoronoiBiomes() {
        int[][] voronoiGrid = generateVoronoiGrid(voronoiCells);
        Map<Integer, VoronoiPoint> voronoiMap = generateVoronoiMap(voronoiCells);
        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                Entity worldTile = tiles[x][y];
                int voronoiCell = voronoiGrid[x][y];
                if(EntityUtil.isLand(worldTile)) {
                    VoronoiPoint voronoiPoint = voronoiMap.get(voronoiCell);
                    List<Entity> entities = voronoiPoint.generateEntities();
                    int finalX = x;
                    int finalY = y;

                    if(entities.size() == 0)
                        continue;

                    // pick one of the entities to add at this pos
                    Entity entityToPlace = RandomUtil.getRandom(entities);
                    PositionComponent pos = MapperFactory.positionComponent.get(entityToPlace);
                    pos.x = finalX;
                    pos.y = finalY;
                    engine.addEntity(entityToPlace);
                }
            }
        }
    }

    // return grid of which voronoi cell each coord is in
    // credit to https://rosettacode.org/wiki/Voronoi_diagram#Java
    public int[][] generateVoronoiGrid(int numCells) {
        List<Coordinate> voronoiCells = generateVoronoiCells(numCells);

        int[][] voronoiGrid = new int[width][height];
        int n = 0;
        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                n = 0;
                for(byte i = 0; i < numCells; i++) {
                    Coordinate cell = voronoiCells.get(i);
                    Coordinate point = new Coordinate(x, y);
                    Coordinate cellN =  voronoiCells.get(n);
                    if(distance(cell, point) < distance(cellN, point)) {
                        n = i;
                    }
                }
                voronoiGrid[x][y] = n;
            }
        }

        return voronoiGrid;
    }

    public WorldBuilder addVoronoiBiomesGeneration(int voronoiCells) {
        withVoronoiBiomes = true;
        this.voronoiCells = voronoiCells;

        return this;
    }

    private Map<Integer, VoronoiPoint> generateVoronoiMap(int numCells) {
        Map<Integer, VoronoiPoint> voronoiPointMap = new HashMap<>();
        for(int i = 0; i < numCells; i++) {
            voronoiPointMap.put(i, generateVoronoiPoint());
        }

        return voronoiPointMap;
    }

    // TODO: allow specifying entities in the world builder function params
    private VoronoiPoint generateVoronoiPoint() {
        Supplier<Entity> oakTreeSupplier = OakTree::new;
        Supplier<Entity> rockSupplier = Rock::new;
        Supplier<Entity> ashTreeSupplier = AshTree::new;
        Supplier<Entity> wheatPlantSupplier = WheatPlant::new;

        // TODO: use gaussian here instead?
        // TODO: Perhaps first coin toss to pick 'dense' or 'sparse' then pick density out of different ranges depending
        Random r = new Random();
        VoronoiPoint point = new VoronoiPoint();

        // TODO: can we get oak and ash densities to be dependant, e.g. more tree types shouldn't make it crazy dense
        //       perhaps we need a ran pick of 'oak' 'ash' or 'both' per point
        //       and when its 'both' then a density is picked and split between the two trees

        // Note: hack by pushing dist negative, but only picking probabilities from [0,1] when generating
        //       then we put more weight on empty islands
        double oakTreeDensity = RandomUtil.getRandomDouble(-0.2, 0.3);
        double ashTreeDensity = RandomUtil.getRandomDouble(-0.2, 0.3);
        double rockDensity = RandomUtil.getRandomDouble(-0.1, 0.2);
        double wheatPlantDensity = RandomUtil.getRandomDouble(-0.5, 0.1);

        point.add(oakTreeSupplier, oakTreeDensity);
        point.add(ashTreeSupplier, ashTreeDensity);
        point.add(rockSupplier, rockDensity);
        point.add(wheatPlantSupplier, wheatPlantDensity);

        return point;
    }

    // Euclidean distance
    private double distance(Coordinate point1, Coordinate point2) {
        return Math.sqrt(
            (point1.getX() - point2.getX())*(point1.getX() - point2.getX())
            + (point1.getY() - point2.getY())*(point1.getY() - point2.getY())
        );
    }
}
