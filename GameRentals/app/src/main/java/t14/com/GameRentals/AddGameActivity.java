package t14.com.GameRentals;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddGameActivity extends Activity {
    private EditText gameName;
    private EditText gameDescription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_game);
        final User currentUser = UserController.getCurrentUser();
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
                    Game temp = new Game(name, description, currentUser);
                    currentUser.getMyGames().addGame(temp);
                    ElasticSearchUsersController.EditUserTask ese = new ElasticSearchUsersController.EditUserTask();
                    ese.execute(currentUser);
                    finish();
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
