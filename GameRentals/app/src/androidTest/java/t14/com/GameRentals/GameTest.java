package t14.com.GameRentals;

import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by cjresler on 2016-03-14.
 */
public class GameTest extends ActivityInstrumentationTestCase2{
    public GameTest() {
        super(MainActivity.class);
    }
    public void testGetStatusString(){
        Game game = new Game("test", "test", null);

        assertTrue(game.getStatusString().equalsIgnoreCase("available"));
        game.setStatus(GameController.STATUS_BIDDED);
        assertTrue(game.getStatusString().equalsIgnoreCase("bidded"));
        game.setStatus(GameController.STATUS_BORROWED);
        assertTrue(game.getStatusString().equalsIgnoreCase("borrowed"));

    }

}
