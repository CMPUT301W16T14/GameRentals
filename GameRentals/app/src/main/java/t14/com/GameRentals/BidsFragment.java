package t14.com.GameRentals;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by yourui on 3/2/16.
 */
public class BidsFragment  extends Fragment {
    private User currentUser;
    private GameList biddedItems;
    private ListView biddedItemsView;
    private ArrayAdapter<Game> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.bids, container,false);

        currentUser = UserController.getCurrentUser();
        biddedItems = currentUser.getBiddedItems();

        biddedItemsView = (ListView)v.findViewById(R.id.biddedItemList);

        //TODO
        return v;
    }

    @Override
    public void onStart(){
        super.onStart();
        //TODO: SET THE BIDLIST TO THE ONE IN SERVER
        adapter = new ArrayAdapter<Game>(getActivity().getApplicationContext(),R.layout.game_list,biddedItems.getList());
        biddedItemsView.setAdapter(adapter);
    }
}
