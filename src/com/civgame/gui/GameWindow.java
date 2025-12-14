package com.civgame.gui;

import javax.swing.*;       
import java.awt.*;          
import java.awt.event.*;
import java.io.IOException; // Needed for File I/O    

// Import Core Logic
import com.civgame.core.Civilization;
import com.civgame.core.FireTribe;
import com.civgame.core.WaterTribe;
import com.civgame.core.ResourceThread;
import com.civgame.core.CombatSystem;
import com.civgame.core.EnemyLogic; // Renamed from EnemyAIThread
import com.civgame.core.GameSaver;  // Import GameSaver

// Import Exception
import com.civgame.exceptions.InsufficientResourcesException;

public class GameWindow extends JFrame {

    // Layout & Panels
    private CardLayout cardLayout;
    private JPanel mainPanel;
    
    // Game Objects
    private Civilization playerCiv;
    private Civilization enemyCiv; 
    
    // Threads
    private ResourceThread gameThread;
    private EnemyLogic enemyThread; 
    private Timer guiTimer; 
    
    // GUI Labels
    private JLabel goldLabel, foodLabel, armyLabel, populationLabel;
    private JLabel enemyGoldLabel, enemyFoodLabel, enemyArmyLabel, enemyPopLabel;

    public GameWindow() {
        // 1. Frame Setup
        setTitle("Civilization Strategy Sim");
        setSize(950, 650); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 

        // 2. Initialize Logic
        playerCiv = new FireTribe(); 
        enemyCiv = new WaterTribe(); 
        
        gameThread = new ResourceThread(playerCiv);
        enemyThread = new EnemyLogic(enemyCiv); 

        // 3. Layout Setup
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // --- MENU SCREEN ---
        JPanel menuPanel = new JPanel(new GridBagLayout());
        menuPanel.setBackground(Color.DARK_GRAY);
        
        // Main Menu Buttons
        JButton startButton = new JButton("NEW GAME");
        startButton.setFont(new Font("Arial", Font.BOLD, 24));
        
        JButton loadButton = new JButton("LOAD GAME");
        loadButton.setFont(new Font("Arial", Font.BOLD, 18));
        loadButton.setBackground(Color.LIGHT_GRAY);

        // Add buttons to menu using GridBag constraints
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 0; gbc.insets = new Insets(10,0,10,0);
        menuPanel.add(startButton, gbc);
        
        gbc.gridy = 1;
        menuPanel.add(loadButton, gbc);

        // --- GAME SCREEN ---
        JPanel gameDashboard = createGameDashboard();

        // Add screens to CardLayout
        mainPanel.add(menuPanel, "MENU");
        mainPanel.add(gameDashboard, "GAME");

        add(mainPanel);

        // --- EVENTS ---

        // Start Button Action
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startGame();
            }
        });

        // Load Button Logic (Menu)
        loadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    GameSaver.loadGame(playerCiv);
                    JOptionPane.showMessageDialog(mainPanel, "Game Loaded Successfully!");
                    startGame(); // Go to game screen after loading
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(mainPanel, "No Save File Found!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
    
    // Helper to start threads and switch screens
    private void startGame() {
        cardLayout.show(mainPanel, "GAME"); 
        gameThread.start();  
        enemyThread.start(); 
        
        // Start the Auto-Refresher Timer (Every 1 second)
        guiTimer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateLabels(); 
            }
        });
        guiTimer.start();
    }

    private JPanel createGameDashboard() {
        JPanel dashboard = new JPanel(new GridLayout(1, 2)); 

        // --- LEFT: PLAYER ---
        JPanel playerPanel = new JPanel(new GridLayout(9, 1)); 
        playerPanel.setBackground(new Color(255, 200, 200)); 
        playerPanel.setBorder(BorderFactory.createTitledBorder("FIRE TRIBE (YOU)"));

        // Initialize Player Labels
        goldLabel = new JLabel(); 
        foodLabel = new JLabel();
        armyLabel = new JLabel(); 
        populationLabel = new JLabel();

        // Initialize Buttons
        JButton trainBtn = new JButton("Train Army (-20 Gold, -10 Food)");
        JButton harvestBtn = new JButton("Harvest Food (+15 Food)");
        
        JButton attackBtn = new JButton("ATTACK ENEMY (-10 Gold)");
        attackBtn.setBackground(new Color(255, 100, 100));
        attackBtn.setForeground(Color.WHITE);
        
        JButton saveBtn = new JButton("SAVE GAME"); 
        saveBtn.setBackground(new Color(100, 255, 100)); 

        // Add Components to Player Panel
        playerPanel.add(goldLabel); 
        playerPanel.add(foodLabel);
        playerPanel.add(armyLabel); 
        playerPanel.add(populationLabel);
        playerPanel.add(trainBtn); 
        playerPanel.add(harvestBtn);
        playerPanel.add(attackBtn); 
        playerPanel.add(new JSeparator());
        playerPanel.add(saveBtn); 

        // --- RIGHT: ENEMY ---
        JPanel enemyPanel = new JPanel(new GridLayout(9, 1)); 
        enemyPanel.setBackground(new Color(200, 200, 255)); 
        enemyPanel.setBorder(BorderFactory.createTitledBorder("ENEMY WATER TRIBE"));

        // Initialize Enemy Labels
        enemyGoldLabel = new JLabel(); 
        enemyFoodLabel = new JLabel();
        enemyArmyLabel = new JLabel(); 
        enemyPopLabel = new JLabel();
        
        // Add Components to Enemy Panel
        enemyPanel.add(enemyGoldLabel); 
        enemyPanel.add(enemyFoodLabel);
        enemyPanel.add(enemyArmyLabel); 
        enemyPanel.add(enemyPopLabel);
        
        // Fill rest with spacers to look neat
        for(int i=0; i<5; i++) enemyPanel.add(new JLabel("")); 

        // Add panels to Dashboard
        dashboard.add(playerPanel);
        dashboard.add(enemyPanel);

        // --- BUTTON ACTIONS ---
        
        trainBtn.addActionListener(e -> { playerCiv.trainArmy(); updateLabels(); });
        harvestBtn.addActionListener(e -> { playerCiv.updateResources(15, 0); updateLabels(); });

        // Save Button Logic
        saveBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    GameSaver.saveGame(playerCiv);
                    JOptionPane.showMessageDialog(mainPanel, "Game Saved to 'civilization_save.txt'!");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(mainPanel, "Error Saving: " + ex.getMessage());
                }
            }
        });

        // Attack Logic
        attackBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String result = CombatSystem.resolveBattle(playerCiv, enemyCiv);
                    JOptionPane.showMessageDialog(mainPanel, result, "Battle Report", JOptionPane.INFORMATION_MESSAGE);
                    
                    if(enemyCiv.isDefeated()) {
                        JOptionPane.showMessageDialog(mainPanel, "VICTORY!", "GAME OVER", JOptionPane.WARNING_MESSAGE);
                        gameThread.stopGame(); 
                        enemyThread.stopAI();
                        guiTimer.stop();
                    }
                    updateLabels(); 
                } catch (InsufficientResourcesException ex) {
                    JOptionPane.showMessageDialog(mainPanel, ex.getMessage(), "Attack Failed", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // CRITICAL FIX: Call this only AFTER all labels are initialized!
        updateLabels(); 

        return dashboard;
    }

    // Refresh ALL labels (Player + Enemy)
    public void updateLabels() {
        if(goldLabel != null) { // Extra safety check
            goldLabel.setText("Gold: " + playerCiv.getGold());
            foodLabel.setText("Food: " + playerCiv.getFood());
            armyLabel.setText("Army Strength: " + playerCiv.getArmyStrength());
            populationLabel.setText("Population: " + playerCiv.getPopulation());
        }
        
        if(enemyGoldLabel != null) { // Extra safety check
            enemyGoldLabel.setText("Gold: " + enemyCiv.getGold());
            enemyFoodLabel.setText("Food: " + enemyCiv.getFood());
            enemyArmyLabel.setText("Army Strength: " + enemyCiv.getArmyStrength());
            enemyPopLabel.setText("Population: " + enemyCiv.getPopulation());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GameWindow().setVisible(true));
    }
}