package rogue.screens;

import asciiPanel.AsciiPanel;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import rogue.ai.CursorAi;
import rogue.components.*;
import rogue.components.actions.SendMessageComponent;
import rogue.components.hunger.HungerComponent;
import rogue.components.player.PlayerShipComponent;
import rogue.components.ship.PlayerOnboardComponent;
import rogue.components.actions.MovingComponent;
import rogue.components.traits.CanAddToInventoryComponent;
import rogue.components.traits.CanStoreItemsInComponent;
import rogue.entities.*;
import rogue.entities.animals.*;
import rogue.entities.enemies.EnemyShip;
import rogue.entities.enemies.EnemyShipStrong;
import rogue.entities.enemies.WeakBandit;
import rogue.environment.Coordinate;
import rogue.environment.EntityListGrid;
import rogue.environment.WorldBuilder;
import rogue.factories.EngineFactory;
import rogue.factories.MapperFactory;
import rogue.factories.PrefabFactory;
import rogue.factories.TileFactory;
import rogue.render.RenderGrid;
import rogue.systems.*;
import rogue.util.TileUtil;

import java.awt.event.KeyEvent;
import java.util.List;
import java.util.stream.Collectors;

public class PlayScreen implements Screen {

    private final Engine engine;
    private final WorldSystem worldSystem;
    private final int panelWidth;
    private final int panelHeight;
    private final int screenWidth;
    private final int screenHeight;
    private Screen subscreen;
    private final AsciiPanel terminal;

    public PlayScreen(AsciiPanel terminal) {
        this.terminal = terminal;
        this.panelWidth = terminal.getWidthInCharacters();
        this.panelHeight = terminal.getHeightInCharacters();
        screenWidth = panelWidth;
        screenHeight = panelHeight - 3; // leave a few lines at bottom for messages

        int worldWidth = 300;
        int worldHeight = 200;

        System.out.printf(
           "Creating PlayScreen. screenWidth: %d, screenHeight: %d, worldWidth: %d, worldHeight: %d\n",
            screenWidth,
            screenHeight,
            worldWidth,
            worldHeight
        );

        engine = EngineFactory.makeEngine();

        World worldEntity = new World();
        engine.addEntity(worldEntity);

        WorldBuilder worldBuilder = new WorldBuilder(engine, worldWidth, worldHeight, worldEntity);

        worldBuilder
                .buildNoiseIslands()
                .addVoronoiBiomesGeneration(20)
                .addEntityAtEmptySeaLocation(EnemyShip::new, 10)
                .addEntityAtEmptySeaLocation(EnemyShipStrong::new, 4)
                .addEntityAtEmptySeaLocation(CivilianShip::new, 5)
                .addEntityAtEmptyLandLocation(WeakBandit::new, 12)
                .addEntityAtEmptySeaLocation(SmallFish::new, 30)
                .addEntityAtEmptySeaLocation(BigFish::new, 20)
                .addEntityAtEmptyLandLocation(Fox::new, 8)
                .addEntityAtEmptyLandLocation(Rat::new, 12)
                .addPrefabUniformlyToLand(PrefabFactory.smallHouse(), 5)
                .addPrefabUniformlyToLand(PrefabFactory.bigHouse(), 3)
                .addPrefabUniformlyToLand(PrefabFactory.abandonedRails(), 5)
                .build();

        worldSystem = engine.getSystem(WorldSystem.class);

        PlayerShip ship = new PlayerShip(0, 0, true, true);
        Coordinate initialCoord = worldSystem.addAtEmptySeaLocation(ship);
        PlayerCharacter player = new PlayerCharacter(initialCoord.getX(), initialCoord.getY(), ship, false, false);
        PlayerOnboardComponent container = new PlayerOnboardComponent(player);
        ship.add(container);

        engine.addEntity(player);

        WanderingAiSystem wanderingAiSystem = new WanderingAiSystem();
        engine.addSystem(wanderingAiSystem);

        engine.update(1);
    }

