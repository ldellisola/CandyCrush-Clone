package game.frontend.Listeners;

import game.backend.CandyGame;
import game.backend.GameListener;
import game.backend.element.Element;
import game.frontend.Panels.GoalPanel;

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

	}

	@Override
	public void cellExplosion(Element e) {

	}
}
