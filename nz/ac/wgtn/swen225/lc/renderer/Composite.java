package nz.ac.wgtn.swen225.lc.renderer;


import java.awt.*;
import java.util.ArrayList;
import java.util.List;

class Composite implements Renderable {
    private final List<Renderable> children = new ArrayList<>();

    public void add(Renderable renderable) {
        children.add(renderable);
    }

    public void clear() {
        children.clear();
    }

    @Override
    public void render(Graphics g) {
        for (Renderable child : children) {
            child.render(g);
        }
    }

}