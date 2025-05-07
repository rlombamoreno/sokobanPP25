package es.upm.pproject.sokoban.model;

public class Cell {
	private boolean isWall;
	private boolean isBox;
	private boolean isTarget;
	private boolean isPlayer;

	public Cell() {
		this.isWall = false;
		this.isBox = false;
		this.isTarget = false;
		this.isPlayer = false;
	}

	public boolean isWall() {
		return isWall;
	}

	public void setWall(boolean isWall) {
		this.isWall = isWall;
	}

	public boolean isBox() {
		return isBox;
	}

	public void setBox(boolean isBox) {
		this.isBox = isBox;
	}

	public boolean isTarget() {
		return isTarget;
	}

	public void setTarget(boolean isTarget) {
		this.isTarget = isTarget;
	}

	public boolean isPlayer() {
		return isPlayer;
	}

	public void setPlayer(boolean isPlayer) {
		this.isPlayer = isPlayer;
	}

	public String getContent() {
		if (isWall) {
			return "Wall";
		} else if (isBox) {
			return "Box";
		} else if (isTarget) {
			return "Target";
		} else if (isPlayer) {
			return "Player";
		} else {
			return " ";
		}
	}

	public boolean isOnGoal() {
		return isTarget && isBox;
	}
	

}
