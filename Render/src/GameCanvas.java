import javax.swing.*;
import java.awt.*;

public class GameCanvas extends JPanel {

    public GameCanvas() {
        // Allow the canvas to resize with the window
        setPreferredSize(new Dimension(800, 600)); // Default size (can be changed)
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //  rendering logic here to draw the game elements
        //  use the Graphics object 'g' to draw shapes, images, etc.
        // For example:
        // g.drawRect(x, y, width, height);
        // g.drawImage(image, x, y, this);
    }

    /**
     *  Update the canvas size based on the window size
     * @return
     */
    @Override
    public Dimension getPreferredSize() {
        return getParent() != null ? getParent().getSize() : super.getPreferredSize();
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Game Canvas Example");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            GameCanvas canvas = new GameCanvas();
            frame.add(canvas);

            frame.pack();
            frame.setVisible(true);
        });
    }


}
