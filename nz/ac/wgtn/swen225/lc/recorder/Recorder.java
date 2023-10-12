package nz.ac.wgtn.swen225.lc.recorder;
import nz.ac.wgtn.swen225.lc.domain.Colour;
import nz.ac.wgtn.swen225.lc.domain.Domain;
import nz.ac.wgtn.swen225.lc.domain.Entity.Enemy;
import nz.ac.wgtn.swen225.lc.domain.Entity.Key;
import nz.ac.wgtn.swen225.lc.domain.Entity.Player;
import nz.ac.wgtn.swen225.lc.domain.Entity.Treasure;
import nz.ac.wgtn.swen225.lc.domain.Tile.*;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * @author Ricky Fong 300500545
 * The Recorder class record the game when the game start
 * After the game is finish or end the record of the game will save
 */
public class Recorder {

    private JSONArray record;

    public Recorder() { this.record = new JSONArray(); }

    /**
     * record the game everytime the gui redraw when the player move
     * @param currentLevel record the current level of the game
     * @param timer record the timer of the ongoing game
     * @param maze record the 2d maze of the ongoing game
     */
    public void setRecord(int currentLevel, int timer, Player player , Tile[][] maze) {
        JSONObject jsonGameState = new JSONObject();
        jsonGameState.put("currentLevel", currentLevel); // save current level
        jsonGameState.put("timer", timer); // save timer

        JSONObject p = savePlayer(player); // save player's information
        jsonGameState.put("player", p);

        JSONObject tiles = saveTiles(maze); // save tile's information
        jsonGameState.put("maze", tiles);

        JSONArray enemies = saveEnemy(); // save enemy's information
        jsonGameState.put("enemies", enemies);

        JSONArray keys = saveKeys(); // save key's information
        jsonGameState.put("keys", keys);

        JSONArray treasure = saveTreasure(); // save treasure's information
        jsonGameState.put("treasures", treasure);

        record.put(jsonGameState); // add all the JSON object in the JSON array
    }

    /**
     * Saving the Entity enemy's information in the json file
     */
    private static JSONArray saveEnemy(){
        JSONArray enemyList = new JSONArray();
        ArrayList<Enemy> enemiesList = Domain.getEnemies();

        for(int i = 0; i < enemiesList.size(); i++){
            JSONObject enemy = new JSONObject();
            enemy.put("x",enemiesList.get(i).getLocation().x());
            enemy.put("y",enemiesList.get(i).getLocation().y());
            enemyList.put(enemy);
        }

        return enemyList ;
    }

    /**
     * Saving the Entity treasure's information in the json file
     */
    private static JSONArray saveTreasure(){
        JSONArray treasureArray = new JSONArray();
        ArrayList<Treasure> treasureList = Domain.getTreasure();

        for(int i = 0; i < treasureList.size(); i++){
            JSONObject key = new JSONObject();
            key.put("x",treasureList.get(i).getLocation().x());
            key.put("y",treasureList.get(i).getLocation().y());
            treasureArray.put(key);
        }
        return treasureArray ;
    }

    /**
     * Saving the Entity key's information in the json file
     */
    private static JSONArray saveKeys(){
        JSONArray keyArray = new JSONArray();
        ArrayList<Key> keyList = Domain.getKeys();

        for(int i = 0; i < keyList.size(); i++){
            JSONObject key = new JSONObject();
            key.put("x",keyList.get(i).getLocation().x());
            key.put("y",keyList.get(i).getLocation().y());
            key.put("colour",getStringColour(keyList.get(i).getColour()));
            keyArray.put(key);
        }
        return keyArray ;

    }


