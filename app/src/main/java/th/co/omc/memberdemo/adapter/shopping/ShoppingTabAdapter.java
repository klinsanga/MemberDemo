package th.co.omc.memberdemo.adapter.shopping;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import th.co.omc.memberdemo.fragment.shopping.ProductFragment;
import th.co.omc.memberdemo.fragment.shopping.PromotionFragment;

/**
 * Created by teera-s on 10/14/2016 AD.
 */

public class ShoppingTabAdapter extends FragmentStatePagerAdapter {
    private final int PAGE_NUM = 2;

    int mNumOfTabs;

    public ShoppingTabAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0) {
            return new ProductFragment();
        }else if(position == 1) {
            return new PromotionFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return PAGE_NUM;
    }
}
