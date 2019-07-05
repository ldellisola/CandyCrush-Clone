package game.frontend;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class GoalPanel extends BorderPane {

	private Label scoreLabel;

	public GoalPanel() {
		setStyle("-fx-background-color: #5490ff");
		scoreLabel = new Label("Goal: 0/0");
		scoreLabel.setAlignment(Pos.CENTER);
		scoreLabel.setStyle("-fx-font-size: 24");
		setCenter(scoreLabel);
	}

	public void update(int curr, int max, String description) {
		scoreLabel.setText(String.format("%s: %d/%d",description,curr,max));
	}

}
