package game.frontend.Listeners;

import game.backend.CandyGame;
import game.backend.GameListener;
import game.backend.element.Element;
import game.frontend.CandyFrame;
import game.frontend.Panels.GoalPanel;
import game.frontend.Panels.MovementsPanel;
import game.frontend.Panels.ScorePanel;

public class GameInfoListener implements GameListener {
	private MovementsPanel movements = new MovementsPanel();
	private ScorePanel scorePanel = new ScorePanel();
	private GoalPanel goals  = new GoalPanel();
	private CandyGame game;

	public GameInfoListener(CandyFrame frame, CandyGame game){
		this.game = game;
		frame.getChildren().addAll(scorePanel, movements,goals);
	}


	@Override
	public void gridUpdated() {
		goals.update(game.getCurrentGoal(),game.getGoal(),game.getGoalDescription());
		movements.update(game.currMovements(),game.maxMovements());
		scorePanel.updateScore(game.getScore());
	}

	@Override
	public void cellExplosion(Element e) {

	}
}
