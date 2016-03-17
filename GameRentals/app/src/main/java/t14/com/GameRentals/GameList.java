package t14.com.GameRentals;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by cjresler on 2016-02-28.
 */
public class GameList implements Serializable{
    private ArrayList<Game> list;

    public GameList() {
        list = new ArrayList<Game>();
    }

    public ArrayList<Game> getList() {
        return list;
    }

    public void setList(ArrayList<Game> list) {
        this.list = list;
    }

    public void addGame(Game game){
        list.add(game);
    }

    public void copyList(GameList copy){
        list.clear();
        for(int i = 0; i < copy.getList().size(); i++){
            list.add(copy.getList().get(i));
        }

    }

    public boolean hasGame(Game game){
        return list.contains(game);
    }

    public Game getGame(int index){
        return list.get(index);
    }

    public Game getGame (String gameName){
        for (int i = 0;i < getSize(); i++) {
            if (this.getGame(i).getGameName().equals(gameName))
                return getGame(i);
        }
        return null;
    }

    public void removeGame(Game game){
        if(list.contains(game)){
            list.remove(game);
        }
    }
    //Delete all games from list
    public void clearList(){
        list.clear();
    }

    public int getSize(){
        return list.size();
    }
}
