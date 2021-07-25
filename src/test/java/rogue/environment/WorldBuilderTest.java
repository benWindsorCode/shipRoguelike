package rogue.environment;

import com.badlogic.ashley.core.Engine;
import org.junit.jupiter.api.Test;
import rogue.entities.World;

import java.util.Arrays;

public class WorldBuilderTest {

    @Test
    public void voronoiTest() {
        Engine engine = new Engine();
        World world = new World();
        WorldBuilder worldBuilder = new WorldBuilder(engine, 20, 25, world);
        int[][] voronoiGrid = worldBuilder.generateVoronoiGrid(7);
        for(int[] row: voronoiGrid) {
            System.out.println(Arrays.toString(row));
        }
    }

}
