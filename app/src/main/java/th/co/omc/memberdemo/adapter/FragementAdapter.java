package th.co.omc.memberdemo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import th.co.omc.memberdemo.fragment.ReportUplineFragment;
import th.co.omc.memberdemo.fragment.ReportSponsorsFragment;
import th.co.omc.memberdemo.fragment.UplineDiagramFragment;

/**
 * Created by teerayut on 12/17/15 AD.
 */
public class FragementAdapter extends FragmentStatePagerAdapter {

    private final int PAGE_NUM = 3;

    int mNumOfTabs;

    public FragementAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0) {
            return new UplineDiagramFragment();
        }else if(position == 1) {
            return new ReportSponsorsFragment();
        }else if(position == 2) {
            return new ReportUplineFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return PAGE_NUM;
    }
}
