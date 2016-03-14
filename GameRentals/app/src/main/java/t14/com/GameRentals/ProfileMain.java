package t14.com.GameRentals;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ProfileMain extends ActionBarActivity {
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        currentUser = UserController.getCurrentUser();

        final EditText userNameEdit = (EditText) findViewById(R.id.nameText);
        final EditText userEmailEdit = (EditText) findViewById(R.id.EmailText);
        final EditText userPhoneEdit = (EditText) findViewById(R.id.PhoneText);
        final TextView userNameView = (TextView) findViewById(R.id.lblName);


        final Button saveButton = (Button)findViewById(R.id.SaveButton);

        userNameEdit.setText(currentUser.getUserName());
        userEmailEdit.setText(currentUser.getEmail());
        userPhoneEdit.setText(currentUser.getPhoneNumber());

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

    public void updateServer(){

        ElasticSearchUsersController.EditUserTask ese = new ElasticSearchUsersController.EditUserTask();
        ese.execute(currentUser);
    }


}