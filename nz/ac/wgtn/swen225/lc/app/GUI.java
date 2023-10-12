package nz.ac.wgtn.swen225.lc.app;
// Author: Emmanuel De Vera

import nz.ac.wgtn.swen225.lc.domain.*;
import nz.ac.wgtn.swen225.lc.domain.Tile.*;
import nz.ac.wgtn.swen225.lc.domain.Entity.*;
import nz.ac.wgtn.swen225.lc.persistency.*;
import nz.ac.wgtn.swen225.lc.renderer.*;
import nz.ac.wgtn.swen225.lc.recorder.*;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Objects;
import java.io.File;
import java.io.IOException;


/**
 * This is the GUI object that create the entirety of App
 * GUI creates a JFrame that will contain all the key components
 * @author Devsdevera (Emmanuel De Vera)
 */
public class GUI {
    private int currentLevel = 1; // Current game level
    private final int maxTime = 60;   // Sets up the time limit to count down from
    private int timeLeft = maxTime; // Time left for the current level
    private Timer timer; // Timer for counting down the time
    private int counter = 0; // Used with Modulus to turn milliseconds into seconds


    private JFrame mainFrame;   // This is the JFrame that contains everything
    private JPanel backPanel;   // This contains the background of the App
    private JPanel mapPanel;    // JPanel that contains the Board of the game
    private JPanel sidePanel;   // JPanel that holds all sidePanel subPanels
    private JPanel levelPanel;  // Shows the Current Level being played
    private JPanel timePanel;   // Shows the Time remaining that is counting down
    private JPanel chipsPanel;  // Shows how many Chips/Treasure still remain
    private JPanel inventoryPanel;  // Shows the keys that the player has collected


    private Player ch;
    private Level play;
    private Domain d;
    private GameCanvas canvas;
    private GameRenderer  renderer;
    private Tile[][] maze;
    private SoundManager soundManager;


    private String levelText = "Level";
    private String timeText = "Time";
    private String chipsText = "Chips";


    private final int tileSize = 68; // Adjust this size as needed for inventory
    private boolean gamePaused = false; // Flag to track if the game is paused
    private boolean showInstructions = false;   // Tracking if Instructions are shown
    private Recorder rec;

    /**
     * Creating the GUI class will create all the JPanel Components
     */
    public GUI() {

        // Frame is the overall GUI JFrame that contains panels.
        mainFrame = new JFrame("Larry Croftâ€™s Adventures");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setResizable(false);

        // Calculate the center of the screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - mainFrame.getWidth()) / 4;
        int y = (screenSize.height - mainFrame.getHeight()) / 4;
        mainFrame.setLocation(x, y); // Set the location to the center

        // Creating the objects of the other modules
        // The Following are Integrations of previous Modules
        soundManager = new SoundManager();
        soundManager.playGameStartSound();

        this.play = Persistency.loadLevel1();
        this.maze = play.board().getBoard();
        this.d = Domain.getInstance();
        this.d.pickLevel(LevelE.LEVEL_ONE);
        this.ch = d.getPlayer();
        this.rec = new Recorder();

        // Creating the render object and the canvas which display the board
        this.renderer = new GameRenderer(maze, ch, d);
        this.canvas = new GameCanvas(renderer);

        // functions to draw App components
        drawBoard();
        createSideBar();
        createMenuBar();
        resetTimer();

        // Add the key listener to the panel. All of these lines are required
        mapPanel.setFocusable(true);
        mapPanel.requestFocus();
        addKeyListener();

        // complete the mainFrame functionality
        mainFrame.pack();
        mainFrame.setVisible(true);
        soundManager.playBackgroundMusic();
    }

