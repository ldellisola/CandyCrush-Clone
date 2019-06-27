package game.backend.cell;

import game.backend.Grid;

public class GoldenCell extends Cell {
    private boolean gold = false;

    public GoldenCell(Grid grid) {
        super(grid);
    }

    public void setGoldenState(){
        this.gold = true;
    }

    public boolean getGoldenState(){
        return  this.gold;
    }

}
