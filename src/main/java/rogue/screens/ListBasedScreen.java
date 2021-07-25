package rogue.screens;

import asciiPanel.AsciiPanel;
import com.badlogic.ashley.core.Entity;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

public abstract class ListBasedScreen implements Screen {
    protected Entity player;
    protected String letters;
    protected AsciiPanel terminal;

    protected abstract String getVerb();
    protected abstract boolean isAcceptable(Entity item);

    // Function called on selected item
    protected abstract Screen use(Entity item);

    // The message to display under loaded list
    protected abstract String getMessage();

    // The string and entities that form the mapping to list
    protected abstract ArrayList<String> getList();
    protected abstract Entity[] getItems();

    // Run validation before proceeding with displayOutput
    protected abstract boolean passesValidation(AsciiPanel terminal);

    public ListBasedScreen(AsciiPanel terminal, Entity player) {
        this.terminal = terminal;
        this.player = player;
        this.letters = "abcdefghijklmnopqrstuvwxyz";
    }

    @Override
    public void displayOutput() {
        if(!passesValidation(terminal)) {
            return;
        }

        ArrayList<String> lines = getList();

        int lastLine = terminal.getHeightInCharacters() - 1;
        int y = lastLine - lines.size();
        int x = 4;

        if (lines.size() > 0)
            terminal.clear(' ', x, y, 20, lines.size());

        for (String line : lines){
            terminal.write(line, x, y++);
        }

        terminal.clear(' ', 0, lastLine, terminal.getWidthInCharacters(), 1);
        terminal.write(getMessage(), 2, lastLine );

        terminal.repaint();
    }

    @Override
    public Screen respondToUserInput(KeyEvent key) {
        char c = key.getKeyChar();

        Entity[] items = getItems();

        if (letters.indexOf(c) > -1
                && items.length > letters.indexOf(c)
                && items[letters.indexOf(c)] != null
                && isAcceptable(items[letters.indexOf(c)]))
            return use(items[letters.indexOf(c)]);
        else if (key.getKeyCode() == KeyEvent.VK_ESCAPE)
            return null;
        else
            return this;
    }
}
