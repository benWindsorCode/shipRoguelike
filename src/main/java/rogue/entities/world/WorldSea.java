package rogue.entities.world;

import com.badlogic.ashley.core.Entity;
import rogue.components.ExamineComponent;
import rogue.components.PositionComponent;
import rogue.components.render.RenderableComponent;
import rogue.components.render.TileComponent;
import rogue.components.traits.IdComponent;
import rogue.components.traits.WorldTileComponent;
import rogue.factories.TileFactory;
import rogue.util.EntityId;
import rogue.util.RandomUtil;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class WorldSea extends Entity {
    public WorldSea(int x, int y) {
        super();
        this.add(new WorldTileComponent());
        this.add(new IdComponent(EntityId.WORLD_SEA));
        this.add(new RenderableComponent());

        int red = TileFactory.sea.color.getRed();
        int green = TileFactory.sea.color.getGreen();
        int blue = TileFactory.sea.color.getBlue();

        // Add some texture to the sea
        List<Integer> deviations = Arrays.asList(-12, -4, -2, 0, 0, +1, +2, +5);
        int blueDeviation = RandomUtil.getRandom(deviations);

        this.add(new TileComponent(TileFactory.sea.glyph, new Color(red, green, blue + blueDeviation)));
        this.add(new PositionComponent(x, y));
        this.add(new ExamineComponent(
                "Sea",
                "Sea",
                "A section of sea."
        ));
    }
}
