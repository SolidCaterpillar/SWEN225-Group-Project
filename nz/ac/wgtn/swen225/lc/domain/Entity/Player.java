package nz.ac.wgtn.swen225.lc.domain.Entity;
import javax.swing.*;

import nz.ac.wgtn.swen225.lc.domain.Board;
import nz.ac.wgtn.swen225.lc.domain.Colour;
import nz.ac.wgtn.swen225.lc.domain.Coord;
import nz.ac.wgtn.swen225.lc.domain.Domain;
import nz.ac.wgtn.swen225.lc.domain.Tile.*;

import java.awt.event.KeyEvent;
import java.util.*;
import java.util.function.Supplier;


import static nz.ac.wgtn.swen225.lc.domain.Board.openExitTile;

public class Player implements Entity{
    //starting Orientation is South
    protected Orientation direction = Orientation.SOUTH;
    protected ArrayList<Treasure> treasures;
    protected Coord location;

    protected ArrayList<Key> keys;

    protected boolean interact;


    public Player(Coord loc){
        this.location = loc;
        treasures = new ArrayList<>();
        keys = new ArrayList<>();
        interact = false;
    }


    public int checkMove(char keyEvent) {
        //char keyCode = keyEvent.getKeyChar(); //convert to char for switch
        this.interaftFalse();
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


            if (!(newPos instanceof FreeTile)) {
                System.out.println("Invalid move");
            } //IF NOT INVALID

            else {
                if(newPos instanceof InformationTile){
                    ((InformationTile) newPos).isChapOn(this);
                }
                Player.interact(this, loc);
                movePlayer(newPos, loc);
                this.tryOpenAdjacentLockedDoor();
            }
        } else {
            throw new IllegalArgumentException("Tile not in board boundary");
        }
        return newPos instanceof InformationTile ? 1 : 0;
    }

    //check if player has key for locked door
    public void tryOpenAdjacentLockedDoor() {
        Coord currentLoc = this.location;
        Tile currentTile = Domain.staticBoard().getTileAtLocation(currentLoc);

        // Define the true adjacent locations
        Coord upLoc = getTrueLocation().moveUp();
        Coord downLoc = getTrueLocation().moveDown();
        Coord leftLoc = getTrueLocation().moveLeft();
        Coord rightLoc = getTrueLocation().moveRight();

        ArrayList<Coord> tiles = new ArrayList<>(Arrays.asList(upLoc, downLoc, leftLoc, rightLoc));

        ArrayList<Tile> doors = Board.getTileList(tiles);

        lockedDoorOpen(doors);

        checkTreasures(doors);

    }

    public void lockedDoorOpen(ArrayList<Tile> doors){
        for (Tile door : doors) {
            if (door instanceof LockedDoor lock) {
                Colour currentColour = lock.getColour();

                // Check if the player has a key of the same color
                if (hasMatchingKey(currentColour)) {
                    // Open the current LockedDoor
                    Domain.staticBoard().replaceTileAt(door.getLocation(), new FreeTile(door.getLocation()));
                }
            }
        }
    }
    private boolean hasMatchingKey(Colour color) {
        for (Key key : keys) {
            if (key.getColour() == color) {
                return true;
            }
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


    public void checkTreasures(ArrayList<Tile> doors) {
        boolean checkTreasures = this.treasures.containsAll(Domain.getTreasure());
        for (Tile tile : doors) {
            if (tile instanceof ExitLock) {
                if (checkTreasures) {
                    Domain.staticBoard().replaceTileAt(tile.getLocation(), new FreeTile(tile.getLocation()));
                }
            }
        }
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

    public Coord getTrueLocation(){
        return new Coord(getY(), getX());
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
                    player.intractTrue();
                    player.treasures.add((Treasure) entityOptional);

                    // remove Entity
                    currentTile.setEntity(null);
                }

                else if (entityOptional instanceof Key) {
                    player.intractTrue();
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
        s+= "\n";
        for(Key k: this.getKeys()) {
            s += "Key:" + k.getColour();
        }

        return s;
    }

    public void testKey(Key k){
        this.keys.add(k);
    }

    public void setLocation(Coord location){
        this.location = location;
    }


    public void setKeys(ArrayList<Key> keys){
        this.keys = keys;
    }

    public void setTreasure(ArrayList<Treasure> treasure){
        this.treasures = treasure;
    }

    public void intractTrue(){
        this.interact = true;
    }
    public void interaftFalse(){
        this.interact = false;
    }

    public static boolean getInteract(Player player){
        return player.interact;
    }
}
