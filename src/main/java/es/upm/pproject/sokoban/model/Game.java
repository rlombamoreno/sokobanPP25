package es.upm.pproject.sokoban.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;

import es.upm.pproject.sokoban.model.Position.Direction;

public class Game {
    private Level currentLevel;
    private static int totalScore = 0;
    private int levelNumber;

    public Game() {
        this.levelNumber = 1;
        loadLevel(levelNumber);
    }

    // Carga un nivel desde un número
    public final boolean loadLevel(int levelNumber) {
        String filename = "level_" + levelNumber + ".txt"; 
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

    
    public static void increaseScore(Game game) {
		game.currentLevel.increaseScore();
		totalScore++;
	}
    public static void decreaseScore(Game game) {
		if (game.currentLevel.getLevelScore() > 0) {
			game.currentLevel.decreaseScore();
			totalScore--;
		}
	}
    public static void resetLevelScore(Game game) {
		totalScore -= game.currentLevel.getLevelScore();
	}
    
    public static void restartLevelScore() {
    	totalScore = 0;
    }

    // Guardar partida en archivo
    public void saveGame(String filename, Deque<Direction> deque) {
    	Deque<Box> boxHistory = currentLevel.getBoard().getBoxHistory();
    	StringBuilder dequeString = new StringBuilder();
    	for (Box box : boxHistory) {
    	    if (box != null) {
    	    	dequeString.append(box.getX()).append(",").append(box.getY());
    	    } else {
    	    	dequeString.append("null");
    	    }
    	    dequeString.append(";");
    	}
    	
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(levelNumber + "\n");
            writer.write(currentLevel.getLevelScore() + "\n");
            writer.write(totalScore + "\n");
            writer.write(deque.toString() + "\n");
            writer.write(dequeString.toString() + "\n"); // Guardar el nombre del nivel
            writer.write(currentLevel.getLevelName() + "\n"); // Guardar el nombre del nivel
            writer.write(currentLevel.getBoard().getHeight() + " " + currentLevel.getBoard().getWidth()+"\n"); // Guardar dimensiones del tablero
            writer.write(currentLevel.getBoard().toString());
        } catch (IOException e) {
        }
    }

    // Cargar una partida guardada
    public Deque<Direction> loadSavedGame(String filename) {
    	 StringBuilder levelData = new StringBuilder();
    	 int levelScore = 0;
    	 String dequeString = "";
    	 String boxesString = "";
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            this.levelNumber = Integer.parseInt(reader.readLine());
            levelScore = Integer.parseInt(reader.readLine());
            Game.totalScore = Integer.parseInt(reader.readLine());
        	dequeString = reader.readLine(); // Leer la línea del stack
        	boxesString = reader.readLine(); // Leer la línea de las cajas
            String line;
            while ((line = reader.readLine()) != null) {
                levelData.append(line).append("\n"); // Leer línea y agregarla al `StringBuilder`
            }
        } catch (IOException e) {
            return new ArrayDeque<>(); // Retorna una pila vacía si hay un error
        }
        this.currentLevel = LevelLoader.loadLevel(levelData.toString());
        this.currentLevel.setLevelScore(levelScore);
        Deque<Direction> moveHistory = setMoveHistoryPlayer(dequeString);
        Deque<Box> boxHistory = setBoxHistory(boxesString);
        currentLevel.getBoard().setBoxHistory(boxHistory);
        return moveHistory;
    }

    private Deque<Box> setBoxHistory(String boxesString) {
    	Deque<Box> boxHistory = new ArrayDeque<>();
    	String[] moves = boxesString.split(";");
    	Board board = currentLevel.getBoard();
    	for (String move : moves) {
			String[] coordinates = move.split(",");
			try {
				int x = Integer.parseInt(coordinates[0].trim());
				int y = Integer.parseInt(coordinates[1].trim());
				Box box = board.getBoxAt(x, y);
				if (box != null) {
					boxHistory.push(box);
				} else {
					System.out.println("Caja no encontrada en las coordenadas: " + move);
				}
			} catch (NumberFormatException e) {
				System.out.println("Formato de coordenadas inválido: " + move);
			}
		}
		return boxHistory;
	}

	public static int getTotalScore() {
		return totalScore;
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
	
	public Deque<Direction> setMoveHistoryPlayer(String dequeString) {
		Deque<Direction> moveHistory = new ArrayDeque<>();
		dequeString = dequeString.replace("[", "").replace("]", "").trim();
		String[] moves = dequeString.split(",");
		for (String move : moves) {
			if (!move.isEmpty()) {
				switch (move.trim().toUpperCase()) {
					case "UP":
						moveHistory.push(Direction.UP);
						break;
					case "DOWN":
						moveHistory.push(Direction.DOWN);
						break;
					case "LEFT":
						moveHistory.push(Direction.LEFT);
						break;
					case "RIGHT":
						moveHistory.push(Direction.RIGHT);
						break;
					default:
						System.out.println("Movimiento desconocido: " + move);
				}
			}
		}
		return moveHistory;
	}
}
