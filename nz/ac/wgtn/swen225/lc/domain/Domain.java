package nz.ac.wgtn.swen225.lc.domain;

import java.util.ArrayList;
import java.util.List;

import nz.ac.wgtn.swen225.lc.domain.Tile.ExitLock;
import nz.ac.wgtn.swen225.lc.domain.Tile.ExitTile;
import nz.ac.wgtn.swen225.lc.domain.Tile.Tile;
import nz.ac.wgtn.swen225.lc.persistency.Persistency;
import nz.ac.wgtn.swen225.lc.persistency.Level;
import nz.ac.wgtn.swen225.lc.domain.Entity.*;


/**
 * Represents the game domain, handling game state and game entities.
 * This class follows the Singleton pattern.
 * @author gautamchai ID: 300505029
 */
public class Domain {


    private static Domain instance = new Domain();


    private Board curBoard;
    private Player curPlayer;

    private ArrayList<Treasure> treasures;
    private ArrayList<Key> keys;
    private ArrayList<Enemy> enemies;

    private boolean gameWon = false;
    private String currPath = null;


    /**
     * Resets the game domain by nullifying its attributes.
     */
    public void reset() {
        curBoard = null;
        curPlayer = null;
        treasures = null;
        keys = null;
        enemies = null;
        gameWon = false;
    }

    /**
     * Private constructor to ensure only one instance of this class is ever created.
     */
    private Domain() {}


    /**
     * Retrieves the singleton instance of the Domain class.
     * @return The singleton instance.
     */
    public static Domain getInstance() {
        return instance;
    }



    /**
     * Loads the game level based on the provided level enum.
     * @param level The desired game level.
     */
    public void pickLevel(LevelE level){
        gameWon = false;
        Level curLevel = null;
        switch(level){
            case TEST_ONE:
                ArrayList<Key> keyList = new ArrayList<Key>(List.of(new Key(new Coord(0,0),null)));
                ArrayList<Enemy> enemyList = new ArrayList<>(List.of(new Enemy(new Coord(0,0))));
                ArrayList<Treasure> treasureList = new ArrayList<>(List.of(new Treasure(new Coord(0,0))));
                curLevel = new Level(new Board(Board.initializeTiles()),new Player(new Coord(0,0)),keyList, treasureList, enemyList);
                break;
            case LEVEL_ONE:
                curLevel = Persistency.loadLevel1();
                break;
            case LEVEL_TWO:
                curLevel = Persistency.loadLevel2();
                break;
            default:
            	return;
        }


        curBoard = curLevel.board();
        Player newPlayer = curLevel.player();
        if(curPlayer != null){
            curPlayer.setLocation(newPlayer.getLocation());
        }else{
            curPlayer = newPlayer;
        }


        treasures = curLevel.treasures(); //Initialize the class-level ArrayLists
        keys = curLevel.keys();


        if(enemies == null){
            enemies = curLevel.enemies();
        }else{
            enemies.clear();
            enemies.addAll(curLevel.enemies());
        }

        if(!curPlayer.getTreasure().isEmpty()){
            curPlayer.setTreasure(newPlayer.getTreasure());
        }
        if(!curPlayer.getKeys().isEmpty()){
            curPlayer.setKeys(newPlayer.getKeys());
        }


    }



    /**
     * Getter method for the Singleton Domain class.
     * @return returns Domain instance's Board.
     */
    public Board getBoard(){
        return curBoard;
    }



    /**
     * Getter method for the Singleton Domain class.
     * @return returns Domain instance's Player.
     */
    public Player getPlayer(){
        return curPlayer;
    }




    /**
     * Getter method for the Singleton Domain class.
     * @return returns Domain instance's Enemies.
     */
    public ArrayList<Enemy> getEnemies(){return enemies;}



    /**
     * Getter method for the Singleton Domain class.
     * @return returns Domain instance's Keys.
     */
    public  ArrayList<Key> getKeys(){return keys;}


    /**
     * Getter method for the Singleton Domain class.
     * @return returns Domain instance's Treasure.
     */
    public  ArrayList<Treasure> getTreasure(){
        return treasures;
    }


