package t14.com.GameRentals;

import java.util.concurrent.ExecutionException;

/**
 * Created by cjresler on 2016-02-28.
 */
public class UserController {
    private static User currentUser;

    public UserController(User user) {
        setUser(user);
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setUser(User user) {
        currentUser = user;
    }

    //Given a GameList, return all games with available status
    public static GameRefList getAvailable(GameRefList games){
        GameRefList gameList = new GameRefList();
        for(int i = 0; i < games.getSize(); i++){
            if(games.getGame(i).getStatus() == GameController.STATUS_AVAILABLE){
                gameList.addGame(games.getGame(i).getGameID());
            }
        }
        return gameList;
    }

    //Given a GameList, return all games with borrowed status
    public static GameRefList getBorrowed(GameRefList games){
        GameRefList gameList = new GameRefList();
        for(int i = 0; i < games.getSize(); i++){
            if(games.getGame(i).getStatus() == GameController.STATUS_BORROWED){
                gameList.addGame(games.getGame(i).getGameID());
            }
        }
        return gameList;
    }

    //Given a GameList, return all games with bidded status
    public static GameRefList getBidded(GameRefList games){
        GameRefList gameList = new GameRefList();
        for(int i = 0; i < games.getSize(); i++){
            if(games.getGame(i).getStatus() == GameController.STATUS_BIDDED){
                gameList.addGame(games.getGame(i).getGameID());
            }
        }
        return gameList;
    }

    public GameRefList getGameList() {
        return currentUser.getMyGames();
    }

    public static void addMyGame(Game game){
        //currentUser.getMyGames().addGame(game);
        //User tempUser = currentUser;

        ElasticsearchGameController.AddGameTask addGameTask = new ElasticsearchGameController.AddGameTask();
        addGameTask.execute(game);

        ElasticSearchUsersController.EditUserTask ese = new ElasticSearchUsersController.EditUserTask();
        ese.execute(currentUser);
    }

    public void deleteGame(){
        //TODO
    }

    public void addBorrowedGame(){
        //TODO
    }

    /*public static void addBiddedGame(Game game){
        currentUser.getBiddedItems().addGame(game);
        ElasticSearchUsersController.EditUserTask ese = new ElasticSearchUsersController.EditUserTask();
        ese.execute(currentUser);
    }

    public Game getGame(GameList list, int index){
        return list.getGame(index);
    }

    public static void deleteBiddedGame(Game game){
        currentUser.getBiddedItems().removeGame(game);
        ElasticSearchUsersController.EditUserTask ese = new ElasticSearchUsersController.EditUserTask();
        ese.execute(currentUser);
    }*/

    public static User getUser(String username){
        User loadedUser = new User(null, null, null);
        ElasticSearchUsersController.GetUserTask esg = new ElasticSearchUsersController.GetUserTask();

        esg.execute(username);

        try{
            loadedUser = (esg.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return loadedUser;
    }
}
