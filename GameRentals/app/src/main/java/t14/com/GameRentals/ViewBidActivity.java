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
    private BidList bidList;
    private User currentUser;

    private Button cancelButton;
    private Button acceptButton;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_bid);

        currentUser = UserController.getCurrentUser();
        final EditText gameNameEdit = (EditText) findViewById(R.id.BidGameName);
        final EditText gameDescriptionEdit = (EditText) findViewById(R.id.BidGameDescription);
        final EditText bidUser = (EditText) findViewById(R.id.bidUser);
        final EditText bidRate = (EditText) findViewById(R.id.bidRate);
        int gamePosition = getIntent().getExtras().getInt("gamePosition");
        int bidPosition = getIntent().getExtras().getInt("bidPosition");

        gameNameEdit.setText(currentUser.getMyGames().getGame(gamePosition).getGameName());
        gameDescriptionEdit.setText(currentUser.getMyGames().getGame(gamePosition).getDescription());

        bidList = currentUser.getMyGames().getGame(gamePosition).getBidList();
        bid = bidList.getItem(bidPosition);
        final String gameName = currentUser.getMyGames().getGame(gamePosition).getGameName();

        String bidUserID = bid.getBidMaker();

        ElasticSearchUsersController.GetUserByIDTask getUserByIDTask = new ElasticSearchUsersController.GetUserByIDTask();
        getUserByIDTask.execute(bidUserID);

        User bidMaker = null;
        try {
            bidMaker = getUserByIDTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        bidUser.setText(bidMaker.getUserName());
        bidRate.setText((CharSequence) bidMaker.getBiddedItems().getGame(gameName));

        cancelButton = (Button)findViewById(R.id.CancelBidButton);
        acceptButton = (Button)findViewById(R.id.acceptBidButton);

        final int length = bidList.getSize();

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bid.setAccepted(false);
                ElasticSearchUsersController.EditUserTask ese = new ElasticSearchUsersController.EditUserTask();
                ese.execute(currentUser);
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
                    bid.setAccepted(true);
                    ElasticSearchUsersController.EditUserTask ese = new ElasticSearchUsersController.EditUserTask();
                    ese.execute(currentUser);
                }
            }
        });

    }

    @Override
    protected void onStart(){
        super.onStart();

    }

}
