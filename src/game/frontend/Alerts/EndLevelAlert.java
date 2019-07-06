package game.frontend.Alerts;

import game.backend.CandyGame;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Optional;

public abstract class EndLevelAlert extends Alert {

	private ButtonType nextLevelBtn, restartBtn, exitBtn;

	protected abstract boolean canHaveNextLevel();

	protected abstract String getImagePath();


	public EndLevelAlert(CandyGame game,String title,String header) {
		super(AlertType.CONFIRMATION);

		setTitle(title);
		setHeaderText(header);
		setContentText("What would you like to do now?");

		Image image = new Image(getImagePath());
		ImageView imageView = new ImageView(image);
		imageView.setFitHeight(70);
		imageView.setFitWidth(100);
		setGraphic(imageView);

		restartBtn = new ButtonType("Restart Level");
		exitBtn = new ButtonType("Exit");

		getButtonTypes().setAll(restartBtn,exitBtn);

		if(canHaveNextLevel() && game.hasNextLevel() ){
			nextLevelBtn = new ButtonType("Next Level");
			getButtonTypes().add(1,nextLevelBtn);
		}
		Optional<ButtonType> result = showAndWait();

		if(result.isPresent()){
			ButtonType pressedBtn = result.get();

			if(pressedBtn == restartBtn)
				game.initGame();
			else if(pressedBtn == exitBtn)
				Platform.exit();
			else if(pressedBtn == nextLevelBtn)
				game.nextLevel();
		}
		else
			Platform.exit();


	}


}
