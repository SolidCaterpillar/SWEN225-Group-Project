package nz.ac.wgtn.swen225.lc.renderer;

import nz.ac.wgtn.swen225.lc.domain.Entity.Player;

import javax.swing.*;
import java.awt.*;



/**
 * The `PlayerRenderer class is responsible for rendering the player character on the game board. It implements the
 * `Renderable` interface, defining the rendering behavior for game elements.
 * This class manages the rendering of the player character using provided data such as the player object, tile size, and
 * icon folder. It supports rendering the player character facing different directions.
 *
 * @author Arnav Dogra (@dograarna) ID:300630190
 *
 * @see Renderable
 * @see Player
 */
public class PlayerRenderer implements Renderable {
    private final Player player;
    private final int tileSize;
    private final String iconFolder;
    private final ImageIcon playerUpIcon;
    private final ImageIcon playerDownIcon;
    private final ImageIcon playerLeftIcon;
    private final ImageIcon playerRightIcon;


    /**
     * Constructs a `PlayerRenderer` object with the given player, tile size, and icon folder.
     *
     * @param player     The player entity to be rendered.
     * @param tileSize   The size of each tile in pixels.
     * @param iconFolder The folder path where player icons are located.
     */

    public PlayerRenderer(Player player, int tileSize, String iconFolder) {
        this.player = player;
        this.tileSize = tileSize;
        this.iconFolder = iconFolder;
        playerUpIcon = loadImageIcon("playerup1.png");
        playerDownIcon = loadImageIcon("playerdown1.png");
        playerLeftIcon = loadImageIcon("playerleft2.png");
        playerRightIcon = loadImageIcon("playerright2.png");
    }


    /**
     * Renders the player character on the specified graphics context.
     *
     * @param g The graphics context on which the player character should be rendered.
     */

    @Override
    public void render(Graphics g) {
        drawPlayer(g);
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
     * Draws the player character on the graphics context, considering the player's current position and direction.
     *
     * @param g The graphics context on which the player character should be drawn.
     */
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


    /**
     * Determines the appropriate player icon based on the player's current direction.
     *
     * @return The ImageIcon corresponding to the player's current direction.
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
     * Renders the player character within focus region of the game board.
     *
     * @param g         The graphics context on which to render the player character.
     * @param startRow  The starting row of the region.
     * @param startCol  The starting column of the region.
     * @param playerRow The row position of the player character.
     * @param playerCol The column position of the player character.
     * @param tileSize  The size of each tile in pixels.
     */

    public void renderPlayer(Graphics g, int startRow, int startCol, int playerRow, int playerCol, int tileSize) {
        ImageIcon playerIcon = getPlayerIcon();
        int playerX = (playerCol - startCol) * tileSize;
        int playerY = (playerRow - startRow) * tileSize;
        g.drawImage(playerIcon.getImage(), playerX, playerY, tileSize, tileSize, null);
    }
}