package nz.ac.wgtn.swen225.lc.renderer;

import javax.swing.*;
import java.awt.*;

/**
 * responsible for handling the rendering logic for the game.
 */
public class Renderer extends JPanel {

    // Define  game-related variables here

    private static final String ICONS_FOLDER = "gameicons/"; // Path to the icons folder

    private ImageIcon wallTileIcon; // specific ImageIcons for display
    private ImageIcon freeTileIcon;
    private ImageIcon treasureIcon;

    private ImageIcon chapIcon;
    private ImageIcon exitIcon;
    private ImageIcon exitLockIcon;

    private ImageIcon infoFieldIcon;
    private ImageIcon keyIcon;
    private ImageIcon lockedDoorIcon;

    // Define game-related variables
     private Board maze;
    // private Chap player;
    // private List<Enemy> actors;
    //private KeyTile KEYTILE;
    // private Key KEY;

    /**
     * Constructor to initialize game-related variables
     *  set up  game state and resources here
     */
    public Renderer() {


        wallTileIcon = loadImageIcon("walltile.png");
        freeTileIcon = loadImageIcon("freetile.png");
        chapIcon = loadImageIcon("chap.png");
        treasureIcon = loadImageIcon("treasure.png");
        exitLockIcon = loadImageIcon("exitlock.png");
        exitIcon = loadImageIcon("exit.png");
        infoFieldIcon = loadImageIcon("infofield.png");
        keyIcon = loadImageIcon("key.png");
        lockedDoorIcon = loadImageIcon("lockeddoor.png");
    }

    /**
     * Method to load images
     * @param filename
     * @return
     */
    private ImageIcon loadImageIcon(String filename) {
        String path = ICONS_FOLDER + filename;
        ImageIcon icon = new ImageIcon(path);
        if (icon.getImageLoadStatus() == MediaTracker.COMPLETE) {
            return icon;
        } else {
            // Handle loading error or provide a default icon
            return null;
        }
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Place rendering logic here to draw the game elements using ImageIcon

        // Draw the maze with different tile types
        if (maze != null) {
            for (int row = 0; row < maze.getHeight(); row++) {
                for (int col = 0; col < maze.getWidth(); col++) {
                    Tile tile = maze.getTileAt(row, col);
                    int x = col * tileSize;
                    int y = row * tileSize;
// tile.type
                    if (tile instanceof WallTile) {
                        WallTile wallTile = (WallTile) tile;
                        drawWallTile(g, x, y, wallTile.getWallType());
                    } else if (tile instanceof FreeTile) {
                        drawFreeTile(g, x, y);
                    } else if (tile instanceof TreasureTile) {
                        drawTreasureTile(g, x, y);
                    } else if (tile instanceof ExitLockTile) {
                        drawExitLockTile(g, x, y);
                    } else if (tile instanceof ExitTile) {
                        drawExitTile(g, x, y);
                    } else if (tile instanceof KeyTile) {
                        drawKeyTile(g, x, y);
                    }
                }
            }
        }

        // Draw the player and actors
        if (player != null) {
            drawPlayer(g, player.getX() * tileSize, player.getY() * tileSize);
        }
        for (Actor actor : actors) {
            drawActor(g, actor.getX() * tileSize, actor.getY() * tileSize);
        }
    }

    private void drawWallTile(Graphics g, int x, int y, WallTile.WallType wallType) {
        // Draw wall tile based on its type
        if (wallIcon != null) {
            // Adjust the drawing logic based on the wallType
            switch (wallType) {
                case NORMAL:
                    // Draw normal wall
                    wallIcon.paintIcon(this, g, x, y);
                    break;
                case EXIT_LOCK:
                    // Draw exit lock
                    // ...
                    break;
                case LOCKED_DOOR:
                    // Draw locked door
                    // ...
                    break;
            }
        }
    }

    private void drawPlayer(Graphics g, int x, int y) {
        // Draw player using playerIcon
        if (chapIcon != null) {
            chapIcon.paintIcon(this, g, x, y);
        }
    }

    private void drawActor(Graphics g, int x, int y) {
        // Draw actor using actorIcon
        if (actorIcon != null) {
            actorIcon.paintIcon(this, g, x, y);
        }
    }

    // Add methods to update and render the game view
    public void updateGameView() {
        // Update the game state (e.g., position of game elements)
        // player.move(); // Move the player based on user input
        // updateActors(); // Update actor positions
    }

    public void renderGameView() {
        // Trigger a repaint to render the updated game state
        repaint();
    }

    // Add any other game-specific methods and logic here
}
