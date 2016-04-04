package t14.com.GameRentals;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by margaret on 16/4/3.
 */
public class CancelBidActivityTest extends ActivityInstrumentationTestCase2 {

    public CancelBidActivityTest(){
        super(CancelBidActivity.class);
    }

    public void testViewVisible(){
        Game game = new Game("test","test","testUser");


        Intent intent = new Intent(this.getInstrumentation().getTargetContext().getApplicationContext(),CancelBidActivity.class);
        intent.putExtra("bidPosition",0);
        setActivityIntent(intent);

        CancelBidActivity cancelBidActivity = (CancelBidActivity)getActivity();
        TextView gameNameText = cancelBidActivity.getGameNameText();
        TextView descriptionText = cancelBidActivity.getDescriptionText();
        TextView gameOwner = cancelBidActivity.getGameOwner();
        TextView gameStatus = cancelBidActivity.getGameStatus();

        ViewAsserts.assertOnScreen(cancelBidActivity.getWindow().getDecorView(), gameNameText);
        ViewAsserts.assertOnScreen(cancelBidActivity.getWindow().getDecorView(), descriptionText);
        ViewAsserts.assertOnScreen(cancelBidActivity.getWindow().getDecorView(), gameOwner);
        ViewAsserts.assertOnScreen(cancelBidActivity.getWindow().getDecorView(), gameStatus);

    }

    public void testCancelBid(){
        Intent intent = new Intent();
        setActivityIntent(intent);
        CancelBidActivity cancelBidActivity = (CancelBidActivity)getActivity();
        final Button cancelButton = cancelBidActivity.getCancelBidButton();
        cancelBidActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                cancelButton.performClick();
            }
        });
    }

}
