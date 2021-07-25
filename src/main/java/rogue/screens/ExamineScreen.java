package rogue.screens;

import asciiPanel.AsciiPanel;

import java.awt.event.KeyEvent;

// Given we dont have messaging system, lets just make a simple examine screen
public class ExamineScreen implements Screen {
    private int panelWidth;
    private int panelHeight;
    private AsciiPanel terminal;
    private String examineText;

    public ExamineScreen(int panelWidth, int panelHeight, AsciiPanel terminal, String examineText) {
        super();
        this.terminal = terminal;
        this.panelWidth = panelWidth;
        this.panelHeight = panelHeight;
        this.examineText = examineText;
    }

    @Override
    public void displayOutput() {
        int lastLine = terminal.getHeightInCharacters() - 1;

        terminal.clear(' ', 0, lastLine - 1, terminal.getWidthInCharacters(), 1);
        terminal.write(examineText, 2, lastLine -1 );
    }

    @Override
    public Screen respondToUserInput(KeyEvent key) {
        return null;
    }
}
