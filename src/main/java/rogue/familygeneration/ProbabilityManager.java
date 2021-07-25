package rogue.familygeneration;

public class ProbabilityManager {
    public static double probMarry(final Person person) {
        double prob = 0;
        switch(person.getAttractiveness()) {
            case GORGEOUS: prob = 0.8; break;
            case ABOVE_AVERAGE: prob = 0.7; break;
            case AVERAGE: prob = 0.5; break;
            case BELOW_AVERAGE: prob = 0.3;
            case HIDEOUS: prob = 0.1;
        }

        return prob;
    }
}
