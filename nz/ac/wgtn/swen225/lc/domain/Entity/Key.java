package nz.ac.wgtn.swen225.lc.domain.Entity;

import nz.ac.wgtn.swen225.lc.domain.Colour;
import nz.ac.wgtn.swen225.lc.domain.Coord;



/**
 * Represents a key entity in the game. A key can be used by the player
 * to unlock doors of a specific colour. Each key has a unique colour associated
 * with it, which determines the type of door it can unlock.
 *
 * @author gautamchai ID: 300505029
 */


public class Key implements Entity{

    protected Colour colour;

    protected Coord location;


    /**
     * Constructs a new key with the specified colour and location.
     *
     * @param co   The starting coordinate of the key on the game board.
     * @param col  The colour of the key.
     */
    public Key(Coord co,Colour col){

        location = co;
        colour = col;
    }


    /**
     * Returns the current location of the key on the game board.
     *
     * @return The coordinate of the key.
     */
    @Override
    public Coord getLocation() {
        return location;
    }


    /**
     * Returns a string representation of the key, which is used for display purposes.
     *
     * @return A string representing the key.
     */
    public String toString(){
        return "k";
    }


    /**
     * Gets the colour of the key.
     *
     * @return The colour of the key.
     */
    public Colour getColour(){
        return colour;
    }

}
