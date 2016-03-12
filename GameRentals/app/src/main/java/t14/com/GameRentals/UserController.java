package t14.com.GameRentals;

/**
 * Created by cjresler on 2016-02-28.
 */
public class UserController {
    private static User currentUser;
    public UserController() {
        currentUser = null;
    }


    public static User getCurrentUser() {
        User user = new User("Margaret","1234569","donkeyoyo@gmail.com");
        ElasticSearchUsersController.AddUserTask esa = new ElasticSearchUsersController.AddUserTask();
        esa.doInBackground(user);
        return user;
    }

    public static void setUser(User current) {
        currentUser = current;
    }

    //Given a GameList, return all games with available status
    public static GameList getAvailable(GameList games){
        GameList gameList = new GameList();
        for(int i = 0; i < games.getSize(); i++){
            if(games.getGame(i).getStatus() == GameController.STATUS_AVAILABLE){
                gameList.addGame(games.getGame(i));
            }
        }
        return gameList;
    }

    //Given a GameList, return all games with borrowed status
    public static GameList getBorrowed(GameList games){
        GameList gameList = new GameList();
        for(int i = 0; i < games.getSize(); i++){
            if(games.getGame(i).getStatus() == GameController.STATUS_BORROWED){
                gameList.addGame(games.getGame(i));
            }
        }
        return gameList;
    }

    //Given a GameList, return all games with bidded status
    public static GameList getBidded(GameList games){
        GameList gameList = new GameList();
        for(int i = 0; i < games.getSize(); i++){
            if(games.getGame(i).getStatus() == GameController.STATUS_BIDDED){
                gameList.addGame(games.getGame(i));
            }
        }
        return gameList;
    }

    public GameList getGameList() {
        return currentUser.getMyGames();
    }

    public static void addMyGame(Game game){
        currentUser.getMyGames().addGame(game);
        ElasticSearchUsersController.EditUserTask ese = new ElasticSearchUsersController.EditUserTask();
        ese.doInBackground(currentUser);
    }

    public void deleteGame(){
        //TODO
    }

    public void addBorrowedGame(){
        //TODO
    }

    public static void addBiddedGame(Game game){
        currentUser.getBiddedItems().addGame(game);
        ElasticSearchUsersController.EditUserTask ese = new ElasticSearchUsersController.EditUserTask();
        ese.doInBackground(currentUser);
    }

    public Game getGame(GameList list, int index){
        return list.getGame(index);
    }

    public static void deleteBiddedGame(Game game){
        currentUser.getBiddedItems().removeGame(game);
        ElasticSearchUsersController.EditUserTask ese = new ElasticSearchUsersController.EditUserTask();
        ese.doInBackground(currentUser);
    }

}
