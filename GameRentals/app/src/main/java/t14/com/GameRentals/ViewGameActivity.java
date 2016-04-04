package t14.com.GameRentals;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;

/** This activity allows the user to edit games on their list of games. <br>
 * User will select a game from their list, and that game will be passed to this <br>
 * activity to edit.
 *
 */


public class ViewGameActivity extends Activity {

    private static final int GET_GEOLOCATION_REQUEST = 0;
    private static final int SET_GEOLOCATION_REQUEST = 1;
    private Game game;
    private User currentUser;
    private static int RESULT_LOAD_IMAGE = 1;
    boolean gameOwnedByCurrentUser = true;

    @Override
    /**Called when activity is created */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_game);
        currentUser = UserController.getCurrentUser();


        //Set up buttons and text boxes
        final EditText gameNameEdit = (EditText) findViewById(R.id.editGameNameEditText);
        final EditText gameDescriptionEdit = (EditText) findViewById(R.id.editGameDescriptionText);
        gameNameEdit.setEnabled(false);
        gameDescriptionEdit.setEnabled(false);
        gameNameEdit.setTextColor(Color.BLACK);
        gameDescriptionEdit.setTextColor(Color.BLACK);
        final Button returnButton = (Button) findViewById(R.id.viewGameReturnButton);
        final Button getLocationButton = (Button) findViewById(R.id.GetLocationButton);
        final TextView statusLabel = (TextView) findViewById(R.id.editGameStatus);
        final TextView borrowerName = (TextView) findViewById(R.id.borrowerName);
        final TextView borrowerNameLabel = (TextView) findViewById(R.id.borrowerNameLabel);
        final AlertDialog.Builder adb = new AlertDialog.Builder(this);

        borrowerName.setVisibility(View.INVISIBLE);
        borrowerNameLabel.setVisibility(View.INVISIBLE);

        game = (Game) getIntent().getSerializableExtra("Game");
        //TODO: Change to Margaret's version of getting game
        statusLabel.setText(game.getStatusString());

        //If game has borrowed status, enable the return button and show borrower name
        if(game.getStatus() == GameController.STATUS_BORROWED &&
                !game.getBorrower().equals(currentUser.getUserName())){
            borrowerName.setVisibility(View.VISIBLE);
            borrowerNameLabel.setVisibility(View.VISIBLE);
            borrowerName.setText(game.getBorrowerName());
            borrowerName.setClickable(true);
            //borrowerName.setText("Borrow name will go here when not NULL");
        }
        else if(game.getStatus() == GameController.STATUS_BORROWED &&
                game.getBorrower().equals(currentUser.getUserName())){
            borrowerName.setVisibility(View.VISIBLE);
            borrowerNameLabel.setVisibility(View.VISIBLE);
            borrowerNameLabel.setText("Owner Username: ");
            borrowerName.setText(game.getOwner());
            borrowerName.setClickable(true);
            gameOwnedByCurrentUser = false;
        }

        gameNameEdit.setText(game.getGameName());
        gameDescriptionEdit.setText(game.getDescription());

        //Only shows if item has borrowed status, and is used to indicate that game has been returned
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(game.getLocation() != null) {
                    Intent locationIntent = new Intent(ViewGameActivity.this, LocationActivity.class);
                    locationIntent.putExtra("GEO_POINT", (Serializable) game.getLocation());
                    locationIntent.putExtra("ENTER_CODE", "GET_GEOLOCATION_REQUEST");
                    startActivityForResult(locationIntent, GET_GEOLOCATION_REQUEST);
                }
                else {
                    Toast.makeText(ViewGameActivity.this, "No Location Set.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        borrowerName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Go to borrower's profile
                //User gameOwner = UserController.getUser(game.getOwner());
                Intent intent = new Intent(ViewGameActivity.this, ViewProfileActivity.class);
                if(gameOwnedByCurrentUser){
                    intent.putExtra("Username", game.getBorrower());

                }
                else{
                    intent.putExtra("Username", game.getOwner());

                }
                startActivity(intent);
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

    /** Update user on server to reflect any edits made */
    public void updateServer(){
        //ElasticSearchUsersController.EditUserTask ese = new ElasticSearchUsersController.EditUserTask();
        //ese.execute(currentUser);
        ElasticsearchGameController.EditGameTask editGameTask = new ElasticsearchGameController.EditGameTask();
        editGameTask.execute(game);
    }

    @Override
    protected void onStart(){
        super.onStart();

    }

}

