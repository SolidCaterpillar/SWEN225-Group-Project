package nz.ac.wgtn.swen225.lc.recorder;

import java.util.*;

import nz.ac.wgtn.swen225.lc.domain.Board;
import nz.ac.wgtn.swen225.lc.domain.Colour;
import nz.ac.wgtn.swen225.lc.domain.Coord;
import nz.ac.wgtn.swen225.lc.domain.Entity.*;
import nz.ac.wgtn.swen225.lc.domain.Tile.*;
import nz.ac.wgtn.swen225.lc.persistency.Level;
import nz.ac.wgtn.swen225.lc.persistency.ParseJson;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author Ricky Fong 300500545
 * The Replay class load the replay game from the file.
 * When Player press the button forward or backward,
 * it display next or previous frame of the record game
 **/
public class Replay {
    private String fileName;

    private HashMap<Integer, GameState> replay;

    private int length;
    private int frame;

    public Replay(String fileName) {
        this.fileName = fileName; // get the file name from when player select or type
        this.replay = new HashMap<>();
        loadReplay(); // load the file
        frame = 0;
    }

    /**
     * Replay the game by reading the json file that exist
     */
    private void loadReplay() {
        try {
            String jsonContent = new String(Files.readAllBytes(Paths.get(fileName)));
            JSONArray record = new JSONArray(new JSONTokener(jsonContent));
            length = record.length();
            for (int i = 0; i < length; i++) {
                ArrayList<Tile> tiles = new ArrayList<>();

                JSONObject gameStateJson = (JSONObject) record.get(i);
                int currentLevel = gameStateJson.getInt("currentLevel"); // get the level
                int timer = gameStateJson.getInt("timer"); // get the timer

                // get the player's position from json
                JSONObject playerObj = (JSONObject) gameStateJson.get("player");
                int playerX = playerObj.getInt("x");
                int playerY = playerObj.getInt("y");
                Player player = new Player(new Coord(playerX,playerY));

                JSONObject inventory =  playerObj.getJSONObject("inventory");

                JSONArray keys = (JSONArray) inventory.get("keys");
                ArrayList<Key> keyList = new ArrayList<>();

                // get the player's keys from inventory from json
                for(int o = 0; o < keys.length(); o++){
                    JSONObject keyObject = (JSONObject) keys.get(o);
                    int keyX = keyObject.getInt("x");
                    int keyY = keyObject.getInt("y");
                    Colour color = parseColour(keyObject.getString("colour"));
                    keyList.add(new Key(new Coord(keyX,keyY),color));
                }
                player.setKeys(keyList);

                JSONArray treasures = (JSONArray) inventory.get("treasures");
                ArrayList<Treasure> treasureList = new ArrayList<>();

                // get the player's treasures from inventory from json
                for(int p = 0; p < treasures.length(); p++){
                    JSONObject treasureObject = (JSONObject) treasures.get(p);
                    int keyX = treasureObject.getInt("x");
                    int keyY = treasureObject.getInt("y");
                    treasureList.add(new Treasure(new Coord(keyX,keyY)));
                }

                player.setTreasure(treasureList);

                JSONObject  maze =  (JSONObject) gameStateJson.get("maze");
                // get the wall's position from json
                JSONArray wallArray = (JSONArray) maze.get("walls");
                for(int j = 0; j < wallArray.length(); j++){
                    JSONObject wallObject = (JSONObject) wallArray.get(j);
                    int wallX = wallObject.getInt("x");
                    int wallY = wallObject.getInt("y");
                    tiles.add(new Wall(new Coord(wallX,wallY)));
                }

                // get the information's position from json
                JSONArray questionBlocksArray = (JSONArray) maze.get("questionBlocks");
                for(int k = 0; k < questionBlocksArray.length(); k++){
                    JSONObject questionBlocks = (JSONObject) questionBlocksArray.get(k);
                    int questionBlocksX = questionBlocks.getInt("x");
                    int questionBlocksY = questionBlocks.getInt("y");
                    String questionBlocksMessage = questionBlocks.getString("message");
                    tiles.add(new InformationTile(new Coord(questionBlocksX,questionBlocksY), questionBlocksMessage));
                }

                // get the information's position from json
                JSONArray exitLocksArray = (JSONArray) maze.get("exitLocks");
                for(int l = 0; l < exitLocksArray.length(); l++){
                    JSONObject exitLocks = (JSONObject) exitLocksArray.get(0);
                    int exitLocksX = exitLocks.getInt("x");
                    int exitLocksY = exitLocks.getInt("y");
                    tiles.add(new ExitTile(new Coord(exitLocksX,exitLocksY)));
                }

                // get the lockedDoor's position from json
                JSONArray lockedDoorsArray = (JSONArray) maze.get("lockedDoors");
                if(!lockedDoorsArray.isEmpty()) {
                    for (int m = 0; m < exitLocksArray.length(); m++) {
                        JSONObject lockedDoors = (JSONObject) lockedDoorsArray.get(m);
                        int lockedDoorsX = lockedDoors.getInt("x");
                        int lockedDoorsY = lockedDoors.getInt("y");
                        Colour color = parseColour(lockedDoors.getString("colour"));
                        tiles.add(new LockedDoor(new Coord(lockedDoorsX, lockedDoorsY), color));
                    }
                }

                // get the freeTile's position from json
                JSONArray freeTileArray = (JSONArray) maze.get("free tiles");
                    for (int m = 0; m < freeTileArray.length(); m++) {
                        JSONObject freeTile = (JSONObject) freeTileArray.get(m);
                        int lockedDoorsX = freeTile.getInt("x");
                        int lockedDoorsY = freeTile.getInt("y");
                        tiles.add(new FreeTile(new Coord(lockedDoorsX, lockedDoorsY)));
                    }
                replay.put(i,new GameState(currentLevel,timer,player,tiles));
            }

            System.out.println("Replay loaded successfully from " + fileName);
        } catch (IOException e) {
            System.err.println("Error loading replay: " + e.getMessage());
        }
    }

    /**
     * Send a frame to the App to display a replay game.
     * @param i A number that increase or decrease by 1 to get the frame of the replay game.
     * @return A game state on particular frame base on index to load a replay game.
     */
    private GameState replay(int i) {
        this.frame += i;
        if(frame <= 0){
            return replay.get(0);
        } else if (frame > length) {
            return replay.get(length - 1);
        }
        return replay.get(frame-1);
    }

    /**
     * Get the colour from enum base on given colour of the door or key.
     * @param col Getting colour from enum.
     * @return A colour of the door or key.
     */
    private static Colour parseColour(String col){

        switch(col){

            case "pink":
                return Colour.PINK;
            case "purple":
                return Colour.PURPLE;
            case "red":
                return Colour.RED;
            case "yellow":
                return Colour.YELLOW;
        }
        //if color is no valid, will throw error
        throw new IllegalArgumentException();
    }

    /**
     * A record class to store level, timer, player and array of tile in each frame.
     */
    private record GameState(int level, int timer,Player player, ArrayList<Tile> arrayTile){}

}