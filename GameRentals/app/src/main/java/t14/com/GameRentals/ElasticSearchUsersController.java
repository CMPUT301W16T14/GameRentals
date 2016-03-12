package t14.com.GameRentals;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import java.io.IOException;
import java.util.ArrayList;

import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import io.searchbox.core.Update;

/**
 * Created by margaret on 16/3/7.
 */
public class ElasticSearchUsersController {

    private static JestDroidClient client;

    //TODO: A function that gets tweets
    public static ArrayList<User> getUsers() {
        verifyConfig();
        // TODO: DO THIS

        return null;
    }


    public static class EditUserTask extends AsyncTask<User,Void,Void>{
        Gson gson = new Gson();
        @Override
        protected Void doInBackground(User... params) {
            verifyConfig();
            for(User user : params) {
                String json = gson.toJson(user);
                try {
                    DocumentResult execute = client.execute(new Update.Builder(json).index("cmput301w16t14").type("user").build());
                    if(execute.isSucceeded()) {
                        user.setUserName(execute.getId());
                    } else {
                        Log.e("TODO", "Our insert of user failed, oh no!");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }
    }

    public static class GetUserTask extends AsyncTask<String,Void,User> {

        @Override
        protected User doInBackground(String... params) {
            verifyConfig();

            // Hold (eventually) the tweets that we get back from Elasticsearch
            User user = new User(null,null,null);
            // NOTE: A HUGE ASSUMPTION IS ABOUT TO BE MADE!
            // Assume that only one string is passed in.

            // The following gets the top "10000" tweets
            //String search_string = "{\"from\":0,\"size\":10000}";

            // The following searches for the top 10 tweets matching the string passed in (NOTE: HUGE ASSUMPTION PREVIOUSLY)
            //String search_string = "{\"query\":{\"match\":{\"message\":\"" + params[0] + "\"}}}";

            // The following gets the top 10000 tweets matching the string passed in
            String search_string = "{\n" +
                    "    \"query\": {\n" +
                    "        \"match\" : { \"userName\" : \""+params[0]+"\" }\n" +
                    "    }\n" +
                    "}";

            Search search = new Search.Builder(search_string).addIndex("cmput301w16t14").addType("user").build();
            try {
                SearchResult result = client.execute(search);
                if(result.isSucceeded()) {
                    user = result.getSourceAsObject(User.class);

                } else {
                    Log.i("TODO", "Search was unsuccessful, do something!");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return user;
        }
    }

    public static class AddUserTask extends AsyncTask<User,Void,Void> {
        Gson gson = new Gson();

        @Override
        protected Void doInBackground(User... params) {
            verifyConfig();

            for(User user : params) {
                String json = gson.toJson(user);
                Index index = new Index.Builder(user).index("cmput301w16t14").type("user").build();

                try {
                    DocumentResult result = client.execute(index);
                    if(result.isSucceeded()) {
                        //user.setUserName(result.getId());
                    } else {
                        Log.e("TODO", "Our insert of user failed, oh no!");
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