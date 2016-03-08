package t14.com.GameRentals;

import android.os.AsyncTask;
import android.util.Log;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

/**
 * Created by aredmond on 3/7/16.
 * From esports branch of LonelyTwitter
 */
public class ElasticSearchGamesController {
    private static JestDroidClient client;

    //TODO: A function that gets tweets
    public static GameList getGames() {
        verifyConfig();
        // TODO: DO THIS.
        return null;
    }

    public static class GetGamesTask extends AsyncTask<String,Void,GameList> {

        @Override
        protected GameList doInBackground(String... params) {
            verifyConfig();

            // Hold (eventually) the tweets that we get back from Elasticsearch
            GameList games = new GameList();

            // NOTE: A HUGE ASSUMPTION IS ABOUT TO BE MADE!
            // Assume that only one string is passed in.

            // The following gets the top "10000" tweets
            //String search_string = "{\"from\":0,\"size\":10000}";

            // The following searches for the top 10 tweets matching the string passed in (NOTE: HUGE ASSUMPTION PREVIOUSLY)
            //String search_string = "{\"query\":{\"match\":{\"message\":\"" + params[0] + "\"}}}";

            // The following gets the top 10000 tweets matching the string passed in
            String search_string = "{\"from\":0,\"size\":10000,\"query\":{\"match\":{\"message\":\"" + params[0] + "\"}}}";

            Search search = new Search.Builder(search_string).addIndex("cmput301w16t14").addType("game").build();
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

    public static class AddGameTask extends AsyncTask<Game,Void,Void> {

        @Override
        protected Void doInBackground(Game... params) {
            verifyConfig();

            for(Game game : params) {
                Index index = new Index.Builder(game).index("cmput301w16t14").type("game").build();

                try {
                    DocumentResult execute = client.execute(index);
                    if(execute.isSucceeded()) {
                        game.setGameID(execute.getId());
                    } else {
                        Log.e("TODO", "Our insert of game failed, oh no!");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }
    }

    // If no client, add one
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
