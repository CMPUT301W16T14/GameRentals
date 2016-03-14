package t14.com.GameRentals;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/** This activity handles adding games to a user's list of games.
 *
 */

public class AddGameActivity extends Activity {
    private EditText gameName;
    private EditText gameDescription;
    private User currentUser;
    private Game game;


    @Override
    /** Called when activity is created */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_game);
        currentUser = UserController.getCurrentUser();
        game = (Game) getIntent().getSerializableExtra("test");
        gameName = (EditText)findViewById(R.id.addGameNameEditText);
        gameDescription = (EditText)findViewById(R.id.addGameDescriptionEditText);
        Button okButton = (Button)findViewById(R.id.addGameOkButton);
        Button cancelButton = (Button)findViewById(R.id.addGameCancelButton);

        /** Handle OK button being clicked */
        okButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String name = gameName.getText().toString();
                String description = gameDescription.getText().toString();
                //Verify that game name is not left empty
                if(name.equalsIgnoreCase("")){
                    gameName.setError("Game name cannot be empty");
                }
                //Verify that description is not left empty
                else if(description.equalsIgnoreCase("")){
                    gameDescription.setError("Game description cannot be empty");
                }
                //else, proper input
                else{
                    game.setGameName(name);
                    game.setDescription(description);
                    currentUser.getMyGames().addGame(game);
                    //Update user and add game to server
                    updateServer();
                    finish();
                }
            }
        });

        /** Return to previous screen when cancel button is clicked */
        cancelButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /** Add game to server, and update current user on server to reflect added game */
    public void updateServer(){
        ElasticsearchGameController.AddGameTask addGameTask = new ElasticsearchGameController.AddGameTask();
        addGameTask.execute(game);

        ElasticSearchUsersController.EditUserTask ese = new ElasticSearchUsersController.EditUserTask();
        ese.execute(currentUser);
    }
}
