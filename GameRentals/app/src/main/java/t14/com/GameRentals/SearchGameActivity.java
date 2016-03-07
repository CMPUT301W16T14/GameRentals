package t14.com.GameRentals;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class SearchGameActivity extends ActionBarActivity {
    private GameList searchGameList;
    private ListView searchList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_game);
        searchList = (ListView)findViewById(R.id.BorrowedItems);

        searchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(SearchGameActivity.this, BidOnActivity.class);
                startActivity(intent);
                Toast.makeText(SearchGameActivity.this, "View The Details", Toast.LENGTH_SHORT).show();
            }

        });
    }

}
