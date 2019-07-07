package game.backend.level;

import game.backend.Figure;
import game.backend.GameState;
import game.backend.cell.Cell;
import game.backend.element.*;

import java.util.Random;

public class Level3 extends Level {
	private static final int FRUITS = 5;
	private static final int MAX_MOVES = 20;

	private int fruitsFound = 0;

	@Override
	protected GameState newState() {
		return new Level3State();
	}

	@Override
	public void initialize(){
		super.initialize();
		Random rand = new Random();

		for (int i = 0; i < FRUITS;) {
			int x = rand.nextInt(SIZE-1),y = rand.nextInt(SIZE);
			
			if((g()[x][y].getContent().isDestroyable())){
				g()[x][y].setContent(rand.nextBoolean() ? new Hazelnut(): new Cherry());
				i++;
			}
		}
	}

	@Override
	public boolean tryMove(int i1, int j1, int i2, int j2) {
		boolean ret;
		if (ret = super.tryMove(i1, j1, i2, j2)) {
			fallElements();
		}
		return ret;
	}

	@Override
	public void fallElements(){
		boolean found; //false
		do {
			super.fallElements();
			found = false;
			for (int j = 0; j < SIZE; j++) {
				if (!g()[SIZE - 1][j].getContent().isDestroyable()) {
					g()[SIZE - 1][j].setContent(new Nothing());
					found = true;
					fruitsFound++;
				}
			}
		}while (found);
	}


	@Override
	public Figure tryRemove(Cell cell){
		if(cell.getContent().isDestroyable())
			return super.tryRemove(cell);
		else
			return null;
	}

	private class Level3State extends GameState{


		@Override
		public int getMaxMoves() {
			return MAX_MOVES;
		}

		@Override
		public int getGoal() {
			return FRUITS;
		}

		@Override
		public int getCurrentGoal() {
			return fruitsFound;
		}

		@Override
		public String getGoalDescription() {
			return "Cherries and Hazelnuts";
		}

		@Override
		public boolean playerWon() {
			return fruitsFound == FRUITS;
		}

	}
}
