package t14.com.GameRentals;

import java.util.concurrent.ExecutionException;

/**
 * Created by cjresler on 2016-02-28.
 */
public class Bid{
    private String bidMaker;
    private double rate;
    private boolean accepted;

    public Bid(User bidMaker, double rate) {
        this.bidMaker = bidMaker.getID();
        this.rate = rate;
        this.accepted = false;
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
        if (accepted == false){
            return "Not Accepted";
        }
        else return "Accepted";
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

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
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
