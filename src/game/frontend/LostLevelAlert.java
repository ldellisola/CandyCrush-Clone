package game.frontend;

import game.backend.CandyGame;

public class LostLevelAlert extends EndLevelAlert {
	public LostLevelAlert(CandyGame game) {
		super(game,"Such a Loser","Better luck next time");

	}

	@Override
	protected boolean canHaveNextLevel() {
		return false;
	}

	@Override
	protected String getImagePath() {
		return "/images/loser.png";
	}
}
