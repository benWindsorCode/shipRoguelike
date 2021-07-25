package rogue.loot;

import com.badlogic.ashley.core.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

public class LootTableEntry {
    private final Supplier<Entity> entitySupplier;
    private final double prob;
    private final int numRolls;

    public LootTableEntry(Supplier<Entity> entitySupplier, double prob, int numRolls) {
        this.entitySupplier = entitySupplier;
        this.prob = prob;
        this.numRolls = numRolls;
    }

    public List<Entity> dropLoot() {
        List<Entity> drops = new ArrayList<>();
        Random r = new Random();

        for(int i = 0; i < numRolls; i++) {
            double roll = r.nextDouble();
            if(roll <= prob)
                drops.add(entitySupplier.get());
        }

        return drops;
    }

    public Supplier<Entity> getEntitySupplier() {
        return entitySupplier;
    }

    public double getProb() {
        return prob;
    }

    public int getNumRolls() {
        return numRolls;
    }
}
