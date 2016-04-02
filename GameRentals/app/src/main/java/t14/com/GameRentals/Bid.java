package t14.com.GameRentals;

import java.io.Serializable;
import java.util.concurrent.ExecutionException;

/**
 * Created by cjresler on 2016-02-28.
 */
public class Bid implements Serializable{
    private String bidMaker;
    private double rate;
    private int accepted;

    public Bid(User bidMaker, double rate) {
        this.bidMaker = bidMaker.getID();
        this.rate = rate;
        this.accepted = 0;
    }


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

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public int isAccepted() {
        return accepted;
    }

    public void setAccepted(int accepted) {
        this.accepted = accepted;
    }

    public String toString(){
        try {
            return "Bidmaker:" + TransformBidMaker() + "\n rate:" + rate + "\n status: " +  TransformIsAccepted();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
