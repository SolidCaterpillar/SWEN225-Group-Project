package src.nz.ac.wgtn.swen225.lc.app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Enumeration;
import javax.imageio.ImageIO;
import java.io.*;


public class GUI {
    
    private int currentLevel; // Current game level
    private int keysCollected; // Number of keys collected
    private int treasuresRemaining; // Number of treasures remaining
    private int timeLeft; // Time left for the current level
    private Timer timer; // Timer for counting down the time
    
    // Composed of a Main Frame which is then broken into Panels    
    private JFrame mainFrame;
    private JPanel backPanel;
    private JPanel mapPanel;
    
    private JPanel sidePanel;  
    private JPanel levelPanel;
    private JPanel timePanel;
    private JPanel chipsPanel;
    private JPanel inventoryPanel;
    
    private JLabel levelLabel;
    private JLabel timeLabel;
    private JLabel chipsLabel;
    
    private String chipsText = "Chips";
    
    private final int tileSize = 44; // Adjust this size as needed
    private final int numRows = 9;
    private final int numCols = 9;
    private final int marginSize = 60; // Adjust this size for margins
    
    private boolean gamePaused; // Flag to track if the game is paused
    
    
    public GUI() {
        
        // Frame is the overall GUI JFrame that contains pannels. 
        mainFrame = new JFrame("Larry Croft’s Adventures");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setResizable(false);
        
        drawBoard();
        createSideBar();
        createMenuBar();
        
        // Add the key listener to the panel. All of these lines are required
        mapPanel.setFocusable(true);
        mapPanel.requestFocus();
        
        mapPanel.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                // This method is called when a key is typed
            }
        
            @Override
            public void keyPressed(KeyEvent e) {
                // This method is called when a key is pressed
                int keyCode = e.getKeyCode();
                
                if (e.isControlDown()) {
                    // Handle Ctrl key combinations
                    switch (keyCode) {
                        case KeyEvent.VK_X:
                            // CTRL-X: Exit the game and lose current game state
                            // Implement game state reset logic and exit
                            chipsText = "CTRL-X";
                            redrawGUI();
                            break;
                        case KeyEvent.VK_S:
                            // CTRL-S: Save the game state
                            // Implement game state save logic
                            chipsText = "CTRL-S";
                            redrawGUI();
                            break;
                        case KeyEvent.VK_R:
                            // CTRL-R: Resume a saved game
                            // Implement game state loading logic using a file selector
                            chipsText = "CTRL-R";
                            redrawGUI();
                            break;
                        case KeyEvent.VK_1:
                            // CTRL-1: Start a new game at level 1
                            // Implement logic to start a new game at level 1
                            chipsText = "CTRL-1";
                            redrawGUI();
                            break;
                        case KeyEvent.VK_2:
                            // CTRL-2: Start a new game at level 2
                            // Implement logic to start a new game at level 2
                            chipsText = "CTRL-2";
                            redrawGUI();
                            break;
                    }
                } else {
                    // Handle regular arrow key movements
                    switch (keyCode) {
                        case KeyEvent.VK_UP:
                            // Handle UP arrow key press (e.g., move up)
                            chipsText = "UP";
                            redrawGUI();
                            break;
                        case KeyEvent.VK_LEFT:
                            // Handle LEFT arrow key press (e.g., move left)
                            chipsText = "LEFT";
                            redrawGUI();
                            break;
                        case KeyEvent.VK_DOWN:
                            // Handle DOWN arrow key press (e.g., move down)
                            chipsText = "DOWN";
                            redrawGUI();
                            break;
                        case KeyEvent.VK_RIGHT:
                            // Handle RIGHT arrow key press (e.g., move right)
                            chipsText = "RIGHT";
                            redrawGUI();
                            break;
                        case KeyEvent.VK_W:
                            // Handle UP arrow key press (e.g., move up)
                            chipsText = "UP";
                            redrawGUI();
                            break;
                        case KeyEvent.VK_A:
                            // Handle LEFT arrow key press (e.g., move left)
                            chipsText = "LEFT";
                            redrawGUI();
                            break;
                        case KeyEvent.VK_S:
                            // Handle DOWN arrow key press (e.g., move down)
                            chipsText = "DOWN";
                            redrawGUI();
                            break;
                        case KeyEvent.VK_D:
                            // Handle RIGHT arrow key press (e.g., move right)
                            chipsText = "RIGHT";
                            redrawGUI();
                            break;
                    }
                }
            }
        
