package game.frontend;

import game.backend.CandyGame;

public class WonLevelAlert extends EndLevelAlert {

	public WonLevelAlert(CandyGame game) {
		super(game,"Congrats!","You won!");

	}

	@Override
	protected boolean canHaveNextLevel() {
		return true;
	}
}
