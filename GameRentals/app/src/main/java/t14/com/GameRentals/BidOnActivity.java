package t14.com.GameRentals;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by yourui on 3/4/16.
 */
public class BidOnActivity extends Activity {
    private Game game;
    private int gamePosition;
    private ArrayList<Game> returnedGames;
    private final String FILENAME = "searchedResults.sav";

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_result_for_bid);

        gamePosition = getIntent().getExtras().getInt("gamePosition");
        game = returnedGames.get(gamePosition);

        Button bidButton = (Button)findViewById(R.id.BidButton);

        bidButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BidOnActivity.this, BidOnGameActivity.class);
                intent.putExtra("gamePosition", gamePosition);
                startActivity(intent);
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
