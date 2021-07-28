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

public class WorldShallowSea extends Entity {
    public WorldShallowSea(int x, int y) {
        super();
        this.add(new WorldTileComponent());
        this.add(new IdComponent(EntityId.WORLD_SHALLOW_SEA));
        this.add(new RenderableComponent());

        int red = TileFactory.shallowSea.color.getRed();
        int green = TileFactory.shallowSea.color.getGreen();
        int blue = TileFactory.shallowSea.color.getBlue();

        // Add some texture to the sea
        List<Integer> deviations = Arrays.asList(-12, -7, 0, 0, 0);
        int blueDeviation = RandomUtil.getRandom(deviations);

        this.add(new TileComponent(TileFactory.shallowSea.glyph, new Color(red, green + blueDeviation, blue + blueDeviation)));

        //this.add(new TileComponent(TileFactory.shallowSea.glyph, TileFactory.shallowSea.color));
        this.add(new PositionComponent(x, y));
        this.add(new ExamineComponent(
                "Shallow Sea",
                "Shallow Sea",
                "A shallow section of sea."
        ));
    }
}
