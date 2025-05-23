package es.upm.pproject.sokoban.model;

import es.upm.pproject.sokoban.model.Position.Direction;

public class WarehouseMan {
	
	private Position position;
	private int moves = 0;

	public WarehouseMan(int x, int y) {
		this.position = new Position(x, y);
	}
	public Position getPosition() {
		return position;
	}
	public void move(Direction direction) {
		position = position.getAdjacent(direction);
		moves++;
	}



}
