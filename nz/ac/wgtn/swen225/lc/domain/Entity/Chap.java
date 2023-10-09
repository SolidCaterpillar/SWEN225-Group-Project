package nz.ac.wgtn.swen225.lc.domain.Entity;

import nz.ac.wgtn.swen225.lc.domain.Board;
import nz.ac.wgtn.swen225.lc.domain.Coord;
import nz.ac.wgtn.swen225.lc.domain.Tile.*;
import nz.ac.wgtn.swen225.lc.domain.Domain;

import java.awt.event.KeyEvent;
import java.util.*;



//REDUNDANT CLASS REPLACED WITH PLAYER


public class Chap extends Player{
    //starting Orientation is South
    protected Orientation direction = Orientation.SOUTH;
    protected ArrayList<Treasure> treasures;
    protected Coord location;

    protected ArrayList<Key> keys;

    public Chap(Coord loc){
        super(loc);
        treasures = new ArrayList<>();
        keys = new ArrayList<>();
    }


    //MAKE THIS A STATIC MOVE LATER IN ENTITY FOR BOTH ENEMY AND PLAYER

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

        //if (!checkInBound(loc)) {return;} //if new loc in bound
        Optional<Tile> optionalTile = Tile.tileAtLoc(loc, Domain.staticBoard()); //get new tile
        if (!optionalTile.isPresent()) { throw new IllegalArgumentException("No tile at the target location!");}//if no tile present
        newPos = optionalTile.get();
        if (!(newPos instanceof FreeTile)) {System.out.println("Invalid move"); } //Currently only movement add interaction later too

        Player.interact(this, loc); //Static interaction method will expand to encompass tile interactions with locked doors etc.

        Tile oldPos = Tile.tileAtLoc(this.location, Domain.staticBoard()).orElseThrow(
                () -> new IllegalArgumentException("OG player position not found")
        );

        newPos.moveEntity(oldPos);
        this.location = loc;
        //Check treasure count here and when its all taken unlock exitlock
        boolean checkTreasures = Domain.getTreasure().equals(getTreasure()); //check if player contains all treasures
        boolean checkKeys;
        if(checkTreasures) {}//unlock Exit TIles.

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
}
