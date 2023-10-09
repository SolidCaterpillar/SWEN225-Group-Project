package nz.ac.wgtn.swen225.lc.domain.Entity;
import javax.swing.*;

import nz.ac.wgtn.swen225.lc.domain.Board;
import nz.ac.wgtn.swen225.lc.domain.Coord;
import nz.ac.wgtn.swen225.lc.domain.Domain;
import nz.ac.wgtn.swen225.lc.domain.Tile.FreeTile;
import nz.ac.wgtn.swen225.lc.domain.Tile.InformationTile;
import nz.ac.wgtn.swen225.lc.domain.Tile.LockedDoor;
import nz.ac.wgtn.swen225.lc.domain.Tile.Tile;

import java.awt.event.KeyEvent;
import java.util.*;


import static nz.ac.wgtn.swen225.lc.domain.Board.openExitTile;

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


    //ADD A IS WALKEABLE TO ENSURE COLLISION DETECTION IS CORRECT THE CURRENT CHECKS ARE INSUFFICIENT.

    /*
    public void checkMove(char keyEvent) {
        //char keyCode = keyEvent.getKeyChar(); //convert to char for switch
        char keyCode = keyEvent;
        this.changeDir(keyCode); //Change orientations

        System.out.println(keyCode);

        Coord loc = null;
        Tile newPos = null;

        loc = switch (keyCode) {
            case 'w'-> loc = this.location.moveUp();
            case 'a' -> loc = this.location.moveLeft();
            case 's' -> loc = this.location.moveDown();
            case 'd' -> loc = this.location.moveRight();
            default -> throw new IllegalArgumentException("Invalid key pressed!");
        };

        if (!Board.checkInBound(loc)) { throw new IllegalArgumentException("Tile not in board boundary");} //if new loc in bound

        //get new tile
        Optional<Tile> optionalTile = Tile.tileAtLoc(loc);

        //if no tile present
        if (!optionalTile.isPresent()) { throw new IllegalArgumentException("No tile at the target location!");}

        newPos = optionalTile.get();


        boolean checkDoor = tryOpenLockedDoor(newPos);//check if next position is a lockedDoor

        //If door existed and player has key then player can move onto it and replace with freetile.
        if(checkDoor){movePlayer(newPos, loc);
            Domain.staticBoard().replaceTileAt(loc, new FreeTile(loc));
        }

        //Currently only movement add interaction later too
        if (!(newPos instanceof FreeTile)) {System.out.println("Invalid move"); }

        //If player is moving onto freetile then interact with its object
        Player.interact(this, loc);

        movePlayer(newPos, loc);

        this.checkTreasures(); //Unlocks ExitTiles which open when player has all treasure
    } */
    public void checkMove(char keyEvent) {
        //char keyCode = keyEvent.getKeyChar(); //convert to char for switch
        char keyCode = keyEvent;
        this.changeDir(keyCode); //Change orientations

        System.out.println(keyCode);

        Coord loc = null;
        Tile newPos = null;

        loc = switch (keyCode) {
            case 'w' -> loc = this.location.moveUp();
            case 'a' -> loc = this.location.moveLeft();
            case 's' -> loc = this.location.moveDown();
            case 'd' -> loc = this.location.moveRight();
            default -> throw new IllegalArgumentException("Invalid key pressed!");
        };
        if (Board.checkInBound(loc)) {
            // get new tile
            Optional<Tile> optionalTile = Tile.tileAtLoc(loc,Domain.staticBoard());

            // if no tile present
            if (!optionalTile.isPresent()) {
                throw new IllegalArgumentException("No tile at the target location!");
            }

            newPos = optionalTile.get();

            boolean checkDoor = tryOpenLockedDoor(newPos);

            if (checkDoor) {
                Domain.staticBoard().replaceTileAt(newPos.getLocation(), new FreeTile(loc));
                movePlayer(newPos, loc);
            }

            if (!(newPos instanceof FreeTile)) {
                System.out.println("Invalid move");
            } //IF NOT INVALID

            else {
                if(newPos instanceof InformationTile){
                    ((InformationTile) newPos).isChapOn(this);
                }
                Player.interact(this, loc);
                movePlayer(newPos, loc);
                this.checkTreasures();
            }
        } else {
            throw new IllegalArgumentException("Tile not in board boundary");
        }
    }

    //check if player has key for locked door
    public boolean tryOpenLockedDoor(Tile lockedDoor) {
        if(lockedDoor instanceof LockedDoor){
            LockedDoor lock = (LockedDoor) lockedDoor;
            return this.keys.contains(lock.getUnlockKey());
        }
        return false;
    }

    public void movePlayer(Tile newPos, Coord loc){
        Tile oldPos = Tile.tileAtLoc(this.location, Domain.staticBoard()).orElseThrow(
                () -> new IllegalArgumentException("OG player position not found")
        );

        newPos.moveEntity(oldPos);

        this.location = loc;
    }


    public void checkTreasures(){
        boolean checkTreasures = this.treasures.containsAll(Domain.getTreasure()); //check if player contains all treasures
        if(checkTreasures) {Board.openExitTile(Domain.staticBoard());} //Unlock all openTiles
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
        Optional<Tile> currentTileOptional = Tile.tileAtLoc(loc,Domain.staticBoard());

        if (currentTileOptional.isPresent()) {
            Tile currentTile = currentTileOptional.get();
            Entity entityOptional = currentTile.getEntity();

            // check if the tile contains an entity
            if(entityOptional != null){

                if (entityOptional instanceof Treasure) {

                    player.treasures.add((Treasure) entityOptional);

                    // remove Entity
                    currentTile.setEntity(null);
                }

                else if (entityOptional instanceof Key) {

                    player.keys.add((Key) entityOptional);

                    // rem oveentity
                    currentTile.setEntity(null);
                }
            }
        }
    }


    //FOR TESTING
    public String getTresDisplay(){
        String s = "";

        for(var x: this.getTreasure()){
            s+= "$";
        }
        return s;
    }

    public void testKey(Key k){
        this.keys.add(k);
    }
}
