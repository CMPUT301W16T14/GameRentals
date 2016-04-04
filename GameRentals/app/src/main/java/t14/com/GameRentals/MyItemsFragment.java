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
import android.widget.ListView;
import android.widget.RadioButton;

import java.io.Serializable;

/**
 * This activity displays the current user's games.
 * @see GameRefList
 * @see GameList
 * @see Game
 *
 */
public class MyItemsFragment extends Fragment implements View.OnClickListener {
    private GameList gameList;
    private ListView myItems;
    private ArrayAdapter<Game> adapter;
    private User currentUser;

    @Override
    /** Called when the activity view is created */
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.my_items, container,false);
        //Initial setup of buttons and list
        Button addButton = (Button)v.findViewById(R.id.AddButton);
        Button testData = (Button)v.findViewById(R.id.addTestDataButton);

        //Comment this out if want to generate test data
        testData.setVisibility(View.GONE);
        testData.setEnabled(false);

        myItems = (ListView)v.findViewById(R.id.myItems);
        RadioButton bidCheckBox = (RadioButton)v.findViewById(R.id.withBidCheckBox);
        RadioButton allCheckBox = (RadioButton)v.findViewById(R.id.withAllCheckBox);
        RadioButton borrowedCheckBox = (RadioButton)v.findViewById(R.id.withLentCheckBox);
        RadioButton availableCheckBox = (RadioButton)v.findViewById(R.id.withAvailableCheckBox);


        //allCheckBox.setChecked(true);

        addButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Game test = new Game("", "", currentUser.getUserName());
                Intent intent = new Intent(getActivity(), AddGameActivity.class);
                intent.putExtra("test", (Serializable)test);
                startActivity(intent);
                adapter.notifyDataSetChanged();
            }
        });

        //Generate test data
        testData.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                CreateTestData test = new CreateTestData();
                test.createObjects();
                //test.setGameStatuses();
            }
        });

        //Handle items on list being clicked
        myItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                AlertDialog.Builder adb = new AlertDialog.Builder(getActivity());
                final Game selectedGame = gameList.getGame(position);
                final int pos = position;
                //Handle if game clicked has available status
                if(selectedGame.getStatus() == GameController.STATUS_AVAILABLE){
                    //Verify that user wants to edit selected game
                    adb.setMessage("Do you want to view the game or edit?");
                    adb.setCancelable(true);
                    adb.setPositiveButton("VIEW", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            //Switch to edit games screen
                            Intent intent = new Intent(getActivity(), ViewGameActivity.class);

                            intent.putExtra("Game", (Serializable)selectedGame);
                            startActivity(intent);
                        }
                    });
                    adb.setNegativeButton("EDIT", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //Switch to edit games screen
                            Intent intent = new Intent(getActivity(), EditGameActivity.class);
                            intent.putExtra("currentUser",currentUser);
                            intent.putExtra("Game", (Serializable)selectedGame);
                            startActivity(intent);
                        }
                    });
                    adb.show();
                }
                //If game clicked has bidded status
                else if(selectedGame.getStatus() == GameController.STATUS_BIDDED){
                    //Check if user wants to edit selected game or view the bids on it
                    adb.setMessage("Do you want to view the game, edit it, or view bids?");
                    adb.setCancelable(true);
                    adb.setPositiveButton("VIEW GAME", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //Switch to edit games screen

                            Intent intent = new Intent(getActivity(), ViewGameActivity.class);

                            intent.putExtra("Game", (Serializable) selectedGame);
                            startActivity(intent);
                        }
                    });
                    adb.setNegativeButton("EDIT GAME", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //TODO:Go to view bids activity
                            Intent intent = new Intent(getActivity(), EditGameActivity.class);

                            intent.putExtra("Game", (Serializable) selectedGame);
                            startActivity(intent);
                        }
                    });
                    adb.setNeutralButton("VIEW BIDS", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //TODO:Go to view bids activity
                            Intent intent = new Intent(getActivity(), ViewBidsListActivity.class);
                            intent.putExtra("gamePosition", pos);
                            startActivity(intent);
                        }
                    });
                    adb.show();
                }
                //If game clicked has borrowed status
                else if(selectedGame.getStatus() == GameController.STATUS_BORROWED){
                    //Verify that user wants to edit selected item
                    adb.setMessage("Do you want to view the game or edit?");
                    adb.setCancelable(true);
                    adb.setPositiveButton("VIEW", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //Change to edit game screen
                            Intent intent = new Intent(getActivity(), ViewGameActivity.class);

                            intent.putExtra("Game", (Serializable)selectedGame);
                            startActivity(intent);
                        }
                    });
                    adb.setNegativeButton("EDIT", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //Change to edit game screen
                            Intent intent = new Intent(getActivity(), EditGameActivity.class);
                            intent.putExtra("currentUser",currentUser);
                            intent.putExtra("Game", (Serializable)selectedGame);
                            startActivity(intent);
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
    /**Called when app returns to this screen */
    public void onResume(){
        super.onResume();
        //TODO:For some reason crashes sometimes so disabled for now
        gameList.copyRefListToGames(currentUser.getMyGames());
        adapter.notifyDataSetChanged();
    }

    @Override
    /**Called when activity is first created */
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        currentUser = UserController.getCurrentUser();
        gameList = new GameList();
        gameList.copyRefListToGames(currentUser.getMyGames());
    }

    @Override
    /**Called when activity starts */
    public void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        //Set up adapter
        currentUser = UserController.getCurrentUser();
        gameList = new GameList();
        gameList.copyRefListToGames(currentUser.getMyGames());
        adapter = new ArrayAdapter<Game>(getActivity().getApplicationContext(),
                R.layout.game_list, gameList.getList());
        myItems.setAdapter(adapter);
    }

    /** This method handles the clicking of radio buttons to sort items being displayed
     *
     *  @param view The view that the buttons exist in
     */
    @Override
    public void onClick(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            //Display All user's games
            case R.id.withAllCheckBox:
                if (checked) {
                    gameList.copyRefListToGames(currentUser.getMyGames());
                    adapter.notifyDataSetChanged();
                }
                break;
            //Display user's available games
            case R.id.withAvailableCheckBox:
                if (checked){
                    gameList.copyRefListToGames(UserController.getAvailable(currentUser.getMyGames()));
                    adapter.notifyDataSetChanged();
                }
                break;
            //Display user's games with bids on them
            case R.id.withBidCheckBox:
                if(checked){
                    gameList.copyRefListToGames(UserController.getBidded(currentUser.getMyGames()));
                    adapter.notifyDataSetChanged();
                }
                break;
            //Display user's games that are currently lent out
            case R.id.withLentCheckBox:
                if(checked){
                    gameList.copyRefListToGames(UserController.getBorrowed(currentUser.getMyGames()));
                    adapter.notifyDataSetChanged();
                }
                break;
        }
    }

}
