package t14.com.GameRentals;


import java.io.Serializable;
import java.util.ArrayList;

import io.searchbox.annotations.JestId;

/** This class contains information for Game objects.
 * Each game has a game name, game ID, descirption, status, list of bids, owner,
 * and borrower.
 * Game status can be either available, bidded, or borrowed.
 * Borrower will be initialized to null and only set if game status is borrowed.
 * Created by cjresler on 2016-02-28.
 */
public class Game implements Serializable{
    private String gameName;
    @JestId
    private String gameID;
    private String description;
    private int status;
    private BidList bidList;
    private User owner;
    private User borrower;

    /** Constructor for game object.
     *
     * @param gameName The name of game to create.
     * @param description A description of the game.
     * @param owner The user that owns this game.
     */
    public Game(String gameName, String description, User owner) {
        this.gameName = gameName;
        this.description = description;
        this.gameID = "";
        this.status = 0;
        this.bidList = new BidList();
        this.owner = owner;
        this.borrower = null;
    }

    /** Return the borrower of game
     *
     * @return User that is borrowing this game
     */
    public User getBorrower() {
        return borrower;
    }

    /** Set borrower of game
     *
     * @param borrower Set borrower to user that is borrowing the game
     */
    public void setBorrower(User borrower) {
        this.borrower = borrower;
    }

    /** Return the owner of game
     *
     * @return User that is the owner of this game
     */
    public User getOwner() {
        return owner;
    }

    /** Set the owner of a game
     *
     * @param owner User that owns this game
     */
    public void setOwner(User owner) {
        this.owner = owner;
    }

    /** Return the status of a game
     *
     * @return Status of game
     */
    public int getStatus() {
        return status;
    }

    /** Return a string that represents the status of game
     *
     * @return The string of the status of the game
     * can be "available", "bidded", or "borrowoed"
     */
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

    /** Set the status of game
     *
     * @param status Status of this game
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /** Return list of bids on this game
     *
     * @return List of bids that have been made on this game
     */
    public BidList getBidList() {
        return bidList;
    }

    /** Set the list of bids on this game
     *
     * @param bidList List of bids to be attached to this game
     */
    public void setBidList(BidList bidList) {
        this.bidList = bidList;
    }

    /** Get the description of game
     *
     * @return Description of game
     */
    public String getDescription() {
        return description;
    }

    /** Set description of game
     *
     * @param description Description of game
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /** Get ID of game
     *
     * @return Game ID
     */
    public String getGameID() {
        return gameID;
    }

    /** Set ID of game
     *
     * @param gameID String of ID that game ID should be set to
     */
    public void setGameID(String gameID) {
        this.gameID = gameID;
    }

    /** Get name of game
     *
     * @return String name of game
     */
    public String getGameName() {
        return gameName;
    }

    /** Set name of game
     *
     * @param gameName Name of game
     */
    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    /** Copies game to have identical attributes to a given one
     *
     * @param game Game that should be copied into this game
     */
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
   /** Display game in proper format
    *
    */
    public String toString(){
        return "Status: " + getStatusString() + "\n" +
                "Game name: " + gameName + "\n" +
                "Description: " + description;
    }
}
