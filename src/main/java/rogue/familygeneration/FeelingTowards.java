package rogue.familygeneration;

public class FeelingTowards implements WithReason {
    private EmotionTowards emotionTowards;
    private String reason;

    public FeelingTowards(EmotionTowards emotionTowards, String reason) {
        this.emotionTowards = emotionTowards;
        this.reason = reason;
    }

    public EmotionTowards getEmotion() {
        return emotionTowards;
    }

    public void setEmotion(EmotionTowards emotionTowards) {
        this.emotionTowards = emotionTowards;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
