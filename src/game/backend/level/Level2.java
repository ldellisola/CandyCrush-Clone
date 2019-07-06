package game.backend.level;

import game.backend.GameListener;
import game.backend.GameState;
import game.backend.cell.CandyGeneratorCell;
import game.backend.cell.Cell;
import game.backend.cell.GoldenCell;
import game.backend.element.Element;
import game.backend.move.Move;

import java.awt.*;

public class Level2  extends Level
{
    private static final int MAX_MOVES = 20;
    private int golden = 0;
    @Override
    public  boolean hasGoldenCells(){ return true;}

    @Override
    protected GameState newState() {
        return new Level2State();
    }

    @Override
    protected void createGrid(){
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                g()[i][j] = new GoldenCell(this);
                gMap.put(g()[i][j], new Point(i,j));
            }
        }
    }

    @Override
    public boolean tryMove(int i1, int j1, int i2, int j2) {
        Move move = moveMaker.getMove(i1, j1, i2, j2);
        swapContent(i1, j1, i2, j2);
        if (move.isValid()) {
            state().addMove();

            if(i1==i2){
                for (int i = 0; i < SIZE ; i++) {
                    if(!((GoldenCell)g()[i1][i]).getGoldenState()) {
                        ((GoldenCell) g()[i1][i]).setGoldenState();
                        golden++;
                    }
                }
            }
            else if(j1==j2){
                for (int i = 0; i < SIZE ; i++) {
                    if(!((GoldenCell)g()[i][j2]).getGoldenState()) {
                        ((GoldenCell) g()[i][j2]).setGoldenState();
                        golden++;
                    }                }
            }

            move.removeElements();
            fallElements();
            wasUpdated();
            return true;
        } else {
            swapContent(i1, j1, i2, j2);
            return false;
        }
    }

    private class Level2State extends GameState{

        private final int TOTAL_CELLS = SIZE * SIZE;


        @Override
        public int getMaxMoves(){
            return MAX_MOVES;
        }

        @Override
        public int getGoal() {
            return TOTAL_CELLS;
        }

        @Override
        public int getCurrentGoal() {
            return golden;
        }

        @Override
        public String getGoalDescription() {
            return "Golden Cells";
        }

        @Override
        public boolean playerWon() {
            return TOTAL_CELLS == golden;
        }
    }
}
