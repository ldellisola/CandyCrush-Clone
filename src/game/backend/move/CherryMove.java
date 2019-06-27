package game.backend.move;

import game.backend.Figure;
import game.backend.FigureDetector;
import game.backend.Grid;

public class CherryMove extends Move {

	public CherryMove(Grid grid) {
		super(grid);
		this.grid = grid;
	}

	@Override
	public boolean internalValidation() {
		this.detector = new FigureDetector(grid);
		if(grid.get(i1,j1).getKey() != "CHERRY")
		f1 = detector.checkFigure(i1, j1);
		if(grid.get(i2,j2).getKey() != "CHERRY")
			f2 = detector.checkFigure(i2, j2);
		return f1 != null || f2 != null;
	}

	@Override
	public void removeElements() {
		if (f1 != null) {
			detector.removeFigure(i1, j1, f1);
		}
		if (f2 != null) {
			detector.removeFigure(i2, j2, f2);
		}
	}

	private Figure f1;
	private Figure f2;

	private FigureDetector detector;
	private Grid grid;

}
