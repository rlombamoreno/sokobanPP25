package es.upm.pproject.sokoban.model;

public class Level {
	private static int levelNumber = 1;
	private String levelName;
	private Board board;

	public Level(String levelName, Board board) {
	    this.levelName = levelName;
	    this.board = board;
	}

	public int getLevelNumber() {
		return levelNumber;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
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
