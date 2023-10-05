package nz.ac.wgtn.swen225.lc.recorder;
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
     */
    //public void setRecord( int currentLevel, int timer, Tile[][] maze){
    public void setRecord(int currentLevel, int timer) {
        JSONObject jsonGameState = new JSONObject();
        jsonGameState.put("currentLevel", currentLevel);
        jsonGameState.put("timer", timer);
        //jsonGameState.put("maze", maze); // use maze's toString to store in json but in 2d
        record.put(jsonGameState);
    }

    /**
     * Save the record game when player finish or end (quit) the game
     * @param fileName record the current level of the game
     */
    public void saveAsFile(String fileName) throws IOException {
        FileWriter fileWriter = new FileWriter(fileName);
        fileWriter.write(record.toString());
        fileWriter.close();
        System.out.println("Game state saved successfully to " + fileName);
    }
}
