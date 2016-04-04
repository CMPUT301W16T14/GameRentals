package t14.com.GameRentals;

import java.io.Serializable;
import java.util.ArrayList;

/** This class returns a list of Game objects.
 * It is used for myGames list, borrowed list, and bid list where users can see the games and interact with them.
 * @see MyItemsFragment
 * @see BorrowFragment
 * @see BidsFragment
 */
public class GameList implements Serializable{
    private ArrayList<Game> list;

    public GameList() {
        list = new ArrayList<Game>();
    }

    /**
     *
     * @return An ArrayList of Games
     * @see Game
     */
    public ArrayList<Game> getList() {
        return list;
    }

    /**
     *
     * @param list
     */
    public void setList(ArrayList<Game> list) {
        this.list = list;
    }

    /**
     * Add's a game to the list.
     * @param game
     * @see Game
     */
    public void addGame(Game game){
        list.add(game);
    }

    /**
     *
     * @param copy
     */
    public void copyList(GameList copy){
        list.clear();
        for(int i = 0; i < copy.getList().size(); i++){
            list.add(copy.getList().get(i));
        }
    }

    /**
     *
     * @param game
     * @return boolean
     */
    public boolean hasGame(Game game){
        return list.contains(game);
    }

    /**
     *
     * @param index
     * @return
     */
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

    /**
     *
     * @param copy
     */
    public void copyRefListToGames(GameRefList copy){
        list.clear();
        if (copy.getSize() > 0)
            for(int i = 0; i < copy.getSize(); i++){
                list.add(copy.getGame(i));
            }
    }

    /**
     *
     * @param game
     */
    public void removeGame(Game game){
        if(list.contains(game)){
            list.remove(game);
        }
    }
    //Delete all games from list
    public void clearList(){
        list.clear();
    }

    /**
     *
     * @return int
     */
    public int getSize(){
        return list.size();
    }
}
