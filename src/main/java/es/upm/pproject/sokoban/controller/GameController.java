package es.upm.pproject.sokoban.controller;

import java.util.Stack;

import es.upm.pproject.sokoban.model.Game;
import es.upm.pproject.sokoban.model.Position.Direction;

public class GameController {
	private Game game;
    private MovementController movementController;

    public GameController() {
        this.game = new Game();
        this.movementController = new MovementController(game.getCurrentLevel().getBoard());
    }
    public void startNewGame() {
        game = new Game();
    }   
    public void restartLevel() {
    	game.resetLevelScore();
        game.loadLevel(game.getCurrentLevelNumber());
        movementController = new MovementController(game.getCurrentLevel().getBoard());
    }   
    public boolean movePlayer(Direction direction) {
    	if(movementController.move(direction)) {
    		game.increaseScore();
    		return true;
		}
        return false;
    } 
    public boolean undoLastMove() {
        if(movementController.undoLastMove()) {
        	game.decreaseScore();
        	return true;
        }
        return false;
    }
    public void saveGame(String filename) {
        game.saveGame(filename, movementController.getMoveHistoryPlayer());
    }
    public boolean loadSavedGame(String filename) {
    	Stack<Direction> moveHistory = game.loadSavedGame(filename);
        if(moveHistory != null) {
			updateBoard(moveHistory) ;
			return true;
		}
		return false;
    }
	public MovementController getMovementController() {
		return movementController;
	}
	public Game getGame() {
		return game;
	}
	public void updateBoard() {
		movementController = new MovementController(game.getCurrentLevel().getBoard());
	}
	public void updateBoard(Stack<Direction> moveHistory) {
		movementController = new MovementController(game.getCurrentLevel().getBoard());
		movementController.setMoveHistoryPlayer(moveHistory);
	}
	
}