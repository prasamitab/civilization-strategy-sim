package com.civgame.core;

import com.civgame.exceptions.InsufficientResourcesException;

public class CombatSystem {

    // Logic: Attacker hits Defender. Returns a String message of what happened.
    public static String resolveBattle(Civilization attacker, Civilization defender) throws InsufficientResourcesException {
        
        // 1. Check if we have money/army
        if (attacker.getGold() < 10) {
            throw new InsufficientResourcesException("Need 10 Gold to launch attack!");
        }
        if (attacker.getArmyStrength() <= 0) {
            throw new InsufficientResourcesException("You have no army to send!");
        }

        // 2. Pay the cost (10 Gold)
        attacker.updateResources(0, -10); 

        // 3. Calculate Damage (Uses Polymorphism)
        int damageDealt = attacker.calculateAttackDamage();

        // 4. Defender takes damage
        String result = defender.receiveAttack(damageDealt);

        return "Battle Report:\n" + 
               attacker.getCivilizationName() + " dealt " + damageDealt + " damage.\n" +
               defender.getCivilizationName() + ": " + result;
    }
}