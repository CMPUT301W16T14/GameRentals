package t14.com.GameRentals;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

public class SearchGameActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_game);

        String[] searchTerms = ((String)getIntent().getExtras().getSerializable("SEARCH_TERM")).split(" ");

        ElasticsearchGameController.SearchGamesTask searchGamesTask = new ElasticsearchGameController.SearchGamesTask();
        searchGamesTask.execute(searchTerms);

        setResult(RESULT_OK);
    }

}
