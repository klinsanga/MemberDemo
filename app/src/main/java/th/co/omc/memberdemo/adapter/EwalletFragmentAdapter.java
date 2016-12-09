package th.co.omc.memberdemo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import th.co.omc.memberdemo.fragment.EwalletReportFragment;
import th.co.omc.memberdemo.fragment.EwalletTopupFragment;

/**
 * Created by teera-s on 10/4/2016 AD.
 */

public class EwalletFragmentAdapter extends FragmentStatePagerAdapter {
    private final int PAGE_NUM = 2;

    int mNumOfTabs;

    public EwalletFragmentAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0) {
            return new EwalletTopupFragment();
        }else if(position == 1) {
            return new EwalletReportFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return PAGE_NUM;
    }
}
