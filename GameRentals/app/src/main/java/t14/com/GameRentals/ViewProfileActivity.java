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
import android.widget.TextView;

public class ViewProfileActivity extends Activity {
    private User user;
    private static int RESULT_LOAD_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_profile);

        //TODO:Pass username to this activity
        String givenUsername = (String) getIntent().getSerializableExtra("Username");
        user = UserController.getUser(givenUsername);

        final EditText userNameText = (EditText) findViewById(R.id.viewProfileNameText);
        final EditText emailText = (EditText) findViewById(R.id.viewProfileEmailText);
        final EditText phoneText = (EditText) findViewById(R.id.viewProfilePhoneText);
        //final TextView userNameView = (TextView) findViewById(R.id.lblName);



        final Button exitButton = (Button)findViewById(R.id.viewProfileExitButton);
        final Button messageButton = (Button)findViewById(R.id.viewProfileSendMessageButton);

        //TODO:Not a requirement but if time can make this button show a user's games
        //final Button viewGamesButton = (Button)findViewById(R.id.viewProfileViewGamesButton);

        userNameText.setText(user.getUserName());
        emailText.setText(user.getEmail());
        phoneText.setText(user.getPhoneNumber());

        //TODO:Use this button to open the message activity and send a message to this user
        messageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                finish();
            }
        });
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    public ImageView getImageProfile(){
        return (ImageView) findViewById(R.id.profile_image);
    }


    @Override
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

    public void updateServer(){

        ElasticSearchUsersController.EditUserTask ese = new ElasticSearchUsersController.EditUserTask();
        ese.execute(user);
    }


}