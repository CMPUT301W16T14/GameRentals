package t14.com.GameRentals;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    private EditText gameOwner;

    private Button cancelBidButton;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cancel_bid);

        gameNameText = (EditText)findViewById(R.id.gameNameText);
        descriptionText = (EditText)findViewById(R.id.descriptionText);
        gameOwner = (EditText)findViewById(R.id.gameOwner);

        biddedItems = new GameList();
        currentUser = (User) getIntent().getExtras().get("currentUser");
        biddedItems.copyRefListToGames(currentUser.getBiddedItems());
        int position = getIntent().getExtras().getInt("bidPosition");
        game = biddedItems.getGame(position);

        gameNameText.setText(game.getGameName());
        descriptionText.setText(game.getDescription());
        ElasticSearchUsersController.GetUserByIDTask getUserTask = new ElasticSearchUsersController.GetUserByIDTask();
        getUserTask.execute(game.getOwner());
        try {
            gameOwner.setText(getUserTask.get().getUserName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        cancelBidButton = (Button)findViewById(R.id.CancelBidButton);

        cancelBidButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(game.getStatus() == 2 && currentUser.getID().equals(game.getBorrower())){
                    Toast.makeText(CancelBidActivity.this,"it is borrowed, can't be deleted",Toast.LENGTH_SHORT).show();
                }
                else{
                    game.getBidList().RemoveBid(currentUser);
                    currentUser.getBiddedItems().removeGame(game.getGameID());
                    ElasticSearchUsersController.EditUserTask ese = new ElasticSearchUsersController.EditUserTask();
                    ese.execute(currentUser);
                    ElasticsearchGameController.EditGameTask editGameTask = new ElasticsearchGameController.EditGameTask();
                    editGameTask.execute(game);
                    Toast.makeText(CancelBidActivity.this,"cancel this bid",Toast.LENGTH_SHORT).show();
                    finish();
                }

            }
        });

    }
}
