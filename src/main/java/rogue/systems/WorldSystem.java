package rogue.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.utils.ImmutableArray;
import rogue.components.PositionComponent;
import rogue.components.RenderableComponent;
import rogue.components.TileComponent;
import rogue.components.ship.PlayerOnboardComponent;
import rogue.components.world.SpawnPortalComponent;
import rogue.entities.Portal;
import rogue.entities.World;
import rogue.entities.WorldTile;
import rogue.environment.Coordinate;
import rogue.environment.EntityListGrid;
import rogue.environment.WorldGrid;
import rogue.factories.FamilyFactory;
import rogue.factories.MapperFactory;
import rogue.factories.TileFactory;
import rogue.render.RenderGrid;
import rogue.util.EntityUtil;
import rogue.util.TileUtil;

public class WorldSystem extends EntitySystem {
    private ImmutableArray<Entity> renderableEntities;
    private ImmutableArray<Entity> renderableNonWorldEntities;
    private ImmutableArray<Entity> playerControlledEntities;
    private ImmutableArray<Entity> portalsToSpawn;
    private ImmutableArray<Entity> nonWorldEntities;
    private ImmutableArray<Entity> ai;

    // World entity singleton which can have universal events dropped on it
    private final World worldEntity;

    // keeps all base world tiles, e.g. ground, sea
    private WorldGrid worldGrid;

    // keeps grid of all tiles player sees
    private RenderGrid renderGrid;

    // keeps grid of all non world tile entities at each location
    private EntityListGrid entityListGrid;

    private final int width;
    private final int height;

    public WorldSystem(final WorldGrid worldGrid, final int width, final int height, final World worldEntity) {
        super();
        System.out.println("Starting initialisation of WorldSystem");
        this.width = width;
        this.height = height;
        this.worldGrid = worldGrid;
        this.renderGrid = new RenderGrid(worldGrid.getWorldTiles());
        this.worldEntity = worldEntity;
        // cant populate yet as the nonWorldEntities list is null
        // populateEntityListGrid();

        System.out.println("Finished initialisation of WorldSystem");
    }

    public void addedToEngine(Engine engine) {
        renderableEntities = engine.getEntitiesFor(FamilyFactory.renderable);
        playerControlledEntities = engine.getEntitiesFor(FamilyFactory.playerControlled);
        renderableNonWorldEntities = engine.getEntitiesFor(FamilyFactory.renderableNonWorld);
        nonWorldEntities = engine.getEntitiesFor(FamilyFactory.nonWorld);
        portalsToSpawn = engine.getEntitiesFor(FamilyFactory.spawnPortal);
        ai = engine.getEntitiesFor(FamilyFactory.ai);
    }

    public void update(float deltaTime) {
        // TODO: inefficient :( perhaps better to keep track on every movement
        populateEntityListGrid();

        // wipe tiles
        RenderGrid renderGrid = new RenderGrid(worldGrid.getWorldTiles());

        // TODO: deal with multiple entities at the same spot
        for(Entity e: renderableNonWorldEntities) {
            PositionComponent position = MapperFactory.positionComponent.get(e);
            RenderableComponent renderable = MapperFactory.renderableComponent.get(e);

            if(renderable.visible) {
                renderGrid.set(position.x, position.y, e);
            }
        }

        // always force AI entities to go on top of others
        for(Entity e: ai) {
            PositionComponent position = MapperFactory.positionComponent.get(e);
            RenderableComponent renderable = MapperFactory.renderableComponent.get(e);

            if(renderable.visible) {
                renderGrid.set(position.x, position.y, e);
            }
        }

        // always redo player placing on render grid to ensure always comes on top
        int playerX = getPlayerX();
        int playerY = getPlayerY();
        renderGrid.set(playerX, playerY, getPlayerEntity());

        this.renderGrid = renderGrid;

        portalsToSpawn.forEach(this::spawnPortal);
    }

    // TODO: this is horribly inefficient, cant we keep track as things move?
    public void populateEntityListGrid() {
        this.entityListGrid = new EntityListGrid(width, height);
        for(Entity e: nonWorldEntities) {
            PositionComponent pos = MapperFactory.positionComponent.get(e);
            entityListGrid.addEntity(pos.x, pos.y, e);
        }
    }

    public RenderGrid getRenderGrid() {
        return renderGrid;
    }

    public WorldGrid getWorldGrid() { return worldGrid; }

    public EntityListGrid getEntityListGrid() { return entityListGrid; }

    public Coordinate addAtEmptySeaLocation(Entity entity) {
        int x;
        int y;

        do {
            x = (int)(Math.random() * width);
            y = (int)(Math.random() * height);
        } while (!TileUtil.isSea(worldGrid.getTileComponents()[x][y]));
        // TODO: dont use world grid here, as the ships aren't appended on

        PositionComponent pos = MapperFactory.positionComponent.get(entity);
        pos.x = x;
        pos.y = y;

        getEngine().addEntity(entity);

        return new Coordinate(x, y);
    }

    // TODO: make more generic
    public Coordinate addAtEmptyLandLocation(Entity entity) {
        int x;
        int y;

        do {
            x = (int)(Math.random() * width);
            y = (int)(Math.random() * height);
        } while (!TileUtil.isLand(worldGrid.getTileComponents()[x][y]));
        // TODO: dont use world grid here, as the ships aren't appended on

        PositionComponent pos = MapperFactory.positionComponent.get(entity);
        pos.x = x;
        pos.y = y;

        getEngine().addEntity(entity);

        return new Coordinate(x, y);
    }

    // TODO: should be managed by a SpawnSystem?
    public void spawnPortal(Entity e) {
        System.out.println("Spawning portal");
        Portal portal = new Portal(0, 0);
        addAtEmptySeaLocation(portal);
        e.remove(SpawnPortalComponent.class);
    }

    public int getPlayerX() {
        if(playerControlledEntities.size() == 0)
            return 0;

        Entity player = playerControlledEntities.get(0);
        PositionComponent pos = MapperFactory.positionComponent.get(player);

        return pos.x;
    }

    public int getPlayerY() {
        if(playerControlledEntities.size() == 0)
            return 0;

        Entity player = playerControlledEntities.get(0);
        PositionComponent pos = MapperFactory.positionComponent.get(player);

        return pos.y;
    }

    public Entity getPlayerEntity() {
        if(playerControlledEntities.size() == 0) {
            throw new RuntimeException("No player controlled entities");
        } else if(playerControlledEntities.size() > 1) {
            throw new RuntimeException("Not allowed more than 1 player controlled entity");
        }

        return playerControlledEntities.get(0);
    }

    public Entity getPlayerCharacterEntity() {
        Entity playerEntity = getPlayerEntity();

        if(EntityUtil.isPlayerCharacter(playerEntity)) {
            return playerEntity;
        } else if(EntityUtil.isShip(playerEntity)) {
            PlayerOnboardComponent playerOnboardComponent = MapperFactory.playerOnboardComponent.get(playerEntity);
            return playerOnboardComponent.player;
        }

        throw new RuntimeException("Cannot locate player character");
    }

    public boolean playerAlive() {
        return playerControlledEntities.size() != 0;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public World getWorldEntity() { return worldEntity; }
}