    @Override
    public void displayOutput() {
        WorldSystem worldSystem = engine.getSystem(WorldSystem.class);
        int left = getScrollX();
        int top = getScrollY();

        //TileComponent[][] tiles = worldSystem.getWorldTiles();
        TileComponent[][] tiles = worldSystem.getRenderGrid().getTileComponents();
        for(int x = 0; x < screenWidth; x++) {
            for(int y = 0; y < screenHeight; y++) {
                int wx = x + left;
                int wy = y + top;

                TileComponent tile = tiles[wx][wy];
                this.terminal.write(tile.glyph, x, y, tile.color);
            }
        }

        int lastLine = terminal.getHeightInCharacters() - 1;
        terminal.clear(' ', 0, lastLine - 1, terminal.getWidthInCharacters(), 1);

        if(worldSystem.getPlayerEntity().getClass().equals(PlayerShip.class)) {
            Entity playerShip = worldSystem.getPlayerEntity();
            PlayerOnboardComponent playerOnboardComponent = MapperFactory.playerOnboardComponent.get(playerShip);
            Entity playerCharacter = playerOnboardComponent.player;

            HealthComponent shipHealth = MapperFactory.healthComponent.get(playerShip);
            HealthComponent playerHealth = MapperFactory.healthComponent.get(playerCharacter);
            HungerComponent playerHunger = MapperFactory.hungerComponent.get(playerCharacter);
            InventoryComponent shipInventory = MapperFactory.inventoryComponent.get(playerShip);
            InventoryComponent playerInventory = MapperFactory.inventoryComponent.get(playerCharacter);

            terminal.write(String.format(
                    "Health %d/%d. Inventory %d/%d. Hunger %d/%d.    Ship Health %d/%d. Ship Inventory %d/%d.",
                    playerHealth.hitpoints,
                    playerHealth.maxHitpoints,
                    playerInventory.currentSize,
                    playerInventory.maxSize,
                    playerHunger.currentHunger,
                    playerHunger.maxHunger,
                    shipHealth.hitpoints,
                    shipHealth.maxHitpoints,
                    shipInventory.currentSize,
                    shipInventory.maxSize
                ),
             2,
             lastLine -1
            );

        } else if (worldSystem.getPlayerEntity().getClass().equals(PlayerCharacter.class)) {
            Entity playerCharacter = worldSystem.getPlayerEntity();
            PlayerShipComponent playerShipComponent = MapperFactory.playerShipComponent.get(playerCharacter);
            Entity playerShip = playerShipComponent.playerShip;

            HealthComponent shipHealth = MapperFactory.healthComponent.get(playerShip);
            HealthComponent playerHealth = MapperFactory.healthComponent.get(playerCharacter);
            HungerComponent playerHunger = MapperFactory.hungerComponent.get(playerCharacter);
            InventoryComponent shipInventory = MapperFactory.inventoryComponent.get(playerShip);
            InventoryComponent playerInventory = MapperFactory.inventoryComponent.get(playerCharacter);

            terminal.write(String.format(
                    "Health %d/%d. Inventory %d/%d. Hunger %d/%d.     Ship Health %d/%d. Ship Inventory %d/%d.",
                    playerHealth.hitpoints,
                    playerHealth.maxHitpoints,
                    playerInventory.currentSize,
                    playerInventory.maxSize,
                    playerHunger.currentHunger,
                    playerHunger.maxHunger,
                    shipHealth.hitpoints,
                    shipHealth.maxHitpoints,
                    shipInventory.currentSize,
                    shipInventory.maxSize
                    ),
                    2,
                    lastLine -1
            );
        }

        if(subscreen != null)
            subscreen.displayOutput();
    }

