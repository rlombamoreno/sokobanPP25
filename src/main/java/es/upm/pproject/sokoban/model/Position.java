package es.upm.pproject.sokoban.model;

import java.util.Objects;

public class Position {
    private int x;
    private int y;
    public enum Direction { UP, DOWN, LEFT, RIGHT;

	public Direction getOpposite() {
		switch (this) {
			case UP: return DOWN;
			case DOWN: return UP;
			case LEFT: return RIGHT;
			case RIGHT: return LEFT;
			default: throw new IllegalArgumentException("Invalid direction");
		}
	} }
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    public void setX(int x) {
		this.x = x;
	}
    
    public void setY(int y) {
    	this.y = y;
    }

    public Position getAdjacent(Direction direction) {
        switch (direction) {
            case UP: return new Position(x, y - 1);
            case DOWN: return new Position(x, y + 1);
            case LEFT: return new Position(x - 1, y);
            case RIGHT: return new Position(x + 1, y);
            default: throw new IllegalArgumentException("Invalid direction");
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Position)) return false;
        Position other = (Position) obj;
        return this.x == other.x && this.y == other.y;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
