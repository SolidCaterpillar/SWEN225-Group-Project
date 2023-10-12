package nz.ac.wgtn.swen225.lc.renderer;

import nz.ac.wgtn.swen225.lc.domain.Entity.Entity;
import nz.ac.wgtn.swen225.lc.domain.Entity.Key;
import nz.ac.wgtn.swen225.lc.domain.Entity.Treasure;
import nz.ac.wgtn.swen225.lc.domain.Tile.FreeTile;
import nz.ac.wgtn.swen225.lc.domain.Tile.LockedDoor;
import nz.ac.wgtn.swen225.lc.domain.Tile.Tile;

import javax.swing.*;
import java.awt.*;


/**
 * The `BoardRenderer` class is responsible for rendering the game board, including tiles, entities, and their icons.
 * It implements the `Renderable` interface, which defines the rendering behavior for game elements.
 * The class loads and manages tile icons, maps them to the corresponding tiles and entities, and draws the entire maze
 * on the board
 * @author Arnav Dogra (@dograarna)
 *
 * @see Renderable
 * @see Tile
 * @see Entity
 * @see ImageIcon
 */
public class BoardRenderer implements Renderable {
    private final Tile[][] maze;
    private final int tileSize;
    private final String iconFolder;
    private final ImageIcon[][] tileIcons;


    /**
     * Constructs a `BoardRenderer` object with the given maze, tile size, and icon folder.
     *
     * @param maze       The 2D array of tiles representing the game board.
     * @param tileSize   The size of each tile in pixels.
     * @param iconFolder The folder path where tile icons are located.
     */
    public BoardRenderer(Tile[][] maze, int tileSize, String iconFolder) {
        this.maze = maze;
        this.tileSize = tileSize;
        this.iconFolder = iconFolder;
        tileIcons = new ImageIcon[maze.length][maze[0].length];
        loadTileIcons( maze);
    }


    /**
     * Renders the game board.
     *
     * @param g The graphics context on which the game board should be rendered.
     */
    @Override
    public void render(Graphics g) {
        drawMaze(g);
    }


    /**
     * Loads tile icons based on the provided maze and assigns them to the corresponding tiles and entities.
     *
     * @param maze The 2D array of tiles representing the game board.
     */
    public void loadTileIcons(Tile[][] maze) {
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
                            iconName = "key" + colorName + ".png";
                        } else if (entity instanceof Treasure) {
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
     * Maps a tile to its corresponding icon filename.
     *
     * @param tile The tile for which to determine the icon filename.
     * @return The filename of the icon associated with the tile.
     */
    private String getIconNameForTile(Tile tile) {
        if (tile == null) {
            return "unknown.png";
        }
        return switch (tile.getClass().getSimpleName()) {
            case "Wall" -> "walltile.png";
            case "FreeTile" -> "freetile.png";
            case "LockedDoor" -> "door" + ((LockedDoor) tile).getColour().toString().toLowerCase() + ".png";
            case "ExitLock" -> "exitlock.png";
            case "ExitTile" -> "exit.png";
            case "InformationTile" -> "infofield.png";
            default -> null;
        };
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
     * Draws the maze on the graphics context, including tiles and their associated icons.
     *
     * @param g The graphics context on which to draw the maze.
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

                    g.drawImage(tileIcons[row][col].getImage(), x + 1, y + 1, width - 2, height - 2, null);
                }
            }
        }
    }



    /**
     * Renders focus region of tiles within the game board
     *
     * @param g         The graphics context on which to render the tiles.
     * @param startRow  The starting row of the region.
     * @param endRow    The ending row of the region.
     * @param startCol  The starting column of the region.
     * @param endCol    The ending column of the region.
     * @param tileSize  The size of each tile.
     */
    public void renderTiles(Graphics g, int startRow, int endRow, int startCol, int endCol, int tileSize) {
        for (int row = startRow; row < endRow; row++) {
            for (int col = startCol; col < endCol; col++) {
                if (tileIcons[row][col] != null) {
                    int x = (col - startCol) * tileSize;
                    int y = (row - startRow) * tileSize;
                    g.drawImage(tileIcons[row][col].getImage(), x, y, tileSize, tileSize, null);
                    g.setColor(Color.BLACK);
                    g.drawRect(x, y, tileSize - 1, tileSize - 1);
                }
            }
        }
    }
}
