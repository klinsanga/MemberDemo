package th.co.omc.memberdemo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import th.co.omc.memberdemo.fragment.OrderHistoryFragment;
import th.co.omc.memberdemo.fragment.OrderToMemberHistoryFragment;

/**
 * Created by teera-s on 10/5/2016 AD.
 */

public class OrderFragmentAdapter extends FragmentStatePagerAdapter {
    private final int PAGE_NUM = 2;

    int mNumOfTabs;

    public OrderFragmentAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0) {
            return new OrderHistoryFragment();
        }else if(position == 1) {
            return new OrderToMemberHistoryFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return PAGE_NUM;
    }
}
