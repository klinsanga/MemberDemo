package th.co.omc.memberdemo.fragment;


import android.os.Bundle;
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
public class MonthCommissionFragment extends Fragment implements CommissionMenuAdapter.ClickListener {

    FragmentManager manager;
    Fragment currentFragment;
    FragmentTransaction transaction;
    CommissionMenuAdapter adapter;
    List<CommissionMenuItem> commissionMenuItemList = new ArrayList<CommissionMenuItem>();

    @Bind(R.id.recyclerview_month) RecyclerView recyclerView;
    public MonthCommissionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_month_commission, container, false);
        ButterKnife.bind(this, view);
        initWidget();
        return view;
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

        commissionMenuItemList.add(new CommissionMenuItem(R.string.com_menu_all_sale));
        commissionMenuItemList.add(new CommissionMenuItem(R.string.com_menu_unilevel));
        commissionMenuItemList.add(new CommissionMenuItem(R.string.com_menu_month_total));

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
            case R.string.com_menu_all_sale:
                if (currentFragment instanceof AllsaleFragment) {

                } else {
                    transaction.replace(R.id.content_frame, new AllsaleFragment(), "AllsaleFragment").addToBackStack(null).commit();
                }
                break;
            case R.string.com_menu_unilevel:
                if (currentFragment instanceof UnilevelFragment) {

                } else {
                    transaction.replace(R.id.content_frame, new UnilevelFragment(), "UnilevelFragment").addToBackStack(null).commit();
                }
                break;
            case R.string.com_menu_month_total :
                if (currentFragment instanceof MonthTotalFragment) {

                } else {
                    transaction.replace(R.id.content_frame, new MonthTotalFragment(), "MonthTotalFragment").addToBackStack(null).commit();
                }
                break;

            default: break;
        }
    }
}
