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
public class ViewProfileActivityTest extends ActivityInstrumentationTestCase2 {

    Instrumentation instrumentation;
    ViewProfileActivity activity;
    EditText username;
    EditText email;
    EditText phone;
    Game game;
    User testUser;
    User toView;
    AlertDialog.Builder adb;
    public ViewProfileActivityTest(){
        super(ViewProfileActivity.class);
    }

    protected void setUp() throws Exception {
        super.setUp();
        instrumentation = getInstrumentation();
        testUser = new User("test", "test", "123");
        UserController.setUser(testUser);
        toView = new User("Hi", "hello", "123");
        Intent intent = new Intent();
        intent.putExtra("Username", toView.getUserName());
        setActivityIntent(intent);
        activity = (ViewProfileActivity)getActivity();
        username = ((EditText) activity.findViewById(R.id.viewProfileNameText));
        email = ((EditText) activity.findViewById(R.id.viewProfileEmailText));
        phone = ((EditText) activity.findViewById(R.id.viewProfilePhoneText));
    }

    //Test editing a game
    @UiThreadTest
    public void testEditGame(){
        EditText gameName = (EditText) activity.findViewById(R.id.editGameNameEditText);
        EditText gameDescription = (EditText) activity.findViewById(R.id.editGameDescriptionText);
        assertEquals("Initial game name", gameName.getText().toString(), "test");
        assertEquals("Initial description", gameDescription.getText().toString(), "test");
        //saveEdit("name", "description");

        Game testGame = (Game) getActivity().getIntent().getSerializableExtra("Game");
        assertEquals("After game name", testGame.getGameName(), "name");
        assertEquals("After description", testGame.getDescription(), "description");

    }

    public void testViewOnScreen() {
        Intent intent = new Intent();
        intent.putExtra("Username", toView.getUserName());
        setActivityIntent(intent);

        ViewProfileActivity viewProfileActivity = (ViewProfileActivity) getActivity();

        EditText editText = (EditText) viewProfileActivity.findViewById(R.id.viewProfileNameText);

        ViewAsserts.assertOnScreen(viewProfileActivity.getWindow().getDecorView(), editText);
        assertEquals("Make sure username is correct", toView.getUserName(), editText.getText().toString());
    }


}