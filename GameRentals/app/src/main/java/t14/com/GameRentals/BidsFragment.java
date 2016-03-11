package t14.com.GameRentals;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by yourui on 3/2/16.
 */
public class BidsFragment  extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //TODO
        return inflater.inflate(R.layout.bids, container,false);
    }
}
