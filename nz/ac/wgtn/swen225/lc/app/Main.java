package nz.ac.wgtn.swen225.lc.app;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import nz.ac.wgtn.swen225.lc.domain.*;
import nz.ac.wgtn.swen225.lc.domain.Tile.*;
import nz.ac.wgtn.swen225.lc.domain.Entity.*;
import nz.ac.wgtn.swen225.lc.persistency.*;
import nz.ac.wgtn.swen225.lc.renderer.*;
//import nz.ac.wgtn.swen225.lc.recorder.*;


public class Main {
    private GUI gui;

    public Main() {
        gui = new GUI();

    }

    public static void main(String[] args) {
        Main game = new Main();

        //game.playGame(); // Start the game loop
    }
}




