package rogue.familygeneration;

import java.util.Arrays;
import java.util.List;

// Emotional states of a person, e.g. I am 'happy' or I feel 'sad'
public enum Emotion {
    HAPPY,
    SAD,
    ANGRY,
    NEUTRAL,
    UPSET;

    public static List<Emotion> getEmotions() {
        return Arrays.asList(
            Emotion.HAPPY,
            Emotion.SAD,
            Emotion.ANGRY,
            Emotion.NEUTRAL,
            Emotion.UPSET
        );
    }
}
