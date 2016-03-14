package t14.com.GameRentals;

import java.io.Serializable;

import io.searchbox.annotations.JestId;

/**
 * Created by cjresler on 2016-02-28.
 */
public class User implements Serializable{
    @JestId
    private String id;
    private String userName;
    private String email;
    private String phoneNumber;
    private GameList myGames;
    private GameList biddedItems;
    private GameList borrowedItems;

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
        myGames = new GameList();
        biddedItems = new GameList();
        borrowedItems = new GameList();
    }

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

    public GameList getMyGames() {
        return myGames;
    }

    public void setMyGames(GameList myGames) {
        this.myGames = myGames;
    }

    public GameList getBiddedItems() {
        return biddedItems;
    }

    public void setBiddedItems(GameList biddedItems) {
        this.biddedItems = biddedItems;
    }

    public GameList getBorrowedItems() {
        return borrowedItems;
    }

    public void setBorrowedItems(GameList borrowedItems) {
        this.borrowedItems = borrowedItems;
    }

    public void addMyGame(Game game) {
        UserController.addMyGame(game);
    }
}
