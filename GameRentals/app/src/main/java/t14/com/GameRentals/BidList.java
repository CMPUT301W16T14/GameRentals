package t14.com.GameRentals;

import java.util.ArrayList;

/**
 * Created by cjresler on 2016-02-28.
 */
public class BidList {
    private ArrayList<Bid> list;

    public BidList() {
        list = new ArrayList<Bid>();
    }

    public ArrayList<Bid> getList() {
        return list;
    }
    public int getSize(){
        return list.size();
    }

    public Bid getItem(int position){
        Bid bid;
        bid = list.get(position);
        return bid;
    }
}
