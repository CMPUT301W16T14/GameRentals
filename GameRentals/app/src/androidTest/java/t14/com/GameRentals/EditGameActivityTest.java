package t14.com.GameRentals;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.test.ViewAsserts;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.Serializable;

/**
 * Created by cjresler on 2016-03-14.
 */
public class EditGameActivityTest extends ActivityInstrumentationTestCase2 {

    Instrumentation instrumentation;
    Activity activity;
    EditText nameInput;
    EditText descriptionInput;
    Game game;
    public EditGameActivityTest(){
        super(EditGameActivity.class);
    }

    protected void setUp() throws Exception {
        super.setUp();
        instrumentation = getInstrumentation();
        User testUser = new User("test", "test", "123");
        UserController.setUser(testUser);
        game = new Game("test", "test", testUser.getUserName());
        Intent intent = new Intent();
        intent.putExtra("Game", (Serializable) game);
        setActivityIntent(intent);
        activity = getActivity();
        nameInput = ((EditText) activity.findViewById(R.id.editGameNameEditText));
        descriptionInput = ((EditText) activity.findViewById(R.id.editGameDescriptionText));
    }

    private void saveEdit(String name, String description) {
        assertNotNull(activity.findViewById(R.id.editGameSaveButton));
        nameInput.setText(name);
        descriptionInput.setText(description);
        ((Button) activity.findViewById(R.id.editGameSaveButton)).performClick();
    }
/*
    private void deleteGame() {
        assertNotNull(activity.findViewById(R.id.editGameSaveButton));
        ((Button) activity.findViewById(R.id.editGameDeleteButton)).performClick();
    } */

    //Test editing a game
    @UiThreadTest
    public void testEditGame(){
        EditText gameName = (EditText) activity.findViewById(R.id.editGameNameEditText);
        EditText gameDescription = (EditText) activity.findViewById(R.id.editGameDescriptionText);
        TextView statusLabel = (TextView) activity.findViewById(R.id.editGameStatus);
        assertEquals("Initial game name", gameName.getText().toString(), "test");
        assertEquals("Initial description", gameDescription.getText().toString(), "test");
        saveEdit("name", "description");

        Game testGame = (Game) getActivity().getIntent().getSerializableExtra("Game");
        assertEquals("After game name", testGame.getGameName(), "name");
        assertEquals("After description", testGame.getDescription(), "description");

    }
/*
    //Test editing a game
    @UiThreadTest
    public void testDeleteGame(){
        EditText gameName = (EditText) activity.findViewById(R.id.editGameNameEditText);
        EditText gameDescription = (EditText) activity.findViewById(R.id.editGameDescriptionText);
        TextView statusLabel = (TextView) activity.findViewById(R.id.editGameStatus);
        assertEquals("Initial game name", gameName.getText().toString(), "test");
        assertEquals("Initial description", gameDescription.getText().toString(), "test");
        saveEdit("name", "description");

        Game testGame = (Game) getActivity().getIntent().getSerializableExtra("Game");
        assertEquals("After game name", testGame.getGameName(), "name");
        assertEquals("After description", testGame.getDescription(), "description");

    }*/

    public void testViewOnScreen() {
        Intent intent = new Intent();
        Game game = new Game("test", "test", null);
        intent.putExtra("Game", game);
        setActivityIntent(intent);

        EditGameActivity editGameActivity = (EditGameActivity) getActivity();

        EditText editText = (EditText) editGameActivity.findViewById(R.id.editGameNameEditText);

        ViewAsserts.assertOnScreen(editGameActivity.getWindow().getDecorView(), editText);
    }


}