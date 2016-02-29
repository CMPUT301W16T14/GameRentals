package t14.com.GameRentals;

/**
 * Created by cjresler on 2016-02-28.
 */
public class UserController {
    private User user;
    private GameList gameList;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public GameList getGameList() {
        return gameList;
    }

    public void addMyGame(){
        //TODO
    }

    public void deleteGame(){
        //TODO
    }

    public void addBorrowedGame(){
        //TODO
    }

    public void addBiddedGame(){
        //TODO
    }

    public Game getGame(GameList list, int index){
        return list.getGame(index);
    }

    public void deleteBiddedGame(){
        //TODO
    }

    public void searchGamesByOwner(){
        //TODO
    }
}
