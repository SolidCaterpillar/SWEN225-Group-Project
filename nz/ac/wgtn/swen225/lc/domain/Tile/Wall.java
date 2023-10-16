package nz.ac.wgtn.swen225.lc.domain.Tile;

import nz.ac.wgtn.swen225.lc.domain.*;
import nz.ac.wgtn.swen225.lc.domain.Entity.Entity;


/**
 * Represents a wall tile in the game board.
 * Wall tiles are barriers that prevent movement through them.
 * @author gautamchai ID: 300505029
 */
public class Wall extends Tile{
    
    public Wall(Coord loc){
        super(loc);
    }


    /**
     * Indicates if this tile is walkable.
     *
     * @return Always returns {@code false} since wall tiles cannot be traversed.
     */
    public boolean isWalkable(){
        return false;
    }

    public String toString(){
        return "|#|";
    }

}
