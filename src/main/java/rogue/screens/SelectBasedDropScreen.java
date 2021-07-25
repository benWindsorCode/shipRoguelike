package rogue.screens;

import asciiPanel.AsciiPanel;
import com.badlogic.ashley.core.Entity;
import rogue.components.InventoryComponent;
import rogue.factories.MapperFactory;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class SelectBasedDropScreen extends SelectBasedScreen {

    public SelectBasedDropScreen(AsciiPanel terminal, Entity player) {
        super(terminal, player);
    }

    @Override
    protected Entity[] getItems() {
        InventoryComponent inventoryComponent = MapperFactory.inventoryComponent.get(player);
        return inventoryComponent.inventory.toArray(new Entity[0]);    }

    @Override
    protected String getMessage() {
        return "'enter' to drop item";
    }

    @Override
    protected Screen extraInputResponses(KeyEvent key) {
        switch (key.getKeyCode()) {
            case KeyEvent.VK_ENTER:
                if(getItems().length == 0)
                    return this;
                Entity item = getItems()[currentItem];
                return new DirectionChoiceScreen(terminal, player, item);
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_RIGHT:
                return this;
        }
        return null;
    }
}
