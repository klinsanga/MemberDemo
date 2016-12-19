package th.co.omc.memberdemo.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.Bind;
import butterknife.ButterKnife;
import th.co.omc.memberdemo.R;
import th.co.omc.memberdemo.adapter.EwalletFragmentAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class EwalletFragment extends Fragment {

    EwalletFragmentAdapter fragmentAdapter;

    @Bind(R.id.pagerEwallet) ViewPager pager;
    @Bind(R.id.ewallet_tab_layout) TabLayout tab;
    public EwalletFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ewallet, container, false);
        ButterKnife.bind(this, view);
        tab.addTab(tab.newTab().setText(R.string.tab_topup));
        tab.addTab(tab.newTab().setText(R.string.tab_report));
        tab.setTabGravity(TabLayout.GRAVITY_FILL);

        fragmentAdapter = new EwalletFragmentAdapter(getFragmentManager(), tab.getTabCount());
        pager.setAdapter(fragmentAdapter);
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab));

        tab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition(), true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        return view;
    }

}
