package th.co.omc.memberdemo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import th.co.omc.memberdemo.fragment.ClarifyHistoryFragment;
import th.co.omc.memberdemo.fragment.ClarifyRecieveFragment;

/**
 * Created by teera-s on 10/5/2016 AD.
 */

public class ClarifyFragmentAdapter extends FragmentStatePagerAdapter {
    private final int PAGE_NUM = 2;

    int mNumOfTabs;

    public ClarifyFragmentAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        /*if(position == 0) {
            return new ClarifyFragment();
        }else*/ if(position == 0) {
            return new ClarifyHistoryFragment();
        }else if(position == 1) {
            return new ClarifyRecieveFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return PAGE_NUM;
    }
}
