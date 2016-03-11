package t14.com.GameRentals;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by yourui on 3/4/16.
 * user case 05.01.01
 */
public class BidOnGameActivity extends Activity {
    private Game game;
    private User currentuser;

    @Override
    public void onCreate(Bundle SavedInstanceState){
        super.onCreate(SavedInstanceState);
        Button okButton = (Button)findViewById(R.id.OKButton);
        setContentView(R.layout.bid_on_game);
        currentuser = UserController.getCurrentUser();


        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder adb = new AlertDialog.Builder(BidOnGameActivity.this);
                adb.setTitle("Bidding");
                adb.setMessage("Are you sure to bid it?");
                adb.setCancelable(true);
                adb.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        game.setStatus(1);//////////////don't know which is set status to bid
                    }
                });
                adb.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                adb.show();
            }
        });
    }
}
