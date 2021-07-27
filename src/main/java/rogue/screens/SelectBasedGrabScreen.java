package rogue.screens;

import asciiPanel.AsciiPanel;
import com.badlogic.ashley.core.Entity;
import rogue.components.InventoryComponent;
import rogue.components.actions.InventoryAddActionComponent;
import rogue.factories.MapperFactory;

import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.List;

public class SelectBasedGrabScreen extends SelectBasedScreen {
    List<Entity> entitiesToGrab;

    public SelectBasedGrabScreen(AsciiPanel terminal, Entity player, List<Entity> entitiesToGrab) {
        super(terminal, player);
        this.entitiesToGrab = entitiesToGrab;
    }

    @Override
    protected Entity[] getItems() {
        return entitiesToGrab.toArray(new Entity[0]);
    }

    @Override
    protected String getMessage() {
        InventoryComponent inventoryComponent = MapperFactory.inventoryComponent.get(player);
        return String.format(
                "'enter' to pick up. 'e' to pick up all. Player inventory %d/%d items.",
                inventoryComponent.currentSize,
                inventoryComponent.maxSize
        );
    }

    @Override
    protected Screen extraInputResponses(KeyEvent key) {
        switch (key.getKeyCode()) {
            case KeyEvent.VK_ENTER:
                if(getItems().length == 0)
                    return this;
                Entity item = getItems()[currentItem];
                player.add(new InventoryAddActionComponent(item));
                break;
            case KeyEvent.VK_E:
                if(getItems().length == 0)
                    return this;
                List<Entity> items = Arrays.asList(getItems());
                player.add(new InventoryAddActionComponent(items));
                break;
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_RIGHT:
                return this;
        }

        return null;
    }
}
