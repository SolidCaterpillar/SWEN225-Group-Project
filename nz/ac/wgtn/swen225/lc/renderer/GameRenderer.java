package nz.ac.wgtn.swen225.lc.renderer;

import nz.ac.wgtn.swen225.lc.domain.Entity.Enemy;
import nz.ac.wgtn.swen225.lc.domain.Entity.Player;
import nz.ac.wgtn.swen225.lc.domain.Tile.Tile;
import nz.ac.wgtn.swen225.lc.domain.Domain;

import javax.swing.*;
import java.awt.*;
import java.util.List;

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
    private ImageIcon playerUpIcon;
    private ImageIcon playerDownIcon;
    private ImageIcon playerLeftIcon;
    private ImageIcon playerRightIcon;
    private List<Enemy> enemies; // List of enemy actors in the game
    private ImageIcon enemyIcon; // Icon for enemy actors
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
        playerUpIcon = loadImageIcon("playerup1.png");
        playerDownIcon = loadImageIcon("playerdown1.png");
        playerLeftIcon = loadImageIcon("playerleft2.png");
        playerRightIcon = loadImageIcon("playerright2.png");
        enemies = dom.getEnemies(); // Initialize the list of enemy actors
        enemyIcon = loadImageIcon("actor1.png");

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
                   System.out.println("Loaded tile icon for row " + row + ", col " + col + ": " + iconName);
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
        drawActors(g);


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

            // Determine the player's direction and select the appropriate icon
            ImageIcon playerIcon = getPlayerIcon();


            // Draw the player icon scaled to the tile size
            g.drawImage(playerIcon.getImage(), playerX + 1, playerY + 1, tileSize - 2, tileSize - 2, this);
        }
    }
    private void drawActors(Graphics g) {
        for (Enemy enemy : enemies) {
            int enemyX = enemy.getLocation().x() * tileSize;
            int enemyY = enemy.getLocation().y() * tileSize;

            // Draw the enemy icon scaled to the tile size
            g.drawImage(enemyIcon.getImage(), enemyX + 1, enemyY + 1, tileSize - 2, tileSize - 2, this);
        }
    }


    private ImageIcon getPlayerIcon() {
        switch (player.getDirection()) {
            case SOUTH:
                return playerUpIcon;
            case NORTH:
                return playerDownIcon;
            case WEST:
                return playerLeftIcon;
            case EAST:
                return playerRightIcon;
            default:
                return playerUpIcon;
        }
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

        // Determine the player's direction and select the appropriate icon
        ImageIcon playerIcon = getPlayerIcon();

        // Render the player
        int playerX = (playerCol - startCol) * tileSize;
        int playerY = (playerRow - startRow) * tileSize;
        // Draw the player icon scaled to the tile size
        g.drawImage(playerIcon.getImage(), playerX, playerY, tileSize, tileSize, this);

        // Render enemy actors within the 5x5 grid
        for (Enemy enemy : enemies) {
            int enemyX = (enemy.getLocation().x() - startCol) * tileSize;
            int enemyY = (enemy.getLocation().y() - startRow) * tileSize;

            // Draw the enemy icon scaled to the tile size
            g.drawImage(enemyIcon.getImage(), enemyX, enemyY, tileSize, tileSize, this);
        }
    }


}
