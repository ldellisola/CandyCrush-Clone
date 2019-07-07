package game.frontend;

import game.backend.CandyGame;
import game.backend.Grid;
import game.backend.level.Level1;
import game.backend.level.Level2;
import game.backend.level.Level3;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class GameApp extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		List<Class<?>> levels = new ArrayList<>();
		levels.add(Level3.class);
		levels.add(Level2.class);
		levels.add(Level1.class);

		CandyGame game = new CandyGame(levels);
		CandyFrame frame = new CandyFrame(game);
		Scene scene = new Scene(frame);
		primaryStage.setResizable(false);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
