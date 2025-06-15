package es.upm.pproject.sokoban.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Stack;

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
    public boolean loadLevel(int levelNumber) {
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

    
    public void increaseScore() {
		currentLevel.increaseScore();
		totalScore++;
	}
    public void decreaseScore() {
		if (currentLevel.getLevelScore() > 0) {
			currentLevel.decreaseScore();
			totalScore--;
		}
	}
    public void resetLevelScore() {
		totalScore -= currentLevel.getLevelScore();
	}
    
    public void restartLevelScore() {
    	totalScore = 0;
    }

    // Guardar partida en archivo
    public void saveGame(String filename, Stack<Direction> stack) {
    	Stack<Box> boxHistory = currentLevel.getBoard().getBoxHistory();
    	StringBuilder stackString = new StringBuilder();
    	for (Box box : boxHistory) {
    	    if (box != null) {
    	    	stackString.append(box.getX()).append(",").append(box.getY());
    	    } else {
    	    	stackString.append("null");
    	    }
    	    stackString.append(";");
    	}
    	
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(levelNumber + "\n");
            writer.write(currentLevel.getLevelScore() + "\n");
            writer.write(totalScore + "\n");
            writer.write(stack.toString() + "\n");
            writer.write(stackString.toString() + "\n"); // Guardar el nombre del nivel
            writer.write(currentLevel.getLevelName() + "\n"); // Guardar el nombre del nivel
            writer.write(currentLevel.getBoard().getHeight() + " " + currentLevel.getBoard().getWidth()+"\n"); // Guardar dimensiones del tablero
            writer.write(currentLevel.getBoard().toString());
        } catch (IOException e) {
        }
    }

    // Cargar una partida guardada
    public Stack<Direction> loadSavedGame(String filename) {
    	 StringBuilder levelData = new StringBuilder();
    	 int levelScore = 0;
    	 String stackString = "";
    	 String boxesString = "";
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            this.levelNumber = Integer.parseInt(reader.readLine());
            levelScore = Integer.parseInt(reader.readLine());
            Game.totalScore = Integer.parseInt(reader.readLine());
        	stackString = reader.readLine(); // Leer la línea del stack
        	boxesString = reader.readLine(); // Leer la línea de las cajas
            String line;
            while ((line = reader.readLine()) != null) {
                levelData.append(line).append("\n"); // Leer línea y agregarla al `StringBuilder`
            }
        } catch (IOException e) {
            return new Stack<>(); // Retorna una pila vacía si hay un error
        }
        this.currentLevel = LevelLoader.loadLevel(levelData.toString());
        this.currentLevel.setLevelScore(levelScore);
        Stack<Direction> moveHistory = setMoveHistoryPlayer(stackString);
        Stack<Box> boxHistory = setBoxHistory(boxesString);
        currentLevel.getBoard().setBoxHistory(boxHistory);
        return moveHistory;
    }

    private Stack<Box> setBoxHistory(String boxesString) {
    	Stack<Box> boxHistory = new Stack<>();
    	String[] moves = boxesString.split(";");
    	Board board = currentLevel.getBoard();
    	for (String move : moves) {
			if (!move.isEmpty() && !move.equals("null")) {
				String[] coordinates = move.split(",");
				if (coordinates.length == 2) {
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
			}
			if (move.equals("null")) {
				boxHistory.push(null);
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
	
	public Stack<Direction> setMoveHistoryPlayer(String stackString) {
		Stack<Direction> moveHistory = new Stack<>();
		stackString = stackString.replace("[", "").replace("]", "").trim();
		String[] moves = stackString.split(",");
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
