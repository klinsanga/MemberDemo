package th.co.omc.memberdemo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import th.co.omc.memberdemo.fragment.DayCommissionFragment;
import th.co.omc.memberdemo.fragment.FaststartFragment;
import th.co.omc.memberdemo.fragment.MonthCommissionFragment;
import th.co.omc.memberdemo.fragment.WeekCommissionFragment;

/**
 * Created by teera-s on 9/27/2016 AD.
 */

public class CommissionFragmentAdapter extends FragmentStatePagerAdapter {
    private final int PAGE_NUM = 3;

    int mNumOfTabs;

    public CommissionFragmentAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0) {
            return new DayCommissionFragment();
        }else if(position == 1) {
            return new WeekCommissionFragment();
        }else if(position == 2) {
            return new MonthCommissionFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return PAGE_NUM;
    }
}
