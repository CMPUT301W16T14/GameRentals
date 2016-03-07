package t14.com.GameRentals;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

/**
 * Created by yourui on 3/2/16.
 */
public class BorrowFragment extends Fragment {

    private View v;
    private Button searchButton;
    private EditText searchText;
    private ListView borrowedGameList;
    private GameList borrowedGames;
    private ArrayAdapter<Game> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.borrow, container, false);
        searchButton = (Button) v.findViewById(R.id.SearchButton);
        searchText = (EditText)v.findViewById(R.id.SearchText);
        borrowedGameList = (ListView)v.findViewById(R.id.BorrowedItems);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cSearchText = searchText.getText().toString();
                Intent intent = new Intent(getActivity(), SearchGameActivity.class);
                intent.putExtra("SEARCH_TERM", cSearchText);
                startActivity(intent);
            }
        });


        return v;
    }

    /*@Override
    public void onStart() {
        super.onStart();
        borrowedGames = UserController.getCurrentUser().getBorrowedItems();
        adapter = new ArrayAdapter<Game>(getActivity().getApplicationContext(),
                R.layout.game_list, borrowedGames.getList());
        borrowedGameList.setAdapter(adapter);
    }*/


}
