package t14.com.GameRentals;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by cjresler on 2016-04-02.
 */
public class CreateTestData {
    ArrayList<String> usernames;
    String email;
    String phoneNumber;
    ArrayList<String> gameNames;
    ArrayList<String> gameDescriptions;
    ArrayList<User> userList;
    ArrayList<Game> gameList;
    ArrayList<Game> gamesFromServer;
    ArrayList<Bid> bidList;

    public CreateTestData(){
        usernames = new ArrayList<String>();
        gameNames = new ArrayList<String>();
        gameDescriptions = new ArrayList<String>();
        userList = new ArrayList<User>();
        gameList = new ArrayList<Game>();
        gamesFromServer = new ArrayList<Game>();
        populateArrays();
        email = "@email.com";
        phoneNumber = "123-456-7890";
        //createObjects();
    }

    private void populateArrays() {
        usernames.add("Connor");
        usernames.add("Austin");
        usernames.add("Janice");
        usernames.add("Qi");
        usernames.add("Margaret");

        gameNames.add("Final Fantasy 10");
        gameDescriptions.add("RPG");
        gameNames.add("Warcraft");
        gameDescriptions.add("Strategy");
        gameNames.add("Roller Coaster Tycoon");
        gameDescriptions.add("Simulation");
        gameNames.add("Hatoful Boyfriend");
        gameDescriptions.add("Pigeon Dating Simulation");
        gameNames.add("League of Legends");
        gameDescriptions.add("MOBA");
        gameNames.add("Zelda");
        gameDescriptions.add("Adventure");
        gameNames.add("Mario");
        gameDescriptions.add("Platformer");
        gameNames.add("The Sims");
        gameDescriptions.add("Simulation");
        gameNames.add("Chrono Trigger");
        gameDescriptions.add("RPG");
        gameNames.add("Pokemon");
        gameDescriptions.add("RPG");
        gameNames.add("Fire Emblem");
        gameDescriptions.add("Strategy RPG");
        gameNames.add("Captain Toad: Treasure Tracker");
        gameDescriptions.add("Platformer");
        gameNames.add("Yoshi's Wooly World");
        gameDescriptions.add("Platformer");
        gameNames.add("Mario Party");
        gameDescriptions.add("Party Game");
        gameNames.add("Mario Kart 8");
        gameDescriptions.add("Racing");
        gameNames.add("Super Smash Bros.");
        gameDescriptions.add("Fighting");
        gameNames.add("Pikmin");
        gameDescriptions.add("Platformer");
        gameNames.add("Chrono Cross");
        gameDescriptions.add("RPG");
        gameNames.add("Mario");
        gameDescriptions.add("Platformer");
        gameNames.add("Final Fantasy 7");
        gameDescriptions.add("RPG");
    }

    public void createObjects(){
        for(int i = 0; i < usernames.size(); i++){
            userList.add(new User(usernames.get(i), usernames.get(i) + email, phoneNumber));
            ElasticSearchUsersController.AddTestUserTask esa = new ElasticSearchUsersController.AddTestUserTask();
            esa.execute(userList.get(i));
            ElasticSearchUsersController.EditTestUserTask ese = new ElasticSearchUsersController.EditTestUserTask();
            ese.execute(userList.get(i));
        }
        for(int i = 0; i < gameNames.size(); i++){
            gameList.add(new Game(gameNames.get(i), gameDescriptions.get(i), usernames.get(i % 5)));
            ElasticsearchGameController.AddTestGameTask addGameTask = new ElasticsearchGameController.AddTestGameTask();
            addGameTask.execute(gameList.get(i));
            Game loadedGame = new Game(null, null, null);
            try{
                loadedGame = (addGameTask.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            gameList.get(i).setGameID(loadedGame.getGameID());
            userList.get(i % 5).getMyGames().addGame(loadedGame.getGameID());
        }
        for(int i = 0; i < usernames.size(); i++){
            ElasticSearchUsersController.EditTestUserTask ese = new ElasticSearchUsersController.EditTestUserTask();
            ese.execute(userList.get(i));
        }


        //Set statuses
        //Last 5 games will have bids
        for(int i = 15; i < gameList.size(); i++){
            gameList.get(i).getBidList().AddBid(userList.get((i + 1) % 5), i - 10);
            gameList.get(i).setStatus(GameController.STATUS_BIDDED);
            userList.get((i + 1)%5).getBiddedItems().addGame(gameList.get(i));
            ElasticSearchUsersController.EditTestUserTask ese = new ElasticSearchUsersController.EditTestUserTask();
            ese.execute(userList.get((i + 1 )%5));
            ElasticsearchGameController.EditTestGameTask editGameTask = new ElasticsearchGameController.EditTestGameTask();
            editGameTask.execute(gameList.get(i));
        }

        //Create a borrowed game for each user
        for(int i = 10; i < 15; i++){
            gameList.get(i).setStatus(GameController.STATUS_BORROWED);
            userList.get((i + 2)%5).getBorrowedItems().addGame(gameList.get(i));
            gameList.get(i).setBorrower(userList.get((i + 2)%5).getUserName());
            ElasticSearchUsersController.EditTestUserTask ese = new ElasticSearchUsersController.EditTestUserTask();
            ese.execute(userList.get((i + 2)%5));
            ElasticsearchGameController.EditTestGameTask editGameTask = new ElasticsearchGameController.EditTestGameTask();
            editGameTask.execute(gameList.get(i));
        }
    }

    public void setGameStatuses(){
        //Last 5 games will have bids
        for(int i = 15; i < gameList.size(); i++){
            gameList.get(i).getBidList().addBid(new Bid(userList.get(i % 5), i - 10));
            gameList.get(i).setStatus(GameController.STATUS_BIDDED);
            userList.get(i%5).getBiddedItems().addGame(gameList.get(i));
            ElasticSearchUsersController.EditTestUserTask ese = new ElasticSearchUsersController.EditTestUserTask();
            ese.execute(userList.get(i%5));
            ElasticsearchGameController.EditTestGameTask editGameTask = new ElasticsearchGameController.EditTestGameTask();
            editGameTask.execute(gameList.get(i));
        }

        //Create a borrowed game for each user
        for(int i = 10; i < 15; i++){
            gameList.get(i).setStatus(GameController.STATUS_BORROWED);
            userList.get(i%5).getBorrowedItems().addGame(gameList.get(i));
            ElasticSearchUsersController.EditTestUserTask ese = new ElasticSearchUsersController.EditTestUserTask();
            ese.execute(userList.get(i%5));
            ElasticsearchGameController.EditTestGameTask editGameTask = new ElasticsearchGameController.EditTestGameTask();
            editGameTask.execute(gameList.get(i));
        }
    }

}
