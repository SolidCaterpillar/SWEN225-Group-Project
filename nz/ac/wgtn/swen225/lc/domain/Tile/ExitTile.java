package nz.ac.wgtn.swen225.lc.domain.Tile;

import nz.ac.wgtn.swen225.lc.domain.*;

public class ExitTile extends FreeTile{

    LevelE nextLevel;

    public ExitTile(Coord loc, LevelE levE) {
        super(loc);
        nextLevel = levE;
    }

    public String toString(){
        return "EX";
    }

    public LevelE getNextLevel() {
        return nextLevel;
    }
}
