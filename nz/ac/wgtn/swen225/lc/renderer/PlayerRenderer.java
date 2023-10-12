package nz.ac.wgtn.swen225.lc.renderer;

import nz.ac.wgtn.swen225.lc.domain.Entity.Player;

import javax.swing.*;
import java.awt.*;


class PlayerRenderer implements Renderable {
    private final Player player;
    private final int tileSize;
    private final String iconFolder;
    private final ImageIcon playerUpIcon;
    private final ImageIcon playerDownIcon;
    private final ImageIcon playerLeftIcon;
    private final ImageIcon playerRightIcon;

    public PlayerRenderer(Player player, int tileSize, String iconFolder) {
        this.player = player;
        this.tileSize = tileSize;
        this.iconFolder = iconFolder;
        playerUpIcon = loadImageIcon("playerup1.png");
        playerDownIcon = loadImageIcon("playerdown1.png");
        playerLeftIcon = loadImageIcon("playerleft2.png");
        playerRightIcon = loadImageIcon("playerright2.png");
    }

    @Override
    public void render(Graphics g) {
        drawPlayer(g);
    }



    private ImageIcon loadImageIcon(String filename) {
        String path = iconFolder + filename;
        return new ImageIcon(path);
    }

    private void drawPlayer(Graphics g) {
        if (player != null) {
            int playerX = player.getX() * tileSize;
            int playerY = player.getY() * tileSize;

            g.setColor(Color.BLACK);
            g.drawRect(playerX, playerY, tileSize, tileSize);

            // Determine the player's direction and select the appropriate icon
            ImageIcon playerIcon = getPlayerIcon();

            // Draw the player icon scaled to the tile size
            g.drawImage(playerIcon.getImage(), playerX + 1, playerY + 1, tileSize - 2, tileSize - 2, null);
        }
    }

    private ImageIcon getPlayerIcon() {
        return switch (player.getDirection()) {
            case NORTH -> playerDownIcon;
            case WEST -> playerLeftIcon;
            case EAST -> playerRightIcon;
            default -> playerUpIcon;
        };
    }


    public void renderPlayer(Graphics g, int startRow, int startCol, int playerRow, int playerCol, int tileSize) {
        ImageIcon playerIcon = getPlayerIcon();
        int playerX = (playerCol - startCol) * tileSize;
        int playerY = (playerRow - startRow) * tileSize;
        g.drawImage(playerIcon.getImage(), playerX, playerY, tileSize, tileSize, null);
    }
}