package th.co.omc.memberdemo.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.Bind;
import butterknife.ButterKnife;
import th.co.omc.memberdemo.R;
import th.co.omc.memberdemo.activity.MainActivity;
import th.co.omc.memberdemo.adapter.OrderFragmentAdapter;
import th.co.omc.memberdemo.utils.MyApplication;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderMainFragment extends Fragment {

    OrderFragmentAdapter adapter;

    @Bind(R.id.tab_layout) TabLayout tabLayout;
    @Bind(R.id.pagerOrder) ViewPager viewPager;
    public OrderMainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order_main, container, false);
        ButterKnife.bind(this, view);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.order_history));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.order_to_member));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        adapter = new OrderFragmentAdapter(getFragmentManager(), tabLayout.getTabCount());
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

        viewPager.setCurrentItem(checkMcode());
        return view;
    }

    private int checkMcode() {
        try {
            MainActivity mainActivity = (MainActivity) getActivity();
            String m = mainActivity.getMcode();
            if (!m.equals(MyApplication.getInstance().getPrefManager().getUser().getMemberCode())) {
                return 1;
            }
        } catch (Exception e) {
            Log.e("Check mcode", e.toString());
        }
        return 0;
    }
}
