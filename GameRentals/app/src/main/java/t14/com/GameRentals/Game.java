package t14.com.GameRentals;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

import io.searchbox.annotations.JestId;

/**
 * Created by cjresler on 2016-02-28.
 */
public class Game{
    private String gameName;
    @JestId
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

    public String getStatusString(){
        switch(this.status){
            case(GameController.STATUS_AVAILABLE):
                return "Available";
            case(GameController.STATUS_BORROWED):
                return "Borrowed";
            case(GameController.STATUS_BIDDED):
                return "Has bids";
        }
        return null;
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

    public void copyGame(Game game){
        this.gameName = game.getGameName();
        this.description = game.getDescription();
        this.gameID = game.getGameID();
        this.status = game.getStatus();
        this.bidList = game.getBidList();
        this.owner = game.getOwner();
        this.borrower = game.getBorrower();
    }

   @Override
    public String toString(){
        return "Status: " + getStatusString() + "\n" +
                "Game name: " + gameName + "\n" +
                "Description: " + description;
    }
}
