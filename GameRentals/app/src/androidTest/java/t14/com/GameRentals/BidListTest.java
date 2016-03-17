package t14.com.GameRentals;

import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by cjresler on 2016-03-14.
 */
public class BidListTest extends ActivityInstrumentationTestCase2{
    public BidListTest() {
        super(MainActivity.class);
    }

    public void testAddBid(){
        BidList bids = new BidList();
        Bid bid = new Bid(null, 4.99);

        bids.addBid(bid);
        assertTrue(bids.getList().contains(bid));
    }

    public void testGetItem(){
        BidList bids = new BidList();
        Bid bid = new Bid(null, 4.99);

        Bid test = bids.getItem(0);
        assertEquals(bid, test);
    }
}
