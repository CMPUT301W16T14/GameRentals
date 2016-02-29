package t14.com.GameRentals;

import java.util.ArrayList;

/**
 * Created by cjresler on 2016-02-28.
 */
public class GameList {
    private ArrayList<Game> list;

    public ArrayList<Game> getList() {
        return list;
    }

    public void setList(ArrayList<Game> list) {
        this.list = list;
    }

    public void addGame(Game game){
        list.add(game);
    }

    public boolean hasGame(Game game){
        return list.contains(game);
    }

    public Game getGame(int index){
        return list.get(index);
    }

    public void removeGame(Game game){
        if(list.contains(game)){
            list.remove(game);
        }
    }
}
