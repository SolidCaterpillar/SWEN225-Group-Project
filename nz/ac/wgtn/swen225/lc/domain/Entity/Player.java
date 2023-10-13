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


/**
 * Represents the Player character in the game, providing functionality
 * for movement, interaction with game items, and game status checks.
 * @author gautamchai ID: 300505029
 */
public class Player implements Entity{

    protected Orientation direction = Orientation.SOUTH;
    protected ArrayList<Treasure> treasures;
    protected Coord location;

    protected ArrayList<Key> keys;

    protected boolean interact;

    /**
     * Constructs a Player object at a specified location.
     * @param loc The starting location of the player.
     */
    public Player(Coord loc){
        this.location = loc;
        treasures = new ArrayList<>();
        keys = new ArrayList<>();
        interact = false;
    }


    /**
     * Checks if the player can move in the specified direction and performs the move if valid.
     *
     * @param keyEvent The character key representing the move direction.
     * @return An integer representing the type of tile the player moved to or interacted with.
     */
    public int checkMove(char keyEvent) {
        this.interactFalse();
        this.changeDir(keyEvent);

        System.out.println(keyEvent);

        Coord loc = null;
        Tile newPos = null;

        loc = switch (keyEvent) {
            case 'w' -> loc = this.location.moveUp();
            case 'a' -> loc = this.location.moveLeft();
            case 's' -> loc = this.location.moveDown();
            case 'd' -> loc = this.location.moveRight();
            default -> throw new IllegalArgumentException("Invalid key pressed!");
        };
        if (Board.checkInBound(loc)) {

            Optional<Tile> optionalTile = Tile.tileAtLoc(loc,Domain.getInstance().getBoard());


            if (!optionalTile.isPresent()) {
                throw new IllegalArgumentException("No tile at the target location!");
            }

            newPos = optionalTile.get();


            if (!(newPos instanceof FreeTile)) {
                System.out.println("Invalid move");
            }

            else {

                Player.interact(this, loc);
                movePlayer(newPos, loc);
                if(this.tryOpenAdjacentLockedDoor()){
                    return 2;
                }
            }
        } else {
            throw new IllegalArgumentException("Tile not in board boundary");
        }
        return newPos instanceof InformationTile ? 1 : 0;
    }



    /**
     * Attempts to open any adjacent locked doors that the player has a matching key for.
     *
     * @return true if the player opened a locked door; false otherwise.
     */
    public boolean tryOpenAdjacentLockedDoor() {
        Coord currentLoc = this.location;

        // Define the true adjacent locations
        Coord upLoc = getTrueLocation().moveUp();
        Coord downLoc = getTrueLocation().moveDown();
        Coord leftLoc = getTrueLocation().moveLeft();
        Coord rightLoc = getTrueLocation().moveRight();

        ArrayList<Coord> tiles = new ArrayList<>(Arrays.asList(upLoc, downLoc, leftLoc, rightLoc));

        ArrayList<Tile> doors = Board.getTileList(tiles);

        return(lockedDoorOpen(doors) || checkTreasures(doors));
    }


    /**
     * Checks and opens locked doors from a provided list of tiles if the player has a matching key.
     *
     * @param doors A list of tiles to check for locked doors.
     * @return true if a locked door was opened; false otherwise.
     */
    public boolean lockedDoorOpen(ArrayList<Tile> doors){
        for (Tile door : doors) {
            if (door instanceof LockedDoor lock) {
                Colour currentColour = lock.getColour();


                if (hasMatchingKey(currentColour)) {
                    Domain.getInstance().getBoard().replaceTileAt(door.getLocation(), new FreeTile(door.getLocation()));
                    return true;
                }
            }
        }
        return false;
    }



    /**
     * Checks if the player has a key of the specified color.
     *
     * @param color The color of the key.
     * @return true if the player has the specified key; false otherwise.
     */
    private boolean hasMatchingKey(Colour color) {
        for (Key key : keys) {
            if (key.getColour() == color) {
                return true;
            }
        }
        return false;
    }


    /**
     * Moves the player to a new position on the board.
     *
     * This method updates the player's location, handles the transition of the player's entity
     * from its current tile to the new tile, and ensures the integrity of the board's state.
     *
     *
     * @param newPos The tile to which the player is moving.
     * @param loc The coordinate of the new tile position.
     * @throws IllegalArgumentException if the original player position is not found on the board.
     */
    public void movePlayer(Tile newPos, Coord loc){
        Tile oldPos = Tile.tileAtLoc(this.location, Domain.getInstance().getBoard()).orElseThrow(
                () -> new IllegalArgumentException("OG player position not found")
        );

        newPos.moveEntity(oldPos);

        this.location = loc;
    }


