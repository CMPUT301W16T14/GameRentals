package t14.com.GameRentals;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

/** This activity handles adding games to a user's list of games.
 *
 */

public class AddGameActivity extends Activity {
    private EditText gameName;
    private EditText gameDescription;
    private User currentUser;
    private Game game;
    private static int RESULT_LOAD_IMAGE = 1;


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

        ImageButton imageButton = (ImageButton) findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View arg0){
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);


            }
        });

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
                    //currentUser.getMyGames().addGame(game);
                    //Update user and add game to server
                    //addTestData();
                    updateServer();
                    //String gameID = game.getGameID();
                    //currentUser.getMyGames().addGame(gameID);
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
    public ImageView getImageProfile(){
        return (ImageView) findViewById(R.id.profile_image);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data){
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            ImageView imageView = getImageProfile();
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            imageView.setTag("Changed");


        }
    }

    /** Add game to server, and update current user on server to reflect added game */
    public void updateServer(){
        ElasticsearchGameController.AddGameTask addGameTask = new ElasticsearchGameController.AddGameTask();
        addGameTask.execute(game);
        //ElasticsearchGameController.EditGameTask editGameTask = new ElasticsearchGameController.EditGameTask();
        //editGameTask.execute(game);

        //ElasticSearchUsersController.EditUserTask ese = new ElasticSearchUsersController.EditUserTask();
        //ese.execute(currentUser);
    }
/*
    public void addTestData(){
        User test = new User("Dude", "dude", "123");
        Game mario = new Game("Mario", "Platform", test);
        mario.setStatus(GameController.STATUS_BORROWED);
        test.getMyGames().addGame(mario);
        currentUser.getBorrowedItems().addGame(mario);
        mario.setBorrower(currentUser);
        ElasticsearchGameController.AddGameTask addGameTask = new ElasticsearchGameController.AddGameTask();
        addGameTask.execute(mario);
        ElasticSearchUsersController.EditUserTask ese = new ElasticSearchUsersController.EditUserTask();
        ese.execute(currentUser);

        ElasticSearchUsersController.AddUserTask esa = new ElasticSearchUsersController.AddUserTask();
        esa.execute(test);
        ElasticSearchUsersController.EditUserTask ese2 = new ElasticSearchUsersController.EditUserTask();
        ese2.execute(test);

    }*/

}
