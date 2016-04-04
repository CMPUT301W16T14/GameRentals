package t14.com.GameRentals;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

/**
 * This allows existing users to edit their profile and new users to create a profile.
 *
 * @see LoginActivity
 * @author JL
 */

public class ProfileMain extends ActionBarActivity {
    private User currentUser;
    private static int RESULT_LOAD_IMAGE = 1;

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        currentUser = UserController.getCurrentUser();

        ImageButton imageButton = (ImageButton) findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View arg0){
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });

        final EditText userNameEdit = (EditText) findViewById(R.id.nameText);
        final EditText userEmailEdit = (EditText) findViewById(R.id.EmailText);
        final EditText userPhoneEdit = (EditText) findViewById(R.id.PhoneText);

        final Button saveButton = (Button)findViewById(R.id.SaveButton);

        userNameEdit.setText(currentUser.getUserName());
        userEmailEdit.setText(currentUser.getEmail());
        userPhoneEdit.setText(currentUser.getPhoneNumber());

        saveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                boolean cancel = false;
                View focusView = null;
                boolean creatingAccount = false;

                //If username or userID is null, it means new User (create a new account)
                if (currentUser.getID() == null || currentUser.getUserName() == null) {
                    String eUsername = userNameEdit.getText().toString();
                    String newEmail = userEmailEdit.getText().toString();
                    String newPhone = userPhoneEdit.getText().toString();

                    // Checks for empty field.
                    if (TextUtils.isEmpty(eUsername)) {
                        userNameEdit.setError(getString(R.string.error_field_required));
                        focusView = userNameEdit;
                        cancel = true;
                    }

                    if (TextUtils.isEmpty(newEmail)) {
                        userEmailEdit.setError(getString(R.string.error_field_required));
                        focusView = userEmailEdit;
                        cancel = true;
                    }

                    if (TextUtils.isEmpty(newPhone)) {
                        userPhoneEdit.setError(getString(R.string.error_field_required));
                        focusView = userPhoneEdit;
                        cancel = true;
                    }

                    if (cancel) {
                        // If there was an error; don't attempt create and focus the first
                        // form field with an error.
                        focusView.requestFocus();
                    } else {
                        //check if the username is taken
                        User enteredUsername = isUsernameValid(eUsername);

                        //if enteredUsername is null, that means the username is available and the account is being created.
                        if (enteredUsername == null) {
                            User newUser = new User(eUsername, newEmail, newPhone);
                            ElasticSearchUsersController.AddUserTask esa = new ElasticSearchUsersController.AddUserTask();
                            esa.execute(newUser);
                            ElasticSearchUsersController.EditUserTask ese = new ElasticSearchUsersController.EditUserTask();
                            ese.execute(newUser);

                            //login
                            UserController.setUser(newUser);
                            creatingAccount = true;

                        } else {
                            //if not null show error
                            userNameEdit.setError(getString(R.string.error_invalid_username));
                            focusView = userNameEdit;
                            cancel = true;
                        }
                        if (cancel) {
                            // If there was an error; don't attempt create and focus the first
                            // form field with an error.
                            focusView.requestFocus();
                        } else {
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        }

                    }
                } else {
                    //users that already exist
                    currentUser.setUserName(userNameEdit.getText().toString());
                    currentUser.setEmail(userEmailEdit.getText().toString());
                    currentUser.setPhoneNumber(userPhoneEdit.getText().toString());

                    updateServer(); //already existing users get updated
                    finish();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    /*if (cancel) {
                        // If there was an error; don't attempt adding an account/saving focus the first
                        // form field with an error.
                        focusView.requestFocus();
                    } else {*/
                        /*if (creatingAccount == true) {
                            //login
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        }*/ /*else {
                            currentUser.setUserName(userNameEdit.getText().toString());
                            currentUser.setEmail(userEmailEdit.getText().toString());
                            currentUser.setPhoneNumber(userPhoneEdit.getText().toString());

                            updateServer(); //already existing users get updated
                            finish();

                        }*/
                    /*currentUser.setUserName(userNameEdit.getText().toString());
                    currentUser.setEmail(userEmailEdit.getText().toString());
                    currentUser.setPhoneNumber(userPhoneEdit.getText().toString());

                    updateServer(); //already existing users get updated
                    finish();*/
                    }
                //return back to main activity
                //Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                //startActivity(intent);
                }
            //}
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
        ese.execute(currentUser);
    }

    private User isUsernameValid(String eUsername) {
        //if the username matches the username of the user loaded(loadedUser's username) then it is valid.
        User checkingUsername = new User(null,null,null);
        ElasticSearchUsersController.GetUserTask checkUsername = new ElasticSearchUsersController.GetUserTask();

        checkUsername.execute(eUsername);

        try{
            checkingUsername = (checkUsername.get());
        } catch (InterruptedException e) {
            throw new RuntimeException();
            //e.printStackTrace();
        } catch (ExecutionException e) {
            throw new RuntimeException();
            //e.printStackTrace();
        }

        return checkingUsername;

    }

}



