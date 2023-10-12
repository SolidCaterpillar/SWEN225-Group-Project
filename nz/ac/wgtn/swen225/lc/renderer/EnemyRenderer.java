package nz.ac.wgtn.swen225.lc.renderer;
import nz.ac.wgtn.swen225.lc.domain.Entity.Enemy;

import javax.swing.*;
import java.awt.*;
import java.util.List;


/**
 * The EnemyRenderer class is responsible for rendering enemy entities on the game board.
 * It implements the `Renderable` interface, which defines the rendering behavior for game elements.
 * This class manages the rendering of enemy entities using provided data such as the list of enemies, tile size, and icon folder.
 *@author Arnav Dogra (@dograarna)
 *
 * @see Renderable
 * @see Enemy
 * @see SoundManager
 */

public class EnemyRenderer implements Renderable {
    private final List<Enemy> enemies;
    private final int tileSize;
    private final String iconFolder;
    private final ImageIcon enemyIcon;


    /**
     * Constructs an EnemyRenderer object with the given list of enemies, tile size, and icon folder.
     *
     * @param enemies    The list of enemy entities to be rendered.
     * @param tileSize   The size of each tile in pixels.
     * @param iconFolder The folder path where enemy icons are located.
     */

    public EnemyRenderer(List<Enemy> enemies, int tileSize, String iconFolder) {
        this.enemies = enemies;
        this.tileSize = tileSize;
        this.iconFolder = iconFolder;
        enemyIcon = loadImageIcon("actor1.png");
    }


    /**
     * Renders enemy entities on the specified graphics context.
     *
     * @param g The graphics context on which the enemy entities should be rendered.
     */

    @Override
    public void render(Graphics g) {
        drawActors(g);
    }


    /**
     * Loads an ImageIcon from a file with the given filename.
     *
     * @param filename The name of the image file.
     * @return An ImageIcon loaded from the specified file.
     */

    private ImageIcon loadImageIcon(String filename) {
        String path = iconFolder + filename;
        return new ImageIcon(path);
    }


    /**
     * Draws enemy entities on the graphics context.
     *
     * @param g The graphics context on which the enemy entities should be drawn.
     */
    private void drawActors(Graphics g) {
        for (Enemy enemy : enemies) {
            int enemyX = enemy.getLocation().x() * tileSize;
            int enemyY = enemy.getLocation().y() * tileSize;

            // Draw the enemy icon scaled to the tile size
            g.drawImage(enemyIcon.getImage(), enemyX + 1, enemyY + 1, tileSize - 2, tileSize - 2, null);
        }
    }


    /**
     * Renders focus region of enemy entities within the game board.
     *
     * @param g         The graphics context on which to render the enemy entities.
     * @param startRow  The starting row of the region.
     * @param startCol  The starting column of the region.
     * @param tileSize  The size of each tile in pixels.
     */

    public void renderEnemies(Graphics g, int startRow, int startCol, int tileSize) {
        for (Enemy enemy : enemies) {
            int enemyX = (enemy.getLocation().x() - startCol) * tileSize;
            int enemyY = (enemy.getLocation().y() - startRow) * tileSize;

            if (enemyX >= 0 && enemyX < 5 * tileSize && enemyY >= 0 && enemyY < 5 * tileSize) {
                g.drawImage(enemyIcon.getImage(), enemyX, enemyY, tileSize, tileSize, null);
            }



        }

    }
}
