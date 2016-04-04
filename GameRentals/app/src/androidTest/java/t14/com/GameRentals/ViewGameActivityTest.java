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
public class ViewGameActivityTest extends ActivityInstrumentationTestCase2 {

    Instrumentation instrumentation;
    ViewGameActivity activity;
    EditText nameInput;
    EditText descriptionInput;
    TextView borrowerName;
    Game game;
    User testUser;
    User borrower;
    public ViewGameActivityTest(){
        super(ViewGameActivity.class);
    }

    protected void setUp() throws Exception {
        super.setUp();
        instrumentation = getInstrumentation();
        testUser = new User("test", "test", "123");
        UserController.setUser(testUser);
        borrower = new User("borrower", "hi", "123");
        game = new Game("test", "test", testUser.getUserName());
        game.setBorrower(borrower.getUserName());
        game.setStatus(GameController.STATUS_BORROWED);
        Intent intent = new Intent();
        intent.putExtra("Game", (Serializable) game);
        setActivityIntent(intent);
        activity = (ViewGameActivity)getActivity();
        nameInput = ((EditText) activity.findViewById(R.id.editGameNameEditText));
        descriptionInput = ((EditText) activity.findViewById(R.id.editGameDescriptionText));
    }

    /**
     * Test to make sure that view is on screen and information from game is properly shown in
     * the text boxes.
     */
    public void testViewOnScreen() {

        EditText editText = (EditText) activity.findViewById(R.id.editGameNameEditText);

        ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(), editText);
        assertEquals("Make sure game title is correct", nameInput.getText().toString(), "test");
    }


}