package t14.com.GameRentals;

import java.io.Serializable;

import io.searchbox.annotations.JestId;



/**
 * This class is in regards to a User object. <br>
 *     Each User has the following attributes: <br>
 *         <ul>
 *             <li> id </li>
 *             <li>userName</li>
 *             <li>email</li>
 *             <li>phoneNumber</li>
 *             <li>myGames list (the game's the user owns and is wanting to rent out</li>
 *             <li>borrowedItems list (the game's that the user is borrowing from others</li>
 *             <li>biddedItems list (game's that that user is currently bidding on</li>
 *         </ul>
 * @param username
 * @see ProfileMain
 *
 * Created by cjresler on 2016-02-28.
 */
public class User implements Serializable {
    @JestId
    protected String id;

    private String userName;
    private String email;
    private String phoneNumber;
    private GameRefList myGames;
    private GameRefList borrowedItems;
    private GameRefList biddedItems;

    // private ArrayList<User> users;

    public User(String userName, String email, String phoneNumber) {
        this.id = "";
        this.userName = userName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        myGames = new GameRefList();
        biddedItems = new GameRefList();
        borrowedItems = new GameRefList();
    }

    /**
     *
     * @return id
     */
    public String getID() {
        return id;
    }

    /**
     *
     * @param ID
     * @return void
     */
    public void setID(String ID) {
        this.id = ID;
    }




    /*public ArrayList<User> getUsers(String s) {
        return this.users;
    }

    public void viewUser(User user){
        this.users.add(user);
    }*/


    /**
     *
     * @return userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     *
     * @param userName
     * @return void
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     *
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email
     * @return void
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return phoneNumber
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     *
     * @param phoneNumber
     * @return void
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     *
     * @return myGames
     */
    public GameRefList getMyGames() {
        return myGames;
    }

    /**
     *
     * @param myGames
     * @return void
     */
    public void setMyGames(GameRefList myGames) {
        this.myGames = myGames;
    }

    /**
     *
     * @return biddedItems
     */
    public GameRefList getBiddedItems() {
        return biddedItems;
    }

    /**
     *
     * @param biddedItems
     * @return void
     */
    public void setBiddedItems(GameRefList biddedItems) {
        this.biddedItems = biddedItems;
    }

    /**
     *
     * @return borrowedItems
     */
    public GameRefList getBorrowedItems() {
        return borrowedItems;
    }

    /**
     *
     * @param borrowedItems
     * @return void
     */
    public void setBorrowedItems(GameRefList borrowedItems) {
        this.borrowedItems = borrowedItems;
    }

    /**
     *
     * @param gameID
     * @return void
     */
    public void addMyGame(String gameID) {
        Game game = myGames.getGame(gameID);
        UserController.addMyGame(game);
    }



}
