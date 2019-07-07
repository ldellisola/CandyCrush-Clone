package game.backend.level;

import game.backend.GameState;
import game.backend.Grid;
import game.backend.cell.CandyGeneratorCell;
import game.backend.cell.Cell;
import game.backend.element.Wall;

public class Level1 extends Level {
	
	private static int REQUIRED_SCORE = 1000;
	private static int MAX_MOVES = 20; 

	
	@Override
	protected GameState newState() {
		return new Level1State(REQUIRED_SCORE, MAX_MOVES);
	}

	
	private class Level1State extends GameState {
		private long requiredScore;
		private int maxMoves;
		
		public Level1State(long requiredScore, int maxMoves) {
			this.requiredScore = requiredScore;
			this.maxMoves = maxMoves;
		}

		@Override
		public int getMaxMoves(){
			return maxMoves;
		}

		@Override
		public int getGoal() {
			return REQUIRED_SCORE;
		}

		@Override
		public int getCurrentGoal() {
			return (int)getScore();
		}

		@Override
		public String getGoalDescription() {
			return "Score";
		}

		public boolean playerWon() {
			return getScore() >= requiredScore;
		}
	}

}
