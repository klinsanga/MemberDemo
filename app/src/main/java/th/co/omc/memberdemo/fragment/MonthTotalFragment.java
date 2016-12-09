package th.co.omc.memberdemo.fragment;


import android.os.AsyncTask;
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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import th.co.omc.memberdemo.R;
import th.co.omc.memberdemo.adapter.TotalMonthAdapter;
import th.co.omc.memberdemo.customview.CustomTextview;
import th.co.omc.memberdemo.model.TotalMonthItem;
import th.co.omc.memberdemo.parse.ParseCommission;
import th.co.omc.memberdemo.utils.EndPoints;
import th.co.omc.memberdemo.utils.setCalendarToMultipleLanguage;

/**
 * A simple {@link Fragment} subclass.
 */
public class MonthTotalFragment extends Fragment implements View.OnClickListener {

    ParseCommission parseCommission;
    FragmentManager manager;
    FragmentTransaction transaction;

    private int month, year;
    private Calendar _calendar;
    private String parameterMonth;

    TotalMonthAdapter adapter;
    List<TotalMonthItem> totalMonthItemList = new ArrayList<TotalMonthItem>();

    @Bind(R.id.total_month_income_back_btn) RelativeLayout back;
    @Bind(R.id.total_month_income_btn_next) LinearLayout next;
    @Bind(R.id.total_month_income_btn_previous) LinearLayout previous;
    @Bind(R.id.total_month_income_text_month) CustomTextview txtviewMonth;
    @Bind(R.id.recyclerview_total_month_income) RecyclerView recyclerView;
    @Bind(R.id.total_month_income_ProgressBarLayout) RelativeLayout progressbar;
    @Bind(R.id.not_found) RelativeLayout no_data;
    public MonthTotalFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_month_total, container, false);
        ButterKnife.bind(this, view);
        initWidget();
        initCalendar();
        return view;
    }

    private void initWidget() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new TotalMonthAdapter(getActivity(), totalMonthItemList);
        back.setOnClickListener(this);
        next.setOnClickListener(this);
        previous.setOnClickListener(this);
    }

    private void prepareItem() {
        totalMonthItemList.clear();
        adapter = new TotalMonthAdapter(getActivity(), totalMonthItemList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        no_data.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        new DoParsTask().execute(EndPoints.API_TOTAL_MONTH);
    }

    private void initCalendar() {
        _calendar = Calendar.getInstance();
        month = _calendar.get(Calendar.MONTH) + 1;
        year = _calendar.get(Calendar.YEAR);
        txtviewMonth.setText(new setCalendarToMultipleLanguage().calendarToString(getActivity(), month) + " " + year);

        if (month < 10) {
            parameterMonth = year + "-0" + month;
        } else {
            parameterMonth = year + "-" + month;
        }
        prepareItem();
    }

    private void nextMonth() {
        if (month > 11) {
            month = 1;
            year++;
        } else {
            month++;
        }

        txtviewMonth.setText(new setCalendarToMultipleLanguage().calendarToString(getActivity(), month) + " " + year);
        if (month < 10) {
            parameterMonth = year + "-0" + month;
        } else {
            parameterMonth = year + "-" + month;
        }
        prepareItem();
    }

    private void previousMonth() {
        if (month <= 1) {
            month = 12;
            year--;
        } else {
            month--;
        }
        txtviewMonth.setText(new setCalendarToMultipleLanguage().calendarToString(getActivity(), month) + " " + year);
        if (month < 10) {
            parameterMonth = year + "-0" + month;
        } else {
            parameterMonth = year + "-" + month;
        }
        prepareItem();
    }

    @Override
    public void onClick(View view) {
        manager = getFragmentManager();
        transaction = manager.beginTransaction();
        switch (view.getId()) {
            case R.id.total_month_income_back_btn :
                CommissionTabFragment tabFragment = new CommissionTabFragment();
                Bundle bundle=new Bundle();
                bundle.putString("FragmentActivity", "month");
                tabFragment.setArguments(bundle);
                transaction.replace(R.id.content_frame, tabFragment, "CommissionTabFragment").addToBackStack(null).commit();
                break;
            case R.id.total_month_income_btn_next:
                nextMonth();
                break;
            case R.id.total_month_income_btn_previous :
                previousMonth();
                break;
            default:break;
        }
    }

    public class DoParsTask extends AsyncTask<String, Void, Void> implements ParseCommission.totalIncomeMonthCallback {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressbar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(String... params) {
            for (String urlVal : params) {
                parseCommission = new ParseCommission(urlVal, parameterMonth);
            }
            parseCommission.postIncomeMonthRequest(this);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            recyclerView.setVisibility(View.VISIBLE);
        }

        @Override
        public void onFailed(String result) {
            totalMonthItemList.clear();
            adapter = new TotalMonthAdapter(getActivity(), totalMonthItemList);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            recyclerView.setVisibility(View.GONE);
            no_data.setVisibility(View.VISIBLE);
            progressbar.setVisibility(View.GONE);
        }

        @Override
        public void onParseIncomeMonthSuccess(List<TotalMonthItem> list) {
            adapter = new TotalMonthAdapter(getActivity(), list);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            progressbar.setVisibility(View.GONE);
        }
    }
}
