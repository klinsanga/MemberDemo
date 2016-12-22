package th.co.omc.memberdemo.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import th.co.omc.memberdemo.R;
import th.co.omc.memberdemo.adapter.CommissionMenuAdapter;
import th.co.omc.memberdemo.model.CommissionMenuItem;

/**
 * A simple {@link Fragment} subclass.
 */
public class DayCommissionFragment extends Fragment implements CommissionMenuAdapter.ClickListener{

    FragmentManager manager;
    Fragment currentFragment;
    FragmentTransaction transaction;
    CommissionMenuAdapter adapter;
    List<CommissionMenuItem> commissionMenuItemList = new ArrayList<CommissionMenuItem>();

    @Bind(R.id.recyclerview_commission) RecyclerView recyclerView;
    public DayCommissionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_commission, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        initWidget();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void initWidget() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new CommissionMenuAdapter(getActivity(), commissionMenuItemList);
        adapter.setClickListener(this);

        if (commissionMenuItemList.size() > 0) {
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        } else {
            prepareMenuItem();
        }
    }

    private void prepareMenuItem() {

        commissionMenuItemList.add(new CommissionMenuItem(R.string.com_menu_fast));
        commissionMenuItemList.add(new CommissionMenuItem(R.string.com_menu_ws));
        commissionMenuItemList.add(new CommissionMenuItem(R.string.com_menu_matching));
        commissionMenuItemList.add(new CommissionMenuItem(R.string.com_menu_keyBonus));
        commissionMenuItemList.add(new CommissionMenuItem(R.string.com_menu_incom_total));

        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setMenuClickListener(View view, int position) {
        manager = getFragmentManager();
        transaction = manager.beginTransaction();
        currentFragment = getFragmentManager().findFragmentById(R.id.content_frame);
        CommissionMenuItem item = commissionMenuItemList.get(position);
        switch (item.getName()) {
            case R.string.com_menu_fast:
                if (currentFragment instanceof FaststartFragment) {

                } else {
                    transaction.replace(R.id.content_frame, new FaststartFragment(), "FaststartFragment").addToBackStack(null).commit();
                }
                break;
            case R.string.com_menu_ws:
                if (currentFragment instanceof WeakStrongFragment) {

                } else {
                    transaction.replace(R.id.content_frame, new WeakStrongFragment(), "WeakStrongFragment").addToBackStack(null).commit();
                }
                break;
            case R.string.com_menu_matching :
                if (currentFragment instanceof MatchingFragment) {

                } else {
                    transaction.replace(R.id.content_frame, new MatchingFragment(), "MatchingFragment").addToBackStack(null).commit();
                }
                break;
            case R.string.com_menu_keyBonus :
                if (currentFragment instanceof KeybonusFragment) {

                } else {
                    transaction.replace(R.id.content_frame, new KeybonusFragment(), "KeybonusFragment").addToBackStack(null).commit();
                }
                break;
            case R.string.com_menu_incom_total:
                if (currentFragment instanceof TotalIncomeFragment) {

                } else {
                    transaction.replace(R.id.content_frame, new TotalIncomeFragment(), "TotalIncomeFragment").addToBackStack(null).commit();
                }
                break;
            default: break;
        }
    }
}
