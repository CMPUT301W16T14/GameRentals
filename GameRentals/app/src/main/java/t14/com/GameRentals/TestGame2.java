package t14.com.GameRentals;

import io.searchbox.annotations.JestId;

/**
 * Created by cjresler on 2016-03-12.
 */
public class TestGame2 {
    private String userName;
    private String email;
    private String phoneNumber;
    private TestGameList myGames;
    private TestGameList biddedItems;
    private TestGameList borrowedItems;
    @JestId
    private String ItemID;

    public String getItemID() {
        return ItemID;
    }

    public void setItemID(String itemID) {
        ItemID = itemID;
    }

    public TestGame2() {
        this.userName = "TEST";
        this.email = "email";
        this.phoneNumber = "9";
        myGames = new TestGameList();
        biddedItems = new TestGameList();
        borrowedItems = new TestGameList();
    }
}
