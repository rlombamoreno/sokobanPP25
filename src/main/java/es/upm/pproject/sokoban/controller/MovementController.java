package es.upm.pproject.sokoban.controller;

import es.upm.pproject.sokoban.model.Board;
import es.upm.pproject.sokoban.model.Position.Direction;

import java.util.ArrayDeque;
import java.util.Deque;

public class MovementController {
    private Board board;
    private Deque<Direction> moveHistoryPlayer;
    

    public MovementController(Board board) {
        this.board = board;
        this.moveHistoryPlayer = new ArrayDeque<>();
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
	public Deque<Direction> getMoveHistoryPlayer() {
		return moveHistoryPlayer;
	}
	public void setMoveHistoryPlayer(Deque<Direction> moveHistory) {
		this.moveHistoryPlayer = moveHistory;
	}
    
}
