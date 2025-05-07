package es.upm.pproject.sokoban.model;

public class Board {
	// The board is a 2D array of cells
	private Cell[][] cells;
	
	// The width and height of the board
	private int width;
	private int height;

	// Constructor
	public Board(int width, int height) {
		this.width = width;
		this.height = height;
		cells = new Cell[height][width];
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				cells[i][j] = new Cell();
			}
		}
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
		} else {
			throw new IndexOutOfBoundsException("Invalid cell position.");
		}
		
	}
}
