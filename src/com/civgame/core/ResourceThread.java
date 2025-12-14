package com.civgame.core;

// This thread runs in the background to simulate time passing.
public class ResourceThread extends Thread {
    
    Civilization civ;
    boolean isRunning = true;

    public ResourceThread(Civilization c) {
        this.civ = c;
    }

    public void run() {
        while(isRunning) {
            try {
                // Sleep for 5 seconds (5000 milliseconds)
                Thread.sleep(5000); 
                
                // Every 5 seconds, population produces Gold but eats Food.
                // +5 Gold, -2 Food
                civ.updateResources(-2, 5); 
                
                // Print to console to prove it works (for now)
                System.out.println("[Time Passed] " + civ.getCivilizationName() + 
                                   " Food: " + civ.getFood() + 
                                   " Gold: " + civ.getGold());
                
            } catch (InterruptedException e) {
                System.out.println("Game Loop Interrupted");
            }
        }
    }

    // Stops the thread when the game ends
    public void stopGame() {
        this.isRunning = false;
    }
}