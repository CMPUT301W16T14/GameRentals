package t14.com.GameRentals;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by aredmond on 3/10/16.
 */
public class SearchGameActivityTest extends ActivityInstrumentationTestCase2 {


    public SearchGameActivityTest() {super(SearchGameActivityTest.class);}

    public void testActivityExists() {
        SearchGameActivity activity = (SearchGameActivity) getActivity();
        assertNotNull(activity);
    }


}
