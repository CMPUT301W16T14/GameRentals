package t14.com.GameRentals;

/**
 * Created by cjresler on 2016-02-28.
 */
public class Bid {
    private User bidMaker;
    private double rate;
    private boolean accepted;

    public User getBidMaker() {
        return bidMaker;
    }

    public void setBidMaker(User bidMaker) {
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
