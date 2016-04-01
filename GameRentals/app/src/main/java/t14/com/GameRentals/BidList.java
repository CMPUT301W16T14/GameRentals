package t14.com.GameRentals;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by cjresler on 2016-02-28.
 */
public class BidList implements Serializable{
    private ArrayList<Bid> list;

    public BidList() {
        list = new ArrayList<Bid>();
    }

    public ArrayList<Bid> getList() {
        ArrayList<Bid> bids = new ArrayList<Bid>();
        for (int i = 0; i < list.size(); i++){
            if (list.get(i).isAccepted() != 2)
                bids.add(list.get(i));
        }
        return bids;
    }
    public int getSize(){
        return list.size();
    }

    public Bid getItem(int position){
        Bid bid;
        bid = list.get(position);
        return bid;
    }

    public void AddBid(User user, double rate){
        Bid bid = new Bid(user,rate);
        list.add(bid);
    }

    public void addBid(Bid bid){
        list.add(bid);
    }

    public void RemoveBid(User user){
        for (int i = 0; i < list.size(); i++){
            if (list.get(i).getBidMaker().equals(user.getID()))
                list.remove(i);
        }
    }


}