    /**
     * Static nested class to represent the state of the game.
     * It contains utilities for game state checks and transitions.
     * @author gautamchai
     */
    public static final class StateClass extends Domain {
        private static int currentLevel = 1; //starting level


        /**
         * Checks if the player character has been defeated by an enemy.
         *
         * @return true if the player is defeated; false otherwise.
         */
        public static boolean isPlayerDead() {
            Coord playerLocation = Domain.getInstance().getPlayer().getLocation();

            for (Enemy enemy : Domain.getInstance().getEnemies()) {
                if (enemy.getLocation().equals(playerLocation)) {
                    return true;
                }
            }

            return false;
        }


        /**
         * Evaluates the current game state.
         *
         * @param level The current game level.
         * @return 0 if the player is dead, 1 if advanced to level 2, 2 if the game is won, 3 otherwise.
         */
        public static int checkGameState(int level) {

            // Check if the player is still alive
            if (isPlayerDead()) {
                System.out.println("Player has died. Reloading current level...");
                return 0;
            }
            Board curBoard = Domain.getInstance().curBoard;
            Player curPlayer = Domain.getInstance().curPlayer;
            // Check if the player is on an exit tile
            if (curBoard.getTileAtLocation(curPlayer.getTrueLocation()) instanceof ExitTile) {
                currentLevel = level;
                if (currentLevel == 1) {
                    System.out.println("Player reached the exit on level 1. Advancing to level 2...");
                    currentLevel = 2;
                    loadLevel(LevelE.LEVEL_TWO);
                    return 1;
                } else if (currentLevel == 2) {
                    System.out.println("Player reached the exit on level 2. You win!");
                    winState();
                    System.out.println(Domain.getInstance().gameWon);
                    return 2;
                }
            }
            return 3;
        }

        /**
         * Method for implementing player movement for player object for Domain instance.
         * @param move
         * @return an int in response to the playerMove representing different player movement interactions 0 for No info Tile 1 for with InfoTile
         */
        public static int movePlayer(char move){
            return Domain.getInstance().curPlayer.checkMove(move);
        }


        /**
         * Reload game level based on what it currently is.
         * @return void
         */
        public static void reloadCurrentLevel() {
            loadLevel(currentLevel == 1 ? LevelE.LEVEL_ONE : LevelE.LEVEL_TWO);
        }

        /**
         * Method for changing levels in the static class.
         * @param level
         */
        private static void loadLevel(LevelE level) {
            Domain.getInstance().pickLevel(level);
        }

        /**
         * Set game is won boolean True for Domain instance variable.
         * @return void type
         */
        private static void winState(){
            Domain.getInstance().gameWon = true;
        }

    }


    /**
     * Sets the enemies for testing purposes.
     *
     * @param en The list of enemies to be set.
     */
    public void setEnemies(ArrayList<Enemy> en) {
        if (enemies == null) {
            enemies = new ArrayList<>();
        }
        for (Enemy enemy : en) {
            enemies.add(enemy);
        }
    }


    /**
     * Replaces a tile at a given location.
     *
     * @param tile The new tile to be placed.
     */
    public void createTileAtLoc(Tile tile) {
        if (Board.checkInBound(tile.getLocation())) {
            curBoard.replaceTileAt(tile.getLocation(), tile); // Replace the old tile with the new
        }
    }


    /**
     * Indicates the state of the game in terms of won or running.
     * @return boolean indicating whether game was won or not
     */
    public boolean getGameWon(){
        return gameWon;
    }

    /**
     * Saves the current game state to a file.
     *
     * @param path The path where the game state will be saved.
     * @return true if saving was successful; false otherwise.
     */
    public static boolean setPath(String path){
        Domain.getInstance().currPath = path;
        Board curBoard = Domain.getInstance().curBoard;
        Player curPlayer = Domain.getInstance().curPlayer;
        var keys = Domain.getInstance().getKeys();
        var treasures = Domain.getInstance().getTreasure();
        var enemies = Domain.getInstance().getEnemies();
        return Persistency.saveLevel(path, new Level(curBoard,curPlayer, keys,treasures,enemies));
    }


}