    /**
     * Checks if the player has collected all the required treasures to unlock the exit.
     *
     * This method assesses whether the player has all the treasures needed to open an
     * ExitLock. If the player does possess the required treasures and an ExitLock tile is
     * found among the provided tiles, the ExitLock is replaced with a FreeTile, thereby
     * unlocking the exit.
     *
     *
     * @param doors A list of tiles to check for the presence of an ExitLock.
     * @return true if all required treasures are with the player and the exit was unlocked;
     *         false otherwise.
     */
    public boolean checkTreasures(ArrayList<Tile> doors) {
        boolean checkTreasures = this.treasures.containsAll(Domain.getInstance().getTreasure());
        for (Tile tile : doors) {
            if (tile instanceof ExitLock) {
                if (checkTreasures) {
                    Domain.getInstance().getBoard().replaceTileAt(tile.getLocation(), new FreeTile(tile.getLocation()));
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * Retrieves a list of keys the player currently has.
     *
     * @return An unmodifiable list of keys the player possesses.
     */
    public ArrayList<Key> getKeys() { //Keygetter
        return new ArrayList<>(Collections.unmodifiableCollection(keys));
    }


    /**
     * Retrieves a list of treasures the player currently has.
     *
     * @return An unmodifiable list of treasures the player possesses.
     */
    public ArrayList<Treasure> getTreasure(){ //Treasuregetter
        return new ArrayList<>(Collections.unmodifiableCollection(treasures));
    }


    /**
     * Gets the current location of the player on the board.
     *
     * @return The player's location represented as coordinates.
     */
    @Override
    public Coord getLocation() {
        return location;
    }

    /**
     * Gets the X-coordinate of the player's location.
     *
     * @return The X-coordinate of the player's location.
     */
    public int getX(){
        return this.location.x();
    }


    /**
     * Gets the Y-coordinate of the player's location.
     *
     * @return The Y-coordinate of the player's location.
     */
    public int getY(){
        return this.location.y();
    }


    /**
     * Retrieves the true location of the player by flipping X and Y coordinates.
     *
     * @return The true location of the player.
     */
    public Coord getTrueLocation(){
        return new Coord(getY(), getX());
    }


    /**
     * Changes the player's orientation based on the provided key code.
     * Useful for updating the sprite's direction in the game.
     *
     * @param keyCode The key representing the direction ('w' for north, 'a' for west, etc.).
     */
    public void changeDir(char keyCode) {
        switch (keyCode) {
            case 'w' -> this.direction = Orientation.NORTH;
            case 'a' -> this.direction = Orientation.WEST;
            case 's' -> this.direction = Orientation.SOUTH;
            case 'd' -> this.direction = Orientation.EAST;
        }
    }

    /**
     * Retrieves the current orientation of the player.
     *
     * @return The player's current orientation.
     */
    public Orientation getDirection(){
        return this.direction;
    }


    public String toString(){
        return "PP";
    }


    /**
     * Allows the player to interact with an entity at a specific location.
     * This interaction can result in collecting keys or treasures.
     *
     * @param player The player entity.
     * @param loc The location to check for interaction.
     */

    public static void interact(Player player, Coord loc) {

        Optional<Tile> currentTileOptional = Tile.tileAtLoc(loc,Domain.getInstance().getBoard());

        if (currentTileOptional.isPresent()) {
            Tile currentTile = currentTileOptional.get();
            Entity entityOptional = currentTile.getEntity();


            if(entityOptional != null){

                if (entityOptional instanceof Treasure) {
                    player.interactTrue();
                    player.treasures.add((Treasure) entityOptional);


                    currentTile.setEntity(null);
                }

                else if (entityOptional instanceof Key) {
                    player.interactTrue();
                    player.keys.add((Key) entityOptional);


                    currentTile.setEntity(null);
                }
            }
        }
    }



    /**
     * Sets the player's location.
     *
     * @param location The new location for the player.
     */
    public void setLocation(Coord location){
        this.location = location;
    }


    /**
     * Sets the keys the player currently possesses.
     *
     * @param keys The list of keys.
     */
    public void setKeys(ArrayList<Key> keys){
        this.keys = keys;
    }


    /**
     * Sets the treasures the player currently possesses.
     *
     * @param treasure The list of treasures.
     */

    public void setTreasure(ArrayList<Treasure> treasure){
        this.treasures = treasure;
    }


    /**
     * Marks that the player has interacted with an entity.
     */
    public void interactTrue(){
        this.interact = true;
    }



    /**
     * Resets the interaction flag for the player.
     */
    public void interactFalse(){
        this.interact = false;
    }




    /**
     * Retrieves the interaction state of a player.
     *
     * @param player The player entity.
     * @return true if the player has interacted with an entity, false otherwise.
     */
    public static boolean getInteract(Player player){
        return player.interact;
    }




    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Player)) {
            return false;
        }

        Player otherPlayer = (Player) obj;

        if (!location.equals(otherPlayer.location)) {
            return false;
        }

        if (treasures.size() != otherPlayer.treasures.size() ||
                !treasures.containsAll(otherPlayer.treasures)) {
            return false;
        }

        if (keys.size() != otherPlayer.keys.size() ||
                !keys.containsAll(otherPlayer.keys)) {
            return false;
        }

        return true;
    }

    public int hashCode() {
        return location.hashCode()+ direction.hashCode();
    }


}
