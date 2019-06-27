package game.frontend;

import game.backend.CandyGame;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public abstract class EndLevelAlert extends Alert {

	private ButtonType nextLevelBtn, restartBtn, exitBtn;

	protected abstract boolean canHaveNextLevel();


	public EndLevelAlert(CandyGame game,String title,String header) {
		super(AlertType.CONFIRMATION);

		setTitle(title);
		setHeaderText(header);
		setContentText("What would you like to do now?");

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
