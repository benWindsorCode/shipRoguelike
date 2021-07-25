package rogue.screens;

import asciiPanel.AsciiPanel;

import java.awt.event.KeyEvent;

public class LooseScreen implements Screen {
    private final int panelWidth;
    private final int panelHeight;
    private final AsciiPanel terminal;

    public LooseScreen(int panelWidth, int panelHeight, AsciiPanel terminal) {
        super();
        this.terminal = terminal;
        this.panelWidth = panelWidth;
        this.panelHeight = panelHeight;
    }

    @Override
    public void displayOutput() {
        int lastLine = terminal.getHeightInCharacters() - 1;
        terminal.write("you lost", 1, 1);
        terminal.writeCenter("-- press [enter] to restart --", lastLine);
    }

    @Override
    public Screen respondToUserInput(KeyEvent key) {
        return key.getKeyCode() == KeyEvent.VK_ENTER ? new PlayScreen(terminal) : this;
    }
}
