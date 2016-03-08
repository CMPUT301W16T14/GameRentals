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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by yourui on 3/2/16.
 * user case 01.01.01 ~ 01.05.01 & 05.04.01
 */
public class MyItemsFragment extends Fragment {
    private GameList gameList;
    private ListView myItems;
    private ArrayAdapter<Game> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        //For testing
        User test = new User("Bill", "hi@hi.com", "9");
        UserController.setUser(test);
        //

        gameList = new GameList();
        View v = inflater.inflate(R.layout.my_items, container,false);
        Button addButton = (Button)v.findViewById(R.id.AddButton);
        myItems = (ListView)v.findViewById(R.id.myItems);
        CheckBox bidCheckBox = (CheckBox)v.findViewById(R.id.withBidCheckBox);
        CheckBox allCheckBox = (CheckBox)v.findViewById(R.id.withAllCheckBox);
        CheckBox borrowedCheckBox = (CheckBox)v.findViewById(R.id.withLentCheckBox);
        CheckBox availableCheckBox = (CheckBox)v.findViewById(R.id.withAvailableCheckBox);
        addButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddGameActivity.class);
                startActivity(intent);
            }
        });

        //Set up adapter
        gameList = UserController.getCurrentUser().getBorrowedItems();
        adapter = new ArrayAdapter<Game>(getActivity().getApplicationContext(),
                R.layout.game_list, gameList.getList());
        myItems.setAdapter(adapter);

        //For testing
        User user = UserController.getCurrentUser();
        Game zelda = new Game("Zelda", "Action RPG", user);
        gameList.addGame(zelda);
        adapter.notifyDataSetChanged();
        //

        //Handle items on list being clicked
        myItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                AlertDialog.Builder adb = new AlertDialog.Builder(getActivity());
                if (!((CheckBox) view).isChecked()) {
                    adb.setMessage("Do you want to edit it?");
                    adb.setCancelable(true);

                    adb.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent = new Intent(getActivity(), EditGameActivity.class);
                            startActivity(intent);
                            Toast.makeText(getActivity(), "edit", Toast.LENGTH_SHORT).show();
                        }
                    });
                    adb.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    adb.show();
                } else {
                    adb.setMessage("Do you want to view the bids?");
                    adb.setCancelable(true);

                    adb.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent = new Intent(getActivity(), EditGameActivity.class);
                            startActivity(intent);
                            Toast.makeText(getActivity(), "edit", Toast.LENGTH_SHORT).show();
                        }
                    });
                    adb.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                }
            }
        });
        bidCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((CheckBox) view).isChecked()) {
                    //TODO(Margaret will do it): Show my_items with bids(sort out games whose status is bidded)
                    Toast.makeText(getActivity(), "show My Games With Bids", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getActivity(), "Show all my Games", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //Show all games
        allCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((CheckBox) view).isChecked()) {
                    //TODO(Margaret will do it): Show my_items with bids(sort out games whose status is bidded)
                    Toast.makeText(getActivity(), "show My Games With Bids", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getActivity(), "Show all my Games", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //Show games that are being borrowed
        borrowedCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((CheckBox) view).isChecked()) {
                    //TODO(Margaret will do it): Show my_items with bids(sort out games whose status is bidded)
                    Toast.makeText(getActivity(), "show My Games With Bids", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getActivity(), "Show all my Games", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //Show available games
        availableCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((CheckBox) view).isChecked()) {
                    //TODO(Margaret will do it): Show my_items with bids(sort out games whose status is bidded)
                    Toast.makeText(getActivity(), "show My Games With Bids", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getActivity(), "Show all my Games", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return v;
    }
}