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
 * @see ProfileMain
 * @see GameRefList
 * @see GameList
 * @see Game
 *
 *@author JL
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
     */
    public void setID(String ID) {
        this.id = ID;
    }

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

    public int getBid(Game game){
        for (int i = 0; i < getBiddedItems().getSize(); i++){
            if(getBiddedItems().getGame(i).equals(game.getGameID())){
                return i;
            }
        }
        return 0;
    }

}
