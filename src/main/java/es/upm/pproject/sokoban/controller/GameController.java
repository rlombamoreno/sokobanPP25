package es.upm.pproject.sokoban.controller;

import es.upm.pproject.sokoban.model.Game;
import es.upm.pproject.sokoban.model.Position.Direction;

public class GameController {
	private Game game;
    private LevelController levelController;
    private MovementController movementController;

    public GameController() {
        this.game = new Game();
        this.levelController = new LevelController();
        this.movementController = new MovementController(game.getCurrentLevel().getBoard());
    }
    public void startNewGame() {
        game = new Game();
    }   
    public void restartLevel() {
        game.loadLevel(game.getCurrentLevelNumber());
        movementController = new MovementController(game.getCurrentLevel().getBoard());
    }   
    public boolean movePlayer(Direction direction) {
        return movementController.move(direction);
    } 
    public boolean undoLastMove() {
        return movementController.undoLastMove();
    }
    public void saveGame(String filename) {
        game.saveGame(filename);
    }
    public boolean loadSavedGame(String filename) {
        return game.loadSavedGame(filename);
    }
    public void displayGameStatus() {
        game.displayGameStatus();
    }
	public MovementController getMovementController() {
		return movementController;
	}
}