package nz.ac.wgtn.swen225.lc.domain.Entity;

import nz.ac.wgtn.swen225.lc.domain.Coord;


/**
 * Represents a treasure in the game world.
 * Treasures are collectible items that players might need to acquire as part of the game's objectives.
 *
 * @author gautamchai ID: 300505029
 */
public class Treasure implements Entity{

    protected Coord location;

    /**
     * Constructs a new Treasure instance at the specified location.
     *
     * @param co The coordinates where this treasure is located.
     */
    public Treasure(Coord co){
        location = co;
    }


    /**
     * Retrieves the location of the treasure.
     *
     * @return The coordinates of this treasure in the game world.
     */
    @Override
    public Coord getLocation() {
        return location;
    }

    /**
     * Returns a string representation of the treasure, typically for rendering in a UI.
     *
     * @return A string representing this treasure.
     */
    public String toString(){
        return "s";
    }
}
