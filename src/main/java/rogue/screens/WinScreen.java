package rogue.screens;

import asciiPanel.AsciiPanel;

import java.awt.event.KeyEvent;

public class WinScreen implements Screen {
    private int panelWidth;
    private int panelHeight;
    private AsciiPanel terminal;

    public WinScreen(int panelWidth, int panelHeight, AsciiPanel terminal) {
        super();
        this.terminal = terminal;
        this.panelWidth = panelWidth;
        this.panelHeight = panelHeight;
    }

    @Override
    public void displayOutput() {
        int lastLine = terminal.getHeightInCharacters() - 1;
        terminal.write("you won", 1, 1);
        terminal.writeCenter("-- press [enter] to restart --", lastLine);
    }

    @Override
    public Screen respondToUserInput(KeyEvent key) {
        return key.getKeyCode() == KeyEvent.VK_ENTER ? new PlayScreen(terminal) : this;
    }
}
