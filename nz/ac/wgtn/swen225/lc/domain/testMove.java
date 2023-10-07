package nz.ac.wgtn.swen225.lc.domain;

import nz.ac.wgtn.swen225.lc.domain.Entity.Enemy;
import nz.ac.wgtn.swen225.lc.domain.Entity.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class testMove {

    public testMove(){}

    //tester method for player movement on the board
    public static void main(String[] args){
        Domain domain = new Domain();

        domain.picKLevel(LevelE.LEVEL_ONE);

        Player ch = domain.getPlayer();

        Board curr = domain.getBoard();


        //add enemy for test
        Coord enemyCoord = new Coord(5,5);
        Enemy enemy =  new Enemy(enemyCoord);
        curr.addEntityToGame(enemy,enemyCoord);

        //Here put other tiles for tests
        Coord ExitTile =  new Coord(0,0);

        //Here put other tiles for LockedDoor
        Coord LockedDoor = new Coord(0,0);



        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            while (true) {
                Domain.StateClass.isPlayerDead();
                ArrayList<Enemy> TEST = new ArrayList<>(List.of(enemy));
                Domain.StateClass.checkGameState(ch, curr, TEST); //DEATH CHECK NOT WORKING RIGHT NOW


                drawB(curr, ch); //call drawB method to display the board

                // Read user input
                System.out.print("Enter WASD to move (Q to quit): ");
                String input = reader.readLine();

                // Check for user input
                if (input != null && !input.isEmpty()) {
                    char move = input.charAt(0);
                    switch (move) {
                        case 'W': ch.checkMove('w');
                        case 'w': ch.checkMove('w');
                            break;
                        case 'A': ch.checkMove('a');
                        case 'a': ch.checkMove('a');

                            break;
                        case 'S': ch.checkMove('s');
                        case 's': ch.checkMove('s');

                            break;
                        case 'D': ch.checkMove('d');
                        case 'd': ch.checkMove('d');

                            break;
                        default:
                            System.out.println("Invalid input. Please use WASD to move or Q to quit.");
                            break;
                    }
                }
                enemy.updateEnemy(); //TEST ENEMY MOVEMENT WORKS!
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static void drawB(Board curr, Player player){
        Board.buildString(curr.getBoard());
        System.out.println("Treasures: " + player.getTresDisplay());
    }

}
