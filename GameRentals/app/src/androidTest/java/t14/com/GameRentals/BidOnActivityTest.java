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
public class BidOnActivityTest extends ActivityInstrumentationTestCase2 {
    //Instrumentation instrumentation;
    //AddGameActivity activity;
    //EditText textInput;
    //EditText textInput2;

    public BidOnActivityTest() {
        super(BidOnActivity.class);
    }

    public void testViewOnScreen() {
        Intent intent = new Intent();
        setActivityIntent(intent);

        BidOnActivity bidOnActivity = (BidOnActivity) getActivity();

        Button button = (Button) bidOnActivity.findViewById(R.id.BidButton);

        ViewAsserts.assertOnScreen(bidOnActivity.getWindow().getDecorView(), button);
    }
}