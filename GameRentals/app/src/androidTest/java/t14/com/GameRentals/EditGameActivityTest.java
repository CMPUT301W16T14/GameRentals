package t14.com.GameRentals;

import android.app.Activity;
import android.app.AlertDialog;
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
    EditGameActivity activity;
    EditText nameInput;
    EditText descriptionInput;
    Game game;
    User testUser;
    public EditGameActivityTest(){
        super(EditGameActivity.class);
    }

    protected void setUp() throws Exception {
        super.setUp();
        instrumentation = getInstrumentation();
        testUser = new User("test", "test", "123");
        UserController.setUser(testUser);
        game = new Game("test", "test", testUser.getUserName());
        game.setStatus(GameController.STATUS_BORROWED);
        Intent intent = new Intent();
        intent.putExtra("Game", (Serializable) game);
        setActivityIntent(intent);
        activity = (EditGameActivity)getActivity();
        nameInput = ((EditText) activity.findViewById(R.id.editGameNameEditText));
        descriptionInput = ((EditText) activity.findViewById(R.id.editGameDescriptionText));
    }

    /** Simulate the save button being clicked
     *
     * @param name
     * @param description
     */
    private void saveEdit(String name, String description) {
        assertNotNull(activity.findViewById(R.id.editGameSaveButton));
        nameInput.setText(name);
        descriptionInput.setText(description);
        ((Button) activity.findViewById(R.id.editGameSaveButton)).performClick();
    }

    /** Simulate the return button being clicked
     *
     */

    private void returnGame() {
        assertNotNull(activity.findViewById(R.id.editGameReturnButton));
        ((Button) activity.findViewById(R.id.editGameReturnButton)).performClick();
    }

    /** Test that a game can be edited. Use Case 01.04.01
     *
     */
    @UiThreadTest
    public void testEditGame(){
        EditText gameName = (EditText) activity.findViewById(R.id.editGameNameEditText);
        EditText gameDescription = (EditText) activity.findViewById(R.id.editGameDescriptionText);
        assertEquals("Initial game name", gameName.getText().toString(), "test");
        assertEquals("Initial description", gameDescription.getText().toString(), "test");
        saveEdit("name", "description");

        Game testGame = (Game) getActivity().getIntent().getSerializableExtra("Game");
        assertEquals("After game name", testGame.getGameName(), "name");
        assertEquals("After description", testGame.getDescription(), "description");

    }

    /** Test that a game can be returned and have its status set to available.
     *  Use case 07.01.01
     *
     */
    @UiThreadTest
    public void testReturnGame(){
        Game testGame = (Game) getActivity().getIntent().getSerializableExtra("Game");
        assertEquals("Status should be borrowed", testGame.getStatus(), 2);
        returnGame();
        Game testGame2 = (Game) getActivity().getIntent().getSerializableExtra("Game");

        assertEquals("Status should be available", testGame2.getStatus(), 0);
    }

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