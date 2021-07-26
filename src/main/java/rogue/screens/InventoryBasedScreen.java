package rogue.screens;

import asciiPanel.AsciiPanel;
import com.badlogic.ashley.core.Entity;
import rogue.components.ExamineComponent;
import rogue.components.InventoryComponent;
import rogue.components.render.TileComponent;
import rogue.factories.MapperFactory;

import java.util.ArrayList;

public abstract class InventoryBasedScreen extends ListBasedScreen {

    public InventoryBasedScreen(AsciiPanel terminal, Entity player) {
        super(terminal, player);
    }

    @Override
    protected ArrayList<String> getList() {
        ArrayList<String> lines = new ArrayList<>();
        InventoryComponent inventoryComponent = MapperFactory.inventoryComponent.get(player);
        Entity[] inventory = inventoryComponent.inventory.toArray(new Entity[0]);

        for (int i = 0; i < inventory.length; i++){
            Entity item = inventory[i];

            if (item == null || !isAcceptable(item))
                continue;

            TileComponent tileComponent = MapperFactory.tileComponent.get(item);
            ExamineComponent examineComponent = MapperFactory.examineComponent.get(item);
            String name = examineComponent == null ? "" : examineComponent.name;
            String line = letters.charAt(i) + " - " + tileComponent.glyph + " " + name;

            lines.add(line);
        }

        return lines;
    }

    @Override
    protected String getMessage() {
        InventoryComponent inventoryComponent = MapperFactory.inventoryComponent.get(player);
        String message = String.format(
                "What would you like to " + getVerb() + "? (inventory %d/%d)",
                inventoryComponent.currentSize,
                inventoryComponent.maxSize
        );

        return message;
    }

    @Override
    protected Entity[] getItems() {
        InventoryComponent inventoryComponent = MapperFactory.inventoryComponent.get(player);
        Entity[] items = inventoryComponent.inventory.toArray(new Entity[0]);

        return(items);
    }

    protected boolean passesValidation(AsciiPanel terminal) {
        return true;
    }
}
