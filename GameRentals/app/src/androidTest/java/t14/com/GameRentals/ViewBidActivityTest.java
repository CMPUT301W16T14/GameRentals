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
public class ViewBidActivityTest extends ActivityInstrumentationTestCase2 {
    Instrumentation instrumentation;
    ViewBidActivity viewBidActivity;
    BidList bids;
    GameList games;
    Button acceptButton;
    Button cancelButton;

    public ViewBidActivityTest() {
        super(ViewBidActivity.class);
    }

    public void testViewOnScreen() {
        Intent intent = new Intent();
        setActivityIntent(intent);

        ViewBidActivity viewBidActivity = (ViewBidActivity) getActivity();

        Button button = (Button) viewBidActivity.findViewById(R.id.acceptBidButton);

        ViewAsserts.assertOnScreen(viewBidActivity.getWindow().getDecorView(), button);
    }


    protected void setUp() throws Exception {
        super.setUp();
        instrumentation = getInstrumentation();
        viewBidActivity = (ViewBidActivity) getActivity();
        acceptButton = ((Button) viewBidActivity.findViewById(R.id.CancelBidButton));
        cancelButton = (Button) viewBidActivity.findViewById(R.id.acceptBidButton);
    }

    //makeTweet(text) fills in the input text field and clicks the 'save' button for the activity under test:
    private void acceptBid() {

        games = new GameList();
        games.addGame(new Game("hi", "hi", null));

        bids = new BidList();
        bids.AddBid(null, 4.99);
        Intent intent = new Intent();
        intent.putExtra("gamePosition", 0);
        intent.putExtra("bidPosition",0);
        setActivityIntent(intent);
        viewBidActivity.findViewById(R.id.acceptBidButton).performClick();
    }


    @UiThreadTest
    public void testAcceptBid(){
        acceptBid();
        assertTrue(bids.getItem(0).isAccepted()==1);
    }


}