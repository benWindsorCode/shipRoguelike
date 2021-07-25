package rogue.screens;

import asciiPanel.AsciiPanel;
import com.badlogic.ashley.core.Entity;
import rogue.components.actions.DropDirectionActionComponent;

import java.awt.event.KeyEvent;

// TODO: initially just tied to drop screen, but later could be more generic and add a direction onto a component
public class DirectionChoiceScreen implements Screen {

    private AsciiPanel terminal;
    private Entity player;
    private Entity item;

    public DirectionChoiceScreen(AsciiPanel terminal, Entity player, Entity item) {
        this.terminal = terminal;
        this.player = player;
        this.item = item;
    }

    @Override
    public void displayOutput() {
        int lastLine = terminal.getHeightInCharacters() - 1;
        terminal.clear(' ', 0, lastLine - 1, terminal.getWidthInCharacters(), 1);
        terminal.write("Choose a direction?", 2, lastLine - 1 );
    }

    @Override
    public Screen respondToUserInput(KeyEvent key) {
        switch (key.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                player.add(new DropDirectionActionComponent(-1, 0, item));
                return null;
            case KeyEvent.VK_RIGHT:
                player.add(new DropDirectionActionComponent(1, 0, item));
                return null;
            case KeyEvent.VK_UP:
                player.add(new DropDirectionActionComponent(0, -1, item));
                return null;
            case KeyEvent.VK_DOWN:
                player.add(new DropDirectionActionComponent(0, 1, item));
                return null;
            case KeyEvent.VK_ESCAPE:
                return null;
        }

        return this;
    }
}
