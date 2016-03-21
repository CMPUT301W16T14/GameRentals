package t14.com.GameRentals;

import java.io.Serializable;

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
}
