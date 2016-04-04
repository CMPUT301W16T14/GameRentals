package t14.com.GameRentals;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.osmdroid.util.GeoPoint;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.concurrent.ExecutionException;

/**
 * Created by yourui on 3/3/16.
 * uc 05.06.01 & 05.07.01
 */
public class ViewBidActivity extends Activity {

    private static final int PICK_GEOLOCATION_REQUEST = 1;
    private Bid bid;
    private Game game;
    private BidList bidList;
    private User currentUser;
    private User bidMaker = null;
    private GeoPoint retrievedPoint;
    private Button cancelButton;
    private Button acceptButton;
    private Button getLocationButton;

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
        getLocationButton = (Button)findViewById(R.id.GetLocationButton);

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
                if(retrievedPoint == null) {
                    Toast.makeText(ViewBidActivity.this, "Set Location First.", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (length == i) {
                        game.setLocation(retrievedPoint);
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
            }
        });

        getLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent locationIntent = new Intent(ViewBidActivity.this, LocationActivity.class);
                startActivityForResult(locationIntent, PICK_GEOLOCATION_REQUEST);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == PICK_GEOLOCATION_REQUEST ) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                // The user picked a contact.
                // The Intent's data Uri identifies which contact was selected.
                retrievedPoint = (GeoPoint) data.getExtras().getSerializable("MAP_POINT");
            }
        }
    }
}
