package com.civgame.core;

import java.io.*; // [Lecture on File Handling - inferred requirement]

public class GameSaver {

    // Saves current stats to a text file
    public static void saveGame(Civilization player) throws IOException {
        // Create a file writer
        FileWriter writer = new FileWriter("civilization_save.txt");
        
        // Write the data in a simple format: Gold,Food,Army,Pop
        writer.write(player.getGold() + "\n");
        writer.write(player.getFood() + "\n");
        writer.write(player.getArmyStrength() + "\n");
        writer.write(player.getPopulation() + "\n");
        
        writer.close(); // Always close the file!
    }

    // Loads stats from the text file
    public static void loadGame(Civilization player) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("civilization_save.txt"));
        
        // Read lines one by one and convert String to Int
        int savedGold = Integer.parseInt(reader.readLine());
        int savedFood = Integer.parseInt(reader.readLine());
        int savedArmy = Integer.parseInt(reader.readLine());
        int savedPop = Integer.parseInt(reader.readLine());

        // ... inside loadGame ...
    player.updateResources(savedFood - player.getFood(), savedGold - player.getGold());
    player.setArmyStrength(savedArmy);
    player.setPopulation(savedPop);
    reader.close();
        
        // Apply to player (You need a setter method for this, or just cheat a bit)
        // Since we didn't make setters for everything, let's just add the difference
        // A cleaner way is to add a "loadState" method to Civilization, but this works for now:
        player.updateResources(savedFood - player.getFood(), savedGold - player.getGold());
        
        // For Army and Pop, we need to hack it slightly since we only have 'trainArmy'
        // Ideally, go back to Civilization.java and add 'setArmyStrength(int v)'
        
        reader.close();
    }
}