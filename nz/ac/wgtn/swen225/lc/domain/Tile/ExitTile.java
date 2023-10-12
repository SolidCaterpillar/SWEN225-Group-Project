package nz.ac.wgtn.swen225.lc.domain.Tile;

import nz.ac.wgtn.swen225.lc.domain.*;

/**
 * Represents an exit tile on the game board.
 * An ExitTile signifies a point in the game where a player can transition to a new level.
 * It extends the FreeTile, implying that it's walkable, and also carries information about the next level.
 * @author gautamchai
 */

public class ExitTile extends FreeTile{

    LevelE nextLevel;

    public ExitTile(Coord loc, LevelE levE) {
        super(loc);
        nextLevel = levE;
    }

    public String toString(){
        return "EX";
    }


    /**
     * Retrieves the level that this exit tile leads to.
     *
     * @return The next level after the current one.
     */
    public LevelE getNextLevel() {
        return nextLevel;
    }
}
