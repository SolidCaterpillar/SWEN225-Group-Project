
package Persistency;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.StringBuilder;
import plugin.main.*;
public class Persistency{

    private static Level loadLevel(String path){
        JSONArray json = getJson(path);
        return new Level();
    }

    public static void saveLevel(Level curLevel, String filename){

    }

    public static Level loadLevel1(){

        return loadLevel("File.txt");

    }

    public static Level loadLevel2(){

        return loadLevel("File.txt");
    }
    
    private static JSONArray getJson(String path){
        StringBuilder json = new StringBuilder();
        try{
            File jsonFile = new File("File.txt");
            
            Scanner sc = new Scanner(jsonFile);
            

            while(sc.hasNext()){
                System.out.println(sc.next());
                //json.append(sc.next()); 
            }
        }catch (FileNotFoundException e){
            System.out.println("not Found");
        }
        System.out.println(json.toString());
        return new JSONArray();
    }  

}

