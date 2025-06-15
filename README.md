# SOKOBAN GAME

This is the README.md file of our project2025 which contains the implementation of the classic Sokoban puzzle game 

## Introduction

Sokoban is a logic game where the player must push boxes onto target locations within a maze. 
The game controls are: 
- **Arroy keys or W,A,S,D**: to move the player
- **U**:  undo a movement
- **R**: restart current level
- **J**: restart the entire game 
- **U**: save a game
- **G**: load a saved game
- **Q**: exits to the Main Menu 

## Requirements

Ensure you have the following installed before running the project:
- Java 11 or later
- Apache Maven
- GitLab
- SonarQube
- Eclipse IDE or Visual Studio Code

## Installation

Clone the repository and navigate to the project folder:

```sh
git clone https://costa.ls.fi.upm.es/gitlab/220227/project2025.git 
cd project2025
```

Build the project with Maven:

```sh
mvn clean package
```

To run the game after building:

```sh
java -jar target/sokoban-0.0.1-SNAPSHOT.jar
```

Send the project to SonarQube: 

```sh
mvn clean verify sonar:sonar -Dsonar.id=220209 -Dsonar.login=squ_1fe48ae2574d675d716cb499271956fd2220972c
```

## Testing

**Location:** `src/test/java/es/upm/pproject/sokoban/`

- **MainTest.java**  
  Contains JUnit tests cases for validating core game functionalities.

**To run the tests:** 

```sh
mvn test 
```

## Implementations used

This implementation inlcudes: 
- Graphical User Interface (GUI) using Swing.
- Keyboard controls.
- Multiple levels loaded from text files.
- MVC (Model-View-Controller) architecture.
- Unit testing with JUnit.
- Code coverage analysis using JaCoCo.
- Code quality analysis using SonarQube.


## Project Structure

The project's Java classes and tests are structured as follows:

```
project2025/
|
+-- src/
|   +-- main/
|   |--+-- java/
|   |       +-- es/upm/pproject/sokoban/
|   |           +-- Sokoban.java                 # Main game launcher
|   |           |
|   |           +-- controller/                  # User input and logic control
|   |           |   +-- GameController.java
|   |           |   +-- InputHandler.java
|   |           |   +-- MovementController.java
|   |           |
|   |           +-- model/                       # Game logic and domain classes
|   |           |   +-- Board.java
|   |           |   +-- Box.java
|   |           |   +-- Cell.java
|   |           |   +-- Game.java
|   |           |   +-- Level.java
|   |           |   +-- LevelLoader.java
|   |           |   +-- Position.java
|   |           |   +-- WarehouseMan.java
|   |           +-- view/                        # Visual components using Swing (GUI) 
|   |               +-- BoardView.java
|   |               +-- CellView.java
|   |               +-- ErrorView.java
|   |               +-- GameFinishView.java
|   |               +-- GameView.java
|   |               +-- LevelCompleteView.java
|   |               +-- MainMenuView.java
|   |               +-- MenuView.java
|   |    
|   |--+-- resources/                          # Images and configuration
|           +-- box.png
|           +-- equis.png
|           +-- log4j.properties
|           +-- player.png
|           +-- title.png
|
|
|---+-- test/
|       +-- java/es/upm/pproject/sokoban/
|           +-- MainTest.java                    # Unit tests for game logic
|
|
+-- level1.txt                                  # Sokoban level files
+-- level2.txt
+-- level3.txt
+-- level_empty.txt
+-- saved_game.txt                              # Saved game state
+-- pom.xml                                     # Maven configuration
+-- dependency-reduced-pom.xml                  # Result of Maven shading
+-- .gitlab-ci.yml                              # GitLab CI/CD pipeline config
+-- README.md                                   # Project documentation
```



