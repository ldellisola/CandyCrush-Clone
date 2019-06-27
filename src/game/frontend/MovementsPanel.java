package game.frontend;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class MovementsPanel extends BorderPane {

	private Label scoreLabel;

	public MovementsPanel() {
		setStyle("-fx-background-color: #5490ff");
		scoreLabel = new Label("Movements: 0/0");
		scoreLabel.setAlignment(Pos.CENTER);
		scoreLabel.setStyle("-fx-font-size: 24");
		setCenter(scoreLabel);
	}

	public void update(int curr, int max) {
		scoreLabel.setText(String.format("Movements: %d/%d",curr,max));
	}

}