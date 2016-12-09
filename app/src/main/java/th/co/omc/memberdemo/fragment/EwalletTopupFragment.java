package th.co.omc.memberdemo.fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import th.co.omc.memberdemo.adapter.EwalletTopupAdapter;
import th.co.omc.memberdemo.customview.CustomTextview;
import th.co.omc.memberdemo.model.EwalletTopupItem;
import th.co.omc.memberdemo.parse.ParseEwallet;
import th.co.omc.memberdemo.utils.EndPoints;
import th.co.omc.memberdemo.utils.setCalendarToMultipleLanguage;

/**
 * A simple {@link Fragment} subclass.
 */
public class EwalletTopupFragment extends Fragment implements View.OnClickListener {


    ParseEwallet parseEwallet;

    private int month, year;
    private Calendar _calendar;
    private String parameterMonth;

    EwalletTopupAdapter adapter;
    List<EwalletTopupItem> ewalletTopupItemList = new ArrayList<EwalletTopupItem>();

    @Bind(R.id.not_found) RelativeLayout no_data;
    @Bind(R.id.topup_btn_next) LinearLayout next;
    @Bind(R.id.topup_btn_previous) LinearLayout previous;
    @Bind(R.id.topup_text_month) CustomTextview textviewMonth;
    @Bind(R.id.topupProgressBarLayout) RelativeLayout progressbar;
    @Bind(R.id.recyclerview_topup) RecyclerView recyclerView;
    public EwalletTopupFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ewallet_topup, container, false);
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
        adapter = new EwalletTopupAdapter(getActivity(), ewalletTopupItemList);
        next.setOnClickListener(this);
        previous.setOnClickListener(this);
    }

    private void prepareItem() {
        ewalletTopupItemList.clear();
        adapter = new EwalletTopupAdapter(getActivity(), ewalletTopupItemList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        no_data.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        new DoParsTask().execute(EndPoints.API_TOPUP);
    }

    private void initCalendar() {
        _calendar = Calendar.getInstance();
        month = _calendar.get(Calendar.MONTH) + 1;
        year = _calendar.get(Calendar.YEAR);
        textviewMonth.setText(new setCalendarToMultipleLanguage().calendarToString(getActivity(), month) + " " + year);

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

        textviewMonth.setText(new setCalendarToMultipleLanguage().calendarToString(getActivity(), month) + " " + year);
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
        textviewMonth.setText(new setCalendarToMultipleLanguage().calendarToString(getActivity(), month) + " " + year);
        if (month < 10) {
            parameterMonth = year + "-0" + month;
        } else {
            parameterMonth = year + "-" + month;
        }
        prepareItem();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.topup_btn_next:
                nextMonth();
                break;
            case R.id.topup_btn_previous :
                previousMonth();
                break;
            default:break;
        }
    }

    public class DoParsTask extends AsyncTask<String, Void, Void> implements ParseEwallet.EwalletTCallback {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressbar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(String... params) {
            for (String urlVal : params) {
                parseEwallet = new ParseEwallet(urlVal, parameterMonth);
            }
            parseEwallet.postTopupRequest(this);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            recyclerView.setVisibility(View.VISIBLE);
        }

        @Override
        public void onFailed(String result) {
            ewalletTopupItemList.clear();
            adapter = new EwalletTopupAdapter(getActivity(), ewalletTopupItemList);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            recyclerView.setVisibility(View.GONE);
            no_data.setVisibility(View.VISIBLE);
            progressbar.setVisibility(View.GONE);
        }

        @Override
        public void onParseTopupSuccess(List<EwalletTopupItem> list) {
            adapter = new EwalletTopupAdapter(getActivity(), list);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            progressbar.setVisibility(View.GONE);
        }
    }
}
