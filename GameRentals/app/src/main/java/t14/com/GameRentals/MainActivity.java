package t14.com.GameRentals;


import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

/** The main activity for an app that allows users to list games<br>
 * for other's to bid on and borrow. Users may also borrow items from<br>
 * other users.
 *
 */

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

        //editGameTask.execute(game);
        //addGameTask.execute(game);
        //currentUser = new User("NEWTEST","piupiupiu@123","123");

        //UserController.setUser(currentUser);
        //User serverUser = currentUser;

        //ElasticSearchUsersController.AddUserTask esa = new ElasticSearchUsersController.AddUserTask();
        //esa.execute(serverUser);
        //ElasticSearchUsersController.EditUserTask ese = new ElasticSearchUsersController.EditUserTask();
        //ese.execute(serverUser);
        /**Load user from server */
        ElasticSearchUsersController.GetUserTask esg = new ElasticSearchUsersController.GetUserTask();
        //TODO:Set this to load whatever username is given from login screen
        esg.execute("bug");

        try{
            loadedUser = (esg.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        //Set current user of app to user that logged in
        currentUser = loadedUser;

        //UserController.setUser(test);
        //Game test = new Game("PunchOut", "Fighting", currentUser.getID());
        //ElasticsearchGameController.AddGameTask addGameTask = new ElasticsearchGameController.AddGameTask();
        //addGameTask.execute(test);

        //ElasticsearchGameController.EditGameTask editGameTask = new ElasticsearchGameController.EditGameTask();
        //editGameTask.execute(paper);
        //String testID = paper.getGameID();
        //currentUser.getMyGames().addGame(testID);





        //ElasticSearchUsersController.EditUserTask ese2 = new ElasticSearchUsersController.EditUserTask();
        //ese2.execute(currentUser);
        /*Game zelda = new Game("mlgb8", "Action RPG", currentUser.getID());
        zelda.setStatus(GameController.STATUS_BIDDED);
        ElasticsearchGameController.AddGameTask addGameTask = new ElasticsearchGameController.AddGameTask();
        addGameTask.execute(zelda);
        ElasticsearchGameController.EditGameTask editGameTask = new ElasticsearchGameController.EditGameTask();
        editGameTask.execute(zelda);

        currentUser.getMyGames().addGame(zelda);

        Game chrono = new Game("mlgb9", "RPG", currentUser.getID());
        chrono.setStatus(GameController.STATUS_AVAILABLE);
        ElasticsearchGameController.AddGameTask addGameTask2 = new ElasticsearchGameController.AddGameTask();
        addGameTask2.execute(chrono);
        ElasticsearchGameController.EditGameTask editGameTask2 = new ElasticsearchGameController.EditGameTask();
        editGameTask2.execute(chrono);
       // currentUser.getMyGames().addGame(chrono);
        //editGameTask2.execute(chrono);

        Game roller = new Game("mlgb10", "Simulation", currentUser.getID());
        roller.setStatus(GameController.STATUS_BORROWED);
        ElasticsearchGameController.AddGameTask addGameTask3 = new ElasticsearchGameController.AddGameTask();
        addGameTask3.execute(roller);
        ElasticsearchGameController.EditGameTask editGameTask3 = new ElasticsearchGameController.EditGameTask();
        editGameTask3.execute(roller);*/

        //currentUser.getMyGames().addGame(roller);
        //editGameTask3.execute(roller);

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
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.activity_main_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if (id == R.id.settings_id){
            Intent intent = new Intent(this,ProfileMain.class);
            startActivity(intent);
            return true;
        }
        int id2 = item.getItemId();
        if (id2 == R.id.action_profile){
            Intent intent = new Intent(this,ProfileMain.class);
            startActivity(intent);
            return true;
        }
        switch(item.getItemId())
        {
            case R.id.settings_id:
                Toast.makeText(getApplicationContext(),"Settings option selected",Toast.LENGTH_LONG).show();
                return true;
            case R.id.search_id:
                Toast.makeText(getApplicationContext(),"Search option selected",Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