    /**
     * Record the game every time the GUI redraws.
     * @param maze The maze to store in JSON format.
     * @return A JSON object representing the maze.
     */
    private static JSONObject saveTiles(Tile[][] maze) {
        JSONObject tiles = new JSONObject();
        JSONObject exit = new JSONObject();
        JSONArray walls = new JSONArray();
        JSONArray lockedDoor = new JSONArray();
        JSONArray exitLock = new JSONArray();
        JSONArray questionBlock = new JSONArray();
        JSONArray freeTiles = new JSONArray();

        // for nested loop the maze to store individual
        for (int row = 0; row < maze.length; row++) {
            for (int col = 0; col < maze[row].length; col++) {
                Tile tile = maze[row][col];
                JSONObject obj = new JSONObject();
                if(tile instanceof FreeTile) {
                    obj.put("x", tile.getLocation().x());
                    obj.put("y", tile.getLocation().y());
                    freeTiles.put(obj);

                }else if(tile instanceof ExitTile){
                    exit.put("x",tile.getLocation().x());
                    exit.put("y",tile.getLocation().y());

                }else if(tile instanceof Wall){
                    obj.put("x",tile.getLocation().x());
                    obj.put("y",tile.getLocation().y());
                    walls.put(obj);

                }else if(tile instanceof LockedDoor lo){
                    obj.put("x",lo.getLocation().x());
                    obj.put("y",lo.getLocation().y());
                    obj.put("colour", getStringColour(lo.getColour()));
                    lockedDoor.put(obj);

                }else if(tile instanceof ExitLock){
                    obj.put("x",tile.getLocation().x());
                    obj.put("y",tile.getLocation().y());
                    exitLock.put(obj);

                }else if(tile instanceof InformationTile msg){
                    System.out.println("msg");
                    obj.put("x",msg.getLocation().x());
                    obj.put("y",msg.getLocation().y());
                    obj.put("message",msg.getInformation() );
                    questionBlock.put(obj);
                }
            }
        }
        tiles.put("exit", exit);
        tiles.put("walls", walls);
        tiles.put("lockedDoors", lockedDoor);
        tiles.put("exitLocks", exitLock);
        tiles.put("questionBlocks", questionBlock);
        tiles.put("free tiles", freeTiles);

        return tiles;
    }
    /**
     * Record the game every time the GUI redraws.
     * @param p A player to get X and Y position.
     * @return A JSON object representing the player.
     */
    private static JSONObject savePlayer(Player p){
        JSONObject player = new JSONObject();
        JSONObject inventory = new JSONObject();
        JSONArray keys = new JSONArray();
        JSONArray treasures = new JSONArray();

        //storing the player's keys
        for(int i = 0; i < p.getKeys().size(); i++){
            JSONObject key = new JSONObject();

            key.put("x",p.getKeys().get(i).getLocation().x());
            key.put("y",p.getKeys().get(i).getLocation().y());
            key.put("colour",getStringColour(p.getKeys().get(i).getColour()));
            keys.put(key);

        }

        //storing the player's treasures
        for(int j = 0; j < p.getTreasure().size(); j++){
            JSONObject treasure = new JSONObject();

            treasure.put("x",p.getTreasure().get(j).getLocation().x());
            treasure.put("y",p.getTreasure().get(j).getLocation().y());
            treasures.put(treasure);
        }

        //store keys and treasures in the player's inventory
        inventory.put("keys", keys);
        inventory.put("treasures", treasures);

        //store player's information
        player.put("x", p.getX());
        player.put("y", p.getY());
        player.put("inventory", inventory);

        return player ;
    }

    /**
     * Get the string colour base on enum base on given colour of the door or key.
     * @param col Getting string colour from enum.
     * @return A colour of the door or key.
     */
    private static String getStringColour(Colour col){
        switch(col){
            case PINK:
                return "pink";
            case PURPLE:
                return "purple";
            case RED:
                return "red";
            case YELLOW:
                return "yellow";
        }
        throw new IllegalArgumentException();
    }

    /**
     * Save the record game when player finish or end (quit) the game
     * @param fileName record the current level of the game
     */
    public void saveAsFile(String fileName) throws IOException {
        FileWriter fileWriter = new FileWriter(fileName);
        fileWriter.write(record.toString(4));
        fileWriter.close();
        System.out.println("Game state saved successfully to " + fileName);
    }
}
