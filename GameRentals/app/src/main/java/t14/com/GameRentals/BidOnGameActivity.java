package t14.com.GameRentals;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
 * user case 05.01.01
 */
public class BidOnGameActivity extends Activity {
    private Game game;
    private ArrayList<Game> returnedGames;
    private double rate;
    private final String FILENAME = "searchedResults.sav";
    @Override
    public void onCreate(Bundle SavedInstanceState){
        super.onCreate(SavedInstanceState);
        Button okButton = (Button)findViewById(R.id.OKButton);
        setContentView(R.layout.bid_on_game);

        int gamePosition = getIntent().getExtras().getInt("gamePosition");
        game = returnedGames.get(gamePosition);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder adb = new AlertDialog.Builder(BidOnGameActivity.this);
                adb.setTitle("Bidding");
                adb.setMessage("Are you sure to bid it?");
                adb.setCancelable(true);
                adb.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //////////TODO:GET RATE VALUE
                        game.getBidList().AddBid(UserController.getCurrentUser(),rate);
                        UserController.addBiddedGame(game);
                    }
                });
                adb.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                adb.show();
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