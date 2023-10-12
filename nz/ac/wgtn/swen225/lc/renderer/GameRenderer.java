package nz.ac.wgtn.swen225.lc.renderer;

import nz.ac.wgtn.swen225.lc.domain.Domain;
import nz.ac.wgtn.swen225.lc.domain.Entity.Player;
import nz.ac.wgtn.swen225.lc.domain.Entity.Enemy;
import nz.ac.wgtn.swen225.lc.domain.Tile.ExitTile;
import nz.ac.wgtn.swen225.lc.domain.Tile.Tile;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The GameRenderer class is responsible for handling the rendering logic for the game.
 * It renders the game board, player, and enemy actors on the game canvas.
 * @Author Arnav Dogra (@dograarna)
 */
public class GameRenderer extends JPanel implements Renderable {

    private final String ICONS_FOLDER = "nz/ac/wgtn/swen225/lc/renderer/gameicons/";
    private final Domain domainObj;
    private final int tileSize;
    private final SoundManager sound;
    private final Composite renderables = new Composite();
    private final Player player;
    private Tile[][] maze;
    private List<Enemy> enemies;
    private BoardRenderer boardRenderer;
    private PlayerRenderer playerRenderer;
    private EnemyRenderer enemyRenderer;

    public GameRenderer(Tile[][] maze, Player player, Domain dom) {
        this.domainObj = dom;
        this.tileSize = domainObj.getBoard().getSize();
        this.maze = maze;
        this.player = player;
        enemies = domainObj.getEnemies();
        this.sound = new SoundManager();
        boardRenderer= new BoardRenderer(maze, tileSize, ICONS_FOLDER);
        playerRenderer= new PlayerRenderer(player, tileSize, ICONS_FOLDER);
        enemyRenderer = new EnemyRenderer(enemies, tileSize, ICONS_FOLDER);
        initializeRenderer();
        loadRenderables();

    }

    private void initializeRenderer() {
        // Set the preferred size of the rendering panel
        int panelWidth = 800;
        int panelHeight = 600;
        setPreferredSize(new Dimension(panelWidth, panelHeight));
    }

    private void loadRenderables() {
        renderables.add(boardRenderer);
        renderables.add(playerRenderer);
        renderables.add(playerRenderer);
    }

    public void reDrawBoard() {
        maze = domainObj.getBoard().getBoard(); // Fetch the updated game board
        boardRenderer.loadTileIcons(maze); // Reload tile icons based on the updated board
        repaint(); // Request the JPanel to repaint itself
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        renderables.render(g);
    }



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
        int tileSize = domainObj.getBoard().getSize();
        int startRow = Math.max(0, Math.min(playerRow - 2, maze.length - 5));
        int startCol = Math.max(0, Math.min(playerCol - 2, maze[0].length - 5));
        int endRow = startRow + 5;
        int endCol = startCol + 5;
        endRow = Math.min(endRow, maze.length);
        endCol = Math.min(endCol, maze[0].length);

        boardRenderer.renderTiles(g, startRow, endRow, startCol, endCol, tileSize);
        playerRenderer.renderPlayer(g, startRow, startCol, playerRow, playerCol, tileSize);
        enemyRenderer.renderEnemies(g, startRow, startCol, tileSize);
        playWiningSound(playerRow, playerCol);

    }


    private void playWiningSound(int playerRow, int playerCol) {
        Tile playerTile = maze[playerRow][playerCol];
        if (playerTile instanceof ExitTile) {
            sound.playLevelCompleteSound();
        }
    }


}

