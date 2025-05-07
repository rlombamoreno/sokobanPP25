package es.upm.pproject.sokoban.model;

public class WarehouseMan {
	
	private Position position;

	public WarehouseMan(int x, int y) {
		this.position = new Position(x, y);
	}

	public int getX() {
		return position.getX();
	}

	public void setX(int x) {
		this.position.setX(x);
	}

	public int getY() {
		return position.getY();
	}

	public void setY(int y) {
		this.position.setY(y);
	}

}
