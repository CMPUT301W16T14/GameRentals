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

public class ProfileMain extends ActionBarActivity {
    private User currentUser;
    private static int RESULT_LOAD_IMAGE = 1;
    ImageView contactImageImgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        currentUser = UserController.getCurrentUser();
        contactImageImgView = (ImageView) findViewById(R.id.profile_image);

        ImageButton imageButton = (ImageButton) findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View arg0){
                Intent i = new Intent();
                i.setType("ïmage/*");
                i.setAction(Intent.ACTION_GET_CONTENT);


                        //Intent.ACTION_PICK,
                        //MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(Intent.createChooser(i, "Select Contact Image"), 1);


            }
        });

        final EditText userNameEdit = (EditText) findViewById(R.id.nameText);
        final EditText userEmailEdit = (EditText) findViewById(R.id.EmailText);
        final EditText userPhoneEdit = (EditText) findViewById(R.id.PhoneText);
        //final ImageView userImageView = (ImageView)findViewById(R.id.profile_image);


        final Button saveButton = (Button)findViewById(R.id.SaveButton);

        userNameEdit.setText(currentUser.getUserName());
        userEmailEdit.setText(currentUser.getEmail());
        userPhoneEdit.setText(currentUser.getPhoneNumber());
        //userImageView.setImageURI(currentUser.getImage());

        saveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                currentUser.setUserName(userNameEdit.getText().toString());
                currentUser.setEmail(userEmailEdit.getText().toString());
                currentUser.setPhoneNumber(userPhoneEdit.getText().toString());
                updateServer();
                finish();
            }
        });

    }
    public ImageView getImageProfile(){
        return (ImageView) findViewById(R.id.profile_image);
    }
    public void onActivityResult(int reqCode, int resCode, Intent data){
        if(resCode == RESULT_OK){
            if(reqCode ==1)
                contactImageImgView.setImageURI(data.getData());

        }
    }

/*
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
    */

    public void updateServer(){

        ElasticSearchUsersController.EditUserTask ese = new ElasticSearchUsersController.EditUserTask();
        ese.execute(currentUser);
    }


}