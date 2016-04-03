package t14.com.GameRentals;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by yourui on 3/2/16.
 * uc 05.02.01
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
        biddedItems = new GameList();
        currentUser = UserController.getCurrentUser();

        biddedItemsView = (ListView)v.findViewById(R.id.biddedItemList);

        biddedItems.copyRefListToGames(currentUser.getBiddedItems());
        adapter = new ArrayAdapter<Game>(getActivity().getApplicationContext(),R.layout.game_list,biddedItems.getList());
        biddedItemsView.setAdapter(adapter);

        biddedItemsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                AlertDialog.Builder adb = new AlertDialog.Builder(getActivity());
                adb.setTitle("Bidding");
                adb.setMessage("Do you want to view the details?");
                adb.setCancelable(true);
                final int position = i;
                adb.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getActivity(),CancelBidActivity.class);
                        intent.putExtra("currentUser",currentUser);
                        intent.putExtra("bidPosition",position);
                        startActivity(intent);
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

        return v;
    }

    @Override
    public void onStart(){
        super.onStart();
        //TODO: SET THE BIDLIST TO THE ONE IN SERVER
        biddedItems.copyRefListToGames(currentUser.getBiddedItems());
        adapter = new ArrayAdapter<Game>(getActivity().getApplicationContext(),R.layout.game_list,biddedItems.getList());
        biddedItemsView.setAdapter(adapter);
    }
}
