package game.frontend.Alerts;

import game.backend.CandyGame;

public class WonLevelAlert extends EndLevelAlert {

	public WonLevelAlert(CandyGame game) {
		super(game,"Congrats!","You won!");

	}

	@Override
	protected boolean canHaveNextLevel() {
		return true;
	}

	@Override
	protected String getImagePath() {
		return "/images/winner.png";
	}
}
