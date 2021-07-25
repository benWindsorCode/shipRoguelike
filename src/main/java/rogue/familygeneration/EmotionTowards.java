package rogue.familygeneration;

import java.util.Arrays;
import java.util.List;

// 'Verb' emotions that you feel towards someone or something, e.g. I 'Like' you
public enum EmotionTowards {
    LIKE,
    DISLIKE,
    LOVE,
    HATE;

    public static List<EmotionTowards> getEmotionTowards() {
        return Arrays.asList(
                EmotionTowards.LIKE,
                EmotionTowards.DISLIKE,
                EmotionTowards.LOVE,
                EmotionTowards.HATE
        );
    }
}
