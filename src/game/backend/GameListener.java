package game.backend;

import game.backend.cell.Cell;
import game.backend.cell.GoldenCell;
import game.backend.element.Element;
import game.frontend.CandyFrame;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.util.Duration;

public interface GameListener {
	
	void gridUpdated();

	
	void cellExplosion(Element e);
	
}