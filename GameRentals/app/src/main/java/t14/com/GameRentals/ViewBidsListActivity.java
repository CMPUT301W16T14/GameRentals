package t14.com.GameRentals;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by yourui on 3/4/16.
 * user case 05.05.01
 */
public class ViewBidsListActivity extends Activity {

    private ArrayList<Bid> bidList;
    private ListView bidListView;
    private User currentUser;
    private int gamePosition;
    private ArrayAdapter<Bid> adapter;


    @Override
    public void onCreate(Bundle SavedInstanceState){
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.view_bids_list);
        currentUser = UserController.getCurrentUser();

        gamePosition = getIntent().getExtras().getInt("gamePosition");
        bidListView = (ListView)findViewById(R.id.bidListView);

        bidList = new ArrayList<Bid>();
        bidList.addAll(currentUser.getMyGames().getGame(gamePosition).getBidList().getList());

        adapter = new ArrayAdapter<Bid>(this.getApplicationContext(), R.layout.game_list, bidList);
        bidListView.setAdapter(adapter);

        bidListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int position = i;

                Intent intent = new Intent(ViewBidsListActivity.this, ViewBidActivity.class);
                intent.putExtra("gamePosition",gamePosition);
                intent.putExtra("bidPosition",position);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onStart(){
        super.onStart();
        //TODO: SET THE BIDLIST TO THE ONE IN SERVER
        currentUser = UserController.getCurrentUser();
        bidList = new ArrayList<Bid>();
        bidList.addAll(currentUser.getMyGames().getGame(gamePosition).getBidList().getList());
        adapter = new ArrayAdapter<Bid>(this.getApplicationContext(),R.layout.game_list,bidList);
        bidListView.setAdapter(adapter);
    }

}
