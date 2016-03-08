package t14.com.GameRentals;


import android.util.Log;

import java.io.IOException;

/**
 * Created by yourui on 3/7/16.
 */
public class ElasticSearchUsersController  {

    private static JestDroidClient client;

    //TODO: A function that gets tweets
    public static ArrayList<Tweet> getTweet(){
        verifyConfig();
        //TODO: DO THIS
        return null;
    }

    public static void addTweet(Tweet tweet){
        verifyConfig();

        Index index = new Index.Builder(tweet).index("testing").type("tweet").build();

        try {
            DocumentResult execute = client.execute(index);
            if (execute.isSucceeded()){
                tweet.setId(execute.getId());
            }else{
                Log.e("TODO", "Our insert of tweet failed, oh no!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class GetTweetsTask extends AsyncTask<String, Void, ArrayList<NormalTweet>> {

        @Override
        protected ArrayList<NormalTweet> doInBackground(String...params){
            verifyConfig();
            //Hold eventually the tweets that we get back from Elasticsearch
            ArrayList<NormalTweet> tweets= new ArrayList<NormalTweet>();

            //NOTE: A HUGE ASSUMPTION IS ABOUT TO BE MADE!
            //Assume that only one string is passed in
            String search_string = params[0];

            Search search = new Search.Builder(search_string).addIndex("testing").addType("tweet").build();
            try {
                SearchResult execute = client.execute(search);
                if (execute.isSucceeded()){
                    List<NormalTweet> foundTweets = execute.getSourceAsObjectList(NormalTweet.class);
                    tweets.addAll(foundTweets);
                }else{
                    Log.i("TODO","Search was unsuccessful, do something!");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return tweets;
        }
    }

    public static class AddTweetTask extends AsyncTask<NormalTweet,Void,Void>{

        @Override
        protected Void doInBackground(NormalTweet... params) {
            verifyConfig();

            for (Tweet tweet : params) {
                Index index = new Index.Builder(tweet).index("testing").type("tweet").build();

                try {
                    DocumentResult execute = client.execute(index);
                    if (execute.isSucceeded()) {
                        tweet.setId(execute.getId());
                    } else {
                        Log.e("TODO", "Our insert of tweet failed, oh no!");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }

    //if no client, add one
    public static void verifyConfig(){
        if (client == null){
            DroidClientConfig.Builder builder = new DroidClientConfig.Builder("http://cmput301.softwareprocess.es:8080");
            DroidClientConfig config = builder.build();

            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            client = (JestDroidClient)factory.getObject();
        }
    }

    //TODO: A function that adds a tweet
}
