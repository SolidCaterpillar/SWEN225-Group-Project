package Domain;
import javax.swing.*;

import Domain.Tile.*;

import java.awt.*;

public class Board {

    int level;
    Tile[][] board = new Tile[26][26];

    public Board(int lev, Tile[][] boardObj){
        if(lev > 3 || lev < 0) throw new IllegalArgumentException("Invalid levels only 0-2"); //ONLY ALLOW LEVELS 0-3
        level = lev;

        //Test board constructor
        if(level == 0) {
            //Test scenario
            for (int i = 0; i < 26; i++) {
                for (int j = 0; j < 26; j++) {

                    JPanel cell = new JPanel();
                    cell.setPreferredSize(new Dimension(25, 25));
                    cell.setBorder(BorderFactory.createLineBorder(Color.BLACK));

                    board[i][j] = new FreeTile(new Coord(i, j));

                }
            }
        }

        else{
            board = boardObj;
        }
        //setupBoard(board);
    }

    // public void setupBoard(Tile[][] brd){
    //     //Nothing here, this is extended in Rendering

    //     //sup


    //     //Test scenario
    //     JFrame frame = new JFrame("Board");
    //         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    //         JPanel boardPanel = new JPanel(new GridLayout(26, 26));

    //         //board = new Tile[26][26];

    //         for (int i = 0; i < 26; i++) {
    //             for (int j = 0; j < 26; j++) {
    //                 Tile currentTile = brd[i][j];
    //                 JPanel cell = new JPanel();
    //                 cell.setPreferredSize(new Dimension(25, 25));
    //                 cell.setBorder(BorderFactory.createLineBorder(Color.BLACK));


    //                 if (currentTile.getTileType() == TileType.FREE) {
    //                     cell.setBackground(Color.WHITE);
    //                 } else {
    //                     cell.setBackground(Color.GRAY);
    //                 }

    //                 boardPanel.add(cell);
    //             }
    //         }

    //         frame.add(boardPanel);
    //         frame.pack();
    //         frame.setVisible(true);
    //     }



    public void updateCamera(){
        //Shift row or column +- in direction of player movement

    }

    public Tile[][] getBoard(){ //For returning game
        Tile[][] copy = board;
        return copy; //Unecessary copy was playing around
    }

    public String toString(){
        String b = "";
        for(int x = 0; x < board.length; x++){
            for(int y = 0; y < board[0].length; y++){
                b += board[x][y];
            }
            b += "\n";
        }
        return b;
    }
}
