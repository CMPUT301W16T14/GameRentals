package t14.com.GameRentals;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by yourui on 3/4/16.
 */
public class BidOnActivity extends Activity {
    private Game game;
    private int gamePosition;
    private User currentUser;
    private ArrayList<Game> returnedGames;
    private final String FILENAME = "searchedResults.sav";

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_result_for_bid);

        //final EditText searchGameId = (EditText) findViewById(R.id.searchGameId);
        final EditText searchGameName = (EditText) findViewById(R.id.searchGameName);
        final EditText searchGameDescription = (EditText) findViewById(R.id.searchGameDescription);
        final EditText searchGameOwner = (EditText)findViewById(R.id.owner);
        loadFromFile();
        currentUser = UserController.getCurrentUser();
        gamePosition = getIntent().getExtras().getInt("gamePosition");
        game = returnedGames.get(gamePosition);

        //searchGameId.setText(game.getGameID());
        searchGameName.setText(game.getGameName());
        searchGameDescription.setText(game.getDescription());
        ElasticSearchUsersController.GetUserTask getUserTask = new ElasticSearchUsersController.GetUserTask();
        getUserTask.execute(game.getOwner());
        try {
            searchGameOwner.setText(getUserTask.get().getUserName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        Button bidButton = (Button)findViewById(R.id.BidButton);

        bidButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BidOnActivity.this, BidOnGameActivity.class);
                intent.putExtra("gamePosition", gamePosition);
                intent.putExtra("currentUser",currentUser);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
        loadFromFile();
    }

    private void loadFromFile(){
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();
            // Took from https://google-gson.googlecode.com/svn/trunk/gson/docs/javadocs/com/google/gson/Gson.html Jan-21-2016
            Type listType = new TypeToken<ArrayList<Game>>() {}.getType();
            returnedGames = gson.fromJson(in, listType);
        } catch(IOException e){
            throw new RuntimeException();
        }
    }
}
