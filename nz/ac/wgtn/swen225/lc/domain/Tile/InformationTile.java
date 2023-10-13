package nz.ac.wgtn.swen225.lc.domain.Tile;

import nz.ac.wgtn.swen225.lc.domain.Coord;
import nz.ac.wgtn.swen225.lc.domain.Entity.Player;


/**
 * Represents an information tile on the game board.
 * An InformationTile provides players with necessary details or hints about the game or its mechanics.
 * It inherits properties from the FreeTile but introduces an additional concept of carrying information.
 * @author gautamchai ID: 300505029
 */

public class InformationTile extends FreeTile{

    protected String information;


    public InformationTile(Coord loc, String info) {
        super(loc);
        information = info;

    }


    /**
     * Retrieves the information/message associated with this tile.
     *
     * @return The informational message of this tile.
     */
    public String getInformation() {
        return information;
    }



    /**
     * Determines if the tile is walkable by enemy entities.
     * Overrides the default behavior for FreeTile instances.
     *
     * @return false, as enemies cannot traverse this tile by default.
     */
    public boolean enemyWalkeable() {
        return false; // Default behavior for non-FreeTile instances
    }

}
