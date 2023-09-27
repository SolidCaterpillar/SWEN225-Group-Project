package nz.ac.wgtn.swen225.lc.renderer;

import Domain.Board;
import Domain.Tile.*;

import javax.swing.*;
import java.awt.*;

import static Domain.Board.tileSize;

/**
 * responsible for handling the rendering logic for the game.
 */
public class GameRenderer extends JPanel {

    // Define  game-related variables here

    private static final String ICONS_FOLDER = "gameicons/"; // Path to the icons folder
    private ImageIcon[][] tileIcons; // 2D array of ImageIcons for tiles
    private Tile[][] maze; // 2D array representing the maze
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
    //private Board maze;
    // private Chap player;
    // private List<Enemy> actors;
    //private KeyTile KEYTILE;
    // private Key KEY;

    /**
     * Constructor to initialize game-related variables
     * set up  game state and resources here
     */
    public GameRenderer(Tile[][] maze) {
        this.maze = maze; // Initialize the maze

        tileIcons = new ImageIcon[maze.length][maze[0].length];
        loadTileIcons();

                wallTileIcon = loadImageIcon("walltile.png");
        freeTileIcon = loadImageIcon("freetile.png");
        chapIcon = loadImageIcon("chap.png");
        treasureIcon = loadImageIcon("treasure.png");
        exitLockIcon = loadImageIcon("exitlock.png");
        exitIcon = loadImageIcon("exit.png");
        infoFieldIcon = loadImageIcon("infofield.png");
        keyIcon = loadImageIcon("key.png");
        lockedDoorIcon = loadImageIcon("lockeddoor.png");
        initializeRenderer();
    }

    private void initializeRenderer() {
        // Set the preferred size of the rendering panel
        // Adjust the width and height based on your game's requirements
        int panelWidth = 800; // Adjust as needed
        int panelHeight = 600; // Adjust as needed
        setPreferredSize(new Dimension(panelWidth, panelHeight));

        // Add any additional initialization logic here
    }
    private void loadTileIcons() {
        for (int row = 0; row < maze.length; row++) {
            for (int col = 0; col < maze[0].length; col++) {
                if (maze[row][col] != null) {
                    String iconName = getIconNameForTile(maze[row][col]);
                    tileIcons[row][col] = loadImageIcon(iconName);
                }
            }
        }
    }

    private String getIconNameForTile(Tile tile) {
        // Map each tile type to its corresponding icon filename
        if (tile instanceof Wall) {
            return "walltile.png";
        } else if (tile instanceof FreeTile) {
            return "freetile.png";
        } else if (tile instanceof TreasureTile) {
            return "treasure.png";
        } else if (tile instanceof ExitLock) {
            return "exitlock.png";
        } else if (tile instanceof ExitTile) {
            return "exit.png";
        } else if (tile instanceof KeyTile) {
            return "key.png";
        } else {
            // Default icon for unknown tile types
            return "unknown.png";
        }
    }

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
        for (int row = 0; row < maze.length; row++) {
            for (int col = 0; col < maze[0].length; col++) {
                if (tileIcons[row][col] != null) {
                    tileIcons[row][col].paintIcon(this, g, col * tileSize, row * tileSize);
                }
            }
        }
        // Debugging output
        for (int row = 0; row < maze.length; row++) {
            for (int col = 0; col < maze[0].length; col++) {
                Tile tile = maze[row][col];
                System.out.println("Tile at (" + row + ", " + col + "): " + (tile != null ? tile.getClass().getSimpleName() : "null"));
                ImageIcon icon = tile != null ? tile.draw() : null;
                if (icon != null) {
                    System.out.println("Icon for this tile: " + icon.toString());
                } else {
                    System.out.println("No icon found for this tile.");
                }

                // Render the tile (similar to your existing code)
                if (tileIcons[row][col] != null) {
                    tileIcons[row][col].paintIcon(this, g, col * tileSize, row * tileSize);
                }
            }
        }


    }




    // Add methods to update and render the game view
    public void updateGameView() {
        // Update the game state (e.g., position of game elements)
        // Example:
        // player.move(); // Move the player based on user input
        // updateActors(); // Update actor positions
    }

    public void renderGameView() {
        // Trigger a repaint to render the updated game state
        repaint();
    }
}
