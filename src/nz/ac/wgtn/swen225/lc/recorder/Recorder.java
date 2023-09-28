package nz.ac.wgtn.swen225.lc.recorder;
import nz.ac.wgtn.swen225.lc.persistency.plugin.main.java.org.json.*;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import nz.ac.wgtn.swen225.lc.app.*;
public class Recorder {
    private static final String GAME_STATE_FILE = "game_state.json";
    List<GameState> recorder; // collecting the game state

    private static class GameState{
        int timer; int currentLevel; String chipsText;

        public GameState(int timer, int currentLevel, String chipsText){
            this.timer = timer;
            this.currentLevel = currentLevel;
            this.chipsText = chipsText;
        }
    }

    public Recorder() {
        this.recorder = new ArrayList<>();
    }

    // testing saving a file from the App
    public void saveGameStateFile(int timer, int currentLevel, String chipsText) {
        GameState gameState = new GameState( timer,  currentLevel,  chipsText);
        recorder.add(gameState);
        JSONArray jsonGameHistory = new JSONArray();
        for(GameState state : recorder) {
            JSONObject jsonGameState = new JSONObject();
            jsonGameState.put("timer", state.timer);
            jsonGameState.put("currentLevel", state.currentLevel);
            jsonGameState.put("chipsText", state.chipsText);
            jsonGameHistory.put(jsonGameState);
        }

        try (FileWriter fileWriter = new FileWriter(GAME_STATE_FILE)) {
            fileWriter.write(jsonGameHistory.toString(4)); // Indent with 4 spaces for readability
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // loading a json file
    public static void loadGameStateFile() {
        try {
            String gameStateJson = new String(Files.readAllBytes(Paths.get(GAME_STATE_FILE)));
            JSONArray recordGame = new JSONArray(gameStateJson);

            for (int i = 0; i < recordGame.length(); i++) {
                JSONObject gameState = (JSONObject) recordGame.get(i);
                int timer = gameState.getInt("timer");
                int currentLevel = gameState.getInt("currentLevel");
                String chipsText = gameState.getString("chipsText");

                //printing the statement to test it out
                System.out.println("Timer : " +timer);
                System.out.println("currentLevel : " +currentLevel);
                System.out.println("chipsText : " +chipsText);
                System.out.println();

                // Sleep for 2 seconds (2000 milliseconds)
                Thread.sleep(1000);

            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args){
        loadGameStateFile();
    }

}
