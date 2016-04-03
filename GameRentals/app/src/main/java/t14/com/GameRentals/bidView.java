package t14.com.GameRentals;

import java.io.Serializable;

/**
 * Created by margaret on 16/4/3.
 */
public class bidView implements Serializable{
    private Bid bid;
    private Game game;


    public bidView(Bid bid, Game game) {
        this.bid = bid;
        this.game = game;
    }

    public Bid getBid() {
        return bid;
    }

    public void setBid(Bid bid) {
        this.bid = bid;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }


    public String toString(){
        return "game name:" + game.getGameName() + "\ngame description:" + game.getDescription()
                + "\nbid status:" + bid.TransformIsAccepted() + "\nbid rate:" + bid.getRate();
    }
}
