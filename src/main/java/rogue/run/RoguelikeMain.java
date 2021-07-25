package rogue.run;

import asciiPanel.AsciiPanel;
import rogue.screens.PlayScreen;
import rogue.screens.Screen;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class RoguelikeMain extends JFrame implements KeyListener {
    private static final long serialVersionUID = 1060623638149583738L;

    private AsciiPanel terminal;
    private Screen screen;
    private final int panelWidth = 140;
    private final int panelHeight = 40;

    // TODO: we could be clever here and split the terminal panel to subscreens like a side inventory bar
    //       each screen would be rendering relative to some coord offset
    public RoguelikeMain(){
        super();
        terminal = new AsciiPanel(panelWidth, panelHeight);
        add(terminal);
        pack();
        screen = new PlayScreen(terminal);
        addKeyListener(this);
        repaint();
    }

    public static void main(String[] args) {
        RoguelikeMain app = new RoguelikeMain();
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setVisible(true);
    }

    public void repaint() {
        terminal.clear();
        screen.displayOutput();
        super.repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        screen = screen.respondToUserInput(e);
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
