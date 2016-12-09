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
import th.co.omc.memberdemo.adapter.CommissionFragmentAdapter;
import th.co.omc.memberdemo.customview.CustomTabLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class CommissionTabFragment extends Fragment {


    CommissionFragmentAdapter adapter;

    @Bind(R.id.pagerCom) ViewPager viewPager;
    @Bind(R.id.com_tab_layout) CustomTabLayout tabLayout;
    public CommissionTabFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_commission_tab, container, false);
        ButterKnife.bind(this, view);

        tabLayout.addTab(tabLayout.newTab().setText(R.string.com_tab1));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.com_tab2));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.com_tab3));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        adapter = new CommissionFragmentAdapter(getFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition(), true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        try {
            String argument = getArguments().getString("FragmentActivity");
            if (argument.equals("month")) {
                viewPager.setCurrentItem(3, true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }
}
