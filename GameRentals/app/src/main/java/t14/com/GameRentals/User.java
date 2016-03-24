package t14.com.GameRentals;

import java.io.Serializable;

import io.searchbox.annotations.JestId;

import java.util.ArrayList;

/**
 * Created by cjresler on 2016-02-28.
 */
public class User implements Serializable {
    @JestId
    protected String id;


    private String userName;
    private String email;
    private String phoneNumber;
    private GameRefList myGames;
    private GameRefList biddedItems;
    private GameRefList borrowedItems;
   // private ArrayList<User> users;

    public String getID() {
        return id;
    }

    public void setID(String ID) {
        this.id = ID;
    }

    public User(String userName, String email, String phoneNumber) {
        this.id = "";
        this.userName = userName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        myGames = new GameRefList();
        biddedItems = new GameRefList();
        borrowedItems = new GameRefList();
    }

    /*public ArrayList<User> getUsers(String s) {
        return this.users;
    }

    public void viewUser(User user){
        this.users.add(user);
    }*/

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public GameRefList getMyGames() {
        return myGames;
    }

    public void setMyGames(GameRefList myGames) {
        this.myGames = myGames;
    }

    public GameRefList getBiddedItems() {
        return biddedItems;
    }

    public void setBiddedItems(GameRefList biddedItems) {
        this.biddedItems = biddedItems;
    }

    public GameRefList getBorrowedItems() {
        return borrowedItems;
    }

    public void setBorrowedItems(GameRefList borrowedItems) {
        this.borrowedItems = borrowedItems;
    }

    public void addMyGame(String gameID) {
        Game game = myGames.getGame(gameID);
        UserController.addMyGame(game);
    }
}
