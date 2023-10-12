package nz.ac.wgtn.swen225.lc.recorder;

import java.util.*;

import nz.ac.wgtn.swen225.lc.app.GUI;
import nz.ac.wgtn.swen225.lc.app.Main;
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

import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Timer;

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

    private GUI gui;

    private boolean isAutoReplaying = false;
    private Timer autoReplayTimer;

    public Replay(String fileName, GUI gui ) {
        this.gui = gui;
        this.fileName = fileName; // get the file name from when player select or type
        this.replay = new HashMap<>();
        loadReplay(); // load the file

        JFrame frame = new JFrame("Replay Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 100);

        JPanel panel = new JPanel();

        JButton prevButton = new JButton("Previous");
        prevButton.addActionListener(e -> replay(-1));
        JButton nextButton = new JButton("Next");
        nextButton.addActionListener(e -> replay(1));

        JButton autoButton = new JButton("Auto replay");
        //autoButton.addActionListener(e -> autoReplay());

        panel.add(prevButton);
        panel.add(nextButton);
        panel.add(autoButton);

        frame.add(panel);
        frame.setLocation(700,500);
        frame.setVisible(true);
        frame.setAlwaysOnTop(true);
    }

    /**
     * Replay the game by reading the JSON file that exists.
     */
    private void loadReplay() {
        try {
            String jsonContent = new String(Files.readAllBytes(Paths.get(fileName)));
            JSONArray record = new JSONArray(new JSONTokener(jsonContent));
            length = record.length();
            for (int i = 0; i < length; i++) {
                Tile[][] mazeTile;

                JSONObject gameStateJson = (JSONObject) record.get(i);
                int currentLevel = gameStateJson.getInt("currentLevel");
                int timer = gameStateJson.getInt("timer");


                Player player = createPlayer(gameStateJson.getJSONObject("player"));
                mazeTile = createMazeTiles(gameStateJson.getJSONObject("maze"));

                createEntityKeys((JSONArray) gameStateJson.get("keys"),mazeTile);
                createEntityTreasures((JSONArray) gameStateJson.get("treasures"),mazeTile);
                createEnemies((JSONArray) gameStateJson.get("enemies"),mazeTile);

                replay.put(i, new GameState(currentLevel, timer, player, mazeTile));
            }
            System.out.println("Replay loaded successfully from " + fileName);
        } catch (IOException e) {
            System.err.println("Error loading replay: " + e.getMessage());
        }
    }

    /**
     * Create a player using information from json
     * @param playerObj A number that increase or decrease by 1 to get the frame of the replay game.
     * @return A player
     */
    private Player createPlayer(JSONObject playerObj) {
        int playerX = playerObj.getInt("x");
        int playerY = playerObj.getInt("y");

        JSONObject inventory = playerObj.getJSONObject("inventory");
        ArrayList<Key> keys = createKeys((JSONArray) inventory.get("keys"));
        ArrayList<Treasure> treasures = createTreasures((JSONArray)inventory.get("treasures"));

        Player player = new Player(new Coord(playerX, playerY));
        player.setKeys(keys);
        player.setTreasure(treasures);

        return player;
    }

    /**
     * Create a player using information from json
     * @param enemiesArray An array list of enemies to get their information from json
     * @param mazeTile A 2d tiles to store enemies at their specific location
     */
    private void createEnemies(JSONArray enemiesArray, Tile[][] mazeTile) {
        for (int p = 0; p < enemiesArray.length(); p++) {
            JSONObject enemiesObject = (JSONObject) enemiesArray.get(p);
            int treasureX = enemiesObject.getInt("x");
            int treasureY = enemiesObject.getInt("y");
            mazeTile[treasureX][treasureY].setEntity(new Treasure(new Coord(treasureX, treasureY)));
        }
    }

    /**
     * Create a player using information from json
     * @param keysArray An array list of keys to get their information from json
     * @param mazeTile A 2d tiles to store keys at their specific location
     */
    private void createEntityKeys(JSONArray keysArray, Tile[][] mazeTile) {
        for (int o = 0; o < keysArray.length(); o++) {
            JSONObject keyObject = (JSONObject) keysArray.get(o);
            int keyX = keyObject.getInt("x");
            int keyY = keyObject.getInt("y");
            Colour color = parseColour(keyObject.getString("colour"));
            mazeTile[keyX][keyY].setEntity(new Key(new Coord(keyX, keyY), color));
        }
    }

    /**
     * Create a player using information from json
     * @param treasuresArray An array list of treasure to get their information from json
     * @param mazeTile A 2d tiles to store keys at their specific location
     */
    private void createEntityTreasures(JSONArray treasuresArray, Tile[][] mazeTile) {
        for (int p = 0; p < treasuresArray.length(); p++) {
            JSONObject treasureObject = (JSONObject) treasuresArray.get(p);
            int treasureX = treasureObject.getInt("x");
            int treasureY = treasureObject.getInt("y");
            mazeTile[treasureX][treasureY].setEntity(new Treasure(new Coord(treasureX, treasureY)));
        }
    }

    /**
     * Create a key from player's inventory using information from json
     * @param keysArray An array list of treasure to get their information from json
     * @return An array of keys
     */
    private ArrayList<Key> createKeys(JSONArray keysArray) {
        ArrayList<Key> keyList = new ArrayList<>();
        for (int o = 0; o < keysArray.length(); o++) {
            JSONObject keyObject = (JSONObject) keysArray.get(o);
            int keyX = keyObject.getInt("x");
            int keyY = keyObject.getInt("y");
            Colour color = parseColour(keyObject.getString("colour"));
            keyList.add(new Key(new Coord(keyX, keyY), color));
        }
        return keyList;
    }

    /**
     * Create a treasures from player's inventory using information from json
     * @param treasuresArray An array list of treasure to get their information from json
     * @return An array of treasures
     */
    private ArrayList<Treasure> createTreasures(JSONArray treasuresArray) {
        ArrayList<Treasure> treasureList = new ArrayList<>();
        for (int p = 0; p < treasuresArray.length(); p++) {
            JSONObject treasureObject = (JSONObject) treasuresArray.get(p);
            int treasureX = treasureObject.getInt("x");
            int treasureY = treasureObject.getInt("y");
            treasureList.add(new Treasure(new Coord(treasureX, treasureY)));
        }
        return treasureList;
    }

    /**
     * Create a 2d array to create maze to store type of tiles information from json
     * @param maze An array list of treasure to get their information from json
     * @return A 2d array of tiles
     */
    private Tile[][] createMazeTiles(JSONObject maze) {
        Tile[][] mazeTile = new Tile[20][20];

        JSONArray wallsArray = (JSONArray) maze.get("walls");
        for (int j = 0; j < wallsArray.length(); j++) {
            JSONObject wallObject = (JSONObject) wallsArray.get(j);
            int wallX = wallObject.getInt("x");
            int wallY = wallObject.getInt("y");
            mazeTile[wallX][wallY] = new Wall(new Coord(wallX, wallY));
        }

        JSONArray questionBlocksArray = (JSONArray) maze.get("questionBlocks");
        for (int k = 0; k < questionBlocksArray.length(); k++) {
            JSONObject questionBlocks = (JSONObject) questionBlocksArray.get(k);
            int questionBlocksX = questionBlocks.getInt("x");
            int questionBlocksY = questionBlocks.getInt("y");
            String questionBlocksMessage = questionBlocks.getString("message");
            mazeTile[questionBlocksX][questionBlocksY] = new InformationTile(new Coord(questionBlocksX, questionBlocksY), questionBlocksMessage);
        }

        JSONArray exitLocksArray = (JSONArray) maze.get("exitLocks");
        for (int l = 0; l < exitLocksArray.length(); l++) {
            JSONObject exitLocks = (JSONObject) exitLocksArray.get(l);
            int exitLocksX = exitLocks.getInt("x");
            int exitLocksY = exitLocks.getInt("y");
            mazeTile[exitLocksX][exitLocksY] = new ExitTile(new Coord(exitLocksX, exitLocksY));
        }

        JSONArray lockedDoorsArray = (JSONArray) maze.get("lockedDoors");
        for (int m = 0; m < lockedDoorsArray.length(); m++) {
            JSONObject lockedDoors = (JSONObject) lockedDoorsArray.get(m);
            int lockedDoorsX = lockedDoors.getInt("x");
            int lockedDoorsY = lockedDoors.getInt("y");
            Colour color = parseColour(lockedDoors.getString("colour"));
            mazeTile[lockedDoorsX][lockedDoorsY] = new LockedDoor(new Coord(lockedDoorsX, lockedDoorsY), color);
        }

        JSONArray freeTileArray = (JSONArray) maze.get("free tiles");
        for (int m = 0; m < freeTileArray.length(); m++) {
            JSONObject freeTile = (JSONObject) freeTileArray.get(m);
            int freeTileX = freeTile.getInt("x");
            int freeTileY = freeTile.getInt("y");
            mazeTile[freeTileX][freeTileY] = new FreeTile(new Coord(freeTileX, freeTileY));
        }

        return mazeTile;
    }


    /**
     * Send a frame to the App to display a replay game.
     * @param i A number that increase or decrease by 1 to get the frame of the replay game.
     * @return A game state on particular frame base on index to load a replay game.
     */
    public void replay(int i) {
        this.frame += i;

        if(frame <= 0){
            gui.replayPane(replay.get(0).maze, replay.get(0).player,  replay.get(0).timer,  replay.get(0).level);
            frame = 0;
        } else if (frame > length) {
            gui.replayPane(replay.get(length - 1).maze, replay.get(length - 1).player, replay.get(length - 1).timer, replay.get(length - 1).level);
            frame = length;
        }else {
            gui.replayPane(replay.get(frame - 1).maze, replay.get(frame - 1).player, replay.get(frame - 1).timer, replay.get(frame - 1).level);
        }
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
    public record GameState(int level, int timer,Player player, Tile[][] maze){}

}
