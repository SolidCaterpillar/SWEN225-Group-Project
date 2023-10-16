package nz.ac.wgtn.swen225.lc.domain.Tile;

import nz.ac.wgtn.swen225.lc.domain.*;
import nz.ac.wgtn.swen225.lc.domain.Entity.Entity;
import nz.ac.wgtn.swen225.lc.domain.Entity.Player;



/**
 * Represents a free tile on the game board.
 * A FreeTile is a type of tile that players and certain entities can traverse. It can be empty or contain
 * specific entities like a player. This class extends the general Tile class to provide specific behavior
 * for tiles that are walkable by entities.
 * @author gautamchai ID: 300505029
 */

public class FreeTile extends Tile{

    public FreeTile(Coord loc){
        super(loc);
    }

    public String toString(){
        return super.objEntity != null ? "|"+super.objEntity+"|" : "|_|";
    }


    /**
     * Determines if the tile is walkable by enemy entities.
     * For a FreeTile, this checks if the tile is indeed of type FreeTile and if it's currently unoccupied or
     * occupied by a player.
     *
     * @return true if an enemy can walk on this tile, false otherwise.
     */
    @Override
    public boolean enemyWalkeable(){
        if(this instanceof FreeTile) {
            Entity check = this.getEntity();
            return check == null || check instanceof Player;
        }
        return false;
    }

}
