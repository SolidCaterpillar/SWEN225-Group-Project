
package Persistency;


public class Persistency{

    private static Level loadLevel(String path){



        return new Level();
    }

    public static void saveLevel(Level curLevel, String filename){

    }

    public static Level loadLevel1(){

        return loadLevel("../level/level1.json");

    }

    public static Level loadLevel2(){

        return loadLevel("../level/level2.json");
    }
    
    public void readJson(){

    }

}

