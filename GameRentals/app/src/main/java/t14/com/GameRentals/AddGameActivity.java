package t14.com.GameRentals;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddGameActivity extends Activity {
    private EditText gameName;
    private EditText gameDescription;
    private User currentUser;
    private Game game;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_game);
        currentUser = UserController.getCurrentUser();
        game = (Game) getIntent().getSerializableExtra("test");
        gameName = (EditText)findViewById(R.id.addGameNameEditText);
        gameDescription = (EditText)findViewById(R.id.addGameDescriptionEditText);
        Button okButton = (Button)findViewById(R.id.addGameOkButton);
        Button cancelButton = (Button)findViewById(R.id.addGameCancelButton);

        okButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String name = gameName.getText().toString();
                String description = gameDescription.getText().toString();
                if(name.equalsIgnoreCase("")){
                    gameName.setError("Game name cannot be empty");
                }
                else if(description.equalsIgnoreCase("")){
                    gameDescription.setError("Game description cannot be empty");
                }
                //else, proper input
                else{
                    //game.setGameName(name);
                    //tempGame.setDescription(description);
                    //Game game2 = new Game(name, description, currentUser);
                    //game2.copyGame(temp);
                    //currentUser.getMyGames().addGame(tempGame);
                    //updateServer();
                    //ElasticSearchUsersController.EditUserTask ese = new ElasticSearchUsersController.EditUserTask();
                    //ese.execute(currentUser);
                    //ElasticsearchGameController.AddGameTask addGameTask = new ElasticsearchGameController.AddGameTask();
                    //addGameTask.execute(temp);
                    //currentUser.addMyGame(temp);

                    //finish();

                    game.setGameName(name);
                    game.setDescription(description);
                    currentUser.getMyGames().addGame(game);
                    updateServer();
                    finish();

                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //finish();
            }
        });
    }

    public void updateServer(){
        ElasticsearchGameController.AddGameTask addGameTask = new ElasticsearchGameController.AddGameTask();
        addGameTask.execute(game);

        ElasticSearchUsersController.EditUserTask ese = new ElasticSearchUsersController.EditUserTask();
        ese.execute(currentUser);
    }
}
