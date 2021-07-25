package rogue.screens;

import asciiPanel.AsciiPanel;
import com.badlogic.ashley.core.Entity;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

// TODO: capital letters dont work well as java detects the shift key and not the shift + second key...

public abstract class DualListBasedScreen implements Screen {
    protected Entity first;
    protected Entity second;
    protected String lowerLetters;
    protected String upperLetters;
    protected AsciiPanel terminal;

    protected abstract String getVerb();
    protected abstract boolean isAcceptable(Entity item);

    // Function called on selected item if its from first entity
    protected abstract Screen firstItemUse(Entity item);

    // Function called on selected item if its from second entity
    protected abstract Screen secondItemUse(Entity item);

    // The message to display under loaded list
    protected abstract String getMessage();

    // The string and entities that form the mapping to list
    protected abstract ArrayList<String> getFirstList();
    protected abstract ArrayList<String> getSecondList();
    protected abstract Entity[] getFirstItems();
    protected abstract Entity[] getSecondItems();

    // Run validation before proceeding with displayOutput
    protected abstract boolean passesValidation(AsciiPanel terminal);

    public DualListBasedScreen(AsciiPanel terminal, Entity first, Entity second) {
        this.terminal = terminal;
        this.first = first;
        this.second = second;
        // first entity gets lower letters
        this.lowerLetters = "abcdefghijklmnopqrstuvwxyz";
        // second entity get upper letters
        this.upperLetters = "1234567890-=[]";
    }

    @Override
    public void displayOutput() {
        if(!passesValidation(terminal)) {
            return;
        }

        ArrayList<String> firstLines = getFirstList();
        ArrayList<String> secondLines = getSecondList();

        int lastLine = terminal.getHeightInCharacters() - 1;
        int firstY = lastLine - firstLines.size();
        int secondY = lastLine - secondLines.size();
        int firstOffset = 4;
        int secondOffset = 50;
        int maxLinesToClear = Math.min(40, Math.max(firstLines.size(), secondLines.size()));

        int clearStart = lastLine - Math.max(firstLines.size(), secondLines.size());
        // Clear entire width of terminal
        if (firstLines.size() > 0 || secondLines.size() > 0)
            terminal.clear(' ', 0, clearStart, terminal.getWidthInCharacters(), maxLinesToClear);

        for (String line : firstLines){
            terminal.write(line, firstOffset, firstY++);
        }

        for (String line : secondLines){
            terminal.write(line, secondOffset, secondY++);
        }

        terminal.clear(' ', 0, lastLine, terminal.getWidthInCharacters(), 1);
        terminal.write(getMessage(), 2, lastLine );

        terminal.repaint();
    }

    @Override
    public Screen respondToUserInput(KeyEvent key) {
        char c = key.getKeyChar();

        Entity[] firstItems = getFirstItems();
        Entity[] secondItems = getSecondItems();

        // TODO: extract nasty condition to function (list, list) -> bool
        if(lowerLetters.indexOf(c) > -1
                && firstItems.length > lowerLetters.indexOf(c)
                && firstItems[lowerLetters.indexOf(c)] != null
                && isAcceptable(firstItems[lowerLetters.indexOf(c)])
        ) {
            return firstItemUse(firstItems[lowerLetters.indexOf(c)]);
        } else if(upperLetters.indexOf(c) > -1
                && secondItems.length > upperLetters.indexOf(c)
                && secondItems[upperLetters.indexOf(c)] != null
                && isAcceptable(secondItems[upperLetters.indexOf(c)])
        ) {
            return secondItemUse(secondItems[upperLetters.indexOf(c)]);
        } else if(key.getKeyCode() == KeyEvent.VK_ESCAPE) {
            return null;
        } else {
            return null;
        }
    }
}
