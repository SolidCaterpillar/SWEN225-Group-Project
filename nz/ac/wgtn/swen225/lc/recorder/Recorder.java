package nz.ac.wgtn.swen225.lc.recorder;
import nz.ac.wgtn.swen225.lc.domain.Coord;
import nz.ac.wgtn.swen225.lc.domain.Entity.Enemy;
import nz.ac.wgtn.swen225.lc.domain.Entity.Player;
import nz.ac.wgtn.swen225.lc.domain.Tile.Tile;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


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
    public void setRecord( int currentLevel, int timer, Tile[][] maze){
        JSONObject jsonGameState = new JSONObject();
        jsonGameState.put("currentLevel", currentLevel);
        jsonGameState.put("timer", timer);

        JSONArray mazeArray = new JSONArray(); // store maze in 2d array

        for (Tile[] row : maze) {
            JSONArray rowArray = new JSONArray();
            for (Tile tile : row) {
                JSONObject tileObject = new JSONObject();
                tileObject.put("row tile", tile); // need to put a value or toString value from domain
                rowArray.put(tileObject);
            }
            mazeArray.put(rowArray);
        }

        // Add the 2d maze array to the JSON object
        jsonGameState.put("Maze", mazeArray);

        record.put(jsonGameState); // add all the JSON object in the JSON array
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
