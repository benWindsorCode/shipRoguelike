package rogue.screens;

import java.awt.event.KeyEvent;

public interface Screen {
    void displayOutput();

    Screen respondToUserInput(KeyEvent key);
}
