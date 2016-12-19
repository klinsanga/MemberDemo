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
import th.co.omc.memberdemo.adapter.FragementAdapter;
import th.co.omc.memberdemo.customview.CustomTabLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class DiagramFragment extends Fragment {

    FragementAdapter adapter;

    @Bind(R.id.pagerDiagram) ViewPager viewPager;
    @Bind(R.id.tab_layout) CustomTabLayout tabLayout;

    public DiagramFragment() {
        // Required empty public constructor
    }

    public static DiagramFragment newInstance() {
        DiagramFragment fragmentAction = new DiagramFragment();

        return fragmentAction;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_diagram, container, false);
        ButterKnife.bind(this, view);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.diagram_tab1));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.diagram_tab2));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.diagram_tab3));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        adapter = new FragementAdapter(getFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
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
