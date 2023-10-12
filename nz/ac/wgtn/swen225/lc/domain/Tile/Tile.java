package nz.ac.wgtn.swen225.lc.domain.Tile;

import nz.ac.wgtn.swen225.lc.domain.*;
import nz.ac.wgtn.swen225.lc.domain.Entity.*;

import javax.swing.*;
import java.util.*;
import java.util.stream.Stream;

/**
 * Represents a generic tile in the game board.
 * This class provides the base for all tile types, defining common attributes and operations.
 * @author gautamchai
 */

public class Tile {

    Coord loc;
    Entity objEntity = null;


    /**
     * Constructs a new Tile instance at the specified location.
     *
     * @param loc The coordinates where this tile is located.
     */
    public Tile(Coord loc){
        this.loc = loc;
    }

    /**
     * Retrieves the location of this tile.
     *
     * @return The coordinates of this tile's location.
     */

    public Coord getLocation(){
        return loc;
    }


    /**
     * Retrieves the entity residing on this tile.
     *
     * @return The entity on this tile, or null if none.
     */
    public Entity getEntity() {
        return objEntity;
    }


    /**
     * Sets the entity to reside on this tile.
     *
     * @param entity The entity to be placed on this tile.
     */
    
    public void setEntity(Entity entity) {
        objEntity = entity;
    }





    /**
     * Finds a tile based on a given location within a specific board.
     *
     * @param loc   The coordinate to check.
     * @param board The game board to search within.
     * @return An Optional containing the found tile, or empty if none found.
     */
    public static Optional<Tile> tileAtLoc(Coord loc, Board board) {
        int x = loc.y();
        int y = loc.x();
        Coord reverse = new Coord(x,y);

        boolean locInBoard = Board.checkInBound(reverse);

        if(locInBoard){
            Tile toReturn = Domain.getInstance().getBoard().getTileAtLocation(reverse);
            return Optional.of(toReturn);
        }
        return Optional.empty();
    }


    /**
     * Performs an entity move from another tile to this tile.
     *
     * @param swap The source tile from which the entity will be moved.
     * @throws IllegalArgumentException If the source tile is null.
     */
    public void moveEntity(Tile swap){
        if(swap != null) {
            Entity toSwap = swap.getEntity(); //if null set it to null anyway
            this.setEntity(toSwap);
            swap.setEntity(null);
        }
        else{
            throw new IllegalArgumentException("No tile to be swapped with");
        }
    }

    /**
     * Determines if this tile is walkable by an enemy entity.
     * By default, this returns false and can be overridden by subclasses.
     *
     * @return {@code true} if enemies can walk over this tile; {@code false} otherwise.
     */
    public boolean enemyWalkeable() {
        return false; // Default behavior for non-FreeTile instances
    }

}
