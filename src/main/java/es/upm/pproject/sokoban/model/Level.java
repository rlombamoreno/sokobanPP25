package es.upm.pproject.sokoban.model;

public class Level {
	private int levelNumber;
	private String levelName;
	private String levelDescription;
	private int width;
	private int height;
	private Board board;

	public Level(int levelNumber, String levelName, String levelDescription, int width, int height) {
	    this.levelNumber = levelNumber;
	    this.levelName = levelName;
	    this.levelDescription = levelDescription;
	    this.board = new Board(width, height);
	}

	public int getLevelNumber() {
		return levelNumber;
	}

	public void setLevelNumber(int levelNumber) {
		this.levelNumber = levelNumber;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public String getLevelDescription() {
		return levelDescription;
	}

	public void setLevelDescription(String levelDescription) {
		this.levelDescription = levelDescription;
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
   	public Board getBoard() {
		return board;
	}
   	public void setBoard(Board board) {
		this.board = board;
	}
   	public boolean isLevelComplete() {
   	    for (Cell[] row : board.getCells()) { 
   	        for (Cell cell : row) {
   	            if (cell.getContent().equals("Box") && !cell.isOnGoal()) {
   	                return false;
   	            }
   	        }
   	    }
   	    return true;
   	}


}
