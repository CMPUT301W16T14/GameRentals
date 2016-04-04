package t14.com.GameRentals;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

/**
 * This allows users to cancel a bid that they have offered if it has not yet been accepted or rejected.
 * @see Bid
 * @see ElasticsearchGameController
 */
public class CancelBidActivity extends Activity {

    private User currentUser;
    private GameList biddedItems;
    private Game game;

    private TextView gameNameText;
    private TextView descriptionText;
    private TextView gameStatus;
    private TextView gameOwner;

    private Button cancelBidButton;
    ////////for UI test
    public TextView getGameNameText(){
        return gameNameText;
    }
    public TextView getDescriptionText(){
        return descriptionText;
    }

    public TextView getGameOwner() {
        return gameOwner;
    }

    public Button getCancelBidButton() {
        return cancelBidButton;
    }

    public TextView getGameStatus() {
        return gameStatus;
    }
    ///////for UI test

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cancel_bid);

        gameNameText = (TextView)findViewById(R.id.gameNameText);
        descriptionText = (TextView)findViewById(R.id.descriptionText);

        gameStatus = (TextView)findViewById(R.id.gameStatus);

        gameNameText.setEnabled(false);
        gameNameText.setTextColor(Color.BLACK);
        descriptionText.setEnabled(false);
        descriptionText.setTextColor(Color.BLACK);
        gameStatus.setEnabled(false);
        gameStatus.setTextColor(Color.BLACK);

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
        final Button returnButton = (Button) findViewById(R.id.cancelBidReturnButton);

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

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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
