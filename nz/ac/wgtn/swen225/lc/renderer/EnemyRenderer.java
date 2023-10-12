package nz.ac.wgtn.swen225.lc.renderer;
import nz.ac.wgtn.swen225.lc.domain.Entity.Enemy;
import nz.ac.wgtn.swen225.lc.domain.Entity.Player;

import javax.swing.*;
import java.awt.*;
import java.util.List;
public class EnemyRenderer implements Renderable {
    private final List<Enemy> enemies;
    private final int tileSize;
    private final String iconFolder;
    private final ImageIcon enemyIcon;

    private SoundManager sound;

    public EnemyRenderer(List<Enemy> enemies, int tileSize, String iconFolder) {
        this.enemies = enemies;
        this.tileSize = tileSize;
        this.iconFolder = iconFolder;
        enemyIcon = loadImageIcon("actor1.png");
        this.sound = new SoundManager();
    }

    @Override
    public void render(Graphics g) {
        drawActors(g);
    }

    private ImageIcon loadImageIcon(String filename) {
        String path = iconFolder + filename;
        return new ImageIcon(path);
    }

    private void drawActors(Graphics g) {
        for (Enemy enemy : enemies) {
            int enemyX = enemy.getLocation().x() * tileSize;
            int enemyY = enemy.getLocation().y() * tileSize;

            // Draw the enemy icon scaled to the tile size
            g.drawImage(enemyIcon.getImage(), enemyX + 1, enemyY + 1, tileSize - 2, tileSize - 2, null);
        }
    }

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
