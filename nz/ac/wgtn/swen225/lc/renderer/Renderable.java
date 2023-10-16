package nz.ac.wgtn.swen225.lc.renderer;

import java.awt.*;

/**
 * The Renderable interface represents objects that can be rendered on the Maze/Board.
 * Classes that implement this interface are expected to provide a method for rendering themselves.
 * @author Arnav Dogra (@dograarna) ID:300630190
 */
public interface Renderable {
    void render(Graphics g);

}