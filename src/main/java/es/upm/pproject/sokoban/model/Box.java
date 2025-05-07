package es.upm.pproject.sokoban.model;

public class Box {
	
	private Position position;
	private boolean isOnTarget;

	public Box(int x, int y) {
		this.position = new Position(x, y);
		this.isOnTarget = false;
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

	public boolean isOnTarget() {
		return isOnTarget;
	}

	public void setOnTarget(boolean isOnTarget) {
		this.isOnTarget = isOnTarget;
	}

}
