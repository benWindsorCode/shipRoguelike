package rogue.components;

import com.badlogic.ashley.core.Component;

import java.awt.*;

public class TileComponent implements Component {
    public char glyph;
    public Color color;

    public TileComponent(final char glyph, final Color color) {
        this.glyph = glyph;
        this.color = color;
    }

    // TODO: am I allowed to override equals on Components? is this gonna break the engine...?
    @Override
    public boolean equals(Object obj) {
        if(!obj.getClass().equals(TileComponent.class))
            return false;

        TileComponent other = (TileComponent) obj;

        // TODO: use this instead of the method in TileUtil
        return this.glyph == other.glyph && this.color == other.color;
    }

    // TODO: am I allowed to override equals on Components? is this gonna break the engine...?
    // TODO: perhaps use intellij generated hash instead, with Object.hash(...)?
    @Override
    public int hashCode() {
        return (int)glyph + color.getRed() + color.getBlue() + color.getGreen();
    }
}
