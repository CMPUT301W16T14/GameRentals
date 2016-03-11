package t14.com.GameRentals;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

/**
 * Created by yourui on 3/4/16.
 */
public class BidOnActivity extends Activity {
    private Game game;
    private int gamePosition;
    private ArrayList<Game> returnedGames;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        gamePosition = getIntent().getExtras().getInt("gamePosition");


        setContentView(R.layout.search_result_for_bid);
        Button bidButton = (Button)findViewById(R.id.BidButton);

        bidButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BidOnActivity.this, BidOnGameActivity.class);
                startActivity(intent);
            }
        });
    }
}
