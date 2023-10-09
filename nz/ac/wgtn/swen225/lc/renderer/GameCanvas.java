package nz.ac.wgtn.swen225.lc.renderer;

import javax.swing.*;
import java.awt.*;

/**
 * This class represents the canvas where the game is displayed.
 */
public class GameCanvas extends JPanel {

    private GameRenderer renderer; // Renderer instance

    public GameCanvas(GameRenderer renderer) {
        this.renderer = renderer;

        // Set the preferred size of the canvas based on the renderer's size
        setPreferredSize(renderer.getPreferredSize());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Delegate the rendering to the Renderer instance
        renderer.renderGameView(g);
        renderer.repaint(); // To repaint the game view
    }
}
