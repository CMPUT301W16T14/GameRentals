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

/**
 * This controller monitors and controls user interaction with the server/database.
 *
 *
 *
 *
 * @author JL
 *
 * Created by margaret on 16/3/7.
 */
public class ElasticSearchUsersController {

    private static JestDroidClient client;
    private static String serverType = "TestUsers";
    private static String testType = "TestUsers";

    //TODO: A function that gets tweets
    public static ArrayList<User> getUsers() {
        verifyConfig();
        // TODO: DO THIS

        return null;
    }


    /**
     * Updates database when user edits their profile.
     */
    public static class EditUserTask extends AsyncTask<User,Void,Void>{
        Gson gson = new Gson();
        @Override
        protected Void doInBackground(User... params) {
            verifyConfig();
            for(User user : params) {
                String json = gson.toJson(user);
                Index index = new Index.Builder(json).index("cmput301w16t14").type(serverType).id(user.getID()).build();
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

    public static class EditTestUserTask extends AsyncTask<User,Void,Void>{
        Gson gson = new Gson();
        @Override
        protected Void doInBackground(User... params) {
            verifyConfig();
            for(User user : params) {
                String json = gson.toJson(user);
                Index index = new Index.Builder(json).index("cmput301w16t14").type(testType).id(user.getID()).build();

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
     *
     * @return user with it's attributes if it exists. <br>
     *     Otherwise, a user with all it's attributes set to null is returned.
     * @see ProfileMain
     *
     */
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

            Search search = new Search.Builder(search_string).addIndex("cmput301w16t14").addType(serverType).build();
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

        protected void onPostExecute(User user){
            //UserController.setUser(user);
        }
    }

    /**
     * This method differs from @GetUserTask as it searches for users <br>
     *     from the user ID given. </br>
     *
     * @return user with it's attributes if it exists. <br>
     *     Otherwise, a user with all it's attributes set to null is returned. </br>
     * @see ProfileMain
     *
     */
    public static class GetUserByIDTask extends AsyncTask<String,Void,User> {

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
                    "        \"match\" : { \"id\" : \""+params[0]+"\" }\n" +
                    "    }\n" +
                    "}";

            Search search = new Search.Builder(search_string).addIndex("cmput301w16t14").addType(serverType).build();
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

        protected void onPostExecute(User user){
            //UserController.setUser(user);
        }
    }

    /**
     * This class is to add a new User into the database.
     *
     * @see ProfileMain
     *
     */
    public static class AddUserTask extends AsyncTask<User,Void,Void> {
        Gson gson = new Gson();
        @Override
        protected Void doInBackground(User... params) {
            verifyConfig();

            for(User user : params) {
                String json = gson.toJson(user);
                Index index = new Index.Builder(json).index("cmput301w16t14").type(serverType).build();

                try {
                    DocumentResult execute = client.execute(index);
                    if(execute.isSucceeded()) {
                        user.setID(execute.getId());

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

    public static class AddTestUserTask extends AsyncTask<User,Void,Void> {
        Gson gson = new Gson();
        @Override
        protected Void doInBackground(User... params) {
            verifyConfig();

            for(User user : params) {
                String json = gson.toJson(user);
                Index index = new Index.Builder(json).index("cmput301w16t14").type(testType).build();

                try {
                    DocumentResult execute = client.execute(index);
                    if(execute.isSucceeded()) {
                        user.setID(execute.getId());

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


    /**
     * This class is to add a new User into the database.
     *
     * @see ProfileMain
     *
     */
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
