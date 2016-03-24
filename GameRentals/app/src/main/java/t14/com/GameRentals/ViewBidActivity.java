package t14.com.GameRentals;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.concurrent.ExecutionException;

/**
 * Created by yourui on 3/3/16.
 * uc 05.06.01 & 05.07.01
 */
public class ViewBidActivity extends Activity {
    private Bid bid;
    private Game game;
    private BidList bidList;
    private User currentUser;
    private User bidMaker = null;
    private Button cancelButton;
    private Button acceptButton;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_bid);

        final EditText gameNameEdit = (EditText) findViewById(R.id.BidGameName);
        final EditText gameDescriptionEdit = (EditText) findViewById(R.id.BidGameDescription);
        final EditText bidUser = (EditText) findViewById(R.id.bidUser);
        final EditText bidRate = (EditText) findViewById(R.id.bidRate);
        final int gamePosition = getIntent().getExtras().getInt("gamePosition");
        final int bidPosition = getIntent().getExtras().getInt("bidPosition");

        currentUser = (User) getIntent().getExtras().get("currentUser");

        gameNameEdit.setText(currentUser.getMyGames().getGame(gamePosition).getGameName());
        gameDescriptionEdit.setText(currentUser.getMyGames().getGame(gamePosition).getDescription());

        bidList = currentUser.getMyGames().getGame(gamePosition).getBidList();
        bid = bidList.getItem(bidPosition);
        game = currentUser.getMyGames().getGame(gamePosition);

        String bidUserID = bid.getBidMaker();

        ElasticSearchUsersController.GetUserByIDTask getUserByIDTask = new ElasticSearchUsersController.GetUserByIDTask();
        getUserByIDTask.execute(bidUserID);


        try {
            bidMaker = getUserByIDTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        bidUser.setText(bidMaker.getUserName());
        bidRate.setText(Double.toString(bid.getRate()));

        cancelButton = (Button)findViewById(R.id.CancelBidButton);
        acceptButton = (Button)findViewById(R.id.acceptBidButton);

        final int length = bidList.getSize();

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                game.getBidList().getItem(bidPosition).setAccepted(false);

                ElasticsearchGameController.EditGameTask editGameTask = new ElasticsearchGameController.EditGameTask();
                editGameTask.execute(game);
                finish();

            }
        });

        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int i;
                for(i = 0; i < length; i++){
                    if (bidList.getItem(i).isAccepted()){
                        AlertDialog.Builder adb = new AlertDialog.Builder(ViewBidActivity.this);
                        adb.setTitle("Alert");
                        adb.setMessage("You can't accept it cause you have already accepted other bid");
                        break;
                    }
                }
                if (length == i) {
                    game.getBidList().getItem(bidPosition).setAccepted(true);
                    game.setStatus(2);
                    bidMaker.getBorrowedItems().addGame(game.getGameID());
                    ElasticSearchUsersController.EditUserTask ese = new ElasticSearchUsersController.EditUserTask();
                    ese.execute(bidMaker);
                    ElasticsearchGameController.EditGameTask editGameTask = new ElasticsearchGameController.EditGameTask();
                    editGameTask.execute(game);
                    finish();
                }
            }
        });

    }

    @Override
    protected void onStart(){
        super.onStart();

    }

}
