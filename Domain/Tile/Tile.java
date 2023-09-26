package Domain.Tile;

import Domain.Coord;
import Domain.Entity.Entity;

public class Tile {

    Coord loc;
    Entity objEntity = null;

    public Tile(Coord loc){
        this.loc = loc;
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


    public boolean isWalkable(){
        if(objEntity == null) return true;
        return false;
    }

}
