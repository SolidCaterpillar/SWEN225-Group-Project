package nz.ac.wgtn.swen225.lc.domain.Tile;

import nz.ac.wgtn.swen225.lc.domain.*;
import nz.ac.wgtn.swen225.lc.domain.Entity.*;

import javax.swing.*;
import java.util.*;
import java.util.stream.Stream;

public class Tile {

    Coord loc;
    Entity objEntity = null;

    public Tile(Coord loc){
        this.loc = loc;
    }

    public Coord getLocation(){
        return loc;
    }



    public Entity getEntity() {
        return objEntity;
    }
    
    public void setEntity(Entity entity) {
        objEntity = entity;
    }



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

    //Entity was mov
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

    public boolean enemyWalkeable() {
        return false; // Default behavior for non-FreeTile instances
    }

}
