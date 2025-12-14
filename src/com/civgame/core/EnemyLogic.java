package com.civgame.core;

import java.util.Random;

public class EnemyLogic extends Thread {
    
    private Civilization enemy;
    private boolean isRunning = true;
    private Random random = new Random();

    public EnemyLogic(Civilization enemy) {
        this.enemy = enemy;
    }

    @Override
    public void run() {
        System.out.println("Enemy AI Started...");
        
        while (isRunning && !enemy.isDefeated()) {
            try {
                // Enemy thinks for 3-6 seconds
                int thinkTime = 3000 + random.nextInt(3000); 
                Thread.sleep(thinkTime);

                // AI DECISION LOGIC:
                // 1. If low on food/gold, Gather Resources.
                // 2. If rich, Train Army.
                
                if (enemy.getGold() >= 20 && enemy.getFood() >= 10) {
                    // 60% chance to train army if rich
                    if (random.nextBoolean()) {
                        enemy.trainArmy();
                        System.out.println("[ENEMY AI] Water Tribe trained soldiers!");
                    } else {
                        enemy.updateResources(10, 10); // Gather
                        System.out.println("[ENEMY AI] Water Tribe gathered resources.");
                    }
                } else {
                    // Too poor, must gather
                    enemy.updateResources(15, 5);
                    System.out.println("[ENEMY AI] Water Tribe is farming...");
                }

            } catch (InterruptedException e) {
                System.out.println("Enemy AI Stopped.");
            }
        }
    }

    public void stopAI() {
        this.isRunning = false;
    }
}