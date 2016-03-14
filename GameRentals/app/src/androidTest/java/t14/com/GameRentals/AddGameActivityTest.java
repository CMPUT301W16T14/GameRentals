package t14.com.GameRentals;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.test.ViewAsserts;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by cjresler on 2016-03-14.
 */
public class AddGameActivityTest extends ActivityInstrumentationTestCase2{
    //Instrumentation instrumentation;
    //AddGameActivity activity;
    //EditText textInput;
    //EditText textInput2;

    public AddGameActivityTest(){
        super(AddGameActivity.class);
    }

    public void testViewOnScreen(){
        Intent intent = new Intent();
        Game game = new Game("test", "test", null);
        intent.putExtra("Game", game);
        setActivityIntent(intent);

        AddGameActivity addGameActivity = (AddGameActivity) getActivity();

        EditText editText = (EditText) addGameActivity.findViewById(R.id.addGameNameEditText);

        ViewAsserts.assertOnScreen(addGameActivity.getWindow().getDecorView(), editText);
    }

/*
    protected void setUp() throws Exception {
        super.setUp();
        instrumentation = getInstrumentation();
        activity = (AddGameActivity) getActivity();
        textInput = ((EditText) activity.findViewById(R.id.addGameNameEditText));
        textInput2 = (EditText) activity.findViewById(R.id.addGameDescriptionEditText);
    }

    //makeTweet(text) fills in the input text field and clicks the 'save' button for the activity under test:
    private void addGame(String name, String description) {
        Intent intent = new Intent();
        Game game = new Game("test", "test", null);
        intent.putExtra("Game", game);
        setActivityIntent(intent);
        textInput.setText(name);
        textInput2.setText(description);
        ((Button) activity.findViewById(R.id.addGameOkButton)).performClick();
    }


    @UiThreadTest
    public void testAddGame(){
        addGame("test name", "description");
        assertTrue(UserController.getCurrentUser().getMyGames().getSize() == 1);
    }
*/

}
