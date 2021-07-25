package rogue.familygeneration;

import com.github.javafaker.Faker;
import rogue.util.RandomUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class FamilyTree {
    private final List<Person> people;
    private final Faker faker;

    public FamilyTree(int initialPopulation, boolean randomiseAges) {
        people = new ArrayList<>();
        faker = new Faker();

        System.out.println("Creating initial population\n");
        for(int i = 0; i < initialPopulation; i++) {
            final Gender gender = RandomUtil.getRandom(Gender.getGenders());
            final int age = randomiseAges ? ThreadLocalRandom.current().nextInt(0, Person.maxAge) : 0;
            final String firstName = faker.name().firstName();
            final String surName = faker.name().lastName();
            Person person = new Person(gender, age, firstName, surName);
            people.add(person);
            System.out.println(String.format("Adding person: %s %s, age: %d, gender: %s, attractiveness: %s", firstName, surName, age, gender, person.getAttractiveness()));
        }
    }

    public void runSimulation(int years) {

    }
}
