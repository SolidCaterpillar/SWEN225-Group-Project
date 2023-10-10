package nz.ac.wgtn.swen225.lc.recorder;
import nz.ac.wgtn.swen225.lc.domain.Coord;
import nz.ac.wgtn.swen225.lc.domain.Entity.Enemy;
import nz.ac.wgtn.swen225.lc.domain.Entity.Player;
import nz.ac.wgtn.swen225.lc.domain.Tile.*;
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
 *
 */
public class Recorder {

    private JSONArray record;

    public Recorder() { this.record = new JSONArray(); }

    /**
     * record the game everytime the gui redraw
     * @param currentLevel record the current level of the game
     * @param timer record the timer of the ongoing game
     * @param maze record the 2d maze of the ongoing game
     */
    public void setRecord( int currentLevel, int timer, Tile[][] maze) {
        JSONObject jsonGameState = new JSONObject();
        jsonGameState.put("currentLevel", currentLevel);
        jsonGameState.put("timer", timer);
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
        JSONArray walls = new JSONArray();
        JSONArray lockedDoors = new JSONArray();
        JSONArray exitLocks = new JSONArray();
        JSONArray questionBlocks = new JSONArray();

        for (int row = 0; row < maze.length; row++) {
            for (int col = 0; col < maze[row].length; col++) {
                Tile tile = maze[row][col];
                JSONObject obj = new JSONObject();
                obj.put("x", tile.getLocation().x());
                obj.put("y", tile.getLocation().y());

                if (tile instanceof ExitTile) {
                    tiles.put("exit", obj);
                } else if (tile instanceof Wall) {
                    obj.put("length_down", 0);
                    obj.put("length_up", 0);
                    obj.put("length_right", 0);
                    obj.put("length_left", 0);
                    walls.put(obj);
                } else if (tile instanceof LockedDoor) {
                    LockedDoor lockedDoor = (LockedDoor) tile;
                    obj.put("colour", lockedDoor.getColour().toString());
                    lockedDoors.put(obj);
                } else if (tile instanceof ExitLock) {
                    exitLocks.put(obj);
                } else if (tile instanceof InformationTile) {
                    InformationTile informationTile = (InformationTile) tile;
                    obj.put("message", informationTile.getInformation());
                    questionBlocks.put(obj);
                }
            }
        }

        tiles.put("walls", walls);
        tiles.put("lockedDoors", lockedDoors);
        tiles.put("exitLocks", exitLocks);
        tiles.put("questionBlocks", questionBlocks);

        return tiles;
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

    /*
    // testing the recorder
    public static void main(String[] args) throws IOException {
        Recorder test = new Recorder();
        Tile[][] mazeArray = new Tile[2][2];
        mazeArray[0][0] = new Tile(new Coord(0,0));
        mazeArray[0][1] = new Tile(new Coord(0,1));
        mazeArray[1][0] = new Tile(new Coord(1,0));
        mazeArray[1][1] = new Tile(new Coord(1,1));

        int currentLevel = 3;
        int timer = 120;

        test.setRecord(currentLevel, timer,mazeArray);
        test.setRecord(currentLevel, timer,mazeArray);
        test.setRecord(currentLevel, timer,mazeArray);

        test.saveAsFile("Testing");
    }
     */
}
