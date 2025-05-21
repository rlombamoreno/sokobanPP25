package es.upm.pproject.sokoban.model;

import java.util.List;

import es.upm.pproject.sokoban.model.Cell.CellType;
import es.upm.pproject.sokoban.model.Position.Direction;

public class Board {
	// The board is a 2D array of cells
	private Cell[][] cells;
	private WarehouseMan warehouseman;
	private List<Box> boxes;
	private List<Position> targets;
	
	// The width and height of the board
	private int width;
	private int height;

	// Constructor
	public Board(int width, int height) {
		this.width = width;
		this.height = height;
		boxes = new java.util.ArrayList<>();
		this.targets = new java.util.ArrayList<>();

		cells = new Cell[height][width];
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				cells[i][j] = new Cell(CellType.EMPTY);
			}
		}
	}
	
	public WarehouseMan getWarehouseman() {
		return warehouseman;
	}
	
	public List<Box> getBoxes() {
		return boxes;
	}
	
	public Cell getCell(int x, int y) {
	    if (x >= 0 && x < width && y >= 0 && y < height) {
	        return cells[y][x];
	    }
	    throw new IndexOutOfBoundsException("Invalid cell position.");
	}
	public void setCells(Cell[][] cells) {
		this.cells = cells;
	}
	public Cell[][] getCells() {
		return cells;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}

	public void setCell(int i, int j, Cell cell) {
		if (i >= 0 && i < height && j >= 0 && j < width) {
			cells[i][j] = cell;
			if (cell.isPlayer()) {
				warehouseman = new WarehouseMan(j, i);
			} if (cell.isBox()) {
				boxes.add(new Box(j, i));
			}
		} else {
			throw new IndexOutOfBoundsException("Invalid cell position.");
		}
		
	}

	public boolean movePlayer(Direction direction) {
		Position playerPos = warehouseman.getPosition();
		Position newPos = playerPos.getAdjacent(direction);
		cells[playerPos.getY()][playerPos.getX()].setType(CellType.EMPTY);
		if (isValidMove(newPos,direction)) {
			cells[newPos.getY()][newPos.getX()].setType(CellType.PLAYER);
			warehouseman.move(direction);
			return true;
		} else {
			cells[playerPos.getY()][playerPos.getX()].setType(CellType.PLAYER);
			return false;
		}
	}


	private boolean isValidMove(Position newPos, Direction direction) {
		if (newPos.getX() < 0 || newPos.getX() >= width || newPos.getY() < 0 || newPos.getY() >= height) {
			return false; // Out of bounds
		}
		Cell targetCell = cells[newPos.getY()][newPos.getX()];
		if (targetCell.isWall()) {
			return false; // Wall
		}
		if (targetCell.isBox()) {
			Position boxNewPos = newPos.getAdjacent(direction);
			if (boxNewPos.getX() < 0 || boxNewPos.getX() >= width || boxNewPos.getY() < 0 || boxNewPos.getY() >= height) {
				return false; // Out of bounds
			}
			Box box = findBox(newPos);
			Cell boxTargetCell = cells[boxNewPos.getY()][boxNewPos.getX()];
			if (boxTargetCell.isWall() || boxTargetCell.isBox()) {
				return false; // Wall or another box
			}
			if(targets.contains(boxNewPos)) {
				box.updateOnTarget(true);
			} else {
				box.updateOnTarget(false);
			}
			box.move(boxNewPos.getX() - newPos.getX(), boxNewPos.getY() - newPos.getY());
			boxTargetCell.setType(CellType.BOX);
			
		}
		return true; // Valid move
	}

	private Box findBox(Position newPos) {
		for (Box box : boxes) {
			if (box.getX() == newPos.getX() && box.getY() == newPos.getY()) {
				return box;
			}
		}
		return null;
	}

	public void setTarget(int i, int j) {
		if (i >= 0 && i < height && j >= 0 && j < width) {
			targets.add(new Position(j, i));
		} else {
			throw new IndexOutOfBoundsException("Invalid cell position.");
		}
	}
}
