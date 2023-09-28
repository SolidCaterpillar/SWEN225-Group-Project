package nz.ac.wgtn.swen225.lc.renderer;

import nz.ac.wgtn.swen225.lc.domain.*;
import nz.ac.wgtn.swen225.lc.domain.Tile.*;
import nz.ac.wgtn.swen225.lc.domain.Entity.*;
import nz.ac.wgtn.swen225.lc.persistency.*;
import javax.swing.*;

public class Test {
static Level play = Persistency.loadLevel1();
    public static void main(String[] args) {
        Tile[][] maze = play.board().getBoard() ;
        Player ch =play.player();
        //System.out.println(ch.getX() + " " + ch.getY());


        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Chip's Challenge Game");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Create instances of your game components
           GameRenderer renderer = new GameRenderer(maze, ch);
            GameCanvas canvas = new GameCanvas(renderer);
            // Add any other game components here

            // Add the renderer and canvas to the frame
            //frame.add(renderer);
            frame.add(canvas);

            // Set up the game loop or event handling here

            frame.pack();
            frame.setVisible(true);
        });
    }
}
