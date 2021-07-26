package rogue.entities.world;

import com.badlogic.ashley.core.Entity;
import rogue.components.ExamineComponent;
import rogue.components.PositionComponent;
import rogue.components.RenderableComponent;
import rogue.components.TileComponent;
import rogue.components.traits.IdComponent;
import rogue.components.traits.WorldTileComponent;
import rogue.factories.TileFactory;
import rogue.util.EntityId;
import rogue.util.RandomUtil;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class WorldGrass extends Entity {
    public WorldGrass(int x, int y) {
        super();
        this.add(new WorldTileComponent());
        this.add(new IdComponent(EntityId.WORLD_GRASS));
        this.add(new RenderableComponent());

        int red = TileFactory.grass.color.getRed();
        int green = TileFactory.grass.color.getGreen();
        int blue = TileFactory.grass.color.getBlue();

        // Add some texture to the grass
        List<Integer> deviations = Arrays.asList(-5, -3, 0, 0, 0, +5, +9);
        int greenDeviation = RandomUtil.getRandom(deviations);
        this.add(new TileComponent(TileFactory.grass.glyph, new Color(red, green + greenDeviation, blue)));

        System.out.println("Creating world grass");
        this.add(new PositionComponent(x, y));
        this.add(new ExamineComponent(
                "Grass",
                "Grass",
                "A swath of green grass."
        ));
    }
}
