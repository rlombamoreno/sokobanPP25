package es.upm.pproject.sokoban.controller;

import java.util.Deque;

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
    	Game.resetLevelScore(game);
        game.loadLevel(game.getCurrentLevelNumber());
        movementController = new MovementController(game.getCurrentLevel().getBoard());
    }   
    public boolean movePlayer(Direction direction) {
    	if(movementController.move(direction)) {
    		Game.increaseScore(game);
    		return true;
		}
        return false;
    } 
    public boolean undoLastMove() {
        if(movementController.undoLastMove()) {
        	Game.decreaseScore(game);
        	return true;
        }
        return false;
    }
    public void saveGame(String filename) {
        game.saveGame(filename, movementController.getMoveHistoryPlayer());
    }
    public boolean loadSavedGame(String filename) {
    	Deque<Direction> moveHistory = game.loadSavedGame(filename);
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
	public void updateBoard(Deque<Direction> moveHistory) {
		movementController = new MovementController(game.getCurrentLevel().getBoard());
		movementController.setMoveHistoryPlayer(moveHistory);
	}
	
}