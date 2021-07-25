package rogue.familygeneration;

import rogue.util.RandomUtil;

import java.util.*;

public class Person {
    private Person currentPartner;
    private List<Person> pastPartners;
    private List<Person> children;
    private boolean alive;
    private int age;
    private final int marryThreshold = 16;
    private final int reproduceLowerThreshold = 16;
    private final int reproduceUpperMaleThreshold = Integer.MAX_VALUE;
    private final int reproduceUpperFemaleThreshold = 40;
    public static final int maxAge = 95;
    private final Gender gender;
    private Attractiveness attractiveness;
    private String firstName;
    private String surName;

    // How this person feels towards others
    private Map<Person, FeelingTowards> feelingTowards;

    // How this person feels in general about themselves + life
    private Feeling feeling;

    public Person(final Gender gender, final String firstName, final String surName) {
        this(gender, 0, firstName, surName);
    }

    public Person(final Gender gender, int age, final String firstName, final String surName) {
        this.gender = gender;
        this.age = age;
        this.firstName = firstName;
        this.surName = surName;
        feelingTowards = new HashMap<>();
        pastPartners = new ArrayList<>();
        children = new ArrayList<>();
        alive = true;
        feeling = new Feeling(Emotion.NEUTRAL, "Just born");
        attractiveness = RandomUtil.getRandom(Arrays.asList(Attractiveness.values()));
    }

    public boolean canMarry() {
        return age >= marryThreshold;
    }

    public boolean canReproduce() {
        int upperThreshold = gender.equals(Gender.FEMALE) ? reproduceUpperFemaleThreshold : reproduceUpperMaleThreshold;

        return age >= reproduceLowerThreshold && age <= upperThreshold;
    }

    public void age() {
        age += 1;
    }

    public Optional<Person> getCurrentPartner() {
        return currentPartner == null ? Optional.empty() : Optional.of(currentPartner);
    }

    public void setCurrentPartner(Person currentPartner) {
        this.currentPartner = currentPartner;
    }

    public Attractiveness getAttractiveness() {
        return attractiveness;
    }

    public void setAttractiveness(Attractiveness attractiveness) {
        this.attractiveness = attractiveness;
    }

    public List<Person> getPastPartners() {
        return pastPartners;
    }

    public void setPastPartners(List<Person> pastPartners) {
        this.pastPartners = pastPartners;
    }

    public List<Person> getChildren() {
        return children;
    }

    public void setChildren(List<Person> children) {
        this.children = children;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Map<Person, FeelingTowards> getFeelingTowards() {
        return feelingTowards;
    }

    public void setFeelingTowards(Map<Person, FeelingTowards> feelingTowards) {
        this.feelingTowards = feelingTowards;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }
}
