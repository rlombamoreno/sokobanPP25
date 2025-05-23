package es.upm.pproject.sokoban.controller;

import es.upm.pproject.sokoban.model.Board;
import es.upm.pproject.sokoban.model.Position.Direction;
import java.util.Stack;

public class MovementController {
    private Board board;
    private Stack<Direction> moveHistory;

    public MovementController(Board board) {
        this.board = board;
        this.moveHistory = new Stack<>();
    }
    public boolean move(Direction direction) {
        boolean moved = board.movePlayer(direction);
        if(moved)
        	moveHistory.push(direction);
        return moved;
    }

    // Deshacer el último movimiento
    public boolean undoLastMove() {
        if (moveHistory.isEmpty()) {
            return false;
        }
        Direction lastMove = moveHistory.pop();
        Direction oppositeMove = lastMove.getOpposite();
        return board.movePlayer(oppositeMove);
    }
    
}
