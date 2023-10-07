package nz.ac.wgtn.swen225.lc.domain;
import javax.swing.*;

import nz.ac.wgtn.swen225.lc.domain.Tile.*;
import nz.ac.wgtn.swen225.lc.domain.*;
import java.awt.*;
//sup
public class Board {

    int level;
    static Tile[][] board = new Tile[20][20];

    static final int tileSize = 122;


    public Board(int lev, Tile[][] boardObj){
        if(lev > 3 || lev < 0) throw new IllegalArgumentException("Invalid levels only 0-2"); //ONLY ALLOW LEVELS 0-3
        level = lev;

        //Test board constructor
        if(level == 0) {

        }

        else{
            board = boardObj;
        }
        //setupBoard(board);
    }

    public void setupBoard(Tile[][] brd){
    //     Nothing here, this is extended in Rendering




         //Test scenario
         JFrame frame = new JFrame("Board");
             frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

             JPanel boardPanel = new JPanel(new GridLayout(26, 26));

             //board = new Tile[26][26];

             for (int i = 0; i < 20; i++) {
                 for (int j = 0; j < 20; j++) {
                     Tile currentTile = brd[i][j];
                     JPanel cell = new JPanel();
                     cell.setPreferredSize(new Dimension(25, 25));
                     cell.setBorder(BorderFactory.createLineBorder(Color.BLACK));


                     if (currentTile instanceof FreeTile) {
                         cell.setBackground(Color.WHITE);
                     } else {
                         cell.setBackground(Color.GRAY);
                     }

                     boardPanel.add(cell);
                 }
             }

             frame.add(boardPanel);
             frame.pack();
             frame.setVisible(true);
         }



    public void updateCamera(){
        //Shift row or column +- in direction of player movement

    }

    public Tile[][] getBoard(){ //For returning game
        Tile[][] copy = board;
        return copy; //Unecessary copy was playing around
    }

    public static int getDim(){
        return board.length;
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



    //Tile at location
    public Tile getTileAt(int x, int y){
        return board[x][y];
    }

    public int getSize(){
        return tileSize;
    }




    public static boolean checkInBound(Coord check) {
        int x = check.x();
        int y = check.y();
        int dim = board.length;

        return x >= 0 && x < dim && y >= 0 && y < dim;
    }


    //String tester
    public static void buildString(Tile[][] stringBoard) {
        StringBuilder ret = new StringBuilder();
        for (int i = 0; i < stringBoard.length; i++) {
            for (int j = 0; j < stringBoard[0].length; j++) {
                ret.append(stringBoard[i][j].toString());
                if (j == stringBoard[0].length - 1) {
                    ret.append("\n");
                }
            }
        }
        System.out.println(ret.toString());
    }


    public Tile getTileAtLocation(Coord location) {
        int x = location.x();
        int y = location.y();

        if (checkInBound(location)) {
            return board[x][y];
        } else {
            throw new IllegalArgumentException("Invalid location: " + location);
        }
    }


}
