package t14.com.GameRentals;

import java.util.ArrayList;

import io.searchbox.annotations.JestId;

/**
 * Created by cjresler on 2016-03-12.
 */
public class TestGame {
    @JestId
    private String testID;
    private String string1;
    private int int1;
    private TestGame2 owner;
    private TestGameList2 bids;

    public TestGame(String string1, int int1) {
        this.string1 = string1;
        this.int1 = int1;
        owner = new TestGame2();
        bids = new TestGameList2();
    }

    public String getTestID() {
        return testID;
    }

    public void setTestID(String id) {
        this.testID = id;
    }
}
