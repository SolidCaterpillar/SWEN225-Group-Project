package nz.ac.wgtn.swen225.lc.app;

/*
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import nz.ac.wgtn.swen225.lc.domain.*;
import nz.ac.wgtn.swen225.lc.domain.Tile.*;
import nz.ac.wgtn.swen225.lc.domain.Entity.*;
import nz.ac.wgtn.swen225.lc.persistency.*;
import nz.ac.wgtn.swen225.lc.renderer.*;
import nz.ac.wgtn.swen225.lc.recorder.*;
*/

/**
 * This is the Main class that runs the game.
 * This is named as per the specifications
 * @author Deveremma (Emmanuel De Vera) 300602434
 */
public class Main {
    /**
     * Creating a Main class will immediately create a GUI object
     */
    public Main() {
        GUI gui = new GUI();
    }
    /**
     * In the main method, making a Main object will make the GUI object
     */
    public static void main(String[] args) {
        Main game = new Main();
        //game.playGame(); // Start the game loop
    }
}
