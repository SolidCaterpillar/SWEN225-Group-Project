package nz.ac.wgtn.swen225.lc.renderer;


import java.awt.*;
import java.util.ArrayList;
import java.util.List;


/**
 * The `Composite` class represents a composite renderer that can contain multiple `Renderable` objects and render them
 * as a group. It implements the `Renderable` interface, allowing it to be used as a single rendering entity.
 *
 * This class can be used to group and manage a collection of renderable objects and render them collectively on the same
 * graphics context.
 *
 *  * @author Arnav Dogra (@dograarna)
 *
 * @see Renderable
 */
class Composite implements Renderable {
    private final List<Renderable> children = new ArrayList<>();

   /**
     * Adds a `Renderable` object to the composite renderer, allowing it to be included in the rendering process.
     *
     * @param renderable The `Renderable` object to be added to the composite renderer.
     */
    public void add(Renderable renderable) {
        children.add(renderable);
    }


    /**
     * Clears all `Renderable` objects from the composite renderer, making it an empty container.
     */
    public void clear() {
        children.clear();
    }


    /**
     * Renders all child `Renderable` objects contained within the composite renderer on the specified graphics context.
     *
     * @param g The graphics context on which the child objects should be rendered.
     */

    @Override
    public void render(Graphics g) {
        for (Renderable child : children) {
            child.render(g);
        }
    }

}