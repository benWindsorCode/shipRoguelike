package rogue.util;

import rogue.components.TileComponent;
import rogue.factories.TileFactory;

public class TileUtil {
    public static boolean isSea(TileComponent tile) {
        return TileUtil.sameTile(tile, TileFactory.sea)
                || TileUtil.sameTile(tile, TileFactory.shallowSea)
                || TileUtil.sameTile(tile, TileFactory.deepSea);
    }

    public static boolean isLand(TileComponent tile) {
        return TileUtil.sameTile(tile, TileFactory.grass);
    }

    // ideally logic works on entities and entity has a 'passable through' component
//    public static boolean canWalkOnItem(TileComponent tile) {
//        return TileUtil.sameTile()
//    }

    public static boolean isWood(TileComponent tile) {
        return TileUtil.sameTile(tile, TileFactory.wood);
    }

    public static boolean isIron(TileComponent tile) {
        return TileUtil.sameTile(tile, TileFactory.iron);
    }

    public static boolean isPortal(TileComponent tile) { return TileUtil.sameTile(tile, TileFactory.portal); }

    public static boolean sameTile(TileComponent first, TileComponent second) {
        return first.glyph == second.glyph && first.color.equals(second.color);
    }
}
