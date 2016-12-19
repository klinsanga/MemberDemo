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
import th.co.omc.memberdemo.adapter.TotalAdapter;
import th.co.omc.memberdemo.customview.CustomTextview;
import th.co.omc.memberdemo.model.FaststartItem;
import th.co.omc.memberdemo.model.TotalItem;
import th.co.omc.memberdemo.parse.ParseCommission;
import th.co.omc.memberdemo.utils.AnimateButton;
import th.co.omc.memberdemo.utils.EndPoints;
import th.co.omc.memberdemo.utils.setCalendarToMultipleLanguage;

/**
 * A simple {@link Fragment} subclass.
 */
public class TotalIncomeFragment extends Fragment implements View.OnClickListener {

    FragmentManager manager;
    FragmentTransaction transaction;

    private int _month, _year;
    private Calendar _calendar;
    private String _parameterMonth;

    ParseCommission parseCommission;
    TotalAdapter adapter;
    List<TotalItem> totalItemList = new ArrayList<TotalItem>();

    @Bind(R.id.total_income_btn_next) LinearLayout next;
    @Bind(R.id.total_income_back_btn) RelativeLayout back;
    @Bind(R.id.total_income_btn_previous) LinearLayout previous;
    @Bind(R.id.total_income_text_month) CustomTextview currentMonth;
    @Bind(R.id.recyclerview_total_income) RecyclerView recyclerView_total;
    @Bind(R.id.TotalIncomeProgressbarLayout) RelativeLayout progressBar;
    @Bind(R.id.not_found) RelativeLayout no_data;
    public TotalIncomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_total_income, container, false);
        ButterKnife.bind(this, view);
        initWidget();
        initCalendar();
        return view;
    }

    @Override
    public void onClick(View view) {
        manager = getFragmentManager();
        transaction = manager.beginTransaction();
        switch (view.getId()) {
            case R.id.total_income_back_btn :
                back.startAnimation(new AnimateButton().animbutton());
                transaction.replace(R.id.content_frame, new CommissionTabFragment(), "CommissionTabFragment").addToBackStack(null).commit();
                break;
            case R.id.total_income_btn_next :
                next.startAnimation(new AnimateButton().animbutton());
                nextMonth();
                break;
            case R.id.total_income_btn_previous :
                previous.startAnimation(new AnimateButton().animbutton());
                previousMonth();
                break;
            default:break;
        }
    }

    private void initWidget() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView_total.setLayoutManager(layoutManager);
        recyclerView_total.setHasFixedSize(true);
        recyclerView_total.setItemAnimator(new DefaultItemAnimator());
        back.setOnClickListener(this);
        next.setOnClickListener(this);
        previous.setOnClickListener(this);
        prepareItem();
    }

    private void prepareItem() {
        totalItemList.clear();
        adapter = new TotalAdapter(getActivity(), totalItemList);
        recyclerView_total.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        recyclerView_total.setVisibility(View.GONE);
        no_data.setVisibility(View.GONE);
        new DoParsTask().execute(EndPoints.API_TOTAL_DAY);
    }

    private void initCalendar() {
        _calendar = Calendar.getInstance();
        _month = _calendar.get(Calendar.MONTH) + 1;
        _year = _calendar.get(Calendar.YEAR);
        currentMonth.setText(new setCalendarToMultipleLanguage().calendarToString(getActivity(), _month) + " " + _year);

        if (_month < 10) {
            _parameterMonth = _year + "-0" + _month;
        } else {
            _parameterMonth = _year + "-" + _month;
        }
        prepareItem();
    }

    private void nextMonth() {
        if (_month > 11) {
            _month = 1;
            _year++;
        } else {
            _month++;
        }

        currentMonth.setText(new setCalendarToMultipleLanguage().calendarToString(getActivity(), _month) + " " + _year);
        if (_month < 10) {
            _parameterMonth = _year + "-0" + _month;
        } else {
            _parameterMonth = _year + "-" + _month;
        }
        prepareItem();
    }

    private void previousMonth() {
        if (_month <= 1) {
            _month = 12;
            _year--;
        } else {
            _month--;
        }

        currentMonth.setText(new setCalendarToMultipleLanguage().calendarToString(getActivity(), _month) + " " + _year);
        if (_month < 10) {
            _parameterMonth = _year + "-0" + _month;
        } else {
            _parameterMonth = _year + "-" + _month;
        }
        prepareItem();
    }

    public class DoParsTask extends AsyncTask<String, Void, Void> implements ParseCommission.VolleyCallback {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(String... params) {
            for (String urlVal : params) {
                parseCommission = new ParseCommission(urlVal, _parameterMonth);
            }
            parseCommission.postTotalIncomeRequest(this);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            recyclerView_total.setVisibility(View.VISIBLE);
        }

        @Override
        public void onFailed(String result) {
            totalItemList.clear();
            adapter = new TotalAdapter(getActivity(), totalItemList);
            recyclerView_total.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            recyclerView_total.setVisibility(View.GONE);
            no_data.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }

        @Override
        public void onParseFaststartSuccess(List<FaststartItem> list) {

        }

        @Override
        public void onParseTotalIncomeSuccess(List<TotalItem> list) {
            adapter = new TotalAdapter(getActivity(), list);
            recyclerView_total.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            progressBar.setVisibility(View.GONE);
        }
    }
}
