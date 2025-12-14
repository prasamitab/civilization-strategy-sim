# Civilization Strategy Sim 🔥💧

Author

Prasamita Bangal | 
Int M.Tech CSE Student | 
Mahindra University

A Java-based turn-based civilization strategy game built using **Swing GUI**, **multithreading**, and **OOP principles**.

## Game Overview

- Player controls the **Fire Tribe**
- Enemy AI controls the **Water Tribe**
- Resources (Food & Gold) update automatically over time
- Players can train armies, attack enemies, and save/load game state
- Enemy civilization acts independently using AI logic


## Key Features

- **Swing GUI** using `JFrame`, `CardLayout`, and `Timer`
- **Multithreading**
  - `ResourceThread` simulates time-based resource generation
  - `EnemyLogic` runs enemy AI decisions concurrently
- **OOP Concepts**
  - Abstraction (`Civilization`)
  - Inheritance (`FireTribe`, `WaterTribe`)
  - Polymorphism (`calculateAttackDamage()`)
  - Encapsulation (private/protected fields)
- **Combat System** with exception handling
- **Save & Load** game functionality using file handling
- **Custom Exceptions** for invalid actions

## Project Structure
src/
└── com/civgame
├── core
│ ├── Civilization.java
│ ├── FireTribe.java
│ ├── WaterTribe.java
│ ├── CombatSystem.java
│ ├── ResourceThread.java
│ ├── EnemyLogic.java
│ └── GameSaver.java
│
├── gui
│ └── GameWindow.java
│
├── exceptions
│ └── InsufficientResourcesException.java
│
└── main
└── TestGame.java


## How to Run
```bash
javac -d bin src/com/civgame/**/*.java
java -cp bin com.civgame.gui.GameWindow
Concepts Used

1] Abstraction & Inheritance
2] Polymorphism
3] Multithreading
4] File I/O
5] Exception Handling
6] Java Swing GUI

##  How to Run the Game

### Compile all source files
From the project root:

```bash
javac -d bin src/com/civgame/**/*.java
### Run the GUI

java -cp bin com.civgame.gui.GameWindow
### Save File
Game state is saved to:


civilization_save.txt
Includes:

Gold

Food

Army Strength

Population


