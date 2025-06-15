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

To run the unit tests: 

```sh
mvn clean test jacoco:report
```

The code coverage report is generated at:

```
xdg-open target/site/jacoco/index.html
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