            @Override
            public void keyReleased(KeyEvent e) {
                // This method is called when a key is released
            }
        });
        
        mainFrame.pack();
        mainFrame.setVisible(true);
    }
    
    
    public void drawBoard(){
        
        backPanel = new JPanel(new BorderLayout());
        backPanel.setBorder(BorderFactory.createEmptyBorder(marginSize, marginSize * 2, marginSize, marginSize * 2));
        //backPanel.setBackground(Color.GREEN.darker());
    
        mapPanel = new JPanel(new GridLayout(numRows, numCols));
        mapPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, marginSize));
        mapPanel.setOpaque(false);
        
        // This method will initialize the positions of specific Tiles. Use this to link to Tile, Estate, and Player
        createTiles();
        
        //backPanel.setBackground(Color.GRAY);
        //mapPanel.setBackground(Color.RED);
        backPanel.add(mapPanel);
        mainFrame.add(backPanel);
    }
    
    public void createMenuBar(){
        // Create the menu bar
        JMenuBar menuBar = new JMenuBar();
        mainFrame.setJMenuBar(menuBar);
    
        // Create a menu
        JMenu menu1 = new JMenu("Game");
        JMenu menu2 = new JMenu("Options");
        JMenu menu3 = new JMenu("Level");
        JMenu menu4 = new JMenu("Help");
        
        menuBar.add(menu1);
        menuBar.add(menu2);
        menuBar.add(menu3);
        menuBar.add(menu4);
        
        JMenuItem pauseMenuItem = new JMenuItem("Pause");
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        JMenuItem saveMenuItem = new JMenuItem("Save");
        JMenuItem loadMenuItem = new JMenuItem("Load");        
        JMenuItem level1MenuItem = new JMenuItem("Level 1");
        JMenuItem level2MenuItem = new JMenuItem("Level 2");
        JMenuItem instructionsMenuItem = new JMenuItem("Game Rules");
        
        menu1.add(pauseMenuItem);
        menu1.add(exitMenuItem);
        menu2.add(saveMenuItem);
        menu2.add(loadMenuItem);
        menu3.add(level1MenuItem);
        menu3.add(level2MenuItem);
        menu4.add(instructionsMenuItem);
    
        // Adding the JOptionPane alongside the MenuItem when clicked
        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int choice = JOptionPane.showConfirmDialog(mainFrame, "Custom JOptionPane: wanna leave bro?", "Exit Confirmation", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
    }

    public void createTiles() {
        
        ImageIcon imageIcon = new ImageIcon(getClass().getResource("icons/walltile3.png"));
        
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                
                // below basically makes a small JPanel square which will contain the content of a Tile.
                JPanel cell = new JPanel();
                cell.setBackground(Color.WHITE);
                cell.setPreferredSize(new Dimension(tileSize, tileSize));
                
                JLabel test = new JLabel();
                test.setIcon(imageIcon);
                
                cell.add(test);
                mapPanel.add(cell);
                
            }
        }
    }
    
    
    public void createlevelPanel(){
        levelPanel = new JPanel();
        
        levelLabel = new JLabel("Level");
        levelPanel.add(levelLabel);
        
        levelPanel.setBackground(Color.WHITE);  // this is needed because the default color is not actually white
        levelPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }
    
    public void createTimePanel(){
        timePanel = new JPanel();
        
        timeLabel = new JLabel("Time");
        timePanel.add(timeLabel);
        
        timePanel.setBackground(Color.WHITE);  // this is needed because the default color is not actually white
        timePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }
    
    public void createChipsPanel(){
        chipsPanel = new JPanel();
        
        chipsLabel = new JLabel(chipsText);
        chipsPanel.add(chipsLabel);
        
        chipsPanel.setBackground(Color.WHITE);
        chipsPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }
    
    public void createinventoryPanel(){
        inventoryPanel = new JPanel(new GridLayout(2, 4));
        
        inventoryPanel.setBackground(Color.WHITE);
        inventoryPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        
        ImageIcon imageIcon = new ImageIcon(getClass().getResource("icons/key.png"));
        
        for (int i = 0; i < 8; i++) {
            // below basically makes a small JPanel square which will contain the content of a Tile.
            JPanel slot = new JPanel();
            slot.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
            
            JLabel test = new JLabel();
            test.setIcon(imageIcon);
            slot.add(test);
            
            inventoryPanel.add(slot);
        }
    }
    
    public void createSideBar(){
        // Create the header panels
        sidePanel = new JPanel(new GridLayout(4, 1)); // 4 rows, 1 column
        sidePanel.setPreferredSize(new Dimension(200, mainFrame.getHeight()));
        
        createlevelPanel();
        sidePanel.add(levelPanel);
        
        createTimePanel();
        sidePanel.add(timePanel);
        
        createChipsPanel();
        sidePanel.add(chipsPanel);
        
        createinventoryPanel();
        sidePanel.add(inventoryPanel);
    
        backPanel.add(sidePanel, BorderLayout.EAST);
    }
        
    public void redrawGUI() {
        
        // this is the only method to redraw the board
        mapPanel.removeAll();
        createTiles();
        
        // this is the method to redraw the side panel
        sidePanel.removeAll();
        createlevelPanel();
        sidePanel.add(levelPanel);
        
        createTimePanel();
        sidePanel.add(timePanel);
        
        createChipsPanel();
        sidePanel.add(chipsPanel);
        
        createinventoryPanel();
        sidePanel.add(inventoryPanel);
        
        // this is needed to confirm the changes
        mainFrame.revalidate();
        mainFrame.repaint();
    }
    
    
    private void exitGame() {
        // Implement game exit logic, potentially saving the game state
        // and resuming from the last unfinished level next time
        // Close the application
        System.exit(0);
    }

    private void saveGame() {
        // Implement logic to save the current game state
        // Show a message indicating that the game is saved
    }

    private void resumeGame() {
        // Implement logic to resume a saved game
        // Show a file selector to load a saved game
    }

    private void startNewGame(int level) {
        // Implement logic to start a new game at the specified level
        // Reset game state and initialize the maze for the new level
    }

    private void pauseGame() {
        // Implement logic to pause the game
        // Display a "game is paused" dialog
    }

    private void moveChap(String direction) {
        // Implement logic to move Chap within the maze
    }

}


