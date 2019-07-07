package game.backend;

import game.backend.cell.Cell;
import game.backend.element.Element;
import game.backend.level.Level;

import java.util.ArrayList;
import java.util.List;

public class CandyGame implements GameListener {
	
	private Class<?> levelClass;
	private int levelIndex = 0;
	private Grid grid;
	private GameState state;

	private List<Class<?>>levels = new ArrayList<>();

	public Level getCurrentLevel(){return (Level)grid;}
	
	public CandyGame(List<Class<?>> levels) {

		if(!levels.isEmpty()) {
			this.levels = levels;
			this.levelClass = levels.get(0);
		}
		else
			throw new IllegalArgumentException("At least one level must be passed");
	}
	
	public void initGame() {
		try {
			grid = (Grid)levelClass.newInstance();
		} catch(IllegalAccessException | InstantiationException e) {
			System.out.println("ERROR STARTING GAME");
		}
		state = grid.createState();
		grid.initialize();
		addGameListener(this);
	}

	public boolean hasNextLevel(){
		return levels.size() > levelIndex+1;
	}

	public void nextLevel() {
		if(hasNextLevel()) {
			grid.wasUpdated();
			this.levelClass = levels.get(++levelIndex);
			try {
				grid = (Grid) levelClass.newInstance();
			} catch (IllegalAccessException | InstantiationException e) {
				System.out.println("ERROR STARTING GAME");
			}
			state = grid.createState();
			grid.initialize();
			addGameListener(this);
		}
	}
	
	public int getSize() {
		return Grid.SIZE;
	}
	
	public boolean tryMove(int i1, int j1, int i2, int j2){
		return grid.tryMove(i1, j1, i2, j2);
	}
	
	public Cell get(int i, int j){
		return grid.getCell(i, j);
	}
	
	public void addGameListener(GameListener listener) {
		grid.addListener(listener);
	}
	
	public long getScore() {
		return state.getScore();
	}

	public int getGoal(){return state.getGoal();}

	public int getCurrentGoal(){return state.getCurrentGoal();}

	public String getGoalDescription() {return state.getGoalDescription();}
	
	public boolean isFinished() {
		return state.gameOver();
	}
	
	public boolean playerWon() {
		return state.playerWon();
	}
	
	@Override
	public void cellExplosion(Element e) {
		state.addScore(e.getScore());
	}
	
	@Override
	public void gridUpdated() {
		//
	}

	public void updateListeners(){
		grid.wasUpdated();
	}

	public int maxMovements(){
		return state.getMaxMoves();
	}

	public int getCurrMovements(){
		return state.getMoves();
	}

}
