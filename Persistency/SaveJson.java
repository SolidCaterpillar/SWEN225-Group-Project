package Persistency;

import org.json.JSONObject;
import Domain.Board;
import Persistency.ParseJson.Entites;
public class SaveJson {
    
    public static JSONObject saveAsJson(Board board){
        JSONObject json = new JSONObject();
        JSONObject dimension = new JSONObject();
        
        //saves the dimension of the board
        dimension.put("width", 20);
        dimension.put("length", 20);

        json.put("dimension", dimension);

        return json;
    }

    public static JSONObject saveEntites(Entites entites){
        JSONObject ents = new JSONObject();

        return null;
    }

        public static JSONObject saveTiles(){
        JSONObject tiles = new JSONObject();
        return null;
    }
}
