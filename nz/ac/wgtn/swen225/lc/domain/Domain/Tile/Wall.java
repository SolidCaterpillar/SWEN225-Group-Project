package Domain.Tile;

import Domain.Coord;
import Domain.Entity.Entity;

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
}
