package t14.com.GameRentals;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

public class ProfileMain extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
