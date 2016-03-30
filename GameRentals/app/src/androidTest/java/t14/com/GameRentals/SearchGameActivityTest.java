package t14.com.GameRentals;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
<<<<<<< HEAD
import android.test.ViewAsserts;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
=======
>>>>>>> origin/stable4

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by aredmond on 3/10/16.
 */
public class SearchGameActivityTest extends ActivityInstrumentationTestCase2 {


    public SearchGameActivityTest() {super(SearchGameActivity.class);}

    public void testActivityExists() {
        Intent intent = new Intent();
        intent.putExtra("SEARCH_TERM", "rpg");
        setActivityIntent(intent);

<<<<<<< HEAD
        SearchGameActivity activity = (SearchGameActivity) getActivity();
        assertNotNull(activity);
    }
=======
        Game EUIV = new Game("EUIV", "grand strategy", "Austin");
        EUIV.setStatus(GameController.STATUS_BIDDED);
>>>>>>> origin/stable4


    public void testViewOnScreen(){
        Intent intent = new Intent();
        intent.putExtra("SEARCH_TERM", "rpg");
        setActivityIntent(intent);

        SearchGameActivity searchGameActivity = (SearchGameActivity) getActivity();

        ListView listView = (ListView) searchGameActivity.findViewById(R.id.ReturnedGamesView);

        ViewAsserts.assertOnScreen(searchGameActivity.getWindow().getDecorView(), listView);
    }

    public void testSearch() {
        User Austin = new User("Austin", "aredmond@ualberta.ca", "780");
        Game EUIV = new Game("EUIV", "grand strategy", Austin);

        Intent intent = new Intent();
        intent.putExtra("SEARCH_TERM", "grand");
        setActivityIntent(intent);

        SearchGameActivity activity = (SearchGameActivity) getActivity();
        boolean hasGame = false;
        for (Game game: activity.getReturnedGames()) {
            if(EUIV.getGameName().equals(game.getGameName())) {
                hasGame = true;
            }
        }
        assertTrue(hasGame);
    }


}
