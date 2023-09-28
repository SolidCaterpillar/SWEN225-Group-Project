package nz.ac.wgtn.swen225.lc.renderer;

import nz.ac.wgtn.swen225.lc.domain.*;
import nz.ac.wgtn.swen225.lc.domain.Tile.*;
import nz.ac.wgtn.swen225.lc.domain.Entity.*;
import nz.ac.wgtn.swen225.lc.persistency.*;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * responsible for handling the rendering logic for the game.
 */
public class GameRenderer extends JPanel {

    // Define  game-related variables here

    private static final String ICONS_FOLDER = "icons/"; // Path to the icons folder
    private ImageIcon[][] tileIcons; // 2D array of ImageIcons for tiles
    private Tile[][] maze; // 2D array representing the maze
    private ImageIcon playerIcon;
    private Clip playerMoveSoundClip;
    private Clip treasureCollectSoundClip;
    private Clip doorOpenSoundClip;
    private Clip playerDeathSoundClip;
    private Player player;

    private int tileSize = 87;
    // Define game-related variables
    //private Board maze;

    // private List<Enemy> actors;
    //private KeyTile KEYTILE;
    // private Key KEY;

    /**
     * Constructor to initialize game-related variables
     * set up  game state and resources here
     */
    public GameRenderer(Tile[][] maze, Player player) {
        this.maze = maze; // Initialize the maze
        this.player = player; // Initialize the player

        tileIcons = new ImageIcon[maze.length][maze[0].length];
        loadTileIcons();
        initializeRenderer();

        playerIcon = loadImageIcon("chap.png");

        //playerMoveSoundClip = loadSoundEffect("player_move.wav");
        //treasureCollectSoundClip = loadSoundEffect("treasure_collect.wav");
        //doorOpenSoundClip = loadSoundEffect("door_open.wav");
        //playerDeathSoundClip = loadSoundEffect("player_death.wav");

    }

    private Clip loadSoundEffect(String soundFilePath) {
        try {
            File soundFile = new File(soundFilePath);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            return clip;
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
            return null;
        }
    }


    public void playPlayerMoveSound() {
        if (playerMoveSoundClip != null) {
            playerMoveSoundClip.setFramePosition(0); // Rewind to the beginning
            playerMoveSoundClip.start();
        }
    }

    public void playTreasureCollectSound() {
        if (treasureCollectSoundClip != null) {
            treasureCollectSoundClip.setFramePosition(0); // Rewind to the beginning
            treasureCollectSoundClip.start();
        }
    }


    private void initializeRenderer() {
        // Set the preferred size of the rendering panel
        int panelWidth = 800; // Adjust as needed
        int panelHeight = 600; // Adjust as needed
        setPreferredSize(new Dimension(panelWidth, panelHeight));

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
                    // Calculate the destination rectangle for the image
                    int x = col * tileSize;
                    int y = row * tileSize;
                    int width = tileSize;
                    int height = tileSize;

                    // Draw the border around the tile
                    g.setColor(Color.BLACK); // You can change the border color
                    g.drawRect(x, y, width, height);

                    // Draw the image scaled to the tile size within the border
                    g.drawImage(tileIcons[row][col].getImage(), x + 1, y + 1, width - 2, height - 2, this);
                }
            }
        }

        // Draw the player
        if (player != null) {
            int playerX = player.getX() * tileSize;
            int playerY = player.getY() * tileSize;
            // Draw the border around the player tile
            g.setColor(Color.BLACK); // You can change the border color
            g.drawRect(playerX, playerY, tileSize, tileSize);
            // Draw the player icon scaled to the tile size within the border
            g.drawImage(playerIcon.getImage(), playerX + 1, playerY + 1, tileSize - 2, tileSize - 2, this);
        }


      /*  // Debugging output
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

                // Render the tile
                if (tileIcons[row][col] != null) {
                    tileIcons[row][col].paintIcon(this, g, col * tileSize, row * tileSize);
                }
            }
        }*/


    }



    private void drawPlayer(Graphics g, int x, int y) {
        if (playerIcon != null) {
            playerIcon.paintIcon(this, g, x, y);
        }
    }

    // Add methods to update and render the game view
    public void updateGameView() {
        // Update the game state (e.g., position of game elements)
        // Example:
        // player.move(); // Move the player based on user input
        // updateActors(); // Update actor positions
    }

    public void renderGameView(Graphics g) {
        // Determine player's position in the 5x5 grid
        int playerRow = player.getY();
        int playerCol = player.getX();
        int startRow = playerRow - 3;
        int startCol = playerCol - 3;

        // Ensure starting row and column are within bounds
        startRow = Math.max(0, startRow);
        startCol = Math.max(0, startCol);

        // Calculate the rendering coordinates for each tile
        for (int row = startRow; row < startRow + 7 && row < maze.length; row++) {
            for (int col = startCol; col < startCol + 7 && col < maze[0].length; col++) {
                if (tileIcons[row][col] != null) {
                    // Calculate the destination rectangle for the image
                    int x = (col - startCol) * tileSize;
                    int y = (row - startRow) * tileSize;
                    int width = tileSize;
                    int height = tileSize;

                    // Draw the tile image
                    g.drawImage(tileIcons[row][col].getImage(), x, y, width, height, this);

                    // Draw a border around the tile
                    g.setColor(Color.BLACK); // Set border color
                    g.drawRect(x, y, width - 1, height - 1); // Draw border
                }
            }
        }

        // Render the player
        int playerX = (playerCol - startCol) * tileSize;
        int playerY = (playerRow - startRow) * tileSize;
        // Draw the player icon scaled to the tile size
        g.drawImage(playerIcon.getImage(), playerX, playerY, tileSize, tileSize, this);

    /*// Render the actors
    for (Actor actor : actors) {
        int actorX = (actor.getX() - startCol) * tileSize;
        int actorY = (actor.getY() - startRow) * tileSize;
        // Draw the actor icon at (actorX, actorY) on the canvas
        // Assuming you have an actorIcon
        actorIcon.paintIcon(this, g, actorX, actorY);
    }*/
    }


}
