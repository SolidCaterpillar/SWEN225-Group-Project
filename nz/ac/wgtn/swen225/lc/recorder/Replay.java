package nz.ac.wgtn.swen225.lc.recorder;

import java.util.*;

import nz.ac.wgtn.swen225.lc.domain.Coord;
import nz.ac.wgtn.swen225.lc.domain.Entity.Enemy;
import nz.ac.wgtn.swen225.lc.domain.Entity.Player;
import nz.ac.wgtn.swen225.lc.domain.Tile.Tile;
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

    public Replay(String fileName) {
        this.fileName = fileName; // get the file name from when player select or type
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
                JSONObject gameStateJson = (JSONObject) record.get(i);
                int currentLevel = gameStateJson.getInt("currentLevel");
                int timer = gameStateJson.getInt("timer");

                JSONArray mazeArray = (JSONArray) gameStateJson.get("maze");
                Tile[][] maze = new Tile[mazeArray.length()][]; // getting the size of the maze

                // contructing the 2D maze
                for (int j = 0; j < mazeArray.length(); j++) {
                    JSONArray rowArray = mazeArray.getJSONArray(j);
                    maze[j] = new Tile[rowArray.length()];

                    for (int k = 0; k < rowArray.length(); k++) {
                        JSONObject tileObj = (JSONObject) rowArray.get(k);
                       // Coord value  = ; //tileObj.getString("name"); // get value from the json file

                        // Create a tile object to retrieved data
                       //Tile tile = new Tile(value); // recreate a tile
                        //maze[j][k] = tile;
                    }
                }

                //displayGame(currentLevel,timer,maze) // sending a data to other modules to create a display
            }

            System.out.println("Replay loaded successfully from " + fileName);
        } catch (IOException e) {
            System.err.println("Error loading replay: " + e.getMessage());
        }
    }
}