package es.upm.pproject.sokoban.model;

public class Level {
	private int levelNumber;
	private String levelName;
	private Board board;
	
	public Level(String levelName, Board board) {
	    this.levelName = levelName;
	    this.board = board;
	}
	public Level(String levelName, Board board, int levelNumber) {
	    this.levelName = levelName;
	    this.board = board;
	    this.levelNumber = levelNumber;
	}

	public int getLevelNumber() {
		return levelNumber;
	}

	public String getLevelName() {
		return levelName;
	}
	
	public Board getBoard() {
		return board;
	}

   	public boolean isLevelComplete() {
   	    for (Box box : board.getBoxes()) {
   	        if (!box.isOnTarget()) {
   	            return false;
   	        } 
   	    }
   	    return true;
   	}


}
