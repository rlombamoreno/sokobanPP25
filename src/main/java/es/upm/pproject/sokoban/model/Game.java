package es.upm.pproject.sokoban.model;

public class Game {
	
	private Board board;
	private WarehouseMan player;
	private Box[] boxes;
	private int moves;
	private boolean isFinished;

	public Game(Board board, WarehouseMan player, Box[] boxes) {
		this.board = board;
		this.player = player;
		this.boxes = boxes;
		this.moves = 0;
		this.isFinished = false;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public WarehouseMan getPlayer() {
		return player;
	}

	public void setPlayer(WarehouseMan player) {
		this.player = player;
	}

	public Box[] getBoxes() {
		return boxes;
	}

	public void setBoxes(Box[] boxes) {
		this.boxes = boxes;
	}

	public int getMoves() {
		return moves;
	}

	public void setMoves(int moves) {
		this.moves = moves;
	}

	public boolean isFinished() {
		return isFinished;
	}

	public void setFinished(boolean isFinished) {
		this.isFinished = isFinished;
	}
}
