package Domain;
import javax.swing.*;
import java.util.*;

public class Tile implements TileObj{
    Coord location = null; //Location on board/map
    TileType type = null; //Type of tile

    JPanel gui = null; //Only for testing might be overridable to change display in Rendering

    Entity objEntity = null; //If does not contain Object then is wall, free Tile, or Lock

    private final static List<Tile> all = new ArrayList<>();
    //AllTiles

    public Coord getLoc(){
        return location;
    }
    public Tile(Coord location, TileType type, JPanel gui) {
        this.location = location;
        this.type = type;
        this.gui = gui;
        all.add(this);
    }


    public TileType getType(){
        return type;
    }

    //For swapping tiles.
    public void swapTile(Tile tile){
        Coord temp =  tile.location;
        tile.location = this.location;
        this.location = temp;
    }

    public TileType getTileType(){
        return type;
    }

    public void setType(TileType change){
        type = change;
    }

    @Override
    public Entity getEntity() {
        return objEntity;
    }

    @Override
    public void setEntity(Entity entity) {
        objEntity = entity;
    }


    public void interact(Entity entity) {
        // define how this tile interacts with an entity

        // Example: If this is a KeyTile, check if the entity is the player,
        // and if so, pick up the key.
        if (getType() == TileType.KEY && entity instanceof Chap) {
            // the key-pickup logic here

            setType(TileType.FREE);
        }
    }
    public boolean isWalkable(){
        if(objEntity == null) return true;
        return false;
    }

    public static Tile tileAtLoc(Coord loc) {
        return all.stream()
                .filter(tile -> tile.getLoc().equals(loc))
                .findFirst()
                .orElse(null); // null if no match.
    }
}
