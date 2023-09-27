import org.json.JSONArray;
import org.json.JSONObject;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Recorder {

    private static final String GAME_STATE_FILE = "game_state.json";

    // GameState class to test the Recorder
    private static class GameState {
        int playerX;
        int playerY;
        int score;

        public GameState(int playerX, int playerY, int score) {
            this.playerX = playerX;
            this.playerY = playerY;
            this.score = score;
        }
    }

    private static void saveGameStateFile(List<GameState> gameHistory) {
        JSONArray jsonGameHistory = new JSONArray();
        int count = 0;
        for (GameState gameState : gameHistory) {
            JSONObject jsonGameState = new JSONObject();
            jsonGameState.put("playerX", gameState.playerX);
            jsonGameState.put("playerY", gameState.playerY);
            jsonGameState.put("score", gameState.score);
            jsonGameState.put("frame", count);
            jsonGameHistory.put(jsonGameState);
            count++;
        }

        try (FileWriter fileWriter = new FileWriter(GAME_STATE_FILE)) {
            fileWriter.write(jsonGameHistory.toString(4)); // Indent with 4 spaces for readability
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // loading a json file
    private static HashMap<Integer, GameState> loadGameStateFile() {
        try {
            String gameStateJson = new String(Files.readAllBytes(Paths.get(GAME_STATE_FILE)));
            JSONArray jsonGameHistory = new JSONArray(gameStateJson);

            HashMap<Integer, GameState> loadedGameHistory = new HashMap<>();
            for (int i = 0; i < jsonGameHistory.length(); i++) {
                JSONObject jsonGameState = jsonGameHistory.getJSONObject(i);
                int playerX = jsonGameState.getInt("playerX");
                int playerY = jsonGameState.getInt("playerY");
                int score = jsonGameState.getInt("score");
                int count = jsonGameState.getInt("frame");
                loadedGameHistory.put(count,(new GameState(playerX, playerY, score)));
            }

            return loadedGameHistory;
        } catch (IOException e) {
            e.printStackTrace();
            return new HashMap<>(); // Return an empty list if there's an error
        }
    }

    private static List<GameState> gameHistory = new ArrayList<>();
    public static void main(String[] args) {
        // Simulate the game loop
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                // Update game state
                int playerX = (int) (Math.random() * 100);
                int playerY = (int) (Math.random() * 100);
                int score = (int) (Math.random() * 100);

                GameState currentState = new GameState(playerX, playerY, score);
                gameHistory.add(currentState);

                // Save game state to JSON file
                saveGameStateFile(gameHistory);
            }
        }, 0, 2000); // Record game state every 2 seconds

        // Simulate the game replay
        try {
            HashMap<Integer,GameState> loadedGameHistory = loadGameStateFile();
            for (Map.Entry<Integer,GameState> entry: loadedGameHistory.entrySet()) {
                // Update game state based on the recorded data
                System.out.println("Frame: "+entry.getKey());
                System.out.println("Player Position: (" + entry.getValue().playerX + ", " + entry.getValue().playerY + ")");
                System.out.println("Score: " + entry.getValue().score);
                Thread.sleep(1000); // replay speed
                System.out.println();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
