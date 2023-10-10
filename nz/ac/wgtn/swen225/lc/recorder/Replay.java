package nz.ac.wgtn.swen225.lc.recorder;

import java.util.*;

import nz.ac.wgtn.swen225.lc.domain.Colour;
import nz.ac.wgtn.swen225.lc.domain.Coord;
import nz.ac.wgtn.swen225.lc.domain.Entity.Enemy;
import nz.ac.wgtn.swen225.lc.domain.Entity.Player;
import nz.ac.wgtn.swen225.lc.domain.Tile.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author Ricky Fong 300500545
 *
 */
public class Replay {
    private String fileName;

    private HashMap<Integer, GameState> replay;

    public Replay(String fileName) {
        this.fileName = fileName; // get the file name from when player select or type
        this.replay = new HashMap<>();
        loadReplay(); // load the file
    }

    /**
     * Replay the game by reading the json file that exist
     */
    public void loadReplay() {
        try {
            String jsonContent = new String(Files.readAllBytes(Paths.get(fileName)));
            JSONArray record = new JSONArray(new JSONTokener(jsonContent));

            for (int i = 0; i < record.length(); i++) {
                ArrayList<Tile> tiles = new ArrayList<>();
                JSONObject gameStateJson = (JSONObject) record.get(i);
                int currentLevel = gameStateJson.getInt("currentLevel");
                int timer = gameStateJson.getInt("timer");

                JSONObject  maze =  (JSONObject) gameStateJson.get("maze");

                JSONObject exit = maze.getJSONObject("exit");
                int exitX = exit.getInt("x");
                int exitY = exit.getInt("y");
                tiles.add(new ExitTile(new Coord(exitX, exitY)));


                JSONArray wallArray = (JSONArray) maze.get("walls");
                for(int j = 0; j < wallArray.length(); j++){
                    JSONObject wallObject = (JSONObject) wallArray.get(j);
                    int wallX = wallObject.getInt("x");
                    int wallY = wallObject.getInt("y");
                    tiles.add(new Wall(new Coord(wallX,wallY)));
                }

                JSONArray questionBlocksArray = (JSONArray) maze.get("questionBlocks");
                for(int k = 0; k < questionBlocksArray.length(); k++){
                    JSONObject questionBlocks = (JSONObject) questionBlocksArray.get(k);
                    int questionBlocksX = questionBlocks.getInt("x");
                    int questionBlocksY = questionBlocks.getInt("y");
                    String questionBlocksMessage = questionBlocks.getString("message");
                    tiles.add(new InformationTile(new Coord(questionBlocksX,questionBlocksY), questionBlocksMessage));

                }


                JSONArray exitLocksArray = (JSONArray) maze.get("exitLocks");
                for(int l = 0; l < exitLocksArray.length(); l++){
                    JSONObject exitLocks = (JSONObject) exitLocksArray.get(0);
                    int exitLocksX = exitLocks.getInt("x");
                    int exitLocksY = exitLocks.getInt("y");
                    tiles.add(new ExitTile(new Coord(exitLocksX,exitLocksY)));
                }


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

                replay.put(i,new GameState(currentLevel,timer,tiles));
            }

            System.out.println(replay);
            System.out.println("Replay loaded successfully from " + fileName);
        } catch (IOException e) {
            System.err.println("Error loading replay: " + e.getMessage());
        }
    }

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

    private record GameState(int level, int timer, ArrayList<Tile> arrayTile){}

/*
    public static void main(String[] args) throws IOException {
       Replay test = new Replay("game_state.json");
    }
    
 */
}