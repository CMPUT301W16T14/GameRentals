package t14.com.GameRentals;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
        int gamePosition = getIntent().getExtras().getInt("gamePosition");
        int bidPosition = getIntent().getExtras().getInt("bidPosition");

        bidList = currentUser.getMyGames().getGame(gamePosition).getBidList();
        bid = bidList.getItem(bidPosition);

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
