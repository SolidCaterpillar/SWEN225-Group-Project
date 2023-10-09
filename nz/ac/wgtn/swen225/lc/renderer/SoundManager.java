package nz.ac.wgtn.swen225.lc.renderer;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SoundManager {
    private Map<String, Clip> soundClips;
    private static final String ICONS_FOLDER = "nz/ac/wgtn/swen225/lc/renderer/gamesounds/";

    public SoundManager() {
        soundClips = new HashMap<>();
        loadSound("footsteps", "footsteps.wav");
        loadSound("pickup", "pickup.wav");
        loadSound("door", "door.wav");
        loadSound("levelchange", "levelchange.wav");
        loadSound("death", "death.wav");
        loadSound("gamestart", "gamestart.wav");
    }

    private void loadSound(String key, String FilePath) {
       String soundFilePath= ICONS_FOLDER+ FilePath;
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

    public void playPlayerMoveSound() {
        playSound("footsteps");
    }

    public void playKeyCollectSound() {
        playSound("pickup");
    }

    public void playDoorOpenSound() {
        playSound("door");
    }

    public void playLevelCompleteSound() {
        playSound("levelchange");
    }
    public void playGameStartSound() {
        playSound("gamestart");
    }

    public void playDeathSound() {
        playSound("death");
    }

    private void playSound(String key) {
        Clip clip = soundClips.get(key);
        if (clip != null) {
            if (clip.isRunning()) {
                clip.stop();
            }
            clip.setFramePosition(0);
            clip.start();
        }
    }
}
