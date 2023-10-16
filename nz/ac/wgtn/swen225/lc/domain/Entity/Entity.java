package nz.ac.wgtn.swen225.lc.domain.Entity;

import nz.ac.wgtn.swen225.lc.domain.Coord;


/**
 * Represents a game entity. All game objects that have a position on the game board
 * should implement this interface. This ensures that they provide methods to retrieve their location.
 *
 * @author gautamchai ID: 300505029
 */
public interface Entity
{

    public Coord getLocation();

}
