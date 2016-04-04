package t14.com.GameRentals;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.test.ViewAsserts;
import android.widget.Button;
import android.widget.TextView;

import org.junit.Before;

import java.util.concurrent.ExecutionException;

/**
 * Created by margaret on 16/4/3.
 */
public class CancelBidActivityTest extends ActivityInstrumentationTestCase2 {

    Game game;
    BidList bidList;
    Bid bid;
    int position;
    User biduser;
    User owner;
    Instrumentation instrumentation;
    Activity activity;

    public CancelBidActivityTest(){
        super(CancelBidActivity.class);
    }

    @Before
    protected void setUp(){
        instrumentation = new Instrumentation();
        position = 0;
        Intent intent = new Intent(this.getInstrumentation().getTargetContext().getApplicationContext(), CancelBidActivity.class);
        intent.putExtra("bidPosition", position);
        setActivityIntent(intent);
        activity = (CancelBidActivity)getActivity();

        ElasticSearchUsersController.GetUserTask getUserTask1 = new ElasticSearchUsersController.GetUserTask();
        getUserTask1.execute("owner");
        User owner = null;
        try {
            owner = getUserTask1.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        ElasticSearchUsersController.GetUserTask getUserTask = new ElasticSearchUsersController.GetUserTask();
        getUserTask.execute("biduser");
        User biduser = null;
        try {
            biduser = getUserTask.get();
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        } catch (ExecutionException e1) {
            e1.printStackTrace();
        }

        UserController.setUser(biduser);


    }

    public void testViewVisible() {
        position = 0;
        Intent intent = new Intent(this.getInstrumentation().getTargetContext().getApplicationContext(), CancelBidActivity.class);
        intent.putExtra("bidPosition", position);
        setActivityIntent(intent);
        activity = (CancelBidActivity)getActivity();

        ElasticSearchUsersController.GetUserTask getUserTask1 = new ElasticSearchUsersController.GetUserTask();
        getUserTask1.execute("owner");
        User owner = null;
        try {
            owner = getUserTask1.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        ElasticSearchUsersController.GetUserTask getUserTask = new ElasticSearchUsersController.GetUserTask();
        getUserTask.execute("biduser");
        User biduser = null;
        try {
            biduser = getUserTask.get();
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        } catch (ExecutionException e1) {
            e1.printStackTrace();
        }

        UserController.setUser(biduser);


        //Button cButton = (Button)activity.findViewById(R.id.CancelBidButton);
        TextView gameNameText = (TextView)activity.findViewById(R.id.gameNameText);
        TextView descriptionText = (TextView)activity.findViewById(R.id.descriptionText);
        TextView gameOwner = (TextView)activity.findViewById(R.id.gameOwner);
        TextView gameStatus = (TextView)activity.findViewById(R.id.gameStatus);


        ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(), gameNameText);
        ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(), descriptionText);
        ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(), gameOwner);
        ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(), gameStatus);
        //assertTrue(cButton.isShown());

    }

    @UiThreadTest
    public void testCancelBid(){
        position = 0;
        Intent intent = new Intent(this.getInstrumentation().getTargetContext().getApplicationContext(), CancelBidActivity.class);
        intent.putExtra("bidPosition", position);
        setActivityIntent(intent);
        activity = (CancelBidActivity)getActivity();

        ElasticSearchUsersController.GetUserTask getUserTask1 = new ElasticSearchUsersController.GetUserTask();
        getUserTask1.execute("owner");
        User owner = null;
        try {
            owner = getUserTask1.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        ElasticSearchUsersController.GetUserTask getUserTask = new ElasticSearchUsersController.GetUserTask();
        getUserTask.execute("biduser");
        User biduser = null;
        try {
            biduser = getUserTask.get();
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        } catch (ExecutionException e1) {
            e1.printStackTrace();
        }
        UserController.setUser(biduser);


        assertFalse(biduser.getBiddedItems().getSize() == 1);
        assertFalse(owner.getMyGames().getSize() == 1);


        CancelBidActivity cancelBidActivity = (CancelBidActivity) getActivity();
        final Button cancelButton = cancelBidActivity.getCancelBidButton();
        cancelButton.performClick();

        assertFalse(owner.getMyGames().getGame(0).getBidList().getSize() == 0);
        assertFalse(biduser.getBiddedItems().getSize() == 0);

    }

}
