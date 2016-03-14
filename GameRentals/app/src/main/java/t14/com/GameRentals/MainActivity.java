package t14.com.GameRentals;


import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import java.util.concurrent.ExecutionException;


@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class MainActivity extends FragmentActivity implements ActionBar.TabListener{
    ActionBar actionbar;
    ViewPager viewpager;
    PagerAdapter ft;
    User currentUser;
    User loadedUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //currentUser = new User("Connor", "resler@ualberta.ca", "123");

        /*UserController.setUser(test);
        Game zelda = new Game("Zelda", "Action RPG", UserController.getCurrentUser());
        zelda.setStatus(GameController.STATUS_BIDDED);
        currentUser.getMyGames().addGame(zelda);

        Game chrono = new Game("Chrono Trigger", "RPG", UserController.getCurrentUser());
        chrono.setStatus(GameController.STATUS_AVAILABLE);
        currentUser.getMyGames().addGame(chrono);

        Game ff = new Game("FF10", "RPG", UserController.getCurrentUser());
        ff.setStatus(GameController.STATUS_BORROWED);
        currentUser.getMyGames().addGame(ff);
        UserController.setUser(currentUser);

        User serverUser = currentUser;
        */
        //ElasticSearchUsersController.AddUserTask esa = new ElasticSearchUsersController.AddUserTask();
        //esa.execute(serverUser);

        ElasticSearchUsersController.GetUserTask esg = new ElasticSearchUsersController.GetUserTask();
        esg.execute("Connor");

        try{
             loadedUser = (esg.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        currentUser = loadedUser;
        UserController.setUser(currentUser);
        assert(currentUser.getUserName().equalsIgnoreCase("Connor"));



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
