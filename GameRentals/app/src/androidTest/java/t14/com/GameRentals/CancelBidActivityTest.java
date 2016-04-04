package t14.com.GameRentals;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.test.ViewAsserts;
import android.widget.Button;
import android.widget.TextClock;
import android.widget.TextView;


import java.util.concurrent.ExecutionException;

/**
 * Created by margaret on 16/4/3.
 */
public class CancelBidActivityTest extends ActivityInstrumentationTestCase2 {

    Game game;
    BidList bidList;
    Bid bid;
    User biduser;
    User owner;
    Instrumentation instrumentation;
    Activity activity;

    public CancelBidActivityTest(){
        super(CancelBidActivity.class);
    }

    protected void setUp() throws Exception{
        super.setUp();
        instrumentation = getInstrumentation();
        activity = getActivity();
        /*owner = new User("testUser5","user@456.com","2124");
        biduser = new User("testUser6","user@123.com","1234");
        game = new Game("test","test",owner.getUserName());
        game.setStatus(1);
        bid = new Bid(biduser,10);
        bidList = new BidList();
        bidList.addBid(bid);
        game.setBidList(bidList);
        UserController.setUser(owner);

        ElasticsearchGameController.AddTestGameTask addTestGameTask = new ElasticsearchGameController.AddTestGameTask();
        addTestGameTask.execute(game);
        ElasticsearchGameController.EditGameTask editGameTask = new ElasticsearchGameController.EditGameTask();
        editGameTask.execute(game);

        owner.getMyGames().addGame(addTestGameTask.get());
        biduser.getBiddedItems().addGame(addTestGameTask.get());

        ElasticSearchUsersController.AddTestUserTask addTestUserTask = new ElasticSearchUsersController.AddTestUserTask();
        addTestUserTask.execute(owner);
        ElasticSearchUsersController.EditUserTask editUserTask = new ElasticSearchUsersController.EditUserTask();
        editUserTask.execute(owner);
        ElasticSearchUsersController.AddTestUserTask addTestUserTask1 = new ElasticSearchUsersController.AddTestUserTask();
        addTestUserTask1.execute(biduser);
        ElasticSearchUsersController.EditUserTask editUserTask1 = new ElasticSearchUsersController.EditUserTask();
        editUserTask1.execute(biduser);

        Intent intent = new Intent();
        intent.putExtra("bidPosition", 0);
        setActivityIntent(intent);*/

        /*Button cButton = (Button)activity.findViewById(R.id.CancelBidButton);
        TextView gameNameText = (TextView)activity.findViewById(R.id.gameNameText);
        TextView descriptionText = (TextView)activity.findViewById(R.id.descriptionText);
        TextView gameOwner = (TextView)activity.findViewById(R.id.gameOwner);
        TextView gameStatus = (TextView)activity.findViewById(R.id.gameStatus);*/

    }

    public void testViewVisible() {
        owner = new User("testUser5","user@456.com","2124");
        biduser = new User("testUser6","user@123.com","1234");
        game = new Game("test","test",owner.getUserName());
        game.setStatus(1);
        bid = new Bid(biduser,10);
        bidList = new BidList();
        bidList.addBid(bid);
        game.setBidList(bidList);

        ElasticsearchGameController.AddTestGameTask addTestGameTask = new ElasticsearchGameController.AddTestGameTask();
        addTestGameTask.execute(game);
        ElasticsearchGameController.EditGameTask editGameTask = new ElasticsearchGameController.EditGameTask();
        editGameTask.execute(game);

        try {
            owner.getMyGames().addGame(addTestGameTask.get());
            biduser.getBiddedItems().addGame(addTestGameTask.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        ElasticSearchUsersController.AddTestUserTask addTestUserTask = new ElasticSearchUsersController.AddTestUserTask();
        addTestUserTask.execute(owner);
        ElasticSearchUsersController.EditUserTask editUserTask = new ElasticSearchUsersController.EditUserTask();
        editUserTask.execute(owner);
        ElasticSearchUsersController.AddTestUserTask addTestUserTask1 = new ElasticSearchUsersController.AddTestUserTask();
        addTestUserTask1.execute(biduser);
        ElasticSearchUsersController.EditUserTask editUserTask1 = new ElasticSearchUsersController.EditUserTask();
        editUserTask1.execute(biduser);




        Intent intent = new Intent();
        intent.putExtra("bidPosition", 0);
        setActivityIntent(intent);

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

        assertTrue(owner.getMyGames().getSize() == 1);
        assertTrue(biduser.getBiddedItems().getSize() == 1);
        assertTrue(owner.getMyGames().getGame(0).getBidList().getSize() == 1);


        CancelBidActivity cancelBidActivity = (CancelBidActivity) getActivity();
        final Button cancelButton = cancelBidActivity.getCancelBidButton();
        cancelButton.performClick();
        assertTrue(owner.getMyGames().getGame(0).getBidList().getSize() == 0);
        assertTrue(biduser.getBiddedItems().getSize() == 0);

    }

}
