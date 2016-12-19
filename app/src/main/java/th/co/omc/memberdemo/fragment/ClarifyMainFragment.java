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
import th.co.omc.memberdemo.adapter.ClarifyFragmentAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClarifyMainFragment extends Fragment {

    ClarifyFragmentAdapter adapter;

    @Bind(R.id.tab_layout) TabLayout tabLayout;
    @Bind(R.id.pagerClarify) ViewPager viewPager;
    public ClarifyMainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_clarify_main, container, false);
        ButterKnife.bind(this, view);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.clarify_history));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.clarify_recieve));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        adapter = new ClarifyFragmentAdapter(getFragmentManager(), tabLayout.getTabCount());
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
        return view;
    }

}
