package es.upm.pproject.sokoban.model;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Stack;


import es.upm.pproject.sokoban.model.Position.Direction;

public class Game {
	private Level currentLevel;
    private int totalScore; 
    private Stack<Direction> moveHistory; 
    private LevelLoader levelLoader;
    private int levelNumber;

    public Game() {
        this.levelLoader = new LevelLoader();
        this.moveHistory = new Stack<>();
        this.totalScore = 0;
        this.levelNumber = 1; 
        loadLevel(levelNumber);
    }

    public boolean loadLevel(int levelNumber) {
        String filename = "level_" + levelNumber + ".txt";
        Level newLevel = levelLoader.loadLevel(filename);
        if (newLevel == null) {
            System.out.println("No more levels. Game Over!");
            return false;
        }
        this.currentLevel = newLevel;
        this.levelNumber = levelNumber;
        return true;
    }

    public boolean movePlayer(Direction direction) {
        if (currentLevel.getBoard().movePlayer(direction)) {
            moveHistory.push(direction);
            totalScore++;
            if (currentLevel.isLevelComplete()) {
                System.out.println("Level " + levelNumber + " completed!");
                loadLevel(levelNumber + 1);
            }
            return true;
        }
        return false;
    }
    
    public boolean undoMove() {
        if (!moveHistory.isEmpty()) {
            Direction lastMove = moveHistory.pop();
            currentLevel.getBoard().movePlayer(lastMove.getOpposite());
            totalScore--; // Reducir puntuación
            return true;
        }
        return false;
    }
    
    public void saveGame(String filename) {
        try (FileWriter writer = new FileWriter("saved_level.txt")) {
            writer.write(levelNumber + "\n");
            writer.write(totalScore + "\n");
            writer.write(currentLevel.getBoard().toString());
            System.out.println("Game saved successfully!");
        } catch (IOException e) {
            System.err.println("Error saving game.");
        }
    }
    
    public boolean loadSavedGame(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            this.levelNumber = Integer.parseInt(reader.readLine());
            this.totalScore = Integer.parseInt(reader.readLine());
            this.currentLevel = levelLoader.loadLevel("saved_level.txt");
            System.out.println("Game loaded successfully!");
            return true;
        } catch (IOException e) {
            System.err.println("Error loading game.");
            return false;
        }
    }
    
    public void displayGameStatus() {
        System.out.println("Level: " + levelNumber);
        System.out.println("Current Score: " + totalScore);
        System.out.println(currentLevel.getBoard());
    }
}