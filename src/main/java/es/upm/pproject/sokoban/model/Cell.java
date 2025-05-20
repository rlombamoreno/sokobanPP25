package es.upm.pproject.sokoban.model;

public class Cell {
	
	public enum CellType { EMPTY, WALL, BOX, TARGET, PLAYER }

	private CellType type;


	public Cell(CellType type) {
		this.type = type;
	}

	public boolean isWall() {
		return type == CellType.WALL;
	}

	public void setType(CellType type) {
		this.type = type;
	}

	public boolean isBox() {
		return type == CellType.BOX;
	}


	public boolean isTarget() {
		return type == CellType.TARGET;
	}


	public boolean isPlayer() {
		return type == CellType.PLAYER;
	}

	public String getContent() {
		if (isWall()) {
			return "Wall";
		} else if (isBox()) {
			return "Box";
		} else if (isTarget()) {
			return "Target";
		} else if (isPlayer()) {
			return "Player";
		} else {
			return " ";
		}
	}
	

}
