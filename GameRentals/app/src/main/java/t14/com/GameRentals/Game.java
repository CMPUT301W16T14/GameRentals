package t14.com.GameRentals;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by cjresler on 2016-02-28.
 */
public class Game implements Serializable{
    private String gameName;
    private String gameID;
    private String description;
    private int status;
    private BidList bidList;
    private User owner;
    private User borrower;

    public Game(String gameName, String description, User owner) {
        this.gameName = gameName;
        this.description = description;
        this.gameID = "";
        this.status = 0;
        this.bidList = new BidList();
        this.owner = owner;
        this.borrower = null;
    }

    public User getBorrower() {
        return borrower;
    }

    public void setBorrower(User borrower) {
        this.borrower = borrower;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public BidList getBidList() {
        return bidList;
    }

    public void setBidList(BidList bidList) {
        this.bidList = bidList;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGameID() {
        return gameID;
    }

    public void setGameID(String gameID) {
        this.gameID = gameID;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    @Override
    public String toString(){
        return "ID: " + gameID +" | " + gameName;
    }
}
