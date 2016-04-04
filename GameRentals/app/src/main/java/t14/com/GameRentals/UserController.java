package t14.com.GameRentals;

import java.util.concurrent.ExecutionException;

/** This class keeps track of the current user of the app through a static variable.
 *  Methods include getting a user from the server other than the current user, and sorting
 *  through a user's list of games to allow them to filter statuses of games they want to see.
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

    /** Takes in a gameRefList and returns a gameList that contains only the Available games
     *  that were in it.
     *
     * @param games
     * @return GameList
     */
    public static GameRefList getAvailable(GameRefList games){
        GameRefList gameList = new GameRefList();
        for(int i = 0; i < games.getSize(); i++){
            if(games.getGame(i).getStatus() == GameController.STATUS_AVAILABLE){
                gameList.addGame(games.getGame(i).getGameID());
            }
        }
        return gameList;
    }

    /** Takes in a gameRefList and returns a gameList that contains only the Borrowed games
     *  that were in it.
     *
     * @param games
     * @return GameList
     */
    public static GameRefList getBorrowed(GameRefList games){
        GameRefList gameList = new GameRefList();
        for(int i = 0; i < games.getSize(); i++){
            if(games.getGame(i).getStatus() == GameController.STATUS_BORROWED){
                gameList.addGame(games.getGame(i).getGameID());
            }
        }
        return gameList;
    }

    /** Takes in a gameRefList and returns a gameList that contains only the Bidded games
     *  that were in it.
     *
     * @param games
     * @return GameList
     */
    public static GameRefList getBidded(GameRefList games){
        GameRefList gameList = new GameRefList();
        for(int i = 0; i < games.getSize(); i++){
            if(games.getGame(i).getStatus() == GameController.STATUS_BIDDED){
                gameList.addGame(games.getGame(i).getGameID());
            }
        }
        return gameList;
    }
    
    public static void addMyGame(Game game){

        ElasticsearchGameController.AddGameTask addGameTask = new ElasticsearchGameController.AddGameTask();
        addGameTask.execute(game);

        ElasticSearchUsersController.EditUserTask ese = new ElasticSearchUsersController.EditUserTask();
        ese.execute(currentUser);
    }

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
