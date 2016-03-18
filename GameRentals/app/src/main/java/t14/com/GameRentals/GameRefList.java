package t14.com.GameRentals;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by cjresler on 2016-03-18.
 */
public class GameRefList implements Serializable {
    private ArrayList<String> list;
    Game test;

    public GameRefList() {
        list = new ArrayList<String>();
    }

    public ArrayList<String> getList() {
        return list;
    }

    public void setList(ArrayList<String> list) {
        this.list = list;
    }

    public void addGame(String game){
        list.add(game);
    }

    public void addGame(Game game){
        list.add(game.getGameID());
    }

    public void copyList(GameRefList copy){
        list.clear();
        for(int i = 0; i < copy.getList().size(); i++){
            list.add(copy.getList().get(i));
        }

    }

    public boolean hasGame(String game){
        return list.contains(game);
    }

    public Game getGame(int index){
        //return list.get(index);
        //TODO:Use elastic search to get game
        Game game = new Game("", "", null);
        String id = list.get(index);
        ElasticsearchGameController.GetGameTask getGameTask = new ElasticsearchGameController.GetGameTask();
        getGameTask.execute(id);
        try{
            game = (getGameTask.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return game;
    }

    public Game getGame (String gameID){
        for (int i = 0;i < getSize(); i++) {
            if (getGame(i).getGameID().equals(gameID)) {
                return getGame(i);
            }
        }
        return null;
    }

    public void removeGame(String game){
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
