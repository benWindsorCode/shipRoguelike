package rogue.util;

import java.util.List;
import java.util.Random;

public class RandomUtil {
    public static <T> T getRandom(List<T> objects) {
        return objects.get(new Random().nextInt(objects.size()));
    }
    public static int getRandomInt(int min, int max) {
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }
    public static double getRandomDouble(double min, double max) {
        Random rand = new Random();
        return min + (max - min) * rand.nextDouble();
    }
}
