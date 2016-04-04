package t14.com.GameRentals;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import java.io.IOException;
import java.util.List;

import io.searchbox.core.Delete;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

/**
 * This class is modeled after the ElasticsearchTweetController introduced in the lab. Its purpose
 * is to interact with the server to update database information.
 *<p>
 * @author aredmond
 */
public class ElasticsearchGameController {
    private static JestDroidClient client;
    private static User currentUser;
    private static String serverType = "TestGames";
    private static String testType = "TestGames";

    public static class GetGamesTask extends AsyncTask<String,Void,GameList> {

        /**
         * @param params This is a list of two strings. The first string holds the type of variable
         *               and the second string holds the value we want the variable to be.
         * @return the top 10000 games matching the string passed in.
         */
        @Override
        protected GameList doInBackground(String... params) {
            verifyConfig();

            // Hold (eventually) the tweets that we get back from Elasticsearch
            GameList games = new GameList();

            String search_string = "{\"from\":0,\"size\":10000,\"query\":{\"match\":{\"" + params[0] +"\":\"" + params[1] + "\"}}}";

            Search search = new Search.Builder(search_string).addIndex("cmput301w16t14").addType(serverType).build();
            try {
                SearchResult execute = client.execute(search);
                if(execute.isSucceeded()) {
                    List<Game> foundGames = execute.getSourceAsObjectList(Game.class);
                    games.getList().addAll(foundGames);
                } else {
                    Log.i("TODO", "Search was unsuccessful, do something!");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return games;
        }
    }

    /**
     * Adding a game to the server.
     * @see Game
     */
    public static class AddGameTask extends AsyncTask<Game,Void,Game> {
        Gson gson = new Gson();
        Game addedGame;

        /**
         *
         * @param params These are the games we wish to add.
         * @return null
         */
        @Override
        protected Game doInBackground(Game... params) {
            verifyConfig();
            //currentUser = UserController.getCurrentUser();

            for(Game game : params) {
                String json = gson.toJson(game);

                Index index = new Index.Builder(json).index("cmput301w16t14").type(serverType).build();

                try {
                    DocumentResult execute = client.execute(index);
                    if(execute.isSucceeded()) {
                        game.setGameID(execute.getId());
                        addedGame = game;
                        //currentUser.getMyGames().addGame(execute.getId());
                    } else {
                        Log.e("TODO", "Our insert of game failed, oh no!");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            return addedGame;
        }

        protected void onPostExecute(Game addedGame){
            ElasticsearchGameController.EditGameTask editGameTask = new ElasticsearchGameController.EditGameTask();
            editGameTask.execute(addedGame);
            String testID = addedGame.getGameID();
            UserController.getCurrentUser().getMyGames().addGame(testID);
            ElasticSearchUsersController.EditUserTask ese = new ElasticSearchUsersController.EditUserTask();
            ese.execute(UserController.getCurrentUser());
        }

    }

    /**
     * @see Game
     */
    public static class AddTestGameTask extends AsyncTask<Game,Void,Game> {
        Gson gson = new Gson();
        Game addedGame;

        /**
         *
         * @param params These are the games we wish to add.
         * @return null
         */
        @Override
        protected Game doInBackground(Game... params) {
            verifyConfig();
            //currentUser = UserController.getCurrentUser();

            for(Game game : params) {
                String json = gson.toJson(game);
                Index index = new Index.Builder(json).index("cmput301w16t14").type(testType).build();

                try {
                    DocumentResult execute = client.execute(index);
                    if(execute.isSucceeded()) {
                        game.setGameID(execute.getId());
                        addedGame = game;
                        //currentUser.getMyGames().addGame(execute.getId());
                    } else {
                        Log.e("TODO", "Our insert of game failed, oh no!");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return addedGame;
        }

        protected void onPostExecute(Game addedGame){
            ElasticsearchGameController.EditTestGameTask editGameTask = new ElasticsearchGameController.EditTestGameTask();
            editGameTask.execute(addedGame);
        }

    }

    /**
     * Removing a game from the server.
     * @see Game
     */
    public static class RemoveGameTask extends AsyncTask<Game,Void,Game> {
        Game removedGame;

        /**
         *
         * @param params These are the games we wish to add.
         * @return null
         */
        @Override
        protected Game doInBackground(Game... params) {
            verifyConfig();
            //currentUser = UserController.getCurrentUser();

            for(Game game : params) {

                Delete delete = new Delete.Builder(game.getGameID()).index("cmput301w16t14").type(serverType).build();

                try {
                    DocumentResult execute = client.execute(delete);
                    if(execute.isSucceeded()) {
                        //game.setGameID(execute.getId());
                        removedGame = game;
                        //currentUser.getMyGames().addGame(execute.getId());
                    } else {
                        Log.e("TODO", "Our insert of game failed, oh no!");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return removedGame;
        }

        protected void onPostExecute(Game removedGame){
            String testID = removedGame.getGameID();
            UserController.getCurrentUser().getMyGames().removeGame(testID);
            ElasticSearchUsersController.EditUserTask ese = new ElasticSearchUsersController.EditUserTask();
            ese.execute(UserController.getCurrentUser());
        }
    }

    /**
     * Get a game from the server.
     * @see Game
     */
    public static class GetGameTask extends AsyncTask<String,Void,Game>{
        @Override
        protected Game doInBackground(String... params) {
            verifyConfig();

            // Hold (eventually) the game that we get back from Elasticsearch
            Game game = new Game("", "", null);

            String insertTerms = "";

            for (String searchTerm: params) {
                insertTerms += "{\"match\": {\"gameID\": \"" + searchTerm + "\"}}, ";
            }

            insertTerms = insertTerms.substring(0, insertTerms.length()-2);

            // The following gets the games with all the search terms
            String search_string = "{\"from\":0,\"size\":10000,\"query\":{\"bool\":{\"must\":[ " + insertTerms + " ]}}}";

            Search search = new Search.Builder(search_string).addIndex("cmput301w16t14").addType(serverType).build();
            try {
                SearchResult execute = client.execute(search);
                if(execute.isSucceeded()) {
                    game = execute.getSourceAsObject(Game.class);
                    //games.getList().addAll(foundGames);
                } else {
                    Log.i("TODO", "Search was unsuccessful, do something!");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return game;
        }
    }

    public static class GetTestGameTask extends AsyncTask<String,Void,Game>{
        @Override
        protected Game doInBackground(String... params) {
            verifyConfig();

            // Hold (eventually) the game that we get back from Elasticsearch
            Game game = new Game("", "", null);

            String insertTerms = "";

            for (String searchTerm: params) {
                insertTerms += "{\"match\": {\"gameID\": \"" + searchTerm + "\"}}, ";
            }

            insertTerms = insertTerms.substring(0, insertTerms.length()-2);

            // The following gets the games with all the search terms
            String search_string = "{\"from\":0,\"size\":10000,\"query\":{\"bool\":{\"must\":[ " + insertTerms + " ]}}}";

            Search search = new Search.Builder(search_string).addIndex("cmput301w16t14").addType(testType).build();
            try {
                SearchResult execute = client.execute(search);
                if(execute.isSucceeded()) {
                    Game foundGame = execute.getSourceAsObject(Game.class);
                    //games.getList().addAll(foundGames);
                    game = foundGame;
                } else {
                    Log.i("TODO", "Search was unsuccessful, do something!");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return game;
        }
    }

    /**
     * Allows users to look for games based on search terms given.
     */
    public static class SearchGamesTask extends AsyncTask<String,Void,GameList> {
        /**
         *
         * @param params These are all the key words that are to be searched
         * @return The top 10000 games that have all the search terms.
         */
        @Override
        protected GameList doInBackground(String... params) {
            verifyConfig();

            // Hold (eventually) the games that we get back from Elasticsearch
            GameList games = new GameList();

            /*
            {
                "query": {
                    "bool": {
                        "must": [
                            { "match": { "description": "first" } },
                            { "match": { "description": "shooter" } }
                        ],
                        "must_not": { "match": {"status"; 2} }
                    }
                }
            }

             */

            String insertTerms = "";

            for (String searchTerm: params) {
                insertTerms += "{\"match\": {\"description\": \"" + searchTerm + "\"}}, ";
            }

            insertTerms = insertTerms.substring(0, insertTerms.length()-2);

            // The following gets the games with all the search terms
            String search_string = "{\"from\":0,\"size\":10000,\"query\":{\"bool\":{\"must\":[ " + insertTerms + " ], \"must_not\": {\"match\": {\"status\": 2}}}}}";

            Search search = new Search.Builder(search_string).addIndex("cmput301w16t14").addType(serverType).build();
            try {
                SearchResult execute = client.execute(search);
                if(execute.isSucceeded()) {
                    List<Game> foundGames = execute.getSourceAsObjectList(Game.class);
                    games.getList().addAll(foundGames);
                } else {
                    Log.i("TODO", "Search was unsuccessful, do something!");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return games;
        }
    }

    /**
     * Updates the server when users edit their games or when there is a change in game attributes.
     * @see Game
     */
    public static class EditGameTask extends AsyncTask<Game,Void,Void>{
        Gson gson = new Gson();

        @Override
        protected Void doInBackground(Game... params) {
            verifyConfig();

            for(Game game : params) {
                String json = gson.toJson(game);
                Index index = new Index.Builder(json).index("cmput301w16t14").type(serverType).id(game.getGameID()).build();

                try {
                    DocumentResult execute = client.execute(index);

                    if(execute.isSucceeded()) {
                        //user.setID(execute.getId());
                    } else {
                        Log.e("TODO", "Our edit of user failed, oh no!");
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }
    }

    public static class EditTestGameTask extends AsyncTask<Game,Void,Void>{
        Gson gson = new Gson();
        @Override
        protected Void doInBackground(Game... params) {
            verifyConfig();
            for(Game game : params) {
                String json = gson.toJson(game);
                Index index = new Index.Builder(json).index("cmput301w16t14").type(testType).id(game.getGameID()).build();
                try {
                    DocumentResult execute = client.execute(index);
                    if(execute.isSucceeded()) {
                        //user.setID(execute.getId());
                    } else {
                        Log.e("TODO", "Our edit of user failed, oh no!");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }
    }

    /**
     * If no client, add one.
     */
    public static void verifyConfig() {
        if(client == null) {
            DroidClientConfig.Builder builder = new DroidClientConfig.Builder("http://cmput301.softwareprocess.es:8080");
            DroidClientConfig config = builder.build();

            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            client = (JestDroidClient) factory.getObject();
        }
    }

}
