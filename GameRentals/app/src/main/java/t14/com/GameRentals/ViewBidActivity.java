package t14.com.GameRentals;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.concurrent.ExecutionException;

/**
 *
 * View the bids other users are offering to borrow your game(s).
 * @see ViewBidsListActivity
 */
public class ViewBidActivity extends Activity {
    private Bid bid;
    private Game game;
    private BidList bidList;
    private User currentUser;
    private User bidMaker = null;
    private Button cancelButton;
    private Button acceptButton;

    /**
     * @see ElasticSearchUsersController #GetUserTask()
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_bid);

        final EditText gameNameEdit = (EditText) findViewById(R.id.BidGameName);
        final EditText gameDescriptionEdit = (EditText) findViewById(R.id.BidGameDescription);

        gameNameEdit.setEnabled(false);
        gameNameEdit.setTextColor(Color.BLACK);
        gameDescriptionEdit.setEnabled(false);
        gameDescriptionEdit.setTextColor(Color.BLACK);
        final TextView bidUser = (TextView) findViewById(R.id.bidUser);
        final EditText bidRate = (EditText) findViewById(R.id.bidRate);
        final int gamePosition = getIntent().getExtras().getInt("gamePosition");
        final int bidPosition = getIntent().getExtras().getInt("bidPosition");

        currentUser = UserController.getCurrentUser();

        gameNameEdit.setText(currentUser.getMyGames().getGame(gamePosition).getGameName());
        gameDescriptionEdit.setText(currentUser.getMyGames().getGame(gamePosition).getDescription());

        bidList = currentUser.getMyGames().getGame(gamePosition).getBidList();
        bid = bidList.getItem(bidPosition);
        game = currentUser.getMyGames().getGame(gamePosition);

        String bidUserID = bid.getBidMaker();

        ElasticSearchUsersController.GetUserTask getUserByIDTask = new ElasticSearchUsersController.GetUserTask();
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
        bidRate.setEnabled(false);
        bidRate.setTextColor(Color.BLACK);

        cancelButton = (Button)findViewById(R.id.CancelBidButton);
        acceptButton = (Button)findViewById(R.id.acceptBidButton);

        final int length = bidList.getSize();

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                game.getBidList().getItem(bidPosition).setAccepted(2);//decline
                int count = 0;
                if (game.getBidList().getSize() == 0) {
                    game.setStatus(0);
                }
                else {
                    for (int i = 0; i < game.getBidList().getSize(); i++) {
                        if (game.getBidList().getItem(i).isAccepted() == 2)
                            count++;
                    }
                    if (count == game.getBidList().getSize()) {
                        game.setStatus(0);
                    }
                }
                bidList.getItem(bidPosition).setAccepted(2);//decline
                ElasticsearchGameController.EditGameTask editGameTask = new ElasticsearchGameController.EditGameTask();
                editGameTask.execute(game);
                ElasticSearchUsersController.EditUserTask ese1 = new ElasticSearchUsersController.EditUserTask();
                ese1.execute(currentUser);
                finish();
            }
        });

        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int i;

                for(i = 0; i < length; i++){
                    if (bidList.getItem(i).isAccepted() == 1){
                        AlertDialog.Builder adb = new AlertDialog.Builder(ViewBidActivity.this);
                        adb.setTitle("Alert");
                        adb.setMessage("You can't accept it cause you have already accepted other bid");
                        finish();
                    }
                }

                if (length == i) {
                    game.getBidList().getItem(bidPosition).setAccepted(1);
                    game.setBorrower(bidMaker.getUserName());
                    game.setStatus(2);
                    bid.setAccepted(1);
                    bidMaker.getBorrowedItems().addGame(game.getGameID());
                    ElasticSearchUsersController.EditUserTask ese = new ElasticSearchUsersController.EditUserTask();
                    ese.execute(bidMaker);
                    ElasticSearchUsersController.EditUserTask ese1 = new ElasticSearchUsersController.EditUserTask();
                    ese1.execute(currentUser);
                    ElasticsearchGameController.EditGameTask editGameTask = new ElasticsearchGameController.EditGameTask();
                    editGameTask.execute(game);
                    finish();
                }
            }
        });

        bidUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Go to borrower's profile
                //User gameOwner = UserController.getUser(game.getOwner());
                Intent intent = new Intent(ViewBidActivity.this, ViewProfileActivity.class);
                intent.putExtra("Username", bidMaker.getUserName());
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart(){
        super.onStart();
    }

}
