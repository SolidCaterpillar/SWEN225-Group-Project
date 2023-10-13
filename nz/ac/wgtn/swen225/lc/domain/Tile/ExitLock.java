package nz.ac.wgtn.swen225.lc.domain.Tile;

import nz.ac.wgtn.swen225.lc.domain.*;

/**
 * Represents an exit lock tile on the game board.
 * An ExitLock signifies a barrier or a locked point in the game, typically preventing a player from directly accessing the exit or some other objective.
 * The exact mechanics or interaction to unlock or overcome this tile might vary, depending on the game rules.
 * @author gautamchai ID: 300505029
 */
public class ExitLock extends Tile{
    
    public ExitLock(Coord loc){
        super(loc);
    }

    public String toString(){
        return "|M|";
    }
}
