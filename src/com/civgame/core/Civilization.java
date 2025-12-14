package com.civgame.core;

// Abstract Class - Demonstrating Abstraction [Lecture 5-8, Slide 32]
public abstract class Civilization {

    // Encapsulation: Private variables [Lecture 5-8, Slide 26]
    private String civilizationName;
    protected int population; // Changed to protected so child classes can access if needed
    private int food;
    private int gold;
    protected int armyStrength; // Changed to protected for logic access

    // Constructor to set starting values
    public Civilization(String name) {
        this.civilizationName = name;
        this.population = 10; // Everyone starts with 10 people
        this.food = 50;       // Starting food
        this.gold = 50;       // Starting gold
        this.armyStrength = 0;
    }

    // Abstract Method: Child classes MUST implement this [Lecture 14-18, Slide 58]
    public abstract int calculateAttackDamage();

    // Concrete Method: Logic for training soldiers
    public void trainArmy() {
        // Cost: 20 Gold, 10 Food -> Gain 5 Army
        if (this.gold >= 20 && this.food >= 10) {
            this.gold -= 20;
            this.food -= 10;
            this.armyStrength += 5;
            System.out.println(civilizationName + " trained army! Strength is now: " + armyStrength);
        } else {
            System.out.println("Not enough resources to train army!");
        }
    }

    // Synchronized Method: Thread Safety for Resources [Lecture 26-31, Slide 26]
    public synchronized void updateResources(int foodToAdd, int goldToAdd) {
        this.food += foodToAdd;
        this.gold += goldToAdd;
        
        // Starvation Logic: If food < 0, people die
        if(this.food < 0) {
            this.food = 0;
            if(this.population > 0) {
                this.population--; 
                System.out.println("WARNING: " + civilizationName + " is starving!");
            }
        }
    }

    // ---------------------------------------------------------
    // NEW COMBAT METHODS ADDED BELOW (Phase 3 Update)
    // ---------------------------------------------------------

    // Method to handle being attacked [Lecture 1-4, Slide 28]
    // Synchronized to prevent data corruption during battle
    public synchronized String receiveAttack(int enemyDamage) {
        // Logic: Damage reduces Army Strength first
        if (this.armyStrength > 0) {
            int armyLoss = enemyDamage / 2; // Army absorbs damage (takes half)
            this.armyStrength -= armyLoss;
            
            if (this.armyStrength < 0) this.armyStrength = 0;
            return "Defense held! Lost " + armyLoss + " Army Strength.";
        } else {
            // If no Army, Population takes direct damage!
            int popLoss = enemyDamage / 5; // 1 Person dies for every 5 damage
            this.population -= popLoss;
            
            if (this.population < 0) this.population = 0;
            return "CRITICAL HIT! Lost " + popLoss + " Population!";
        }
    }
    
    // Helper to check if game is lost
    public boolean isDefeated() {
        return population <= 0;
    }

    // ---------------------------------------------------------
    // Getters
    // ---------------------------------------------------------
    public String getCivilizationName() { return civilizationName; }
    public int getPopulation() { return population; }
    public int getFood() { return food; }
    public int getGold() { return gold; }
    public int getArmyStrength() { return armyStrength; }
    // NEW: Setters for Loading Game
    public void setArmyStrength(int strength) { this.armyStrength = strength; }
    public void setPopulation(int pop) { this.population = pop; }
}

