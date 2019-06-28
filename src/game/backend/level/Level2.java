package game.backend.level;

import game.backend.GameState;
import game.backend.cell.CandyGeneratorCell;
import game.backend.cell.Cell;
import game.backend.cell.GoldenCell;
import game.backend.move.Move;

import java.awt.*;

public class Level2  extends Level
{
    private static final int MAX_MOVES = 20;

    private Cell wallCell ;
    private CandyGeneratorCell candyGenCell;
    @Override
    protected GameState newState() {
        return new Level2State();
    }



    private class Level2State extends GameState{

        @Override
        public int getMaxMoves(){
            return MAX_MOVES;
        }

        @Override
        public boolean playerWon() {
            return ((GoldenCell)g()[1][1]).getGoldenState();
        }
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
            if(i1==i2){
                for (int i = 0; i < SIZE ; i++) {
                    ((GoldenCell)g()[i1][i]).setGoldenState();
                }
            }
            else if(j1==j2){
                for (int i = 0; i < SIZE ; i++) {
                    ((GoldenCell)g()[i][j1]).setGoldenState();
                }
            }

            move.removeElements();
            fallElements();
            return true;
        } else {
            swapContent(i1, j1, i2, j2);
            return false;
        }
    }
}
