package t14.com.GameRentals;

import android.app.ActionBar;
import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

public class AddGameActivity extends FragmentActivity {
    ActionBar actionbar;
    ViewPager viewpager;
    PagerAdapter ft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_game);
    }

    //Formatted screen pops up for user to fill out descriptions for the new game.

}
