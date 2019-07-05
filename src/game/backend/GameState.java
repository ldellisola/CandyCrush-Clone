package game.backend;

public abstract class GameState {
	
	private long score = 0;
	private int moves = 0;
	
	public void addScore(long value) {
		this.score = this.score + value;
	}
	
	public long getScore(){
		return score;
	}
	
	public void addMove() {
		moves++;
	}
	
	public int getMoves() {
		return moves;
	}

	public abstract int getMaxMoves();

	public abstract int getGoal();
	public abstract int getCurrentGoal();
	public abstract String getGoalDescription();

	public boolean gameOver(){
		return playerWon() || getMoves() > getMaxMoves()-1;
	}
	
	public abstract boolean playerWon();

}
