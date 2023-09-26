package src.nz.ac.wgtn.swen225.lc.app;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    // Private fields for the game
    private char[][] gameBoard; // Represents the game board with different tiles (e.g., walls, keys, doors)
    private int playerX; // Player's X-coordinate on the board
    private int playerY; // Player's Y-coordinate on the board
    private int keysCollected; // Number of keys collected by the player
    private boolean hasChip; // Indicates whether the player has collected the chip
    private boolean gameOver; // Indicates whether the game is over
    private GUI gui;
    
    public Main() {
        // Initialize the game board, player position, and other fields
        // You would typically load the board from a file or create it programmatically.
        // For simplicity, let's assume a 2D char array for the board.
        // 'W' represents walls, 'K' represents keys, 'D' represents doors, 'C' represents the chip, etc.
        
        playerX = 4; // Initial player X-coordinate
        playerY = 4; // Initial player Y-coordinate
        keysCollected = 0;
        hasChip = false;
        gameOver = false;
        gui = new GUI();
        
    }
    
    // Method to move the player in a given direction (e.g., "up", "down", "left", "right")
    private void movePlayer(String direction) {
        // Implement logic to move the player, check for collisions, and update the game state
    }

    // Method to check for collisions when the player attempts to move
    private boolean checkCollision(int newX, int newY) {
        // Implement collision detection logic here (e.g., checking for walls, keys, doors, chip, etc.)
        // Return true if there's a collision, false otherwise
        return false;
    }

    // Method to handle player interaction with objects (e.g., picking up keys, opening doors)
    private void handleInteraction(int newX, int newY) {
        // Implement logic to handle interactions with keys, doors, chip, etc.
    }

    // Method to check if the player has won the game
    private boolean checkWinCondition() {
        // Implement logic to check if the player has collected all necessary items (e.g., chip) and reached the exit
        // Return true if the player has won, false otherwise
        return false;
    }

    // Method to display the game board
    private void displayGameBoard() {
        // Implement logic to print the game board with the player's position and other game elements
    }

    // Main game loop
    private void playGame() {
        while (!gameOver) {
            displayGameBoard();
            // Get player input for movement (e.g., "up", "down", "left", "right")
            // Call movePlayer method with the chosen direction
            // Check for win condition and update gameOver if necessary
        }
        // Display game over message or win message
    }

    public static void main(String[] args) {
        Main game = new Main();
        
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.gui.decrementTime();
                game.gui.redrawGUI();
            }
        });
        
        timer.start();
        //game.playGame(); // Start the game loop
    }
}




