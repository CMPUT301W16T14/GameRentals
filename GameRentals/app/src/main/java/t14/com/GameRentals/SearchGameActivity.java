package t14.com.GameRentals;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class SearchGameActivity extends Activity {

    private ArrayList<Game> returnedGames;
    private ListView returnedGamesList;
    private ArrayAdapter<Game> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_game);

        String[] searchTerms = ((String)getIntent().getExtras().getSerializable("SEARCH_TERM")).split(" ");

        ElasticsearchGameController.SearchGamesTask searchGamesTask = new ElasticsearchGameController.SearchGamesTask();
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
        //if(returnedGames == null) {

        //}
        //adapter = new ArrayAdapter<Game>(this, R.layout.game_list, returnedGames);
        //returnedGamesList.setAdapter(adapter);
    }

}
