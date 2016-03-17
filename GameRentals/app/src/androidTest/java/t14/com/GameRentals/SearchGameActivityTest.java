package t14.com.GameRentals;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by aredmond on 3/10/16.
 */
public class SearchGameActivityTest extends ActivityInstrumentationTestCase2 {


    public SearchGameActivityTest() {super(SearchGameActivityTest.class);}

    public void testSearchQuery() {

        Game EUIV = new Game("EUIV", "grand strategy", new User("Austin", "aredmond@gmail.com", "7801234576"));
        EUIV.setStatus(GameController.STATUS_BIDDED);

        ElasticsearchGameController.AddGameTask addGameTask = new ElasticsearchGameController.AddGameTask();
        addGameTask.execute(EUIV);

        Intent intent = new Intent();
        intent.putExtra("Search_Term", "grand strategy game");

        String[] searchTerms = ((String)intent.getExtras().getSerializable("SEARCH_TERM")).split(" ");

        final ElasticsearchGameController.SearchGamesTask searchGamesTask = new ElasticsearchGameController.SearchGamesTask();

        searchGamesTask.execute(searchTerms);

        ArrayList<Game> returnedGames = new ArrayList<Game>();

        try {
            returnedGames.addAll(searchGamesTask.get().getList());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        Boolean hasGame = false;

        for(Game game: returnedGames) {
            if(game.getGameName().equals("EUIV")) {hasGame = true;}
        }

        assertTrue(returnedGames.contains(EUIV));

    }



}
