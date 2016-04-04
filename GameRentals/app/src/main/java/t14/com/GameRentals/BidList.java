package t14.com.GameRentals;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Every game has a list of bids being offered from other uses.
 * @see ViewBidsListActivity
 */
public class BidList implements Serializable{
    private ArrayList<Bid> list;

    public BidList() {
        list = new ArrayList<Bid>();
    }

    /**
     *
     * @return ArrayList
     */
    public ArrayList<Bid> getList() {
        ArrayList<Bid> bids = new ArrayList<Bid>();

        for (int i = 0; i < list.size(); i++){
            if (list.get(i).isAccepted() != 2)
                bids.add(list.get(i));
        }

        return bids;
    }

    /**
     *
     * @return list.size()
     */
    public int getSize(){
        return list.size();
    }

    /**
     *
     * @param position
     * @return Bid
     * @see Bid
     */
    public Bid getItem(int position){
        Bid bid;
        bid = list.get(position);
        return bid;
    }

    /**
     *
     * @param user
     * @param rate
     * @see User
     */
    public void AddBid(User user, double rate){
        Bid bid = new Bid(user,rate);
        list.add(bid);
    }

    /**
     *
     * @param bid
     * @see Bid
     */
    public void addBid(Bid bid){
        list.add(bid);
    }

    /**
     *
     * @param user
     * @see User
     */
    public void RemoveBid(User user){
        for (int i = 0; i < list.size(); i++){
            if (list.get(i).getBidMaker().equals(user.getUserName()))
                list.remove(i);
        }
    }

}
