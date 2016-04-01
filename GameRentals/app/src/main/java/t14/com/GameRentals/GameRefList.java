package t14.com.GameRentals;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by cjresler on 2016-03-18.
 */
public class GameRefList implements Serializable {
    private ArrayList<String> gameIDs;

    public GameRefList() {
        gameIDs = new ArrayList<String>();
    }

    public ArrayList<String> getList() {
        return gameIDs;
    }

    public void setList(ArrayList<String> list) {
        this.gameIDs = list;
    }

    public void addGame(String gameID){
        gameIDs.add(gameID);
    }

    public void addGame(Game game){
        gameIDs.add(game.getGameID());
    }

    public void copyList(GameRefList copy){
        gameIDs.clear();
        for(int i = 0; i < copy.getList().size(); i++){
            gameIDs.add(copy.getList().get(i));
        }

    }

    public boolean hasGame(String game){
        return gameIDs.contains(game);
    }

    public Game getGame(int index){
        //return list.get(index);
        //TODO:Use elastic search to get game
        Game game = new Game("", "", null);
        String id = gameIDs.get(index);
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

        if(gameIDs.contains(game)){
            gameIDs.remove(game);
        }
    }
    //Delete all games from list
    public void clearList(){
        gameIDs.clear();
    }

    public int getSize(){
        return gameIDs.size();
    }
}
