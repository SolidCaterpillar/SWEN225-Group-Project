package nz.ac.wgtn.swen225.lc.renderer;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
/**
 * The SoundManager class is responsible for managing and playing various sound effects in the game.
 * It loads and stores sound clips and provides methods to play specific sounds.
 * @author Arnav Dogra (@dograarna)
 */
public class SoundManager {
    private final Map<String, Clip> soundClips;
    private static final String ICONS_FOLDER = "nz/ac/wgtn/swen225/lc/renderer/gamesounds/";

    /**
     * Constructor for the SoundManager class.
     * Initializes the soundClips map and loads several default sound effects.
     */
    public SoundManager() {
        soundClips = new HashMap<>();
        loadSound("footsteps", "footsteps.wav");
        loadSound("pickup", "pickup.wav");
        loadSound("door", "door.wav");
        loadSound("levelchange", "levelchange.wav");
        loadSound("death", "death.wav");
        loadSound("gamestart", "gamestart.wav");
        loadSound("gamemusic", "gamemusic.wav");
    }

    /**
     * Load a sound clip from a file and store it in the soundClips map.
     *
     * @param key       The unique identifier for the sound clip.
     * @param filePath  The path to the sound file to be loaded.
     */

    private void loadSound(String key, String filePath) {
       String soundFilePath= ICONS_FOLDER+ filePath;
        try {
            File soundFile = new File(soundFilePath);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            soundClips.put(key, clip);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
    /**
     * Play the sound of the player's movement.
     */
    public void playPlayerMoveSound() {
        playSound("footsteps");
    }

    /**
     * Play the sound of collecting  item on board.
     */
    public void playItemCollectSound() {
        playSound("pickup");
    }

    /**
     * Play the sound of a door opening.
     */
    public void playDoorOpenSound() {
        playSound("door");
    }

    /**
     * Play the sound when completing a level.
     */
    public void playLevelCompleteSound() {
        playSound("levelchange");
    }

    /**
     * Play the sound when the game starts when user presses play button.
     */
    public void playGameStartSound() {
        playSound("gamestart");
    }

    /**
     * Play the sound of the player's death.
     */
    public void playDeathSound() {
        playSound("death");
    }


    /**
     * Play the background music.
     */
    public void playBackgroundMusic() {
        Clip clip = soundClips.get("gamemusic");
        if (clip != null) {
            if (clip.isRunning()) {
                clip.stop();
            }
            clip.setFramePosition(0);
            clip.loop(5);
        }
    }

    /**
     * Stop background music sound clip.
     *
     */
    public void stopBackgroundMusic() {
        Clip clip = soundClips.get("gamemusic");
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    /**
     * Play a sound clip by name.
     *
     * @param name The key of the sound clip to be played.
     */
    private void playSound(String name) {
        Clip clip = soundClips.get(name);
        if (clip != null) {
            if (clip.isRunning()) {
                clip.stop();
            }
            clip.setFramePosition(0);
            clip.start();
        }
    }
}
