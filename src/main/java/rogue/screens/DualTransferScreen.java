package rogue.screens;

import asciiPanel.AsciiPanel;
import com.badlogic.ashley.core.Entity;
import rogue.components.ExamineComponent;
import rogue.components.InventoryComponent;
import rogue.components.TileComponent;
import rogue.components.actions.InventoryTransferActionComponent;
import rogue.factories.MapperFactory;

import java.util.ArrayList;

public class DualTransferScreen extends DualListBasedScreen {

    public DualTransferScreen(AsciiPanel terminal, Entity first, Entity second) {
        super(terminal, first, second);
    }

    @Override
    protected String getVerb() {
        String verb = "transfer";
        return verb;
    }

    @Override
    protected boolean isAcceptable(Entity item) {
        return true;
    }

    @Override
    protected Screen firstItemUse(Entity item) {
        // from first -> second
        first.add(new InventoryTransferActionComponent(second, item));
        return null;
    }

    @Override
    protected Screen secondItemUse(Entity item) {
        // from second -> first
        second.add(new InventoryTransferActionComponent(first, item));
        return null;
    }

    @Override
    protected String getMessage() {
        ExamineComponent firstExamine = MapperFactory.examineComponent.get(first);
        ExamineComponent secondExamine = MapperFactory.examineComponent.get(second);
        InventoryComponent firstInventory = MapperFactory.inventoryComponent.get(first);
        InventoryComponent secondInventory = MapperFactory.inventoryComponent.get(second);

        return String.format(
                "What would you like to transfer?  %s (%d/%d) <-> %s (%d/%d)",
                firstExamine.name,
                firstInventory.currentSize,
                firstInventory.maxSize,
                secondExamine.name,
                secondInventory.currentSize,
                secondInventory.maxSize
        );
    }

    @Override
    protected ArrayList<String> getFirstList() {
        return getList(first, lowerLetters);
    }

    @Override
    protected ArrayList<String> getSecondList() {
        return getList(second, upperLetters);
    }

    @Override
    protected Entity[] getFirstItems() {
        return getItems(first);
    }

    @Override
    protected Entity[] getSecondItems() {
        return getItems(second);
    }

    @Override
    protected boolean passesValidation(AsciiPanel terminal) {
        return true;
    }

    // NOTE: NOT to be confused with getList @override function in single list components
    private ArrayList<String> getList(Entity entity, String letters) {
        ArrayList<String> lines = new ArrayList<>();
        InventoryComponent inventoryComponent = MapperFactory.inventoryComponent.get(entity);
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

    // NOTE: NOT to be confused with getItems @override function in other single list components
    protected Entity[] getItems(Entity entity) {
        InventoryComponent inventoryComponent = MapperFactory.inventoryComponent.get(entity);
        Entity[] items = inventoryComponent.inventory.toArray(new Entity[0]);

        return(items);
    }
}
