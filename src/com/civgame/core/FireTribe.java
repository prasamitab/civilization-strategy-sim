package com.civgame.core;

public class FireTribe extends Civilization {

    public FireTribe() {
        super("Fire Tribe"); // Calls the parent constructor
    }

    // Overriding the abstract method
    // Fire Tribe gets a bonus to Attack
    public int calculateAttackDamage() {
        int baseDamage = getArmyStrength();
        // Bonus: Fire tribe does 20% more damage
        return baseDamage + (baseDamage * 20 / 100); 
    }
}
