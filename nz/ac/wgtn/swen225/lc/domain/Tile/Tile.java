package nz.ac.wgtn.swen225.lc.domain.Tile;

import nz.ac.wgtn.swen225.lc.domain.*;
import nz.ac.wgtn.swen225.lc.domain.Entity.*;

import javax.swing.*;
import java.util.*;

public class Tile {


    private final static List<Tile> all = new ArrayList<>();

    Coord loc;
    Entity objEntity = null;

    public Tile(Coord loc){
        this.loc = loc;
        all.add(this);
    }

    public Coord getLoc(){
        return loc;
    }


    void interact(Entity entity){

        
    }; // Define how the tile interacts with an entity

    public Entity getEntity() {
        return objEntity;
    }
    
    public void setEntity(Entity entity) {
        objEntity = entity;
    }


    public static Optional<Tile> tileAtLoc(Coord loc) {
        return all.stream()
                .filter(tile -> tile.getLoc().equals(loc))
                .findFirst();
    }

    public ImageIcon draw(){
        return null;
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


}
