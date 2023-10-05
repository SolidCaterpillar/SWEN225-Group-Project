package nz.ac.wgtn.swen225.lc.domain;

import nz.ac.wgtn.swen225.lc.domain.Entity.Player;

public class testMove {

    public testMove(){}

    //tester method for player movement on the board
    public static void main(String[] args){
        Domain domain = new Domain();
        domain.picKLevel(LevelE.LEVEL_ONE);

        Player ch = domain.getPlayer();
    }
}
