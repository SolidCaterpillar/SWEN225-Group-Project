package nz.ac.wgtn.swen225.lc.domain.Entity;
import javax.swing.*;

import nz.ac.wgtn.swen225.lc.domain.Coord;
import nz.ac.wgtn.swen225.lc.domain.Tile.FreeTile;
import nz.ac.wgtn.swen225.lc.domain.Tile.Tile;

import java.awt.event.KeyEvent;
import java.util.*;

import static nz.ac.wgtn.swen225.lc.domain.Board.getDim;

//REDUNDANT CLASS REPLACED WITH CHAP

public class Player implements Entity{
    //starting Orientation is South
    protected Orientation direction = Orientation.SOUTH;
    protected ArrayList<Treasure> treasures;
    protected Coord location;

    protected ArrayList<Key> keys;

    public Player(Coord loc){
        this.location = loc;
        treasures = new ArrayList<>();
        keys = new ArrayList<>();
    }


    //MAKE THIS A STATIC MOVE LATER IN ENTITY FOR BOTH ENEMY AND PLAYER
    public void checkMove(char keyEvent) {
        //char keyCode = keyEvent.getKeyChar(); //convert to char for switch
        char keyCode = keyEvent;
        this.changeDir(keyCode); //Change orientations

        Coord loc = null;
        Tile newPos = null;

        loc = switch (keyCode) {
            case 'w'-> loc = this.location.moveUp();
            case 'a' -> loc = this.location.moveLeft();
            case 's' -> loc = this.location.moveDown();
            case 'd' -> loc = this.location.moveRight();
            default -> throw new IllegalArgumentException("Invalid key pressed!");
        };

        if (checkInBound(loc)) { //if new loc in bound
            Optional<Tile> optionalTile = Tile.tileAtLoc(loc); //get new tile
            if (optionalTile.isPresent()) { //if new tile presen
                newPos = optionalTile.get();
                if (newPos instanceof FreeTile) { //Currently only movement add interaction later too

                    Player.interact(this, loc); //Static interaction method will expand to encompass tile interactions with locked doors etc.

                    Tile oldPos = Tile.tileAtLoc(this.location).orElseThrow(
                            () -> new IllegalArgumentException("OG player position not found")
                    );

                    newPos.moveEntity(oldPos);
                    this.location = loc;
                } else {
                    //Play sound
                    System.out.println("Invalid move");
                }
            } else {
                throw new IllegalArgumentException("No tile at the target location!");
            }
        }
    }


    public boolean checkInBound(Coord check) {
        int x = check.x();
        int y = check.y();
        int dim = getDim();

        return x >= 0 && x < dim && y >= 0 && y < dim;
    }

    public ArrayList<Key> getKeys() { //Keygetter
        return new ArrayList<>(Collections.unmodifiableCollection(keys));
    }

    public ArrayList<Treasure> getTreasure(){ //Treasuregetter
        return new ArrayList<>(Collections.unmodifiableCollection(treasures));
    }

    @Override
    public Coord getLocation() {
        return location;
    }
    public int getX(){
        return this.location.x();
    }

    public int getY(){
        return this.location.y();
    }




    //Change orientation for sprites
    public void changeDir(char keyCode) {
        switch (keyCode) {
            case 'w' -> this.direction = Orientation.NORTH;
            case 'a' -> this.direction = Orientation.WEST;
            case 's' -> this.direction = Orientation.SOUTH;
            case 'd' -> this.direction = Orientation.EAST;
        }
    }

    public Orientation getDirection(){
        return this.direction;
    }

    public String toString(){
        return "PP";
    }

    public static void interact(Player player, Coord loc) {
        // get the tile at the player's current location
        Optional<Tile> currentTileOptional = Tile.tileAtLoc(loc);

        if (currentTileOptional.isPresent()) {
            Tile currentTile = currentTileOptional.get();
            Optional<Entity> entityOptional = currentTile.getEntity();

            // check if the tile contains an entity
            entityOptional.ifPresent(entity -> {

                if (entity instanceof Treasure) {

                    player.treasures.add((Treasure) entity);

                    // remove Entity
                    currentTile.setEntity(null);
                }

                else if (entity instanceof Key) {

                    player.keys.add((Key) entity);

                    // rem oveentity
                    currentTile.setEntity(null);
                }
            });
        }
    }

}
