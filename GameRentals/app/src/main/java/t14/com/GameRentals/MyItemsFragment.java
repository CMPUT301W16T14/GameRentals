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
import android.widget.RadioButton;
import android.widget.Toast;

/**
 * Created by yourui on 3/2/16.
 * user case 01.01.01 ~ 01.05.01 & 05.04.01
 */
public class MyItemsFragment extends Fragment implements View.OnClickListener {
    private GameList gameList;
    private ListView myItems;
    private ArrayAdapter<Game> adapter;
    private User currentUser;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //TODO: Keep track of which radio button is pressed so appropriate items are displayed
        //when switching screens

        //currentUser = UserController.getCurrentUser();

        //gameList = new GameList();
        View v = inflater.inflate(R.layout.my_items, container,false);
        Button addButton = (Button)v.findViewById(R.id.AddButton);
        myItems = (ListView)v.findViewById(R.id.myItems);
        RadioButton bidCheckBox = (RadioButton)v.findViewById(R.id.withBidCheckBox);
        RadioButton allCheckBox = (RadioButton)v.findViewById(R.id.withAllCheckBox);
        RadioButton borrowedCheckBox = (RadioButton)v.findViewById(R.id.withLentCheckBox);
        RadioButton availableCheckBox = (RadioButton)v.findViewById(R.id.withAvailableCheckBox);


        //allCheckBox.setChecked(true);

        addButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Game test = new Game("", "", currentUser);
                Intent intent = new Intent(getActivity(), AddGameActivity.class);
                intent.putExtra("test", test);
                startActivity(intent);
                adapter.notifyDataSetChanged();
            }
        });

        //Handle items on list being clicked
        myItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                AlertDialog.Builder adb = new AlertDialog.Builder(getActivity());
                final Game selectedGame = gameList.getList().get(position);
                final int pos = position;
                //Handle if game clicked has available status
                if(selectedGame.getStatus() == GameController.STATUS_AVAILABLE){
                    adb.setMessage("Do you want to edit it?");
                    adb.setCancelable(true);
                    adb.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getActivity(), EditGameActivity.class);
                            //Bundle bundle = new Bundle();
                            //TODO:Need to fix this, doesn't always give the correct item
                            //bundle.putSerializable("position", pos);
                            //intent.putExtras(bundle);
                            intent.putExtra("Game", selectedGame);
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
                }
                else if(selectedGame.getStatus() == GameController.STATUS_BIDDED){
                    adb.setMessage("Do you want to edit or view bids?");
                    adb.setCancelable(true);
                    adb.setPositiveButton("EDIT", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getActivity(), EditGameActivity.class);
                            intent.putExtra("Game", selectedGame);
                            startActivity(intent);
                            Toast.makeText(getActivity(), "edit", Toast.LENGTH_SHORT).show();
                        }
                    });
                    adb.setNegativeButton("VIEW BIDS", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //TODO:Go to view bids activity
                            Intent intent = new Intent(getActivity(), ViewBidsListActivity.class);
                            startActivity(intent);
                        }
                    });
                    adb.show();
                }
                else if(selectedGame.getStatus() == GameController.STATUS_BORROWED){
                    adb.setMessage("Do you want to edit it?");
                    adb.setCancelable(true);
                    adb.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getActivity(), EditGameActivity.class);
                            intent.putExtra("Game", selectedGame);
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
                }
            }
        });

        bidCheckBox.setOnClickListener(this);
        allCheckBox.setOnClickListener(this);
        availableCheckBox.setOnClickListener(this);
        borrowedCheckBox.setOnClickListener(this);
        return v;
    }

    @Override
    public void onResume(){
        super.onResume();
        gameList.copyList(currentUser.getMyGames());
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        currentUser = UserController.getCurrentUser();
        gameList = new GameList();
        gameList.copyList(currentUser.getMyGames());


        /*For testing
        Game zelda = new Game("Zelda", "Action RPG", UserController.getCurrentUser());
        zelda.setStatus(GameController.STATUS_BIDDED);
        UserController.getCurrentUser().getMyGames().addGame(zelda);

        Game chrono = new Game("Chrono Trigger", "RPG", UserController.getCurrentUser());
        chrono.setStatus(GameController.STATUS_AVAILABLE);
        UserController.getCurrentUser().getMyGames().addGame(chrono);

        Game ff = new Game("FF10", "RPG", UserController.getCurrentUser());
        ff.setStatus(GameController.STATUS_BORROWED);
        UserController.getCurrentUser().getMyGames().addGame(ff);
        */
    }

    @Override
    public void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        //Set up adapter
        adapter = new ArrayAdapter<Game>(getActivity().getApplicationContext(),
                R.layout.game_list, gameList.getList());
        myItems.setAdapter(adapter);
    }

    //This handles clicking of the radio buttons
    @Override
    public void onClick(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.withAllCheckBox:
                if (checked) {
                    //Toast.makeText(getActivity(), "Show All Games", Toast.LENGTH_SHORT).show();
                    gameList.copyList(currentUser.getMyGames());
                    adapter.notifyDataSetChanged();
                }
                break;
            case R.id.withAvailableCheckBox:
                if (checked){
                    //Toast.makeText(getActivity(), "Show Available Games", Toast.LENGTH_SHORT).show();
                    gameList.copyList(UserController.getAvailable(currentUser.getMyGames()));
                    adapter.notifyDataSetChanged();
                }
                break;
            case R.id.withBidCheckBox:
                if(checked){
                    //Toast.makeText(getActivity(), "Show Games With Bids", Toast.LENGTH_SHORT).show();
                    gameList.copyList(UserController.getBidded(currentUser.getMyGames()));
                    adapter.notifyDataSetChanged();
                }
                break;
            case R.id.withLentCheckBox:
                if(checked){
                    //Toast.makeText(getActivity(), "Show Games Being Lent Out", Toast.LENGTH_SHORT).show();
                    gameList.copyList(UserController.getBorrowed(currentUser.getMyGames()));
                    adapter.notifyDataSetChanged();
                }
                break;
        }
    }
}
