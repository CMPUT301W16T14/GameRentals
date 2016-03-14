package t14.com.GameRentals;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by yourui on 3/11/16.
 */
public class CancelBidActivity extends Activity {

    private User currentUser;
    private GameList biddedItems;
    private Game game;

    private Button cancelBidButton;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        currentUser = UserController.getCurrentUser();
        biddedItems = currentUser.getBiddedItems();
        int position = getIntent().getExtras().getInt("bidPosition");
        game = biddedItems.getGame(position);

        cancelBidButton = (Button)findViewById(R.id.CancelBidButton);

        cancelBidButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                game.getBidList().RemoveBid(game);
                currentUser.getBiddedItems().removeGame(game);
                ElasticSearchUsersController.EditUserTask ese = new ElasticSearchUsersController.EditUserTask();
                ese.execute(currentUser);
                Toast.makeText(CancelBidActivity.this,"cancel this bid",Toast.LENGTH_SHORT);
            }
        });

    }
}
