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
import org.json.JSONTokener;

public class Replay {
    private String fileName;

    public Replay(String fileName) {
        this.fileName = fileName;
        loadReplay();
    }

    public void loadReplay() {
        try {
            String jsonContent = new String(Files.readAllBytes(Paths.get(fileName)));
            JSONArray record = new JSONArray(new JSONTokener(jsonContent));

            for (int i = 0; i < record.length(); i++) {
                JSONObject gameStateJson = (JSONObject) record.get(i);
                int currentLevel = gameStateJson.getInt("currentLevel");
                int timer = gameStateJson.getInt("timer");

                JSONArray mazeArray = (JSONArray) gameStateJson.get("maze");
                Tile[][] maze = new Tile[mazeArray.length()][];
            }

            System.out.println("Replay loaded successfully from " + fileName);
        } catch (IOException e) {
            System.err.println("Error loading replay: " + e.getMessage());
        }
    }
}