package game.backend.level;

import game.backend.Figure;
import game.backend.GameState;
import game.backend.Grid;
import game.backend.cell.CandyGeneratorCell;
import game.backend.cell.Cell;
import game.backend.element.*;
import game.backend.move.BombMove;

import java.security.Signature;
import java.util.Random;

public class Level3 extends Level {
	private static final int CHERRIES = 10;
	private static final int MAX_MOVES = 200;

	private int cherrysFound = 0;

	private Cell wallCell;
	private Cell candyGenCell;

	@Override
	protected GameState newState() {
		return new Level3State();
	}

	@Override
	public void initialize(){
		super.initialize();

		Random rand = new Random();

		for (int i = 0; i < CHERRIES;) {
			int x = rand.nextInt(SIZE-1),y = rand.nextInt(SIZE);

			if(g()[x][y].getContent().getKey() != "CHERRY"){
				g()[x][y].setContent(new Cherry());
				i++;
			}



		}

	}

/*	@Override
	public boolean tryMove(int i1, int j1, int i2, int j2) {
		boolean ret;
		if (ret = super.tryMove(i1, j1, i2, j2)) {
			fallElements();
		}
		return ret;
	}*/

	@Override
	public void fallElements(){
		boolean found = false;
		do {
			found = false;
			for (int j = 0; j < SIZE; j++) {
				if (g()[SIZE - 1][j].getContent().equals(new Cherry())) {
					g()[SIZE - 1][j].setContent(new Nothing()); // CANCER
					found = true;
					cherrysFound++;
				}
			}
			super.fallElements();
		}while (found);
	}


	@Override
	public Figure tryRemove(Cell cell){
		if(cell.getContent().isDestoyable())
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
		public boolean playerWon() {
			return cherrysFound == CHERRIES;
		}

	}
}
