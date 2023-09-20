import java.util.ArrayList;


class Level{

    private ArrayList<Tile> tiles;
    private ArrayList<Item> items;
    private ArrayList<Entity> Entites;

    public Level(){
        tiles = new ArrayList<>();
        Items = new ArrayList<>();
        Entites = new ArrayList<>();
    }

    public ArrayList<Tile> getTiles(){
        return list;
    }

    public ArrayList<Item> getItems(){
        return item;
    }

    public ArrayList<Entity> getEntites(){
        return entites;
    }


}
