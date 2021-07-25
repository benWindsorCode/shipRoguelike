package rogue.familygeneration;

public class Feeling implements WithReason {
    private Emotion emotion;
    private String reason;

    public Feeling(Emotion emotion, String reason) {
        this.emotion = emotion;
        this.reason = reason;
    }

    public Emotion getEmotion() {
        return emotion;
    }

    public void setEmotion(Emotion emotion) {
        this.emotion = emotion;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
