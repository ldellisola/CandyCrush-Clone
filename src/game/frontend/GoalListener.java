package game.frontend;

import game.backend.CandyGame;
import game.backend.GameListener;
import game.backend.element.Element;

public class GoalListener implements GameListener {
	private GoalPanel goals;
	private CandyGame game;
	public GoalListener(CandyGame game){
		this.game = game;
		goals = new GoalPanel();
	}

	public  GoalPanel getPanel(){
		return goals;
	}

	@Override
	public void gridUpdated() {
		goals.update(game.getCurrentGoal(),game.getGoal(),game.getGoalDescription());
	}

	@Override
	public void cellExplosion(Element e) {

	}
}
