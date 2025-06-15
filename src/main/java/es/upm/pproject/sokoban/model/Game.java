package es.upm.pproject.sokoban.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import es.upm.pproject.sokoban.model.Position.Direction;

public class Game {
    private Level currentLevel;
    private int totalScore;
    private LevelLoader levelLoader;
    private int levelNumber;

    public Game() {
        this.levelLoader = new LevelLoader();
        this.totalScore = 0;
        this.levelNumber = 1;
        loadLevel(levelNumber);
    }

    // Carga un nivel desde un número
    public boolean loadLevel(int levelNumber) {
        String filename = "level" + levelNumber + ".txt"; 
        StringBuilder levelData = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                levelData.append(line).append("\n"); // Leer línea y agregarla al `StringBuilder`
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + filename);
            return false;
        }

        Level newLevel = LevelLoader.loadLevel(levelData.toString()); // Pasamos el contenido del archivo
        if (newLevel == null) {
            System.out.println("No hay más niveles. ¡Juego terminado!");
            return false;
        }
        
        this.currentLevel = newLevel;
        this.levelNumber = levelNumber;
        return true;
    }

    // Mueve al almacenero
    public boolean movePlayer(Direction direction) {
        return currentLevel.getBoard().movePlayer(direction);
    }

    // Reiniciar el nivel actual
    public void restartLevel() {
        loadLevel(levelNumber);
    }

    // Guardar partida en archivo
    public void saveGame(String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(levelNumber + "\n");
            writer.write(totalScore + "\n");
            writer.write(currentLevel.getBoard().toString());
            System.out.println("Game saved successfully!");
        } catch (IOException e) {
            System.err.println("Error saving game.");
        }
    }

    // Cargar una partida guardada
    public boolean loadSavedGame(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            this.levelNumber = Integer.parseInt(reader.readLine());
            this.totalScore = Integer.parseInt(reader.readLine());
            this.currentLevel = LevelLoader.loadLevel("level" + levelNumber + ".txt");
            System.out.println("Game loaded successfully!");
            return true;
        } catch (IOException e) {
            System.err.println("Error loading game.");
            return false;
        }
    }

    // Mostrar estado del juego
    public void displayGameStatus() {
        System.out.println("Level Name: " + currentLevel.getLevelName());
        System.out.println("Level Number: " + levelNumber);
        System.out.println("Total Score: " + totalScore);
        System.out.println(currentLevel.getBoard().toString()); // Aquí imprimimos el tablero correctamente
    }

    public Level getCurrentLevel() {
        return currentLevel;
    }

    public int getCurrentLevelNumber() {
        return levelNumber;
    }

	public void setCurrentLevelNumber(int i) {
		this.levelNumber = i;
		
	}
}
