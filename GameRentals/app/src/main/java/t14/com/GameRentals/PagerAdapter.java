package t14.com.GameRentals;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by yourui on 3/2/16.
 */
public class PagerAdapter extends FragmentStatePagerAdapter {
    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index)
    {
        switch (index)
        {
            case 0:
                return new MyItemsFragment();
            case 1:
                return new BorrowFragment();
            case 2:
                return new BidsFragment();
        }
        return null;
    }


    @Override
    public int getCount() {
        return 3;
    }

}