    /**
     * Creating the GUI class will create all the JPanel Components
     */
    private void addKeyListener(){
        mapPanel.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}  // This method is called when a key is typed
            @Override
            public void keyReleased(KeyEvent e) {} // This method is called when a key is released
            @Override
            public void keyPressed(KeyEvent e) {
                // This method is called when a key is pressed
                int keyCode = e.getKeyCode();
                if (e.isControlDown()) {    // Handle Ctrl key combinations
                    switch (keyCode) {
                        case KeyEvent.VK_X -> {
                            // CTRL-X: Exit the game and lose current game state
                            // Implement game state reset logic and exit
                            int choice = JOptionPane.showConfirmDialog(mainFrame, "Custom JOptionPane: wanna leave bro?", "Exit Confirmation", JOptionPane.YES_NO_OPTION);
                            if (choice == JOptionPane.YES_OPTION) {
                                System.exit(0);
                            }
                        }
                        case KeyEvent.VK_S -> {
                            // CTRL-S: Save the game state
                            // Implement game state save logic
                            writeFile();
                        }
                        case KeyEvent.VK_R -> {
                            // CTRL-R: Resume a saved game
                            // Implement game state loading logic using a file selector
                            loadFile();
                        }
                        case KeyEvent.VK_1 -> {
                            // CTRL-1: Start a new game at level 1
                            // Implement logic to start a new game at level 1
                            currentLevel = 1;
                            timeLeft = maxTime;
                            if (timer.isRunning()) {
                                timer.stop();
                            }
                            replay(1);
                        }
                        case KeyEvent.VK_2 -> {
                            // CTRL-2: Start a new game at level 2
                            // Implement logic to start a new game at level 2
                            currentLevel = 2;
                            timeLeft = maxTime;
                            if (timer.isRunning()) {
                                timer.stop();
                            }
                            replay(2);

                        }
                        case KeyEvent.VK_E -> {
                            // CTRL-2: Start a new game at level 2
                            // Implement logic to start a new game at level 2
                            showInstructions = !showInstructions;
                            gamePaused = showInstructions;
                        }
                    }
                } else { // Handle regular key presses
                    if(!gamePaused){
                        switch (keyCode) {
                            case KeyEvent.VK_ESCAPE -> {
                                gamePaused = true;
                                levelText = "Paused";
                            }
                            case KeyEvent.VK_UP -> {
                                // Handle UP arrow key press (e.g., move up)
                                moveChecker('w');
                            }
                            case KeyEvent.VK_LEFT -> {
                                // Handle LEFT arrow key press (e.g., move left)
                                moveChecker('a');
                            }
                            case KeyEvent.VK_DOWN -> {
                                // Handle DOWN arrow key press (e.g., move down)
                                moveChecker('s');
                            }
                            case KeyEvent.VK_RIGHT -> {
                                // Handle RIGHT arrow key press (e.g., move right)
                                moveChecker('d');
                            }
                            case KeyEvent.VK_W -> {
                                // Handle UP arrow key press (e.g., move up)
                                soundManager.playPlayerMoveSound();
                                moveChecker('w');
                            }
                            case KeyEvent.VK_A -> {
                                // Handle LEFT arrow key press (e.g., move left)
                                soundManager.playPlayerMoveSound();
                                moveChecker('a');
                            }
                            case KeyEvent.VK_S -> {
                                // Handle DOWN arrow key press (e.g., move down)
                                soundManager.playPlayerMoveSound();
                                moveChecker('s');
                            }
                            case KeyEvent.VK_D -> {
                                // Handle RIGHT arrow key press (e.g., move right)
                                soundManager.playPlayerMoveSound();
                                moveChecker('d');
                            }
                        }
                    }else{
                        // This is to prevent pause conflicts
                        if (keyCode == KeyEvent.VK_ESCAPE) {
                            gamePaused = showInstructions;
                            levelText = "Level";
                        }
                    }
                }
                redrawGUI();    // always redrawGUI per key input
                renderer.reDrawBoard();
            }
        });
    }


    public void replayPane(Tile[][] Maze, Player player, int Time, int Level) {
        // Remove all components from the content pane
        Container contentPane = mainFrame.getContentPane();
        contentPane.removeAll();

        this.play = Persistency.loadLevel1();
        this.maze = play.board().getBoard();
        this.d = Domain.getInstance();
        this.rec = new Recorder();

        // Creating the render object and the canvas which display the board
        this.timeLeft = Time;
        this.currentLevel = Level;
        this.renderer = new GameRenderer(Maze, player, d);
        this.canvas = new GameCanvas(renderer);

        // functions to draw App components
        drawBoard();
        createSideBar();
        createMenuBar();
        // Reset Timer will initial create and start the timer
        resetTimer();

        // Add the key listener to the panel. All of these lines are required
        mapPanel.setFocusable(true);
        mapPanel.requestFocus();
        addKeyListener();
        // Revalidate and repaint the content pane
        contentPane.revalidate();
        contentPane.repaint();
    }

    public void replay(int level) {
        // Remove all components from the content pane
        Container contentPane = mainFrame.getContentPane();
        contentPane.removeAll();

        // Reinitialize all necessary objects and components
        this.play = Persistency.loadLevel1();
        this.maze = play.board().getBoard();
        this.d = Domain.getInstance();
        this.currentLevel = level;
        if(level == 1){
            this.d.pickLevel(LevelE.LEVEL_ONE);
        }else{
            this.d.pickLevel(LevelE.LEVEL_TWO);
        }
        this.ch = d.getPlayer();
        this.ch.changeDir('s');
        this.renderer = new GameRenderer(maze, ch, d);
        this.canvas = new GameCanvas(renderer);


        // Re-create components
        drawBoard();
        createSideBar();
        createMenuBar();
        resetTimer();

        // Add key listener
        mapPanel.setFocusable(true);
        mapPanel.requestFocus();
        // Add your key listener code here
        addKeyListener();
        // Revalidate and repaint the content pane
        contentPane.revalidate();
        contentPane.repaint();
    }


    public void moveChecker(char key) {
        soundManager.playPlayerMoveSound();
        int stepState = Domain.StateClass.movePlayer(key);
        if (stepState == 1){
            showInstructions = true;
        }else if(stepState == 2) {
            soundManager.playDoorOpenSound();
        }else{
            showInstructions = false;
        }
        if(Player.getInteract(ch)){
            soundManager.playItemCollectSound();
            ch.interaftFalse();
        }
        rec.setRecord(currentLevel, timeLeft, ch, maze, d);
        try {
            rec.saveAsFile("Larry_Croftâ€™s_Adventures");
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * The method that will load the file, used for recorder
     * This is used to bring up a saved state of the game.
     */
    public void loadFile() {    //static
        // Create a file chooser
        String currentDirectory = System.getProperty("user.dir");
        JFileChooser fileChooser = new JFileChooser(currentDirectory);
        int returnValue = fileChooser.showOpenDialog(null);

        // Check if the user selected a file
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            // Get the selected file
            File selectedFile = fileChooser.getSelectedFile();
            Persistency.loadLevel("level/" + selectedFile.getName());
        } else {
            System.out.println("No file selected.");
        }
    }
    /**
     * Used after Save functionality, menu Save or CTRL-S
     * Will save all the actions made by the user
     */
    public void writeFile() {   // static

        // Create a file chooser
        String currentDirectory = System.getProperty("user.dir");
        JFileChooser fileChooser = new JFileChooser(currentDirectory);
        int returnValue = fileChooser.showSaveDialog(null); // Use Save dialog to select a file to write to

        // Check if the user selected a file
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            // Get the selected file
            File selectedFile = fileChooser.getSelectedFile();
            System.out.println(Domain.setPath(selectedFile.getName() + ".json"));
        } else {
            System.out.println("No file selected.");
        }
    }

    /**
     * Important method to create a new Timer and set behaviour
     * The timer is a powerful object that is independent of any input or idle state
     */
    public void resetTimer(){
        // The timer is set to tick every 50th of a second
        timer = new Timer(20, e -> {
            if(!gamePaused){
                // useful to always verify the state of the game
                counter ++;
                int status = Domain.StateClass.checkGameState(currentLevel);
                if(status == 1){
                    currentLevel = 2;
                    timeLeft = 60;
                }else if(status == 0){
                    dialogBackground("died");
                    timer.stop();
                }else if(status == 2){
                    dialogBackground("win");
                    timer.stop();
                }
                redrawGUI();
                renderer.reDrawBoard();
                // Using modulus, every second the enemies move and the time decreases
                if(counter % 50 == 0) {
                    decrementTime();
                    for (Enemy enemy : Domain.getInstance().getEnemies()) {
                        enemy.updateEnemy();
                        redrawGUI();
                        renderer.reDrawBoard();
                    }
                }
            }
        });
        timer.start();
    }

    /**
     * Draw the board as well as the background of the GUI
     * Setting up the dimensions for the central mapPanel object
     */
    public void drawBoard() {
        backPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Loading the background image
                ImageIcon backgroundImage  = new ImageIcon(Objects.requireNonNull(getClass().getResource("icons/background.png")));
                g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        // Create a border to show the background with equal proportions
        backPanel.setBorder(BorderFactory.createEmptyBorder(60, 150, 60, 150));
        backPanel.setPreferredSize(new Dimension(1280, 720));
        // The mapPanel is now the canvas / board that Renderer has made
        mapPanel = this.canvas;
        mapPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        mapPanel.setOpaque(false);
        // The Central board and background container is added to the Frame
        backPanel.add(mapPanel, BorderLayout.CENTER);
        mainFrame.add(backPanel);
    }

    /**
     * Creating the top menu bar with all the Menu Items
     */
    public void createMenuBar(){
        // Create the menu bar
        JMenuBar menuBar = new JMenuBar();
        mainFrame.setJMenuBar(menuBar);

        // Create the main menu dropdowns
        JMenu menu1 = new JMenu("Game");
        JMenu menu2 = new JMenu("Options");
        JMenu menu3 = new JMenu("Level");
        JMenu menu4 = new JMenu("Help");

        // Add the four dropdowns to the menu
        menuBar.add(menu1);
        menuBar.add(menu2);
        menuBar.add(menu3);
        menuBar.add(menu4);

        // Create all the necessary Menu Items
        JMenuItem pauseMenuItem = new JMenuItem("Pause");
        JMenuItem resumeMenuItem = new JMenuItem("Resume");
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        JMenuItem saveMenuItem = new JMenuItem("Save");
        JMenuItem replayMenuItem = new JMenuItem("Replay");
        JMenuItem loadMenuItem = new JMenuItem("Load");
        JMenuItem level1MenuItem = new JMenuItem("Level 1");
        JMenuItem level2MenuItem = new JMenuItem("Level 2");
        JMenuItem instructionsMenuItem = new JMenuItem("Game Rules");

        // Sort the Menu Items to be added into the appropriate dropdown.
        menu1.add(pauseMenuItem);
        menu1.add(resumeMenuItem);
        menu1.add(exitMenuItem);
        menu2.add(saveMenuItem);
        menu2.add(loadMenuItem);
        menu2.add(replayMenuItem);
        menu3.add(level1MenuItem);
        menu3.add(level2MenuItem);
        menu4.add(instructionsMenuItem);

        pauseMenuItem.addActionListener(e -> {
            // Pressing Pause Menu Item will set the pause boolean
            gamePaused = true;
            levelText = "Paused";
            redrawGUI();
            renderer.reDrawBoard();
        });
        resumeMenuItem.addActionListener(e -> {
            // Pressing Resume Menu Item will set the pause boolean false
            gamePaused = false;
            levelText = "Level";
            redrawGUI();
            renderer.reDrawBoard();
        });
        // Adding the JOptionPane alongside the MenuItem when clicked
        exitMenuItem.addActionListener(e -> {
            // Create a basic JOptionPane to confirm leaving or not
            int choice = JOptionPane.showConfirmDialog(mainFrame, "Custom JOptionPane: Would you like to exit the game?", "Exit Confirmation", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
        loadMenuItem.addActionListener(e -> {
            // Call the LoadFile method
            loadFile();
        });
        saveMenuItem.addActionListener(e -> {
            // Call the WriteFile method
            writeFile();
        });
        level1MenuItem.addActionListener(e -> {
            // Turn the current Level to one and reset
            currentLevel = 1;
            timeLeft = maxTime;
            if (timer.isRunning()) {
                timer.stop();
            }
            redrawGUI();
            renderer.reDrawBoard();
            replay(1);
        });
        level2MenuItem.addActionListener(e -> {
            // Turn the current Level to two and reset
            currentLevel = 2;
            timeLeft = maxTime;
            if (timer.isRunning()) {
                timer.stop();
            }
            redrawGUI();
            renderer.reDrawBoard();
            replay(2);
        });
        instructionsMenuItem.addActionListener(e -> {
            // Using a ternary operator to create a toggle and sync with gamePaused
            showInstructions = !showInstructions;
            gamePaused = gamePaused || showInstructions;

            // if game is paused, and then show instructions.
            // if game is paused it must remain paused, if it is not, then do not be paused.
            redrawGUI();
            renderer.reDrawBoard();
        });
        replayMenuItem.addActionListener(e -> {
            gamePaused = true;
            String currentDirectory = System.getProperty("user.dir");
            JFileChooser fileChooser = new JFileChooser(currentDirectory);
            int returnValue = fileChooser.showOpenDialog(null);

            // Check if the user selected a file
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                // Get the selected file
                File selectedFile = fileChooser.getSelectedFile();
                Replay replay = new Replay(selectedFile.getName(), this);
            } else {
                System.out.println("No file selected.");
            }
        });
    }
    public void dialogBackground(String filename) {
        SwingUtilities.invokeLater(() -> {
            JPanel customDialog = new JPanel(new BorderLayout()) {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    // Load the background image
                    ImageIcon backgroundImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("icons/" + filename + ".png")));
                    g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
                }
            };
            JButton okButton = new JButton("Play Again");
            okButton.addActionListener(e -> {
                // Close the dialog
                Window parentWindow = SwingUtilities.getWindowAncestor(okButton);
                parentWindow.dispose();
                timeLeft = maxTime;
                replay(filename.equals("win") ? 1 : currentLevel);
            });
            okButton.setFocusable(false);
            // Add the "OK" button to the SOUTH position of the BorderLayout
            customDialog.add(okButton, BorderLayout.SOUTH);

            JDialog dialog = new JDialog();
            dialog.setUndecorated(true);
            dialog.setContentPane(customDialog);
            dialog.setSize(720, 405);
            dialog.setLocationRelativeTo(mainFrame);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
            dialog.setVisible(true);
        });
    }


    /**
     * Basic logic for decrementTime
     * If it hits 0 seconds left a basic JOptionPane is shown
     */
    public void decrementTime() {
        timeLeft--;
        if (timeLeft < 1) {
            timer.stop();
            redrawGUI();
            renderer.reDrawBoard();
            soundManager.playDeathSound();
            dialogBackground("time");
        }
    }


    /**
     *
     */
    public void createLevelPanel() {

        int n = !showInstructions ? 2 : 1;

        levelPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Load your background image
                ImageIcon backgroundImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("icons/shield" + n + ".png")));
                g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };

        // Set the background of levelPanel to be transparent
        levelPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();

        // Centered label at the first row
        if(!showInstructions){


            JLabel levelLabel = new JLabel(levelText);
            Font font = new Font("Arial", Font.BOLD, 20);
            levelLabel.setFont(font);

            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 2; // Span two columns
            gbc.insets = new Insets(0, 0, 5, 0); // Add some spacing
            levelPanel.add(levelLabel, gbc);

            JPanel sprite2 = new JPanel(new GridLayout(1, 2)); // Two cells below the label
            sprite2.setOpaque(false);

            ImageIcon imageIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("icons/0.png")));
            for (int i = 0; i < 3; i++) {
                // Create a small JPanel square which will contain the content of a Tile.
                JPanel cell = new JPanel();
                cell.setPreferredSize(new Dimension(tileSize / 2, tileSize / 2 + 10));

                if (i == 2) {
                    imageIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("icons/" + currentLevel + ".png")));
                }

                JLabel sprite = new JLabel();
                sprite.setIcon(imageIcon);

                cell.setOpaque(false);
                cell.add(sprite);
                sprite2.add(cell);
            }

            gbc.gridx = 0;
            gbc.gridy = 1; // Start from the second row
            gbc.gridwidth = 1; // Reset grid width to default
            gbc.insets = new Insets(0, 0, 0, 0); // Add some spacing
            levelPanel.add(sprite2, gbc);
        }

    }

    /**
     *
     */
    public void createTimePanel(){

        int n = !showInstructions ? 2 : 3;

        timePanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Load your background image
                ImageIcon backgroundImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("icons/shield" + n + ".png")));
                g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };

        timePanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        // Centered label at the first row

        if(!showInstructions) {
            JLabel timeLabel = new JLabel(timeText);
            Font font = new Font("Arial", Font.BOLD, 20);
            timeLabel.setFont(font);

            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 2; // Span two columns
            gbc.insets = new Insets(0, 0, 5, 0); // Add some spacing
            timePanel.add(timeLabel, gbc);

            JPanel sprite2 = new JPanel(new GridLayout(1, 2)); // Two cells below the label
            sprite2.setOpaque(false);

            ImageIcon imageIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("icons/0.png")));
            for (int i = 0; i < 3; i++) {
                // Create a small JPanel square which will contain the content of a Tile.
                JPanel cell = new JPanel();
                cell.setPreferredSize(new Dimension(tileSize / 2, tileSize / 2 + 10));
                cell.setBackground(Color.WHITE);

                if (i == 1) {
                    imageIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("icons/" + (timeLeft / 10) + ".png")));
                }

                if (i == 2) {
                    imageIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("icons/" + (timeLeft % 10) + ".png")));
                }

                JLabel sprite = new JLabel();
                sprite.setIcon(imageIcon);
                cell.add(sprite);
                cell.setOpaque(false);

                sprite2.add(cell);
            }

            gbc.gridx = 0;
            gbc.gridy = 1; // Start from the second row
            gbc.gridwidth = 1; // Reset grid width to default
            gbc.insets = new Insets(0, 0, 0, 0); // Add some spacing
            timePanel.add(sprite2, gbc);

            timePanel.setBackground(Color.WHITE);
        }

    }

    /**
     *
     */
    public void createChipsPanel(){

        int n = !showInstructions ? 2 : 4;

        chipsPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Load your background image
                ImageIcon backgroundImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("icons/shield" + n + ".png")));
                g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };

        chipsPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();

        if(!showInstructions) {
            // Centered label at the first row
            JLabel chipsLabel = new JLabel("Relics");
            Font font = new Font("Arial", Font.BOLD, 20);
            chipsLabel.setFont(font);

            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 2; // Span two columns
            gbc.insets = new Insets(0, 0, 5, 0); // Add some spacing
            chipsPanel.add(chipsLabel, gbc);

            JPanel sprite2 = new JPanel(new GridLayout(1, 2)); // Two cells below the label
            sprite2.setOpaque(false);

            ImageIcon imageIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("icons/0.png")));
            int z = (Domain.getInstance().getTreasure().size() - ch.getTreasure().size()) + (Domain.getInstance().getKeys().size() - ch.getKeys().size());
            for (int i = 0; i < 3; i++) {
                // Create a small JPanel square which will contain the content of a Tile.
                JPanel cell = new JPanel();
                cell.setPreferredSize(new Dimension(tileSize / 2, tileSize / 2 + 10));
                cell.setBackground(Color.WHITE);

                if (i == 1) {
                    imageIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("icons/" + (z / 10) + ".png")));
                }
                if (i == 2) {
                    imageIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("icons/" + (z % 10) + ".png")));
                }

                JLabel sprite = new JLabel();
                sprite.setIcon(imageIcon);
                cell.add(sprite);
                cell.setOpaque(false);

                sprite2.add(cell);
            }

            gbc.gridx = 0;
            gbc.gridy = 1; // Start from the second row
            gbc.gridwidth = 1; // Reset grid width to default
            gbc.insets = new Insets(0, 0, 0, 0); // Add some spacing
            chipsPanel.add(sprite2, gbc);

            chipsPanel.setBackground(Color.WHITE);
        }
    }

    /**
     * Track the inventory of the player in a 4x2 grid
     */
    public void createInventoryPanel() {
        inventoryPanel = new JPanel(new GridLayout(2, 4));
        inventoryPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        inventoryPanel.setOpaque(false);

        java.util.ArrayList<Entity> inven = new java.util.ArrayList<Entity>(ch.getKeys());
        inven.addAll(ch.getTreasure());

        for (int i = 0; i < 8; i++) {
            final Entity item;  // Declare item as final reference
            if (inven.size() > i) {
                item = inven.get(i);
            } else {
                item = null;  // Handle the case where inven.size() <= i
            }
            // Below basically makes a small JPanel square which will contain the content of a Tile.
            JPanel cell = new JPanel(new BorderLayout()) {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    // Load your background image

                    if (item instanceof Treasure) {
                        ImageIcon backgroundImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("icons/treasure.png")));
                        g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
                    } else if(item instanceof Key){
                        ImageIcon backgroundImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("icons/key" + ((Key) item).getColour().toString().toLowerCase() + ".png")));
                        g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
                    }else {
                        ImageIcon backgroundImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("icons/walltile3.png")));
                        g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
                    }
                }
            };
            cell.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            cell.setOpaque(false);
            inventoryPanel.add(cell);
        }
    }


    /**
     * Using all the sideBar components to comprise the full SideBar
     */
    public void createSideBar(){
        // Create the header panels
        sidePanel = new JPanel(new GridLayout(4, 1)); // 4 rows, 1 column
        sidePanel.setPreferredSize(new Dimension(300, mainFrame.getHeight()));
        sidePanel.setOpaque(false);

        // Call the methods that create the SideBar Components
        createLevelPanel();
        sidePanel.add(levelPanel);
        createTimePanel();
        sidePanel.add(timePanel);
        createChipsPanel();
        sidePanel.add(chipsPanel);
        createInventoryPanel();
        sidePanel.add(inventoryPanel);

        // Add the sidePanel components to SidePanel
        backPanel.add(sidePanel, BorderLayout.EAST);
        // Add the full sidePanel to the back panel
    }

    /**
     * Important Method to Refresh the GUI for movement and sidePanel info
     */
    public void redrawGUI() {
        // this is the only method to redraw the board
        mapPanel.removeAll();
        sidePanel.removeAll();

        // Recall the methods that create the SideBar Components
        createLevelPanel();
        sidePanel.add(levelPanel);
        createTimePanel();
        sidePanel.add(timePanel);
        createChipsPanel();
        sidePanel.add(chipsPanel);
        createInventoryPanel();
        sidePanel.add(inventoryPanel);

        // this is needed to confirm the changes
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    /**
     * Getter method for Fuzz to get the [collected keys, total keys]
     * @return an int Array of basic Key info
     */
    public int[] keyInfo(){
        int x = ch.getKeys().size();
        int y = Domain.getInstance().getKeys().size();

        return new int[]{x, y};
    }
    /**
     * Getter method for Fuzz to get the [collected treasure, total treasure]
     * @return an int Array of basic treasure info
     */
    public int[] treasureInfo(){
        int x = ch.getTreasure().size();
        int y = Domain.getInstance().getTreasure().size();

        return new int[]{x, y};
    }
    /**
     * Getter method for Fuzz to get the [collected treasure, total treasure]
     * @return an int Array of basic treasure info
     */
    public int[] doorInfo(){
        int x = ch.getTreasure().size();
        int y = Domain.getInstance().getTreasure().size();

        return new int[]{x, y};
    }
    /**
     * Getter method for Fuzz to get the [x position, y position]
     * @return an int Array of basic coordinate info
     */
    public int[] playerCoords(){
        int x = ch.getLocation().x();
        int y = ch.getLocation().y();

        return new int[]{x, y};
    }
    /**
     * Getter method for Fuzz to get the Player
     * @return the Player
     */
    public Player getPlayer(){
        return ch;
    }
    public int getTime(){
        return timeLeft;
    }
    public void setTime(int time){
        timeLeft = time;
    }
}