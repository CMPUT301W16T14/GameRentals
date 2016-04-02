package t14.com.GameRentals;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

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
        imageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);


            }
        });

        final EditText userNameEdit = (EditText) findViewById(R.id.nameText);
        final EditText userEmailEdit = (EditText) findViewById(R.id.EmailText);
        final EditText userPhoneEdit = (EditText) findViewById(R.id.PhoneText);
        final TextView userNameView = (TextView) findViewById(R.id.lblName);

        final Button saveButton = (Button) findViewById(R.id.SaveButton);

        userNameEdit.setText(currentUser.getUserName());
        userEmailEdit.setText(currentUser.getEmail());
        userPhoneEdit.setText(currentUser.getPhoneNumber());

        saveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                boolean cancel = false;
                View focusView = null;

                //if username is null use checkandupdate (for null users)
                if (currentUser.getID() == null || currentUser.getUserName() == null) {
                    //userNameEdit.setError(getString(R.string.error_invalid_username));
                    //checks username if it's being used
                    //get string from what they enter
                    String username = userNameEdit.getText().toString();
                    //do elastic search and see if the user is null
                    User user = isUsernameValid(username);
                    //if returns null, it means the username does not exist so it can use it
                    if (user.getUserName() == null){
                        ElasticSearchUsersController.AddUserTask esa = new ElasticSearchUsersController.AddUserTask();
                        esa.execute(user);
                        ElasticSearchUsersController.EditUserTask ese = new ElasticSearchUsersController.EditUserTask();
                        ese.execute(user);
                    } else {
                        //if not null show error
                        userNameEdit.setError(getString(R.string.error_invalid_username));
                        focusView = userNameEdit;
                        cancel = true;
                    }

                    if (cancel) {
                        // If there was an error; don't attempt login and focus the first
                        // form field with an error.
                        focusView.requestFocus();






                    //checkAndUpdateServer();
                } else {
                    currentUser.setUserName(userNameEdit.getText().toString());
                    currentUser.setEmail(userEmailEdit.getText().toString());
                    currentUser.setPhoneNumber(userPhoneEdit.getText().toString());


                    updateServer(); //already existing users get updated
                    finish();


                }


                //currentUser.setUserName(userNameEdit.getText().toString());
                //currentUser.setEmail(userEmailEdit.getText().toString());
                //currentUser.setPhoneNumber(userPhoneEdit.getText().toString());


                //updateServer(); //already existing users get updated
                //finish();
            }
        });

    }

    public ImageView getImageProfile() {
        return (ImageView) findViewById(R.id.profile_image);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
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

    public void updateServer() {

        ElasticSearchUsersController.EditUserTask ese = new ElasticSearchUsersController.EditUserTask();
        ese.execute(currentUser);
    }

    private User isUsernameValid(String email) {
        //if the username matches the username of the user loaded(loadedUser's username) then it is valid.
        User loadedUser = new User(null,null,null);
        ElasticSearchUsersController.GetUserTask username = new ElasticSearchUsersController.GetUserTask();

        username.execute(email);

        try{
            loadedUser = (username.get());
        } catch (InterruptedException e) {
            throw new RuntimeException();
            //e.printStackTrace();
        } catch (ExecutionException e) {
            throw new RuntimeException();
            //e.printStackTrace();
        }

        return loadedUser;

    }







}




    /*public void checkAndUpdateServer(){
        //checks username if it's being used
        //get string from what they enter
        //String username = userNameEdit.getText().toString();





        //do elastic search and see if the user is null
        //if null show error
        //if not null add a new user and update it immediately

        //if the username matches the username of the user loaded(loadedUser's username) then it is valid.
        User testUser = new User(null,null,null);
        ElasticSearchUsersController.GetUserTask username = new ElasticSearchUsersController.GetUserTask();

        username.execute(email);

        try{
            testUser = (username.get());
        } catch (InterruptedException e) {
            throw new RuntimeException();
            //e.printStackTrace();
        } catch (ExecutionException e) {
            throw new RuntimeException();
            //e.printStackTrace();
        }

        return testUser;

    }*/







        //if it's not it will add
        //ElasticSearchUsersController.AddUserTask esa = new ElasticSearchUsersController.AddUserTask();
        //esa.execute(serverUser);
        //ElasticSearchUsersController.EditUserTask ese = new ElasticSearchUsersController.EditUserTask();
        //ese.execute(serverUser);



        //otherwise it will show error



    /*}

}*/