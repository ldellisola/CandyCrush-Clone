package game.backend.level;

import game.backend.GameState;
import game.backend.Grid;
import game.backend.cell.CandyGeneratorCell;
import game.backend.cell.Cell;
import game.backend.element.Cherry;
import game.backend.element.Nothing;
import game.backend.element.Wall;

import java.security.Signature;
import java.util.Random;

public class Level3 extends Grid {
	private static final int cherrys = 5;
	private static final int maxMoves = 200;

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

		for (int i = 0; i < cherrys;) {
			int x = rand.nextInt(SIZE-1),y = rand.nextInt(SIZE);

			if(g()[x][y].getContent().getKey() != "CHERRY"){
				g()[x][y].setContent(new Cherry());
				i++;
			}



		}

	}

	@Override
	public boolean tryMove(int i1, int j1, int i2, int j2) {
		boolean ret;
		if (ret = super.tryMove(i1, j1, i2, j2)) {
			state().addMove();
			fallElements();
		}
		return ret;
	}

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
	protected void fillCells() {
		wallCell = new Cell(this);
		wallCell.setContent(new Wall());
		candyGenCell = new CandyGeneratorCell(this);

		//corners
		g()[0][0].setAround(candyGenCell, g()[1][0], wallCell, g()[0][1]);
		g()[0][SIZE-1].setAround(candyGenCell, g()[1][SIZE-1], g()[0][SIZE-2], wallCell);
		g()[SIZE-1][0].setAround(g()[SIZE-2][0], wallCell, wallCell, g()[SIZE-1][1]);
		g()[SIZE-1][SIZE-1].setAround(g()[SIZE-2][SIZE-1], wallCell, g()[SIZE-1][SIZE-2], wallCell);

		//upper line cells
		for (int j = 1; j < SIZE-1; j++) {
			g()[0][j].setAround(candyGenCell,g()[1][j],g()[0][j-1],g()[0][j+1]);
		}
		//bottom line cells
		for (int j = 1; j < SIZE-1; j++) {
			g()[SIZE-1][j].setAround(g()[SIZE-2][j], wallCell, g()[SIZE-1][j-1],g()[SIZE-1][j+1]);
		}
		//left line cells
		for (int i = 1; i < SIZE-1; i++) {
			g()[i][0].setAround(g()[i-1][0],g()[i+1][0], wallCell ,g()[i][1]);
		}
		//right line cells
		for (int i = 1; i < SIZE-1; i++) {
			g()[i][SIZE-1].setAround(g()[i-1][SIZE-1],g()[i+1][SIZE-1], g()[i][SIZE-2], wallCell);
		}
		//central cells
		for (int i = 1; i < SIZE-1; i++) {
			for (int j = 1; j < SIZE-1; j++) {
				g()[i][j].setAround(g()[i-1][j],g()[i+1][j],g()[i][j-1],g()[i][j+1]);
			}
		}
	}

	private class Level3State extends GameState{

		@Override
		public boolean gameOver() {
			return playerWon() || getMoves() > maxMoves;
		}

		@Override
		public boolean playerWon() {
			return cherrysFound == cherrys;
		}
	}
}
