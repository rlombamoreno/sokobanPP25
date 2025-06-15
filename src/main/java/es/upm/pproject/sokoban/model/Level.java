package es.upm.pproject.sokoban.model;

public class Level {
	private String levelName;
	private Board board;
	private int levelScore;
	
	public Level(String levelName, Board board) {
	    this.levelName = levelName;
	    this.board = board;
	    this.levelScore = 0;
	}

	public String getLevelName() {
		return levelName;
	}
	public Board getBoard() {
		return board;
	}
	public int getLevelScore() {
		return levelScore;
	}
	public void increaseScore() {
		this.levelScore += 1;
	}
	public void decreaseScore() {
		if (this.levelScore > 0) {
			this.levelScore -= 1;
		}
	}
}
