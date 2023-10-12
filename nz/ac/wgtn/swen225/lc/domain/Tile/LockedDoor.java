package nz.ac.wgtn.swen225.lc.domain.Tile;

import nz.ac.wgtn.swen225.lc.domain.*;
import nz.ac.wgtn.swen225.lc.domain.Entity.Key;


/**
 * Represents a locked door tile on the game board.
 * Locked doors can only be unlocked with a key of the corresponding color.
 * Inherits properties from the Wall tile but introduces the concept of a color associated with the lock.
 * @author gautamchai
 */
public class LockedDoor extends Wall {

    protected Colour colour;


    /**
     * Constructs a new LockedDoor instance at the specified location with a specified color.
     *
     * @param loc The coordinates where this locked door is located.
     * @param col The color associated with this locked door.
     */
    public LockedDoor(Coord loc, Colour col){
        super(loc);
        colour = col;
    }

    public String toString(){
        return "|L|";
    }



    /**
     * Retrieves the color associated with this locked door.
     *
     * @return The color of the lock on this door.
     */
    public Colour getColour(){
        return colour;
    }

}
