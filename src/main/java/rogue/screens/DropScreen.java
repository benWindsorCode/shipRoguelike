package rogue.screens;

import asciiPanel.AsciiPanel;
import com.badlogic.ashley.core.Entity;

public class DropScreen extends InventoryBasedScreen {

    public DropScreen(AsciiPanel terminal, Entity player) {
        super(terminal, player);
    }

    @Override
    protected String getVerb() {
        return "drop";
    }

    @Override
    protected boolean isAcceptable(Entity item) {
        return true;
    }

    @Override
    protected Screen use(Entity item) {
        return new DirectionChoiceScreen(terminal, player, item);
    }
}
