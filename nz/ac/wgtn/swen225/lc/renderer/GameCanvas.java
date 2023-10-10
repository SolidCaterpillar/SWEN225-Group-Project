package nz.ac.wgtn.swen225.lc.renderer;

import javax.swing.*;
import java.awt.*;

/**
 * The GameCanvas class represents the canvas where the game is displayed.
 * It extends JPanel and provides focus region.
 * @Author Arnav Dogra (@dograarna)
 */
public class GameCanvas extends JPanel {

    private final GameRenderer renderer; // Renderer instance

    /**
     * Constructs a GameCanvas with a specified GameRenderer.
     *
     * @param renderer The GameRenderer instance responsible for rendering the game.
     */
    public GameCanvas(GameRenderer renderer) {
        this.renderer = renderer;

        // Set the preferred size of the canvas based on the renderer's size
        setPreferredSize(renderer.getPreferredSize());
    }

    /**
     * Overrides the paintComponent method to render the game view.
     *
     * @param g The Graphics context to render the game.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        renderer.renderGameView(g);
        renderer.repaint(); // To repaint the game view
    }
}
