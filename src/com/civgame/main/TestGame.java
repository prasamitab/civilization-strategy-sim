package com.civgame.main;

// IMPORTS: This tells Java to look in the 'core' folder for your classes
import com.civgame.core.Civilization;
import com.civgame.core.FireTribe;
import com.civgame.core.ResourceThread;

public class TestGame {
    public static void main(String[] args) {
        System.out.println("--- STARTING TEST ---");

        // 1. Create the Fire Tribe
        Civilization player1 = new FireTribe();
        System.out.println("Created: " + player1.getCivilizationName());
        System.out.println("Initial Food: " + player1.getFood());
        System.out.println("Initial Gold: " + player1.getGold());
        
        // 2. Start the Background Thread (The "30 Mark" Feature)
        ResourceThread gameTimer = new ResourceThread(player1);
        gameTimer.start(); 
        System.out.println(">> Background Thread Started...");
        
        // 3. Simulate a User Action
        System.out.println(">> Attempting to train army...");
        player1.trainArmy(); 
        
        // The program will now keep running.
        // Watch the console log! You should see updates every 5 seconds.
    }
}