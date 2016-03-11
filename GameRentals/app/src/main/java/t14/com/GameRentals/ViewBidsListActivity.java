package t14.com.GameRentals;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

/**
 * Created by yourui on 3/4/16.
 * user case 05.05.01
 */
public class ViewBidsListActivity extends Activity {

    private BidList bidList =  new BidList();
    private ListView bidListView;


    @Override
    public void onCreate(Bundle SavedInstanceState){
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.view_bids_list);

        bidListView = (ListView)findViewById(R.id.bidListView);

        bidListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int position = i;

                Intent intent = new Intent(ViewBidsListActivity.this, ViewBidActivity.class);
                intent.putExtra("position",position);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onStart(){
        super.onStart();
        //TODO: SET THE BIDLIST TO THE ONE IN SERVER
    }

}
