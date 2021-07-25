package rogue.familygeneration;

import java.util.Arrays;
import java.util.List;

public enum Gender {
    MALE,
    FEMALE;

    public static List<Gender> getGenders() {
        return Arrays.asList(Gender.MALE, Gender.FEMALE);
    }
}
