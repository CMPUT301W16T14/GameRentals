package t14.com.GameRentals;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class SearchGameActivity extends Activity {
    private final String FILENAME = "searchedResults.sav";

    private ArrayList<Game> returnedGames;
    private ListView returnedGamesList;
    private ArrayAdapter<Game> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_game);


        returnedGamesList = (ListView)findViewById(R.id.ReturnedGamesView);

        String[] searchTerms = ((String)getIntent().getExtras().getSerializable("SEARCH_TERM")).split(" ");

        final ElasticsearchGameController.SearchGamesTask searchGamesTask = new ElasticsearchGameController.SearchGamesTask();

        searchGamesTask.execute(searchTerms);
        try {
            returnedGames = new ArrayList<Game>();
            returnedGames.addAll(searchGamesTask.get().getList());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        adapter = new ArrayAdapter<Game>(this, R.layout.game_list, returnedGames);
        returnedGamesList.setAdapter(adapter);

        setResult(RESULT_OK);
    }

    @Override
    protected void onStart() {
        super.onStart();

        setResult(RESULT_OK);

        saveInFile();/////////////save the results to local file to do bid.

        returnedGamesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                AlertDialog.Builder adb = new AlertDialog.Builder(SearchGameActivity.this);
                adb.setTitle("view the game");
                adb.setMessage("Do you want to view the game?");
                adb.setCancelable(true);

                adb.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(SearchGameActivity.this, BidOnActivity.class);
                        intent.putExtra("gamePosition",i);
                        startActivity(intent);
                    }
                });

                adb.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
            }
        });

    }

    private void saveInFile(){
        try{
            FileOutputStream fos = openFileOutput(FILENAME,0);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
            Gson gson = new Gson();
            gson.toJson(returnedGames, out);
            out.flush();
            fos.close();
        }catch(FileNotFoundException e){
            throw new RuntimeException();
        }catch (IOException e){
            throw new RuntimeException();
        }
    }

}
