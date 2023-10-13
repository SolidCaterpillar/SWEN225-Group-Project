package nz.ac.wgtn.swen225.lc.renderer;

import nz.ac.wgtn.swen225.lc.domain.Domain;
import nz.ac.wgtn.swen225.lc.domain.Entity.Player;
import nz.ac.wgtn.swen225.lc.domain.Entity.Enemy;
import nz.ac.wgtn.swen225.lc.domain.Tile.ExitTile;
import nz.ac.wgtn.swen225.lc.domain.Tile.Tile;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * The GameRenderer class is responsible for handling the rendering logic for the game.
 * It renders the game board, player, and enemy actors on the game canvas.
 *
 * @author Arnav Dogra (@dograarna) ID:300630190
 */
public class GameRenderer extends JPanel implements Renderable {

    private final String ICONS_FOLDER = "nz/ac/wgtn/swen225/lc/renderer/gameicons/";
    private final Domain domainObj;
    private final int tileSize;
    private final SoundManager sound;
    private final Composite renderables = new Composite();
    private final Player player;
    private Tile[][] maze;
    private final List<Enemy> enemies;
    private final BoardRenderer boardRenderer;
    private final PlayerRenderer playerRenderer;
    private final EnemyRenderer enemyRenderer;



    /**
     * Constructs a `GameRenderer` object with the given game board, player, and domain.
     *
     * @param maze   The 2D array representing the game board.
     * @param player The player entity.
     * @param dom    The domain object.
     */
    public GameRenderer(Tile[][] maze, Player player, Domain dom) {
        this.domainObj = dom;
        this.tileSize = domainObj.getBoard().getSize();
        this.maze = maze;
        this.player = player;
        this.enemies = domainObj.getEnemies();
        this.sound = new SoundManager();
        boardRenderer= new BoardRenderer(maze, tileSize, ICONS_FOLDER);
        playerRenderer= new PlayerRenderer(player, tileSize, ICONS_FOLDER);
        enemyRenderer = new EnemyRenderer(enemies, tileSize, ICONS_FOLDER);
        initializeRenderer();
        loadRenderables();

    }


    /**
     * Sets the preferred size of the rendering panel to the specified width and height.
     *
     */
    private void initializeRenderer() {
        // Set the preferred size of the rendering panel
        int panelWidth = 800;
        int panelHeight = 600;
        setPreferredSize(new Dimension(panelWidth, panelHeight));
    }



    /**
     * Loads renderable objects into the composite renderer.
     */
    private void loadRenderables() {
        renderables.add(boardRenderer);
        renderables.add(playerRenderer);
        renderables.add(playerRenderer);
    }

    /**
     * Redraws the game board based on the updated data and requests the JPanel to repaint itself.
     */
    public void reDrawBoard() {
        maze = domainObj.getBoard().getBoard();
        boardRenderer.loadTileIcons(maze);
        repaint();
    }


    /**
     * Paints the game components on the JPanel using the specified Graphics context.
     *
     * @param g The Graphics context for rendering.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        renderables.render(g);
    }


    /**
     * Renders the game
     *
     * @param g The graphics context on which the game character should be rendered.
     */
    @Override
    public void render(Graphics g) {
        renderGameView(g);
    }

    /**
     * Renders the game view on the canvas.
     *
     * @param g The Graphics context for rendering.
     */
    public void renderGameView(Graphics g) {
        int playerRow = player.getY();
        int playerCol = player.getX();
        int startRow = Math.max(0, Math.min(playerRow - 2, maze.length - 5));
        int startCol = Math.max(0, Math.min(playerCol - 2, maze[0].length - 5));
        int endRow = startRow + 5;
        int endCol = startCol + 5;
        endRow = Math.min(endRow, maze.length);
        endCol = Math.min(endCol, maze[0].length);

        boardRenderer.renderTiles(g, startRow, endRow, startCol, endCol, tileSize);
        playerRenderer.renderPlayer(g, startRow, startCol, playerRow, playerCol, tileSize);
        enemyRenderer.renderEnemies(g, startRow, startCol, tileSize);
    }

}

