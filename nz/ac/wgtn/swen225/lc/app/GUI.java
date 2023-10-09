/*package nz.ac.wgtn.swen225.lc.app;
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
import java.util.Enumeration;
import javax.imageio.ImageIO;
import java.io.*;

public class GUI {

    private int currentLevel = 1; // Current game level
    private int treasuresRemaining; // Number of treasures remaining
    private int maxTime = 60;

    //private Recorder rec;
    private int timeLeft = maxTime; // Time left for the current level
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



    private Level play;
    private Tile[][] maze;
    private Player ch;
    private Domain d;
    private GameRenderer renderer;
    private GameCanvas canvas;
    private Recorder rec;



    private String levelText = "Level";
    private String timeText = "Time";
    private String chipsText = "Chips";



    private final int tileSize = 68; // Adjust this size as needed
    private final int numRows = 9;
    private final int numCols = 9;
    private boolean gamePaused = false; // Flag to track if the game is paused

    private boolean showInstructions = false;


    public GUI() {

        // Frame is the overall GUI JFrame that contains pannels.
        mainFrame = new JFrame("Larry Croftâ€™s Adventures");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setResizable(false);

        //rec = new Recorder();


        play = Persistency.loadLevel1();
        maze = play.board().getBoard() ;


        d = new Domain();
        d.picKLevel(LevelE.LEVEL_ONE);
        ch = d.getPlayer();

        renderer = new GameRenderer(maze, ch, d);
        canvas = new GameCanvas(renderer);
        rec = new Recorder();




        drawBoard();
        createSideBar();
        createMenuBar();

        resetTimer();

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
                            int choice = JOptionPane.showConfirmDialog(mainFrame, "Custom JOptionPane: wanna leave bro?", "Exit Confirmation", JOptionPane.YES_NO_OPTION);
                            if (choice == JOptionPane.YES_OPTION) {
                                System.exit(0);
                            }
                            break;
                        case KeyEvent.VK_S:
                            // CTRL-S: Save the game state
                            // Implement game state save logic
                            chipsText = "CTRL-S";
                            redrawGUI();
                            loadFile();
                            break;
                        case KeyEvent.VK_R:
                            // CTRL-R: Resume a saved game
                            // Implement game state loading logic using a file selector
                            chipsText = "CTRL-R";
                            redrawGUI();
                            writeFile();
                            break;
                        case KeyEvent.VK_1:
                            // CTRL-1: Start a new game at level 1
                            // Implement logic to start a new game at level 1
                            chipsText = "CTRL-1";
                            currentLevel = 1;
                            timeLeft = maxTime;
                            if (timer.isRunning()) {
                                timer.stop();
                            }
                            resetTimer();
                            redrawGUI();
                            break;
                        case KeyEvent.VK_2:
                            // CTRL-2: Start a new game at level 2
                            // Implement logic to start a new game at level 2
                            chipsText = "CTRL-2";
                            currentLevel = 2;
                            timeLeft = maxTime;
                            if (timer.isRunning()) {
                                timer.stop();
                            }
                            resetTimer();
                            redrawGUI();
                            break;
                        case KeyEvent.VK_E:
                            // CTRL-2: Start a new game at level 2
                            // Implement logic to start a new game at level 2
                            showInstructions = showInstructions ? false : true;
                            gamePaused = showInstructions;
                            redrawGUI();
                            break;
                    }
                } else {
                    // Handle regular arrow key movements
                    if(!gamePaused){
                        switch (keyCode) {
                            case KeyEvent.VK_ESCAPE:
                                chipsText = "Escape";
                                gamePaused = true;
                                levelText = "Paused";
                                redrawGUI();
                                break;
                            case KeyEvent.VK_UP:
                                // Handle UP arrow key press (e.g., move up)
                                chipsText = "UP";
                                ch.checkMove(e.getKeyChar());
                                redrawGUI();
                                break;
                            case KeyEvent.VK_LEFT:
                                // Handle LEFT arrow key press (e.g., move left)
                                chipsText = "LEFT";
                                ch.checkMove(e.getKeyChar());
                                redrawGUI();
                                break;
                            case KeyEvent.VK_DOWN:
                                // Handle DOWN arrow key press (e.g., move down)
                                chipsText = "DOWN";
                                ch.checkMove(e.getKeyChar());
                                redrawGUI();
                                break;
                            case KeyEvent.VK_RIGHT:
                                // Handle RIGHT arrow key press (e.g., move right)
                                chipsText = "RIGHT";
                                ch.checkMove(e.getKeyChar());
                                redrawGUI();
                                break;
                            case KeyEvent.VK_W:
                                // Handle UP arrow key press (e.g., move up)
                                chipsText = "UP";
<<<<<<< HEAD
                                ch.checkMove(e);
=======
                                ch.checkMove(e.getKeyChar());
>>>>>>> 2f8797a517d3aaf20579dc457dc3dbcf6940393c
                                redrawGUI();
                                break;
                            case KeyEvent.VK_A:
                                // Handle LEFT arrow key press (e.g., move left)
                                chipsText = "LEFT";
                                ch.checkMove(e.getKeyChar());
                                redrawGUI();
                                break;
                            case KeyEvent.VK_S:
                                // Handle DOWN arrow key press (e.g., move down)
                                chipsText = "DOWN";
                                ch.checkMove(e.getKeyChar());
                                redrawGUI();
                                break;
                            case KeyEvent.VK_D:
                                // Handle RIGHT arrow key press (e.g., move right)
                                chipsText = "RIGHT";
                                ch.checkMove(e.getKeyChar());
                                redrawGUI();
                                break;
                        }
                    }else{
                        switch (keyCode) {
                            case KeyEvent.VK_ESCAPE:
                                chipsText = "Escape";
                                gamePaused = showInstructions;
                                levelText = "Level";
                                redrawGUI();
                                break;
                        }
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

    public int getTime(){
        return timeLeft;
    }




    public void loadFile() {    //static

        // Create a file chooser
        String currentDirectory = System.getProperty("user.dir");
        JFileChooser fileChooser = new JFileChooser(currentDirectory);
        int returnValue = fileChooser.showOpenDialog(null);

        // Check if the user selected a file
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            // Get the selected file
            File selectedFile = fileChooser.getSelectedFile();
            // Read the contents of the file
            try {
                BufferedReader reader = new BufferedReader(new FileReader(selectedFile));
                String line;
                timeText = selectedFile.getName();
                System.out.println("Contents of " + selectedFile.getName() + ":");
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
                reader.close();


            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Error reading the file.");
            }
        } else {
            System.out.println("No file selected.");
        }

    }


    public void writeFile() {   // static

        // Create a file chooser
        String currentDirectory = System.getProperty("user.dir");
        JFileChooser fileChooser = new JFileChooser(currentDirectory);
        int returnValue = fileChooser.showSaveDialog(null); // Use Save dialog to select a file to write to

        // Check if the user selected a file
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            // Get the selected file
            File selectedFile = fileChooser.getSelectedFile();
            // Write "Hello, World!" to the file
            try {
                FileWriter writer = new FileWriter(selectedFile);
                writer.write("Hello, World!");
                writer.close();
                System.out.println("Successfully wrote 'Hello, World!' to " + selectedFile.getName());
                timeText = selectedFile.getName();
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Error writing to the file.");
            }
        } else {
            System.out.println("No file selected.");
        }
    }

    public void resetTimer(){
        timer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!gamePaused){
                    decrementTime();
                    redrawGUI();
                    Domain.StateClass.checkGameState();
                    for(Enemy enemy : Domain.getEnemies()){
                        enemy.updateEnemy();
                    }
                }
            }
        });
        timer.start();
    }

    public void drawBoard() {
        backPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Load your background image
                ImageIcon backgroundImage  = new ImageIcon(getClass().getResource("icons/background.png"));
                g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };

        backPanel.setBorder(BorderFactory.createEmptyBorder(60, 150, 60, 150));
        backPanel.setPreferredSize(new Dimension(1280, 720));

        mapPanel = canvas;
        mapPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        mapPanel.setOpaque(false);

        // This method will initialize the positions of specific Tiles. Use this to link to Tile, Estate, and Player
        //createTiles();

        backPanel.add(mapPanel, BorderLayout.CENTER);
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
        JMenuItem resumeMenuItem = new JMenuItem("Resume");
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        JMenuItem saveMenuItem = new JMenuItem("Save");
        JMenuItem replayMenuItem = new JMenuItem("Replay");
        JMenuItem loadMenuItem = new JMenuItem("Load");
        JMenuItem level1MenuItem = new JMenuItem("Level 1");
        JMenuItem level2MenuItem = new JMenuItem("Level 2");
        JMenuItem instructionsMenuItem = new JMenuItem("Game Rules");

        menu1.add(pauseMenuItem);
        menu1.add(resumeMenuItem);
        menu1.add(exitMenuItem);
        menu2.add(saveMenuItem);
        menu2.add(loadMenuItem);
        menu2.add(replayMenuItem);
        menu3.add(level1MenuItem);
        menu3.add(level2MenuItem);
        menu4.add(instructionsMenuItem);





        pauseMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gamePaused = true;
                levelText = "Paused";
                redrawGUI();
            }
        });

        resumeMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gamePaused = false;
                levelText = "Level";
                redrawGUI();
            }
        });

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

        loadMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadFile();
            }
        });

        saveMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                writeFile();
            }
        });

        level1MenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentLevel = 1;
                timeLeft = maxTime;
                if (timer.isRunning()) {
                    timer.stop();
                }
                resetTimer();
                redrawGUI();
            }
        });

        level2MenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentLevel = 2;
                timeLeft = maxTime;
                if (timer.isRunning()) {
                    timer.stop();
                }
                resetTimer();
                redrawGUI();
            }
        });

        instructionsMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showInstructions = showInstructions ? false : true;
                gamePaused = showInstructions;
                redrawGUI();
            }
        });
    }

    public void decrementTime(){
        timeLeft --;
        if(timeLeft < 1){
            timer.stop();
            redrawGUI();
            //JOptionPane.showMessageDialog(null, "Replace this with a function to stop movement", "Information", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void createTiles() {

        ImageIcon imageIcon = new ImageIcon(getClass().getResource("icons/walltile3.png"));

        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {

                // below basically makes a small JPanel square which will contain the content of a Tile.
                JPanel cell = new JPanel(new BorderLayout()) {
                    @Override
                    protected void paintComponent(Graphics g) {
                        super.paintComponent(g);
                        // Load your background image
                        ImageIcon backgroundImage  = new ImageIcon(getClass().getResource("icons/walltile3.png"));
                        g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
                    }
                };
                cell.setPreferredSize(new Dimension(tileSize, tileSize));

                //JLabel sprite = new JLabel();
                //sprite.setIcon(imageIcon);

                //cell.add(sprite);
                mapPanel.add(cell);

            }
        }
    }


    public void createLevelPanel() {

        int n = !showInstructions ? 2 : 1;

        levelPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Load your background image
                ImageIcon backgroundImage = new ImageIcon(getClass().getResource("icons/shield" + n + ".png"));
                g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };

        // Set the background of levelPanel to be transparent
        levelPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();

        // Centered label at the first row
        if(!showInstructions){


            levelLabel = new JLabel(levelText);
            Font font = new Font("Arial", Font.BOLD, 20);
            levelLabel.setFont(font);

            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 2; // Span two columns
            gbc.insets = new Insets(0, 0, 5, 0); // Add some spacing
            levelPanel.add(levelLabel, gbc);

            JPanel sprite2 = new JPanel(new GridLayout(1, 2)); // Two cells below the label
            sprite2.setOpaque(false);

            ImageIcon imageIcon = new ImageIcon(getClass().getResource("icons/0.png"));
            for (int i = 0; i < 3; i++) {
                // Create a small JPanel square which will contain the content of a Tile.
                JPanel cell = new JPanel();
                cell.setPreferredSize(new Dimension(tileSize / 2, tileSize / 2 + 10));

                if (i == 2) {
                    imageIcon = new ImageIcon(getClass().getResource("icons/" + currentLevel + ".png"));
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

    public void createTimePanel(){

        int n = !showInstructions ? 2 : 3;

        timePanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Load your background image
                ImageIcon backgroundImage = new ImageIcon(getClass().getResource("icons/shield" + n + ".png"));
                g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };

        timePanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        // Centered label at the first row

        if(!showInstructions) {
            timeLabel = new JLabel(timeText);
            Font font = new Font("Arial", Font.BOLD, 20);
            timeLabel.setFont(font);

            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 2; // Span two columns
            gbc.insets = new Insets(0, 0, 5, 0); // Add some spacing
            timePanel.add(timeLabel, gbc);

            JPanel sprite2 = new JPanel(new GridLayout(1, 2)); // Two cells below the label
            sprite2.setOpaque(false);

            ImageIcon imageIcon = new ImageIcon(getClass().getResource("icons/0.png"));
            for (int i = 0; i < 3; i++) {
                // Create a small JPanel square which will contain the content of a Tile.
                JPanel cell = new JPanel();
                cell.setPreferredSize(new Dimension(tileSize / 2, tileSize / 2 + 10));
                cell.setBackground(Color.WHITE);

                if (i == 1) {
                    imageIcon = new ImageIcon(getClass().getResource("icons/" + (timeLeft / 10) + ".png"));
                }

                if (i == 2) {
                    imageIcon = new ImageIcon(getClass().getResource("icons/" + (timeLeft % 10) + ".png"));
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

    public void createChipsPanel(){

        int n = !showInstructions ? 2 : 4;

        chipsPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Load your background image
                ImageIcon backgroundImage = new ImageIcon(getClass().getResource("icons/shield" + n + ".png"));
                g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };

        chipsPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();

        if(!showInstructions) {
            // Centered label at the first row
            chipsLabel = new JLabel(chipsText);
            Font font = new Font("Arial", Font.BOLD, 20);
            chipsLabel.setFont(font);

            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 2; // Span two columns
            gbc.insets = new Insets(0, 0, 5, 0); // Add some spacing
            chipsPanel.add(chipsLabel, gbc);

            JPanel sprite2 = new JPanel(new GridLayout(1, 2)); // Two cells below the label
            sprite2.setOpaque(false);

            ImageIcon imageIcon = new ImageIcon(getClass().getResource("icons/0.png"));
            for (int i = 0; i < 3; i++) {
                // Create a small JPanel square which will contain the content of a Tile.
                JPanel cell = new JPanel();
                cell.setPreferredSize(new Dimension(tileSize / 2, tileSize / 2 + 10));
                cell.setBackground(Color.WHITE);

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

    public void createinventoryPanel(){
        inventoryPanel = new JPanel(new GridLayout(2, 4));

        inventoryPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        inventoryPanel.setOpaque(false);

        for (int i = 0; i < 8; i++) {
            // below basically makes a small JPanel square which will contain the content of a Tile.
            int num = i > 3 ? (i % 4) + 1 : ((3 - i) % 4) + 1;
            JPanel cell = new JPanel(new BorderLayout()) {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    // Load your background image

                    ImageIcon backgroundImage  = new ImageIcon(getClass().getResource("icons/key" + num + ".png"));
                    g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
                }
            };
            cell.setOpaque(false);
            inventoryPanel.add(cell);
        }
    }

    public void createSideBar(){
        // Create the header panels
        sidePanel = new JPanel(new GridLayout(4, 1)); // 4 rows, 1 column
        sidePanel.setPreferredSize(new Dimension(300, mainFrame.getHeight()));
        sidePanel.setOpaque(false);

        createLevelPanel();
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
        //createTiles();

        // this is the method to redraw the side panel
        sidePanel.removeAll();
        createLevelPanel();
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


        rec.setRecord(currentLevel, timeLeft, maze);
        //try{
            //rec.saveAsFile("game_state.json");
        //}catch(IOException e){}

    }


    public void drawInstructions() {

        // this is the only method to redraw the board
        mapPanel.removeAll();
        //createTiles();

        // this is the method to redraw the side panel
        sidePanel.removeAll();

        // this is needed to confirm the changes
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    public int[] keyInfo(){
        int x = ch.getKeys().size();
        //int y = Domain.getKeys().size();
        int y = 5;

        int[] keyInfo = {x, y};
        return keyInfo;
    }

    public int[] treasureInfo(){
        int x = ch.getTreasure().size();
        int y = Domain.getTreasure().size();

        int[] treasureInfo = {x, y};
        return treasureInfo;
    }

    public int[] doorInfo(){
        int x = 0;
        int y = 0;

        int[] doorInfo = {x, y};
        return doorInfo;
    }

    public int[] playerCoords(){
        int x = ch.getLocation().x();
        int y = ch.getLocation().y();

        int[] coordinates = {x, y};
        return coordinates;
    }

    public Player getPlayer(){
        return ch;
    }

}

<<<<<<< HEAD
*/
=======
>>>>>>> 2f8797a517d3aaf20579dc457dc3dbcf6940393c
