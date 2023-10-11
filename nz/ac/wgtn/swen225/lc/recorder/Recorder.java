package nz.ac.wgtn.swen225.lc.recorder;
import nz.ac.wgtn.swen225.lc.domain.Colour;
import nz.ac.wgtn.swen225.lc.domain.Coord;
import nz.ac.wgtn.swen225.lc.domain.Entity.Enemy;
import nz.ac.wgtn.swen225.lc.domain.Entity.Player;
import nz.ac.wgtn.swen225.lc.domain.Tile.*;
import nz.ac.wgtn.swen225.lc.persistency.Level;
import nz.ac.wgtn.swen225.lc.renderer.GameCanvas;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import nz.ac.wgtn.swen225.lc.app.*;

/**
 * @author Ricky Fong 300500545
 * The Recorder class record the game when the game start
 * After the game is finish or end the record of the game will save
 **/
public class Recorder {

    private JSONArray record;

    public Recorder() { this.record = new JSONArray(); }

    /**
     * record the game everytime the gui redraw
     * @param currentLevel record the current level of the game
     * @param timer record the timer of the ongoing game
     * @param maze record the 2d maze of the ongoing game
     */
    public void setRecord( int currentLevel, int timer, Player player ,Tile[][] maze) {
        JSONObject jsonGameState = new JSONObject();
        jsonGameState.put("currentLevel", currentLevel);
        jsonGameState.put("timer", timer);
        JSONObject p = savePlayer(player);
        jsonGameState.put("player", p);
        JSONObject tiles = saveTiles(maze);
        jsonGameState.put("maze", tiles);
        record.put(jsonGameState); // add all the JSON object in the JSON array
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

        // for nested loop the maze
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
                    obj.put("x",msg.getLocation().x());
                    obj.put("y",msg.getLocation().y());
                    obj.put("message",msg.getInformation() );
                    questionBlock.put(obj);
                }
            }
        }

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

        for(int i = 0; i < p.getKeys().size(); i++){
            JSONObject key = new JSONObject();

            key.put("x",p.getKeys().get(i).getLocation().x());
            key.put("y",p.getKeys().get(i).getLocation().y());
            key.put("colour",getStringColour(p.getKeys().get(i).getColour()));
            keys.put(key);

        }

        for(int j = 0; j < p.getTreasure().size(); j++){
            JSONObject treasure = new JSONObject();

            treasure.put("x",p.getTreasure().get(j).getLocation().x());
            treasure.put("y",p.getTreasure().get(j).getLocation().y());
            treasures.put(treasure);
        }


        //sets up the player inventory
        inventory.put("keys", keys);
        inventory.put("treasures", treasures);

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
