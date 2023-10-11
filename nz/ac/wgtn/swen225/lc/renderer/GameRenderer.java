package nz.ac.wgtn.swen225.lc.renderer;

import nz.ac.wgtn.swen225.lc.domain.Entity.*;
import nz.ac.wgtn.swen225.lc.domain.Tile.ExitTile;
import nz.ac.wgtn.swen225.lc.domain.Tile.FreeTile;
import nz.ac.wgtn.swen225.lc.domain.Tile.LockedDoor;
import nz.ac.wgtn.swen225.lc.domain.Tile.Tile;
import nz.ac.wgtn.swen225.lc.domain.Domain;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * The GameRenderer class is responsible for handling the rendering logic for the game.
 * It renders the game board, player, and enemy actors on the game canvas.
 * @Author Arnav Dogra (@dograarna)
 */
public class GameRenderer extends JPanel {

    // Fields
    private static final String ICONS_FOLDER = "nz/ac/wgtn/swen225/lc/renderer/gameicons/";
    private final ImageIcon[][] tileIcons;
    private Tile[][] maze;
    private final Player player;
    private final Domain domainObj;
    private final int tileSize;
    private final ImageIcon playerUpIcon;
    private final ImageIcon playerDownIcon;
    private final ImageIcon playerLeftIcon;
    private final ImageIcon playerRightIcon;
    private final List<Enemy> enemies;
    private final ImageIcon enemyIcon;
    private final SoundManager sound ;
    private int playerX;
    private  int playerY;


    /**
     * Constructor to initialize game-related variables and set up the game state and resources.
     *
     * @param maze    A 2D array of Tile objects representing the game board.
     * @param player  The player character.
     * @param dom     The Domain object containing the game state.
     */
    public GameRenderer(Tile[][] maze, Player player, Domain dom) {
        this.maze = maze;
        this.player = player;
        this.domainObj = dom;
        tileIcons = new ImageIcon[maze.length][maze[0].length];
        tileSize = domainObj.getBoard().getSize();
        loadTileIcons();
        initializeRenderer();
        playerUpIcon = loadImageIcon("playerup1.png");
        playerDownIcon = loadImageIcon("playerdown1.png");
        playerLeftIcon = loadImageIcon("playerleft2.png");
        playerRightIcon = loadImageIcon("playerright2.png");
        enemies = dom.getEnemies(); // Initialize the list of enemy actors
        enemyIcon = loadImageIcon("actor1.png");
        sound = new SoundManager();

    }

    /**
     * Initializes the renderer by setting the preferred size of the rendering panel.
     */
    private void initializeRenderer() {
        // Set the preferred size of the rendering panel
        int panelWidth = 800;
        int panelHeight = 600;
        setPreferredSize(new Dimension(panelWidth, panelHeight));

    }


    /**
     * Loads tile icons based on the game board configuration.
     */
    private void loadTileIcons() {
        for (int row = 0; row < maze.length; row++) {
            for (int col = 0; col < maze[0].length; col++) {
                Tile tile = maze[row][col];
                if (tile != null) {
                    String iconName;
                    if (tile instanceof FreeTile freeTile) {
                        Entity entity = freeTile.getEntity();
                        if (entity instanceof Key key) {
                            // Get the color of the key and construct the icon name
                            String colorName = key.getColour().toString().toLowerCase();
                            iconName = "key" + colorName + ".png"; // e.g., key_red.png
                        } else if (entity instanceof Treasure ) {
                            iconName = "treasure.png"; // Load the treasure entity icon
                        } else {
                            iconName = getIconNameForTile(tile);
                        }
                    } else {
                        iconName = getIconNameForTile(tile);
                    }
                    tileIcons[row][col] = loadImageIcon(iconName);
                  //  System.out.println("Loaded tile icon for row " + row + ", col " + col + ": " + iconName);
                }
            }
        }
    }

    /**
     * Determines the icon name for a given tile.
     *
     * @param tile The tile for which to determine the icon name.
     * @return The icon name for the tile.
     */
    private String getIconNameForTile(Tile tile) {
        if (tile == null) {
           // System.out.println(" Tile Null");
            return "unknown.png";
        }
        switch (tile.getClass().getSimpleName()) {
            case "Wall":
                return "walltile.png";
            case "FreeTile":
                return "freetile.png";
            case "LockedDoor":
                LockedDoor lockD = (LockedDoor) tile;
                String doorColor = lockD.getColour().toString().toLowerCase();
                return "door" + doorColor + ".png";
            case "ExitLock":
                return "exitlock.png";
            case "ExitTile":
                return "exit.png";
            case "InformationTile":
                return "infofield.png";
            default:
                return null; // Return null for keys and treasures
        }
    }

    /**
     * Loads an ImageIcon from a file.
     *
     * @param filename The name of the image file.
     * @return The loaded ImageIcon.
     */
    private ImageIcon loadImageIcon(String filename) {
        String path = ICONS_FOLDER + filename;
       // System.out.println("Loading image from path: " + path); // Added  for debugging
        return new ImageIcon(path);

    }

    /**
     * Redraws the game board based on the updated game state.
     */
    public  void reDrawBoard(){
        maze = domainObj.getBoard().getBoard(); // Fetch the updated game board
        loadTileIcons(); // Reload tile icons based on the updated board
        repaint(); // Request the JPanel to repaint itself
    }

