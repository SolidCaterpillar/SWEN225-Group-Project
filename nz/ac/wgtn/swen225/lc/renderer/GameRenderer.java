package nz.ac.wgtn.swen225.lc.renderer;

import nz.ac.wgtn.swen225.lc.domain.Entity.Player;
import nz.ac.wgtn.swen225.lc.domain.Tile.Tile;
import nz.ac.wgtn.swen225.lc.domain.Domain;

import javax.swing.*;
import java.awt.*;

/**
 * responsible for handling the rendering logic for the game.
 */
public class GameRenderer extends JPanel {

    private static final String ICONS_FOLDER = "nz/ac/wgtn/swen225/lc/renderer/gameicons/";
    private ImageIcon[][] tileIcons;
    private Tile[][] maze;
    private ImageIcon playerIcon;
    private Player player;
    private Domain domOb;
    private int tileSize;


    /**
     * Constructor to initialize game-related variables
     * set up  game state and resources here
     */
    public GameRenderer(Tile[][] maze, Player player, Domain dom) {
        this.maze = maze;
        this.player = player;
        this.domOb = dom;
        tileIcons = new ImageIcon[maze.length][maze[0].length];
        tileSize = domOb.getBoard().getSize();
        loadTileIcons();
        initializeRenderer();
        playerIcon = loadImageIcon("playerup1.png");
    }


    private void initializeRenderer() {
        // Set the preferred size of the rendering panel
        int panelWidth = 800;
        int panelHeight = 600;
        setPreferredSize(new Dimension(panelWidth, panelHeight));

    }

    private void loadTileIcons() {
        for (int row = 0; row < maze.length; row++) {
            for (int col = 0; col < maze[0].length; col++) {
                if (maze[row][col] != null) {
                    String iconName = getIconNameForTile(maze[row][col]);
                    tileIcons[row][col] = loadImageIcon(iconName);
                   System.out.println("Loaded tile icon for row " + row + ", col " + col + ": " + iconName); // Add this line for debugging                }
           }}
        }
    }

    private String getIconNameForTile(Tile tile) {
        if (tile == null) {
            System.out.println(" Tile Null");
            return "unknown.png";
        }
        switch (tile.getClass().getSimpleName()) {
            case "Wall":
                return "walltile.png";
            case "FreeTile":
                return "freetile.png";
            case "TreasureTile":
                return "treasure.png";
            case "ExitLock":
                return "exitlock.png";
            case "ExitTile":
                return "exit.png";
            case "KeyTile":
                return "key.png";
            default:
                return "keyyellow.png";
        }
    }

    private ImageIcon loadImageIcon(String filename) {
        String path = ICONS_FOLDER + filename;
        System.out.println("Loading image from path: " + path); // Add this line for debugging
        ImageIcon icon = new ImageIcon(path);        return icon;
    }


    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawMaze(g);
        drawPlayer(g);


    }
    private void drawMaze(Graphics g) {


        for (int row = 0; row < maze.length; row++) {
            for (int col = 0; col < maze[0].length; col++) {
                if (tileIcons[row][col] != null) {
                    int x = col * tileSize;
                    int y = row * tileSize;
                    int width = tileSize;
                    int height = tileSize;

                    g.setColor(Color.BLACK);
                    g.drawRect(x, y, width, height);

                    g.drawImage(tileIcons[row][col].getImage(), x + 1, y + 1, width - 2, height - 2, this);
                }
            }
        }



    }
    private void drawPlayer(Graphics g) {
        if (player != null) {
            int playerX = player.getX() * domOb.getBoard().getSize();
            int playerY = player.getY() * domOb.getBoard().getSize();

            g.setColor(Color.BLACK);
            g.drawRect(playerX, playerY, tileSize, tileSize);

            g.drawImage(playerIcon.getImage(), playerX + 1, playerY + 1, tileSize - 2, tileSize - 2, this);
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
        int startRow = playerRow - 2;
        int startCol = playerCol - 2;

        int tileSize = domOb.getBoard().getSize();
        // Ensure starting row and column are within bounds
        startRow = Math.max(0, startRow);
        startCol = Math.max(0, startCol);

        // Calculate the rendering coordinates for each tile
        for (int row = startRow; row < startRow + 5 && row < maze.length; row++) {
            for (int col = startCol; col < startCol + 5 && col < maze[0].length; col++) {
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

    }


}
