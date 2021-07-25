package rogue.screens;

import asciiPanel.AsciiPanel;

import java.awt.event.KeyEvent;

public interface Screen {
    public void displayOutput();

    public Screen respondToUserInput(KeyEvent key);
}
