package t14.com.GameRentals;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

/**
 * Created by yourui on 3/11/16.
 */
public class CancelBidActivity extends Activity {

    private User currentUser;
    private GameList biddedItems;
    private Game game;

    private EditText gameNameText;
    private EditText descriptionText;
    private EditText gameStatus;

    private TextView gameOwner;

    private Button cancelBidButton;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cancel_bid);

        gameNameText = (EditText)findViewById(R.id.gameNameText);
        descriptionText = (EditText)findViewById(R.id.descriptionText);

        gameStatus = (EditText)findViewById(R.id.gameStatus);

        gameOwner = (TextView)findViewById(R.id.gameOwner);

        biddedItems = new GameList();
        currentUser = UserController.getCurrentUser();
        biddedItems.copyRefListToGames(currentUser.getBiddedItems());
        int position = getIntent().getExtras().getInt("bidPosition");
        game = biddedItems.getGame(position);

        gameNameText.setText(game.getGameName());
        descriptionText.setText(game.getDescription());
        ElasticSearchUsersController.GetUserTask getUserTask = new ElasticSearchUsersController.GetUserTask();
        getUserTask.execute(game.getOwner());
        try {
            gameOwner.setText(getUserTask.get().getUserName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Bid bid = game.getBidList().getBid(currentUser);

        gameStatus.setText(bid.TransformIsAccepted());

        cancelBidButton = (Button)findViewById(R.id.CancelBidButton);

        cancelBidButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(game.getStatus() == 2 && currentUser.getUserName().equals(game.getBorrower())){
                    Toast.makeText(CancelBidActivity.this,"it is borrowed, can't be deleted",Toast.LENGTH_SHORT).show();
                }
                else{
                    game.getBidList().RemoveBid(currentUser);
                    currentUser.getBiddedItems().removeGame(game.getGameID());
                    if(game.getBidList().getSize() == 0){
                        game.setStatus(0);
                    }
                    ElasticSearchUsersController.EditUserTask ese = new ElasticSearchUsersController.EditUserTask();
                    ese.execute(currentUser);
                    ElasticsearchGameController.EditGameTask editGameTask = new ElasticsearchGameController.EditGameTask();
                    editGameTask.execute(game);

                    Toast.makeText(CancelBidActivity.this,"cancel this bid",Toast.LENGTH_SHORT).show();
                    finish();
                }

            }
        });

        gameOwner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Go to borrower's profile
                //User gameOwner = UserController.getUser(game.getOwner());
                Intent intent = new Intent(CancelBidActivity.this, ViewProfileActivity.class);
                intent.putExtra("Username", game.getOwner());
                startActivity(intent);
            }
        });

    }
}
