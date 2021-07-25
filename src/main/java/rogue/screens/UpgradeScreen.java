package rogue.screens;

import asciiPanel.AsciiPanel;
import com.badlogic.ashley.core.Entity;
import rogue.components.InventoryComponent;
import rogue.components.actions.UpgradeShipComponent;
import rogue.entities.PlayerCharacter;
import rogue.factories.MapperFactory;
import rogue.util.TileUtil;

import java.awt.event.KeyEvent;
import java.util.Arrays;

// TODO: convert to 'ListBasedScreen' and make use of crafting system of recipes
public class UpgradeScreen implements Screen {
    private Entity player;
    private boolean canUpgradeShip;
    private int shipUpgradeWoodAmount = 3;
    private int shipUpgradeIronAmount = 1;
    private AsciiPanel terminal;

    public UpgradeScreen(AsciiPanel terminal, Entity player) {
        this.terminal = terminal;
        this.player = player;
    }

    @Override
    public void displayOutput() {
        int lastLine = terminal.getHeightInCharacters() - 1;
        if(!player.getClass().equals(PlayerCharacter.class)) {
            terminal.clear(' ', 0, lastLine, terminal.getWidthInCharacters(), 1);
            terminal.write("Cannot upgrade while on Ship", 2, lastLine);
            canUpgradeShip = false;
            return;
        }

        terminal.clear(' ', 0, lastLine, terminal.getWidthInCharacters(), 1);
        terminal.write("What would you like to Upgrade?", 2, lastLine);

        InventoryComponent inventoryComponent = MapperFactory.inventoryComponent.get(player);
        Entity[] items = inventoryComponent.inventory.toArray(new Entity[0]);

        long woodCount = Arrays.stream(items)
                .map(MapperFactory.tileComponent::get)
                .filter(TileUtil::isWood)
                .count();

        long ironCount = Arrays.stream(items)
                .map(MapperFactory.tileComponent::get)
                .filter(TileUtil::isIron)
                .count();

        // TODO: check player only has V1 of ship?
        if(woodCount >= shipUpgradeWoodAmount && ironCount >= shipUpgradeIronAmount) {
            terminal.write("a) Ship", 4, lastLine);
            canUpgradeShip = true;
        } else {
            terminal.write("Gather at least 3 WOOD and 1 IRON to UPGRADE your SHIP", 4, lastLine - 1);
        }

        terminal.repaint();
    }

    @Override
    public Screen respondToUserInput(KeyEvent key) {
        if (key.getKeyCode() == KeyEvent.VK_ESCAPE)
            return null;
        else if(canUpgradeShip && key.getKeyCode() == KeyEvent.VK_A) {
            player.add(new UpgradeShipComponent(shipUpgradeWoodAmount, shipUpgradeIronAmount));
            return null;
        } else {
            return this;
        }
    }
}
