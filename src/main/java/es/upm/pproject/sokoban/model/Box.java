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


	public int getY() {
		return position.getY();
	}

   public void move(int dx, int dy) {
	   	position.setX(position.getX() + dx);
	   	position.setY(position.getY() + dy);
   }
	public void updateOnTarget(Cell cell) {
		isOnTarget = cell.isTarget();
	}

}
