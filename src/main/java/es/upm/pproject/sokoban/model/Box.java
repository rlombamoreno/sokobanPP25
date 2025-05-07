package es.upm.pproject.sokoban.model;

public class Box {
	
	private int x;
	private int y;
	private boolean isOnTarget;

	public Box(int x, int y) {
		this.x = x;
		this.y = y;
		this.isOnTarget = false;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public boolean isOnTarget() {
		return isOnTarget;
	}

	public void setOnTarget(boolean isOnTarget) {
		this.isOnTarget = isOnTarget;
	}

}
