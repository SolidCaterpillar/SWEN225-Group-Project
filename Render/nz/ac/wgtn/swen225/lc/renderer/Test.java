package nz.ac.wgtn.swen225.lc.renderer;

import Domain.Tile.Tile;

import javax.swing.*;
import Persistency.*;
public class Test {

    public static void main(String[] args) {
        Tile[][] maze = Persistency.loadLevel1().board().getBoard() ;

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Chip's Challenge Game");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Create instances of your game components
            GameRenderer renderer = new GameRenderer(maze);
            //GameCanvas canvas = new GameCanvas();
            // Add any other game components here

            // Add the renderer and canvas to the frame
            frame.add(renderer);
            //frame.add(canvas);

            // Set up the game loop or event handling here

            frame.pack();
            frame.setVisible(true);
        });
    }
}
