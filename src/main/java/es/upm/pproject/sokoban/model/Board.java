package es.upm.pproject.sokoban.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import es.upm.pproject.sokoban.model.Cell.CellType;
import es.upm.pproject.sokoban.model.Position.Direction;

public class Board {
	private Cell[][] cells;
	private WarehouseMan warehouseman;
	private List<Box> boxes;
	private List<Position> targets;
	private Stack<Box> boxHistory;
	
	
	// The width and height of the board
	private int width;
	private int height;

	// Constructor
	public Board(int width, int height) {
		this.width = width;
		this.height = height;
		boxes = new ArrayList<>();
		this.targets = new ArrayList<>();
		boxHistory = new Stack<>();

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
	
	public Cell getCell(int i, int j) {
	    if (i >= 0 && i < height  && j >= 0 && j < width) {
	        return cells[i][j];
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
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
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
		
		if (!isValidMove(newPos,direction)) {
			return false;
		}
		cells[playerPos.getY()][playerPos.getX()].setType(CellType.EMPTY);
		cells[newPos.getY()][newPos.getX()].setType(CellType.PLAYER);
		warehouseman.move(direction);
		return true;
	}


	private boolean isValidMove(Position newPos, Direction direction) {
		if (newPos.getX() < 0 || newPos.getX() >= width || newPos.getY() < 0 || newPos.getY() >= height) {
			return false; // Out of bounds
		}
		Cell targetCell = cells[newPos.getY()][newPos.getX()];
		if (targetCell.isWall()) {
			return false; // Wall
		}else if (targetCell.isBox()) {
			Position boxNewPos = newPos.getAdjacent(direction);
			if (boxNewPos.getX() < 0 || boxNewPos.getX() >= width || boxNewPos.getY() < 0 || boxNewPos.getY() >= height) {
				return false; // Out of bounds
			}
			Box box = findBox(newPos);
			Cell boxTargetCell = cells[boxNewPos.getY()][boxNewPos.getX()];
			if (boxTargetCell.isWall() || boxTargetCell.isBox()) {
				return false; // Wall or another box
			}
			
			if(box == null) {
				return false; // No box at the target position
			}
			box.updateOnTarget(targets.contains(boxNewPos));
			
			box.move(boxNewPos.getX() - newPos.getX(), boxNewPos.getY() - newPos.getY());
			boxTargetCell.setType(CellType.BOX);
			boxHistory.push(box);
			
		}else {
			boxHistory.push(null);
		}
		return true;
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
	
	public Stack<Box> getBoxHistory() {
		return boxHistory;
	}
	
	public boolean isLevelCompleted() {
		for (Box box : boxes) {
			if (!box.isOnTarget()) {
				return false; 
			}
		}
		return true; 
	}

	public boolean undoLastMove(Direction oppositeMove) {
		Box box = boxHistory.pop();
		Position playerPos = warehouseman.getPosition();
		Position newPos = playerPos.getAdjacent(oppositeMove);
		if(box != null) {
			Cell targetBoxCell = cells[box.getY()][box.getX()];
			targetBoxCell.setType(CellType.EMPTY);
			box.updateOnTarget(targets.contains(playerPos));
			cells[playerPos.getY()][playerPos.getX()].setType(CellType.BOX);
			box.move(playerPos.getX()-box.getX(), playerPos.getY()-box.getY());
		}else {
			cells[playerPos.getY()][playerPos.getX()].setType(CellType.EMPTY);
		}
		cells[newPos.getY()][newPos.getX()].setType(CellType.PLAYER);
		warehouseman.move(oppositeMove);
		return true;
		
	}
	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    for (int i = 0; i < height; i++) {
	        for (int j = 0; j < width; j++) {
	            char symbol = ' ';
	            Cell cell = cells[i][j];
	            if (cell.isWall()) symbol = '+';
	            else if (cell.isBox()) symbol = '#';
	            else if (targets.contains(new Position(j, i))) symbol = '*';
	            else if (cell.isPlayer()) symbol = 'W';
	            else symbol = ' ';

	            sb.append(symbol);
	        }
	        sb.append("\n"); 
	    }
	    return sb.toString();
	}

	public Box getBoxAt(int x, int y) {
		for (Box box : boxes) {
			if (box.getX() == x && box.getY() == y) {
				return box;
			}
		}
		return null;
	}

	public void setBoxHistory(Stack<Box> boxHistory2) {
		this.boxHistory = boxHistory2;
	}



}
