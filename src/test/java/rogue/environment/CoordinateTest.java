package rogue.environment;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class CoordinateTest {

    @Test
    public void CoordinateHash() {
        Coordinate coord1 = new Coordinate(3, 4);
        Coordinate coord2 = new Coordinate(3, 4);

        Map<Coordinate, Integer> map = new HashMap<>();
        map.put(coord1, 4);
        map.put(coord2, 6);

        System.out.println(map);
    }
}
