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
 * The fragment shows all games that are borrowed by the user in a list.
 * <p>
 * @author aredmond
 */
public class BorrowFragment extends Fragment {

    private View v;
    private Button searchButton;
    private EditText searchText;
    private ListView borrowedGameList;
    private GameList borrowedGames;
    private ArrayAdapter<Game> adapter;

    /**
     * On creation, the view has one button used to confirm the the search parameters, one edittext to hold
     * terms to be searched, and one listview that displays all the games that are returned.
     * <p>
     *
     * @param inflater Instantiates a layout XML file into its corresponding View objects.
     * @param container Hold components together to form the design of your application.
     * @param savedInstanceState The saved data that the system uses to restore the previous state.
     * @return The view
     *
     */
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.borrow, container, false);
        searchButton = (Button) v.findViewById(R.id.SearchButton);

        searchText = (EditText) v.findViewById(R.id.SearchText);
        borrowedGameList = (ListView)v.findViewById(R.id.BorrowedItems);

        searchButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String sSearchText = searchText.getText().toString();
                Intent intent = new Intent(getActivity(), SearchGameActivity.class);
                intent.putExtra("SEARCH_TERM", sSearchText);
                startActivity(intent);
            }
        });

        return v;
    }

    /**
     * On start, the fragment sets the adapter so that the view shows the most up-to-date list of
     * borrowed games.
     * <p>
     */
    @Override
    public void onStart() {
        super.onStart();
        borrowedGames.copyRefListToGames(UserController.getCurrentUser().getBorrowedItems());
        adapter = new ArrayAdapter<Game>(getActivity().getApplicationContext(),
                R.layout.game_list, borrowedGames.getList());
        borrowedGameList.setAdapter(adapter);
    }


}
