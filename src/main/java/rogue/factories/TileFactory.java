package rogue.factories;

import asciiPanel.AsciiPanel;
import rogue.components.TileComponent;

import java.awt.*;

// TODO: so much relies on glyphs + colors being equal, means cant have two entities with the same glyph + colour without them being considered equal
public class TileFactory {
    public static final TileComponent bounds = new TileComponent('x', AsciiPanel.brightBlack);
    public static final TileComponent grass = new TileComponent((char)219, AsciiPanel.green);
    public static final TileComponent mountain = new TileComponent((char)30, new Color(0, 51, 0));
    public static final TileComponent highMountain = new TileComponent((char)30, new Color(0, 77, 0));
    public static final TileComponent deepSea = new TileComponent((char)247, new Color(0, 0, 77));
    public static final TileComponent sea = new TileComponent((char)178, AsciiPanel.blue);
    public static final TileComponent shallowSea = new TileComponent((char)178, AsciiPanel.cyan);
    public static final TileComponent character = new TileComponent((char)1, AsciiPanel.brightWhite);
    public static final TileComponent shipV1 = new TileComponent((char)30, new Color(153, 102, 0));
    public static final TileComponent shipV2 = new TileComponent((char)30, new Color(153, 192, 0));
    public static final TileComponent enemyShip = new TileComponent((char)30, AsciiPanel.red);
    public static final TileComponent enemyShipStrong = new TileComponent((char)30, AsciiPanel.brightRed);
    public static final TileComponent civilianShip = new TileComponent((char)30, AsciiPanel.brightBlack);
    public static final TileComponent weakBandit = new TileComponent((char)2, AsciiPanel.red);
    public static final TileComponent oakTree = new TileComponent((char)210, new Color(77, 38, 0));
    public static final TileComponent ashTree = new TileComponent((char)210, new Color(102, 51, 0));
    public static final TileComponent wood = new TileComponent((char)186, new Color(77, 38, 0));
    public static final TileComponent iron = new TileComponent((char)219, new Color(115, 115, 115));
    public static final TileComponent gold = new TileComponent((char)219, new Color(255, 163, 0));
    public static final TileComponent portal = new TileComponent((char)219, new Color(153, 51, 102));
    public static final TileComponent rock = new TileComponent((char)42, new Color(115, 115, 115));
    public static final TileComponent stone = new TileComponent((char)177, new Color(115, 115, 115));
    public static final TileComponent examine = new TileComponent('X', AsciiPanel.brightWhite);
    public static final TileComponent chest = new TileComponent((char)8, new Color(77, 38, 0));
    public static final TileComponent repairKit = new TileComponent('r', new Color(77, 38, 0));
    public static final TileComponent ironChest = new TileComponent((char)8, new Color(110, 115, 100));
    public static final TileComponent stoneAltar = new TileComponent((char)8, new Color(115, 115, 115));
    public static final TileComponent fox = new TileComponent('f', new Color(172, 57, 57));
    public static final TileComponent smallFish = new TileComponent('f', new Color(102, 153, 255));
    public static final TileComponent bigFish = new TileComponent('F', new Color(102, 153, 255));
    public static final TileComponent rat = new TileComponent('r', new Color(115, 115, 115));
    public static final TileComponent ratMeat = new TileComponent('r', AsciiPanel.brightRed);
    public static final TileComponent rawFish = new TileComponent('f', AsciiPanel.brightRed);
    public static final TileComponent stoneWall = new TileComponent((char)219, new Color(110, 115, 100));
    public static final TileComponent horizontalRail = new TileComponent((char)205, new Color(193, 196, 187));
    public static final TileComponent verticalRail = new TileComponent((char)186, new Color(193, 196, 187));
    public static final TileComponent railCross = new TileComponent((char)206, new Color(193, 196, 187));
}