    // TODO: moving examine cursor advances time... how can we stop this? Perhaps a pause movement event, or pause AI or remove the AI temporarily?
    // TODO: at some point we'll need an 'item grid'/'non world tiles grid' as well as renderGrid and worldGrid
    //       as we want to pickup when on top of an item and renderGrid will not work for this
    @Override
    public Screen respondToUserInput(KeyEvent key) {
        if(!worldSystem.playerAlive())
            return new LooseScreen(panelWidth, panelHeight, terminal);

        if(subscreen != null) {
            subscreen = subscreen.respondToUserInput(key);
        } else {
            Entity player = worldSystem.getPlayerEntity();
            PositionComponent playerPos = MapperFactory.positionComponent.get(player);
            int mx = 0;
            int my = 0;

            // TODO: add some gurads so if in cursor mode you cant try to grab things etc.
            switch (key.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    mx = -1;
                    my = 0;
                    break;
                case KeyEvent.VK_RIGHT:
                    mx = 1;
                    my = 0;
                    break;
                case KeyEvent.VK_UP:
                    mx = 0;
                    my = -1;
                    break;
                case KeyEvent.VK_DOWN:
                    mx = 0;
                    my = 1;
                    break;
                // TODO: how do we handle two chests close to eachother? probably better to do a press o, press direction, method
                case KeyEvent.VK_O:
                    EntityListGrid entityListGrid = worldSystem.getEntityListGrid();
                    PositionComponent openPos = MapperFactory.positionComponent.get(player);
                    List<Entity> surrounding = entityListGrid.surroundingEntities(openPos.x, openPos.y);
                    for(Entity e: surrounding) {
                        CanStoreItemsInComponent canStoreItemsInComponent = MapperFactory.canStoreItemsInComponentComponentMapper.get(e);
                        if(canStoreItemsInComponent != null) {
                            System.out.println("Near chest!!!");
                            subscreen = new DualTransferScreen(terminal, player, e);
                            break;
                        }
                    }
                    break;
                case KeyEvent.VK_G:
                    List<Entity> entities = worldSystem.getEntityListGrid()
                            .getEntities(playerPos.x, playerPos.y)
                            .stream()
                            .filter(entity -> entity != player)
                            .filter(entity -> {
                                CanAddToInventoryComponent canAddToInventoryComponent = MapperFactory.canAddToInventoryComponent.get(entity);
                                return canAddToInventoryComponent != null;
                            })
                            .collect(Collectors.toList());

                    subscreen = new SelectBasedGrabScreen(terminal, player, entities);
                    break;
                case KeyEvent.VK_D:
                    // subscreen = new DropScreen(terminal, player);
                    subscreen = new SelectBasedDropScreen(terminal, player);
                    break;
                case KeyEvent.VK_T:
                    if(player.getClass().equals(PlayerShip.class)) {
                        PlayerOnboardComponent playerOnboardComponent = MapperFactory.playerOnboardComponent.get(player);
                        subscreen = new DualTransferScreen(terminal, playerOnboardComponent.player, player);
                    } else if(player.getClass().equals(PlayerCharacter.class)) {
                        PlayerShipComponent playerShipComponent = MapperFactory.playerShipComponent.get(player);
                        subscreen = new DualTransferScreen(terminal, player, playerShipComponent.playerShip);
                    } else {
                        break;
                    }
                    break;
                case KeyEvent.VK_I:
                    subscreen = new SelectBasedInventoryScreen(terminal, player);
                    break;
                case KeyEvent.VK_C:
                    if(player.getClass().equals(PlayerShip.class)) {
                        subscreen = new DualCraftingScreen(terminal, player, null);
                        break;
                    } else {
                        RecipeBookComponent recipeBookComponent = MapperFactory.recipeBookComponent.get(player);
                        subscreen = new DualCraftingScreen(terminal, player, recipeBookComponent.recipeBook);
                    }
                    break;
                case KeyEvent.VK_E:
                    // TODO: cant to examine as we check render grid and just see cursor itself...
                    // TODO: perhaps just do directional examine for now?
                    if(player.getClass().equals(Cursor.class)) {
                        PositionComponent cursorPos = MapperFactory.positionComponent.get(player);

                        // TODO: is there a nicer way than casting this?
                        CursorAi cursorAi = (CursorAi) MapperFactory.aiComponent.get(player).ai;

                        // TODO: fix null ptr error here when examing self, as tileEntered not set on CursorAi
                        ExamineComponent examineComponent = MapperFactory.examineComponent.get(cursorAi.getTileEntered());

                        // TODO: why didnt this write to terminal?
                        if(examineComponent != null) {
                            System.out.println(examineComponent.description);
                            String examineString = examineComponent.description;

                            HealthComponent healthComponent = MapperFactory.healthComponent.get(cursorAi.getTileEntered());
                            if(healthComponent != null) {
                                examineString = String.format("%s (health: %d/%d)", examineString, healthComponent.hitpoints, healthComponent.maxHitpoints);
                            }
                            subscreen = new ExamineScreen(panelWidth, panelHeight, terminal, examineString);
                        } else {
                            System.out.println("No description available");
                            subscreen = new ExamineScreen(panelWidth, panelHeight, terminal, "No description available.");
                        }
                        ReturnControlComponent returnControlComponent = MapperFactory.returnControlComponent.get(player);
                        engine.removeEntity(player);
                        Entity newControlledEntity = returnControlComponent.entity;
                        newControlledEntity.add(new PlayerControlledComponent());

                        SendMessageComponent messages = new SendMessageComponent();
                        messages.addMessage("You examined something!");
                        newControlledEntity.add(messages);

                        unpauseEntities();
                        // make sure we override player for rest of function to run
                        // player = newControlledEntity;
                    } else {
                        pauseEntities();

                        PositionComponent pos = MapperFactory.positionComponent.get(player);
                        Cursor cursor = new Cursor(player, pos.x, pos.y);
                        player.remove(PlayerControlledComponent.class);
                        engine.addEntity(cursor);
                    }
                    break;
            }

            // TODO: should we handle screen transitions with a ScreenSystem?
            // TODO: don't handle the win so grossly
            // TODO: portal should spawn within some radius of player
            WorldSystem worldSystem = engine.getSystem(WorldSystem.class);
            RenderGrid renderGrid = worldSystem.getRenderGrid();
            PositionComponent pos = MapperFactory.positionComponent.get(player);
            TileComponent playerTile = MapperFactory.tileComponent.get(player);
            TileComponent newTile = renderGrid.getTileComponent(pos.x + mx, pos.y + my);

            // You can win by entering the portal with upgraded ship
            if(TileUtil.isPortal(newTile) && TileUtil.sameTile(playerTile, TileFactory.shipV2))
                return new WinScreen(panelWidth, panelHeight, terminal);

            if (mx != 0 || my != 0) {
                MovingComponent mov = new MovingComponent(mx, my);
                player.add(mov);
            }
        }

        // TODO: perhaps put these updates in the non-subscreen branch of above, to freeze time when in subscreen?
        // Two updates, to mean that position updates then screen redraws, is there nicer way to do this?
        engine.update(1);
       // engine.update(1);
        return this;
    }

    private void pauseEntities() {
        // TODO: how can we actaully stop time? do we even need to?
        // pause ai systems - simulates stopping time
        NaivePlayerAiAttackSystem naivePlayerAiAttackSystem = engine.getSystem(NaivePlayerAiAttackSystem.class);
        naivePlayerAiAttackSystem.setProcessing(false);
        WanderingAiSystem wanderingAiSystem = engine.getSystem(WanderingAiSystem.class);
        wanderingAiSystem.setProcessing(false);
    }

    private void unpauseEntities() {
        // restart ai systems
        NaivePlayerAiAttackSystem naivePlayerAiAttackSystem = engine.getSystem(NaivePlayerAiAttackSystem.class);
        naivePlayerAiAttackSystem.setProcessing(true);
        WanderingAiSystem wanderingAiSystem = engine.getSystem(WanderingAiSystem.class);
        wanderingAiSystem.setProcessing(true);
    }

    public int getScrollX() {
        return Math.max(0, Math.min(worldSystem.getPlayerX() - screenWidth / 2, worldSystem.getWidth() - screenWidth));
    }

    public int getScrollY() {
        return Math.max(0, Math.min(worldSystem.getPlayerY() - screenHeight / 2, worldSystem.getHeight() - screenHeight));
    }
}
