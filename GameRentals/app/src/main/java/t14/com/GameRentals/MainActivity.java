package t14.com.GameRentals;


import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;


@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class MainActivity extends FragmentActivity implements ActionBar.TabListener{
    ActionBar actionbar;
    ViewPager viewpager;
    PagerAdapter ft;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        User test = new User("Connor", "resler@ualberta.ca", "9");
        UserController.setUser(test);


        ElasticSearchUsersController.AddUserTask esa = new ElasticSearchUsersController.AddUserTask();
        esa.execute(test);

        //ElasticSearchUsersController.GetUserTask esg = new ElasticSearchUsersController.GetUserTask();
        //esg.execute("Bill");

        setContentView(R.layout.activity_main);
        viewpager = (ViewPager) findViewById(R.id.pager);
        ft = new PagerAdapter(getSupportFragmentManager());
        actionbar = getActionBar();
        viewpager.setAdapter(ft);
        actionbar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionbar.addTab(actionbar.newTab().setText("My Items").setTabListener(this));
        actionbar.addTab(actionbar.newTab().setText("Borrow").setTabListener(this));
        actionbar.addTab(actionbar.newTab().setText("Bids").setTabListener(this));
        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int arg0) {
                actionbar.setSelectedNavigationItem(arg0);
            }
            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub
            }
            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub
            }
        });
    }
    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
        // TODO Auto-generated method stub
    }
    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        viewpager.setCurrentItem(tab.getPosition());
    }
    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
        // TODO Auto-generated method stub
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        //For testing
        //User test = new User("Bill", "hi@hi.com", "9");

        //ElasticSearchUsersController.AddUserTask esa = new ElasticSearchUsersController.AddUserTask();
        //esa.execute(test);

       // ElasticSearchUsersController.GetUserTask esg = new ElasticSearchUsersController.GetUserTask();
       // esg.execute("Bill");
        //UserController.setUser(test);/////////for testing

        //
    }
}
