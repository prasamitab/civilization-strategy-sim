package com.civgame.core;

public class WaterTribe extends Civilization {

    public WaterTribe() {
        super("Water Tribe");
    }

    // Water Tribe is weaker in attack (maybe they are better at defense later)
    public int calculateAttackDamage() {
        return getArmyStrength(); // No bonus
    }
}
