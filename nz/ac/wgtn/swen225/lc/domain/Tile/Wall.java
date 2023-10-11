package nz.ac.wgtn.swen225.lc.domain.Tile;

import nz.ac.wgtn.swen225.lc.domain.*;
import nz.ac.wgtn.swen225.lc.domain.Entity.Entity;
public class Wall extends Tile{
    
    public Wall(Coord loc){
        super(loc);
    }

    @Override
    void interact(Entity entity){

        
    }; // Define how the tile interacts with an entity

    public boolean isWalkable(){
        return false;
    }

    public String toString(){
        return "|#|";
    }

    public static boolean checkWall(Coord loc){

        return false;
    }
}
