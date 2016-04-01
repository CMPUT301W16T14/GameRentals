package t14.com.GameRentals;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class ViewMyGamesActivity extends Activity {
    private ListView myGamesList;
    private GameList games = new GameList();
    private ArrayAdapter<Game> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_my_games);
        Button addGameButton = (Button) findViewById(R.id.addGame);

        //For testing
        User test = new User("Bill", "hi@hi.com", "9");
        UserController.setUser(test);
        //

        //Set up adapter
        myGamesList = (ListView) findViewById(R.id.myGames);
        games.copyRefListToGames(UserController.getCurrentUser().getMyGames());
        adapter = new ArrayAdapter<Game>(this,
                R.layout.game_list, games.getList());
        myGamesList.setAdapter(adapter);

        //For testing
        User user = UserController.getCurrentUser();
        Game zelda = new Game("Zelda", "Action RPG", user.getID());
        games.addGame(zelda);
        adapter.notifyDataSetChanged();
        //

        final Intent i = new Intent(getApplicationContext(), AddGameActivity.class);
        //Open add game dialogue
        addGameButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                setResult(RESULT_OK);

                startActivity(i);
            }
        });

    }
}
