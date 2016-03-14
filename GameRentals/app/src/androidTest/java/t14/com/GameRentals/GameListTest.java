package t14.com.GameRentals;

import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by cjresler on 2016-03-14.
 */
public class GameListTest extends ActivityInstrumentationTestCase2{
    public GameListTest() {
       super(MainActivity.class);
    }

    public void testAddGame(){
        GameList games = new GameList();
        Game game = new Game("Test", "Test", null);

        games.addGame(game);
        assertTrue(games.hasGame(game));
    }

    public void testRemoveGame(){
        GameList games = new GameList();
        Game game = new Game("Test", "Test", null);

        games.removeGame(game);
        assertFalse(games.hasGame(game));
    }

    public void testHasGame(){
        GameList games = new GameList();
        Game game = new Game("Hello", "Hello", null);

        assertFalse(games.hasGame(game));

        games.addGame(game);
        games.hasGame(game);

        assertTrue(games.hasGame(game));
    }

    public void testCopyList(){
        GameList list1 = new GameList();
        GameList list2 = new GameList();

        Game game = new Game("Test", "Test", null);

        list1.addGame(game);
        assertTrue(list1.hasGame(game));
        list2.copyList(list1);
        assertTrue(list2.hasGame(game));
    }

    public void testGetSize(){
        GameList games = new GameList();
        Game game = new Game("test", "test", null);
        assertTrue(games.getSize() == 0);

        games.addGame(game);
        assertTrue(games.getSize() == 1);
    }
}
