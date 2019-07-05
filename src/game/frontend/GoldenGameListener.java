package game.frontend;

import game.backend.CandyGame;
import game.backend.GameListener;
import game.backend.cell.Cell;
import game.backend.cell.GoldenCell;
import game.backend.element.Element;
import game.backend.level.Level2;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class GoldenGameListener implements GameListener {
	private CandyGame game;
	private BoardPanel panel;

	private boolean[][] goldenMatrix;

	public GoldenGameListener(CandyGame game, BoardPanel panel){
		this.game = game;
		this.panel = panel;
		cleanGoldenMatrix();
	}

	private void cleanGoldenMatrix(){
		goldenMatrix= new boolean[game.getSize()][game.getSize()];
	}
	@Override
	public void gridUpdated() {

		if(game.getCurrentLevel().hasGoldenCells()){

			if(game.isFinished()){

				cleanGoldenMatrix();

				Timeline timeLine = new Timeline();
				Duration frameGap = Duration.millis(100);
				Duration frameTime = Duration.ZERO;
				for (int i = 0; i < game.getSize() ; i++) {
					for (int j = 0; j < game.getSize() ; j++) {
						Cell cell = game.get(i, j);
						int finalI = i;
						int finalJ = j;

						timeLine.getKeyFrames().add(new KeyFrame(frameTime, e -> panel.stopGoldenEffect(finalI, finalJ)));
					}
					frameTime = frameTime.add(frameGap);
				}
				timeLine.play();
			}
			else {
				Timeline timeLine = new Timeline();
				Duration frameGap = Duration.millis(100);
				Duration frameTime = Duration.ZERO;
				for (int i = game.getSize()-1; i  >= 0 ; i--) {
					for (int j = game.getSize()-1; j >=0; j--) {
						Cell cell = game.get(i, j);
						int finalI = i;
						int finalJ = j;

						if (cell instanceof GoldenCell && ((GoldenCell) cell).getGoldenState() && !goldenMatrix[i][j]) {  //SI
							timeLine.getKeyFrames().add(new KeyFrame(frameTime, e -> panel.setGoldenEffect(finalI, finalJ)));
							goldenMatrix[i][j] = true;
						}
					}
					frameTime = frameTime.add(frameGap);
				}
				timeLine.play();
			}
		}

	}

	@Override
	public void cellExplosion(Element e) {

	}
}


