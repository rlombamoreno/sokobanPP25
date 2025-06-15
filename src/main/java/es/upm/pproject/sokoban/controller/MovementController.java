package es.upm.pproject.sokoban.controller;

import es.upm.pproject.sokoban.model.Board;
import es.upm.pproject.sokoban.model.Position.Direction;
import java.util.Stack;

public class MovementController {
    private Board board;
    private Stack<Direction> moveHistoryPlayer;
    

    public MovementController(Board board) {
        this.board = board;
        this.moveHistoryPlayer = new Stack<>();
    }
    public boolean move(Direction direction) {
        boolean moved = board.movePlayer(direction);
        if(moved)
        	moveHistoryPlayer.push(direction);
        return moved;
    }
    public Board getCurrentBoard() {
		return board;
	}
    // Deshacer el último movimiento
    public boolean undoLastMove() {
        if (moveHistoryPlayer.isEmpty()) {
            return false;
        }
        Direction lastMove = moveHistoryPlayer.pop();
        Direction oppositeMove = lastMove.getOpposite();
        return board.undoLastMove(oppositeMove);
    }
    
}
