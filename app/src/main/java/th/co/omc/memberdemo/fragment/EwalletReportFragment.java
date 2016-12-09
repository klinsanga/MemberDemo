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
import th.co.omc.memberdemo.adapter.EwalletReportAdapter;
import th.co.omc.memberdemo.customview.CustomTextview;
import th.co.omc.memberdemo.model.EwalletReportItem;
import th.co.omc.memberdemo.parse.ParseEwallet;
import th.co.omc.memberdemo.utils.EndPoints;
import th.co.omc.memberdemo.utils.setCalendarToMultipleLanguage;

/**
 * A simple {@link Fragment} subclass.
 */
public class EwalletReportFragment extends Fragment implements View.OnClickListener {

    ParseEwallet parseEwallet;

    private int month, year;
    private Calendar _calendar;
    private String parameterMonth;

    EwalletReportAdapter adapter;
    List<EwalletReportItem> ewalletReportItemList = new ArrayList<EwalletReportItem>();

    @Bind(R.id.report_btn_next) LinearLayout next;
    @Bind(R.id.report_btn_previous) LinearLayout previous;
    @Bind(R.id.report_text_month) CustomTextview txtMonth;
    @Bind(R.id.reportProgressBarLayout) RelativeLayout progressbar;
    @Bind(R.id.recyclerview_report) RecyclerView recyclerView;
    @Bind(R.id.not_found) RelativeLayout no_data;
    public EwalletReportFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ewallet_report, container, false);
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
        adapter = new EwalletReportAdapter(getActivity(), ewalletReportItemList);
        next.setOnClickListener(this);
        previous.setOnClickListener(this);
    }

    private void prepareItem() {
        ewalletReportItemList.clear();
        adapter = new EwalletReportAdapter(getActivity(), ewalletReportItemList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        no_data.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        new DoParsTask().execute(EndPoints.API_REPORT_INOUT);
    }

    private void initCalendar() {
        _calendar = Calendar.getInstance();
        month = _calendar.get(Calendar.MONTH) + 1;
        year = _calendar.get(Calendar.YEAR);
        txtMonth.setText(new setCalendarToMultipleLanguage().calendarToString(getActivity(), month) + " " + year);

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

        txtMonth.setText(new setCalendarToMultipleLanguage().calendarToString(getActivity(), month) + " " + year);
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
        txtMonth.setText(new setCalendarToMultipleLanguage().calendarToString(getActivity(), month) + " " + year);
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
            case R.id.report_btn_next :
                nextMonth();
                break;
            case R.id.report_btn_previous :
                previousMonth();
                break;
            default: break;
        }
    }

    public class DoParsTask extends AsyncTask<String, Void, Void> implements ParseEwallet.EwalletReportCallback {

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
            parseEwallet.postReportRequest(this);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            recyclerView.setVisibility(View.VISIBLE);
        }

        @Override
        public void onFailed(String result) {
            ewalletReportItemList.clear();
            adapter = new EwalletReportAdapter(getActivity(), ewalletReportItemList);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            recyclerView.setVisibility(View.GONE);
            no_data.setVisibility(View.VISIBLE);
            progressbar.setVisibility(View.GONE);
        }

        @Override
        public void onParseReportSuccess(List<EwalletReportItem> list) {
            adapter = new EwalletReportAdapter(getActivity(), list);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            progressbar.setVisibility(View.GONE);
        }
    }
}
