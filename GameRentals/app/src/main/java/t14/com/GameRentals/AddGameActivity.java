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

/**
 * Users can add games to myGames that they wish to lend out.
 */

public class AddGameActivity extends Activity {
    private EditText gameName;
    private EditText gameDescription;
    private User currentUser;
    private Game game;
    private static int RESULT_LOAD_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_game);
        currentUser = (User)getIntent().getExtras().get("currentUser");
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
                    updateServer();
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

    /**
     * Uploads the game added to the server.
     * @see ElasticsearchGameController #AddGameTask()
     */
    public void updateServer(){
        ElasticsearchGameController.AddGameTask addGameTask = new ElasticsearchGameController.AddGameTask();
        addGameTask.execute(game);
    }
}
