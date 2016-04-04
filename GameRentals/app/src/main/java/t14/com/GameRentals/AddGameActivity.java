package t14.com.GameRentals;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

/** This activity handles adding games to a user's list of games.
 *
 */

public class AddGameActivity extends Activity {
    private EditText gameName;
    private EditText gameDescription;
    private User currentUser;
    private Game game;
    private static int RESULT_LOAD_IMAGE = 1;
    //private gameImage gameimage;
    private ImageView gameimage;
    private Boolean filledMainImage;
    private  Bitmap thumbnail;
    private ArrayList<Bitmap> gameimages;
    private ImageView imageViewCard;
    //static final int REQUEST_CAPTURING_IMAGE = 1234;


    @Override
    /** Called when activity is created */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_game);
        currentUser = (User)getIntent().getExtras().get("currentUser");
        game = (Game) getIntent().getSerializableExtra("test");
        gameName = (EditText)findViewById(R.id.addGameNameEditText);
        gameDescription = (EditText)findViewById(R.id.addGameDescriptionEditText);
        Button okButton = (Button)findViewById(R.id.addGameOkButton);
        Button cancelButton = (Button)findViewById(R.id.addGameCancelButton);
        gameimage = (ImageView)findViewById(R.id.gameImage);

        filledMainImage = Boolean.FALSE;
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
                //ArrayList<Image> gameImages = new ArrayList<Image>();
                Image gameImage = new Image(null);
                Bitmap gameimage = null;
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

                    gameImage.setImage(gameImage);
                    //gameImages.add(new Image(gameimage));
                    thumbnail = null;
                    //currentUser.getMyGames().addGame(game);
                    //Update user and add game to server
                    //addTestData();
                    updateServer();
                    //String gameID = game.getGameID();
                    //currentUser.getMyGames().addGame(gameID);
                    finish();
                }
                /*
                User owner = model.getUser();
                Bitmap cardimage = null;
                ArrayList<Image> cardImages = new ArrayList<Image>();
                if(view.getImageViewCard().getTag().equals("Changed")) {
                    for(Bitmap bmp: view.getCardImages()) {
                        //cardimage = ((BitmapDrawable) view.getImageViewCard().getDrawable()).getBitmap();
                        cardImages.add(new Image(bmp));
                    }
                }

                else {
                    cardimage = BitmapFactory.decodeResource(view.getResources(), R.drawable.img_no_img);
                    cardImages.add(new Image(cardimage));
                }
                Toast.makeText(view, "Submitted a card...",
                        Toast.LENGTH_SHORT).show();
                Card card = new Card(name, quantity, quality, catagory, series, tradable, comments, cardImages, owner);
                model.getUser().addInventoryItem(card);
                //model.getUser().addInventoryItem(new Card(name, new Image(cardimage), quantity, quality, catagory, series, tradable, comments, owner));
                //cardimage.recycle();
                cardimage = null;

                profileSerializer.Serialize(model, view);
                view.navigateToInventory();
                */
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
        return (ImageView) findViewById(R.id.gameImage);
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

            Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
            /*
            ImageView imageView = getImageProfile();
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            imageView.setTag("Changed");
            filledMainImage = Boolean.TRUE;
*/

            if(!filledMainImage) {
                ImageView imageView = getImageViewCard();
                imageView.setImageBitmap(bitmap);
                imageView.setTag("Changed");
                filledMainImage = Boolean.TRUE;
            }

            //getCardImages().add(bitmap);
            //this.Update(null);


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
    public void loadImage(){
        Intent i = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(i, RESULT_LOAD_IMAGE);
    }
    public ArrayList<Bitmap> getCardImages(){
        return this.gameimages;
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

    /*
    @Override

    public void Update(Model model) {
        mAdapter.notifyDataSetChanged();

    }

    public void navigateToInventory(){
        finish();

    }
    */

/*
    public void setRecyclerViewLayoutManager(LayoutManagerType layoutManagerType) {

        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

    }
*/
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public Boolean getFilledMainImage() {
        return filledMainImage;
    }

    public void setFilledMainImage(Boolean filledMainImage) {
        this.filledMainImage = filledMainImage;
    }

    public ImageView getImageViewCard() {
        return imageViewCard;
    }
}
