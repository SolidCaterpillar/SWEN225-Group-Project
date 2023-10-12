package nz.ac.wgtn.swen225.lc.renderer;

import nz.ac.wgtn.swen225.lc.domain.Entity.Entity;
import nz.ac.wgtn.swen225.lc.domain.Entity.Key;
import nz.ac.wgtn.swen225.lc.domain.Entity.Treasure;
import nz.ac.wgtn.swen225.lc.domain.Tile.FreeTile;
import nz.ac.wgtn.swen225.lc.domain.Tile.LockedDoor;
import nz.ac.wgtn.swen225.lc.domain.Tile.Tile;

import javax.swing.*;
import java.awt.*;



public class BoardRenderer implements Renderable {
    private final Tile[][] maze;
    private final int tileSize;
    private final String iconFolder;
    private final ImageIcon[][] tileIcons;

    public BoardRenderer(Tile[][] maze, int tileSize, String iconFolder) {
        this.maze = maze;
        this.tileSize = tileSize;
        this.iconFolder = iconFolder;
        tileIcons = new ImageIcon[maze.length][maze[0].length];
        loadTileIcons( maze);
    }

    @Override
    public void render(Graphics g) {
        drawMaze(g);
    }


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

    private ImageIcon loadImageIcon(String filename) {
        String path = iconFolder + filename;
        return new ImageIcon(path);
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

                    g.drawImage(tileIcons[row][col].getImage(), x + 1, y + 1, width - 2, height - 2, null);
                }
            }
        }
    }

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