    /**
     * Overrides the paintComponent method to render full game board.
     *
     * @param g The Graphics context for rendering.
     */
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawMaze(g);
        drawPlayer(g);
        drawActors(g);


    }

    /**
     * Draws the game board.
     *
     * @param g The Graphics context for rendering.
     */

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

    /**
     * Draws the player character on the game board.
     *
     * @param g The Graphics context for rendering.
     */

    private void drawPlayer(Graphics g) {
        if (player != null) {
            int playerX = player.getX() * domainObj.getBoard().getSize();
            int playerY = player.getY() * domainObj.getBoard().getSize();

            g.setColor(Color.BLACK);
            g.drawRect(playerX, playerY, tileSize, tileSize);

            // Determine the player's direction and select the appropriate icon
            ImageIcon playerIcon = getPlayerIcon();


            // Draw the player icon scaled to the tile size
            g.drawImage(playerIcon.getImage(), playerX + 1, playerY + 1, tileSize - 2, tileSize - 2, this);
        }
    }

    /**
     * Draws enemy actors on the game board.
     *
     * @param g The Graphics context for rendering.
     */
    private void drawActors(Graphics g) {
        for (Enemy enemy : enemies) {
            int enemyX = enemy.getLocation().x() * tileSize;
            int enemyY = enemy.getLocation().y() * tileSize;

            // Draw the enemy icon scaled to the tile size
            g.drawImage(enemyIcon.getImage(), enemyX + 1, enemyY + 1, tileSize - 2, tileSize - 2, this);
        }
    }


    /**
     * Determines the appropriate player icon based on the player's direction.
     *
     * @return The ImageIcon representing the player character.
     */

    private ImageIcon getPlayerIcon() {
        return switch (player.getDirection()) {
            case NORTH -> playerDownIcon;
            case WEST -> playerLeftIcon;
            case EAST -> playerRightIcon;
            default -> playerUpIcon;
        };
    }

    /**
     * Renders the game view on the canvas.
     *
     * @param g The Graphics context for rendering.
     */
    public void renderGameView(Graphics g) {
        int playerRow = player.getY();
        int playerCol = player.getX();
        int tileSize = domainObj.getBoard().getSize();
        int startRow = Math.max(0, Math.min(playerRow - 2, maze.length - 5));
        int startCol = Math.max(0, Math.min(playerCol - 2, maze[0].length - 5));
        int endRow = startRow + 5;
        int endCol = startCol + 5;
        endRow = Math.min(endRow, maze.length);
        endCol = Math.min(endCol, maze[0].length);

        renderTiles(g, startRow, endRow, startCol, endCol, tileSize);
        renderPlayer(g, startRow, startCol, playerRow, playerCol, tileSize);
        renderEnemies(g, startRow, startCol, tileSize);
        playWiningSound(playerRow, playerCol);
    }

    /**
     * Renders the tiles on the focus region
     *
     * @param g         The Graphics context for rendering.
     * @param startRow  The starting row index for rendering.
     * @param endRow    The ending row index for rendering.
     * @param startCol  The starting column index for rendering.
     * @param endCol    The ending column index for rendering.
     * @param tileSize  The size of a single tile.
     */

    private void renderTiles(Graphics g, int startRow, int endRow, int startCol, int endCol, int tileSize) {
        for (int row = startRow; row < endRow; row++) {
            for (int col = startCol; col < endCol; col++) {
                if (tileIcons[row][col] != null) {
                    int x = (col - startCol) * tileSize;
                    int y = (row - startRow) * tileSize;
                    g.drawImage(tileIcons[row][col].getImage(), x, y, tileSize, tileSize, this);
                    g.setColor(Color.BLACK);
                    g.drawRect(x, y, tileSize - 1, tileSize - 1);
                }
            }
        }
    }


    /**
     * Renders the player character on the focus region
     *
     * @param g         The Graphics context for rendering.
     * @param startRow  The starting row index for rendering.
     * @param startCol  The starting column index for rendering.
     * @param playerRow The current row of the player character.
     * @param playerCol The current column of the player character.
     * @param tileSize  The size of a single tile.
     */
    private void renderPlayer(Graphics g, int startRow, int startCol, int playerRow, int playerCol, int tileSize) {
        ImageIcon playerIcon = getPlayerIcon();
        playerX = (playerCol - startCol) * tileSize; // Store in class field
        playerY = (playerRow - startRow) * tileSize; // Store in class field
        g.drawImage(playerIcon.getImage(), playerX, playerY, tileSize, tileSize, this);
    }

    /**
     * Renders enemy characters on the focus region
     *
     * @param g         The Graphics context for rendering.
     * @param startRow  The starting row index for rendering.
     * @param startCol  The starting column index for rendering.
     * @param tileSize  The size of a single tile.
     */

    private void renderEnemies(Graphics g, int startRow, int startCol, int tileSize) {
        for (Enemy enemy : enemies) {
            int enemyX = (enemy.getLocation().x() - startCol) * tileSize;
            int enemyY = (enemy.getLocation().y() - startRow) * tileSize;

            if (enemyX >= 0 && enemyX < 5 * tileSize && enemyY >= 0 && enemyY < 5 * tileSize) {
                g.drawImage(enemyIcon.getImage(), enemyX, enemyY, tileSize, tileSize, this);
            }
            if (enemyX == playerX && enemyY == playerY) {
                System.out.println("reaches enemy and player location check");
                sound.playDeathSound();
            }
        }
    }
    /**
     * Plays wining sound when the player reaches the exit tile.
     *
     * @param playerRow The current row of the player character.
     * @param playerCol The current column of the player character.
     */
    private void playWiningSound(int playerRow, int playerCol) {
        Tile playerTile = maze[playerRow][playerCol];
        if (playerTile instanceof ExitTile) {
            sound.playLevelCompleteSound();
        }
    }



}
