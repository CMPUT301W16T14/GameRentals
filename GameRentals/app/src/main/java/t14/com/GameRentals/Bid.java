package t14.com.GameRentals;

import java.io.Serializable;
import java.util.concurrent.ExecutionException;

/**
 * When a user wants to borrow a game, they can make a bid offer to the owner.
 * This class is specifically about creating the bid.
 */
public class Bid implements Serializable{
    private String bidMaker;
    private double rate;
    private int accepted;

    /**
     *
     * @param bidMaker
     * @param rate
     * @see User
     */
    public Bid(User bidMaker, double rate) {
        this.bidMaker = bidMaker.getUserName();
        this.rate = rate;
        this.accepted = 0;
    }

    /**
     *
     * @return User
     * @see User
     */
    public String getBidMaker() {
        return bidMaker;
    }

    public String TransformBidMaker() throws ExecutionException, InterruptedException {
        User Maker;
        ElasticSearchUsersController.GetUserByIDTask getUserByIDTask= new ElasticSearchUsersController.GetUserByIDTask();
        getUserByIDTask.execute(bidMaker);
        Maker = getUserByIDTask.get();
        return Maker.getUserName();
    }

    public String TransformIsAccepted(){
        if (accepted == 0){
            return "Not Accepted";
        }
        else if (accepted == 1)return "Accepted";
        else return "Declined";
    }

    public void setBidMaker(String bidMaker) {
        this.bidMaker = bidMaker;
    }

    /**
     *
     * @return double
     */
    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    /**
     *
     * @return int
     */
    public int isAccepted() {
        return accepted;
    }

    /**
     *
     * @param accepted
     */
    public void setAccepted(int accepted) {
        this.accepted = accepted;
    }

    /**
     *
     * @return User
     * @see User
     */
    public String toString(){
        /*try {
            return "Bidmaker:" + TransformBidMaker() + "\n rate:" + rate + "\n status: " +  TransformIsAccepted();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        return bidMaker;
        //return null;
    }
}
